package cafeSubscription.coffee.domain.cafe.controller;

import cafeSubscription.coffee.domain.cafe.DTO.AddCafeRequest;
import cafeSubscription.coffee.domain.cafe.DTO.request.SearchAttributes;
import cafeSubscription.coffee.domain.cafe.entity.Cafe;
import cafeSubscription.coffee.domain.cafe.DTO.UpdateCafeRequest;
import cafeSubscription.coffee.domain.cafe.search.SearchType;
import cafeSubscription.coffee.domain.cafe.service.CafeService;
import cafeSubscription.coffee.domain.operatingHour.OperatingHour;
import cafeSubscription.coffee.domain.operatingHour.service.OperatingHourService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/cafe")
@RequiredArgsConstructor
@Slf4j
public class CafeController {

    private final CafeService cafeService;
    private final OperatingHourService operatingHourService; // OperatingHourService 추가

    //@PreAuthorize("hasRole('owner')") //TODO - 사장권한, 기능 테스트를 위해 권한은 주석처리함
    @PostMapping("/add") // 카페 등록
    public ResponseEntity<Map<String, Object>> addCafe(//@AuthenticationPrincipal User user,
                                                       @RequestBody AddCafeRequest request) {

        //String username = user.getUsername();

        // AddCafeRequest에서 받은 OperatingHour 객체를 먼저 저장
        OperatingHour operatingHour = operatingHourService.save(request.getOperatingHour()); // OperatingHour 저장

        Cafe savedCafe = cafeService.save(request.toEntity(operatingHour)); // 저장된 OperatingHour 객체를 Cafe에 연결해서 저장

        Map<String, Object> data = createCafeData(savedCafe);
        log.info("카페 생성");

        return createResponseEntity(data, "카페 등록에 성공하였습니다.", HttpStatus.CREATED);
    }

    @GetMapping("/{cafeId}") // 카페 단건 조회
    public ResponseEntity<Map<String, Object>> getCafe(@PathVariable long cafeId) {

        Cafe cafe = cafeService.findById(cafeId);

        if (cafe == null) {
            return createResponseEntity(null, "카페를 찾을 수 없습니다.", HttpStatus.NOT_FOUND); // 카페가 없는 경우 404 반환
        }

        Map<String, Object> data = createCafeData(cafe);
        log.info("카페ID : {} 조회", cafeId);
        return createResponseEntity(data, "카페 조회에 성공하였습니다.", HttpStatus.OK);
    }

    @GetMapping("/list") // 카페 목록 조회
    public ResponseEntity<Map<String, Object>> listCafes() {
        List<Cafe> cafes = cafeService.findAll();

        List<Map<String, Object>> cafeList = cafes.stream()
                .map(this::createCafeData) // 각 Cafe 객체에 대해 createCafeData 호출
                .toList();

        log.info("카페 전체 조회");
        return createResponseEntity(cafeList, "카페 조회에 성공하였습니다.", HttpStatus.OK);
    }
    
    //@PreAuthorize("hasRole('ower')")
    @PatchMapping("/{cafeId}") // 카페 수정
    public ResponseEntity<Map<String, Object>> updateCafe(//@AuthenticationPrincipal User user,
                                                          @PathVariable long cafeId,
                                                          @RequestBody UpdateCafeRequest request) {
       // TODO - cafeId를 소유한 userId가 일치한지
        
        // UpdateCafeRequest에서 받은 데이터를 통해 Cafe를 업데이트
        //String username = user.getUsername();
        
        OperatingHour updatedOperatingHour = operatingHourService.update((int) cafeId, request.getOperatingHour()); // 운영시간 업데이트
        Cafe updatedCafe = cafeService.update(cafeId, request.toEntity(updatedOperatingHour));

        log.info("카페ID: {} 카페 수정", cafeId);
        Map<String, Object> data = createCafeData(updatedCafe); // 업데이트된 데이터를 전달

        return createResponseEntity(data, "카페 수정에 성공하였습니다.", HttpStatus.OK);
    }


    //@PreAuthorize("hasRole('ower')")
    @DeleteMapping("/{cafeId}") // 카페 삭제
    public ResponseEntity<Map<String, Object>> deleteCafe(//@AuthenticationPrincipal User user ,
                                                          @PathVariable long cafeId) {

        // TODO - cafeId를 소유한 userId가 일치한지

        //String username = user.getUsername();

        cafeService.delete(cafeId);
        log.info("카페ID: {} 카페 삭제", cafeId);
        return createResponseEntity(null, "카페 삭제에 성공하였습니다.", HttpStatus.OK);
    }


    private ResponseEntity<Map<String, Object>> createResponseEntity(Object data, String message, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("httpStatus", status.value());
        response.put("timeStamp", LocalDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_DATE_TIME));
        response.put("data", data);
        return new ResponseEntity<>(response, status);
    }

    private Map<String, Object> createCafeData(Cafe cafe) {
        Map<String, Object> data = new HashMap<>();
        data.put("cafeId", cafe.getCafeId());
        data.put("cafeName", cafe.getCafeName());
        data.put("operatingHours", cafe.getOperatingHour());
        data.put("description", cafe.getDescription());
        //data.put("location", cafe.getLocation());

        return data;
    }

    @PostMapping("/list")
    public ResponseEntity<Page<Cafe>> getCafeList(@RequestParam(value = "type") SearchType searchType, @RequestBody SearchAttributes searchAttributes) {
        log.info("[GetCafeList] Requested..");

        Page<Cafe> list = cafeService.getCafeList(searchType, searchAttributes);

        log.info("[GetCafeList] OK!");
        return ResponseEntity.ok().body(list);
    }
}
