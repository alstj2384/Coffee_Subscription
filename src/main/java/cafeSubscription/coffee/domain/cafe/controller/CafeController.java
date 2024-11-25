package cafeSubscription.coffee.domain.cafe.controller;

import cafeSubscription.coffee.domain.cafe.dto.AddCafeRequest;
import cafeSubscription.coffee.domain.cafe.dto.CafeDTO;
import cafeSubscription.coffee.domain.cafe.dto.CafeResponseDto;
import cafeSubscription.coffee.domain.cafe.dto.request.SearchAttributes;
import cafeSubscription.coffee.domain.cafe.entity.Cafe;

import cafeSubscription.coffee.domain.cafe.dto.UpdateCafeRequest;
import cafeSubscription.coffee.domain.cafe.search.SearchType;
import cafeSubscription.coffee.domain.cafe.service.CafeService;
import cafeSubscription.coffee.domain.operatingHour.OperatingHour;
import cafeSubscription.coffee.domain.operatingHour.service.OperatingHourService;
import cafeSubscription.coffee.domain.user.entity.Business;
import cafeSubscription.coffee.domain.user.repository.BusinessRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "카페 CRUD", description="카페 등록, 생성, 삭제, 조회, 수정, 리스트 조회 api")
@RestController
@RequestMapping("/api/cafe")
@RequiredArgsConstructor
@Slf4j
public class CafeController {

    private final CafeService cafeService;
    private final OperatingHourService operatingHourService;
    private final BusinessRepository businessRepository;

    @Operation(summary = "카페등록 API", description = "사장님이 카페 등록시 사용")
    @PreAuthorize("hasRole('owner')") // 사장권한
    @PostMapping("/add") // 카페 등록
    public ResponseEntity<Map<String, Object>> addCafe(@AuthenticationPrincipal User user,
                                                       @RequestBody AddCafeRequest request) {
        // 사용자 인증 확인 (null 체크)
        if (user == null) {
            throw new RuntimeException("사용자 인증이 필요합니다.");
        }

        // 인증된 사용자의 userName 얻기
        String username = user.getUsername(); // 사용자 userName이 받아짐
        log.info("인증된 유저ID: {}", username);

        // 비즈니스 조회: userName으로 Business 객체 찾기
        Business business = businessRepository.findByUser_Username(username)
                .orElseThrow(() -> new RuntimeException("비지니스 사용자가 아닌 ID: " + username));

        // 비즈니스 아이디 얻기
        Long businessId = business.getBusinessId();
        log.info("인증된 유저 {}의 비지니스Id: {}", username, businessId);

        // AddCafeRequest에서 받은 OperatingHour 객체를 먼저 저장
        OperatingHour operatingHour = operatingHourService.save(request.getOperatingHour());
        // Cafe 객체 저장: 요청에서 받은 데이터를 기반으로 카페 생성
        Cafe savedCafe = cafeService.save(request.toEntity(operatingHour, business));
        log.info("카페 생성: {}", savedCafe);

        // 확인용 응답 데이터
        Map<String, Object> data = createCafeData(savedCafe);

        // 성공적인 응답 반환
        return createResponseEntity(data, "카페 등록에 성공하였습니다.", HttpStatus.CREATED);
    }

    @Operation(summary = "카페 단건 조회 API", description = "카페 단건 조회 시 사용")
    @GetMapping("/{cafeId}") // 카페 단건 조회
    public ResponseEntity<Map<String, Object>> getCafe(@PathVariable long cafeId) {
        try {
            CafeDTO cafe = CafeDTO.fromEntity(cafeService.findById(cafeId));
            Map<String, Object> data = Map.of("cafe", cafe); // 응답 데이터 구성
            log.info("카페ID : {} 조회", cafeId);
            return createResponseEntity(data, "카페 조회에 성공하였습니다.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.warn("카페ID : {} 조회 실패 - {}", cafeId, e.getMessage());
            return createResponseEntity(null, "카페를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "카페 목록 조회 API", description = "여러 카페 리스트 조회 시 사용")
    @GetMapping("/list") // 카페 목록 조회
    public ResponseEntity<Map<String, Object>> listCafes() {
        List<CafeDTO> cafes = cafeService.findAll(); // DTO 리스트 반환
        Map<String, Object> data = Map.of("cafes", cafes); // 응답 데이터 구성
        log.info("카페 전체 조회");
        return createResponseEntity(data, "카페 조회에 성공하였습니다.", HttpStatus.OK);
    }

    @Operation(summary = "특정 카페 삭제 API", description = "사장님이 자신 카페 삭제시 사용")
    @DeleteMapping("/{cafeId}") // 카페 삭제
    public ResponseEntity<Map<String, Object>> deleteCafe(@AuthenticationPrincipal User user ,
                                                          @PathVariable long cafeId) {

        // TODO - cafeId를 소유한 userId가 일치한지

        String username = user.getUsername();

        cafeService.delete(cafeId);
        log.info("카페ID: {} 카페 삭제", cafeId);
        return createResponseEntity(null, "카페 삭제에 성공하였습니다.", HttpStatus.OK);
    }

    @Operation(summary = "카페데이터 수정 API", description = "카페데이터 수정이 사항시 사용")
    @PutMapping("/{cafeId}") // 카페 수정
    public ResponseEntity<Map<String, Object>> updateCafe(@AuthenticationPrincipal User user,
                                                          @PathVariable long cafeId,
                                                          @RequestBody UpdateCafeRequest request) {
       // TODO - cafeId를 소유한 userId가 일치한지

        // UpdateCafeRequest에서 받은 데이터를 통해 Cafe를 업데이트
        String username = user.getUsername();

        OperatingHour updatedOperatingHour = operatingHourService.update((int) cafeId, request.getOperatingHour()); // 운영시간 업데이트
        Cafe updatedCafe = cafeService.update(cafeId, request.toEntity(updatedOperatingHour));

        log.info("카페ID: {} 카페 수정", cafeId);
        Map<String, Object> data = createCafeData(updatedCafe); // 업데이트된 데이터를 전달

        return createResponseEntity(data, "카페 수정에 성공하였습니다.", HttpStatus.OK);
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

    @PostMapping("/sortedList")
    public ResponseEntity<Page<CafeResponseDto>> getCafeList(@RequestParam(value = "type") SearchType searchType, @RequestBody SearchAttributes searchAttributes) {
        log.info("[GetCafeList] 카페 목록 조회..");

        Page<Cafe> list = cafeService.getCafeList(searchType, searchAttributes);

        Page<CafeResponseDto> dtoList = list.map(CafeResponseDto::toDto);

        log.info("[GetCafeList] 카페 목록 조회 완료!");
        return ResponseEntity.ok().body(dtoList);
    }

    @GetMapping("/best")
    public ResponseEntity<CafeResponseDto> getBestCafe(@RequestParam(value = "type") SearchType searchType, @RequestBody SearchAttributes searchAttributes) {
        log.info("[GetBestCafe] 카페 조회..");

        Page<Cafe> list = cafeService.getCafeList(searchType, searchAttributes);

        CafeResponseDto dto = CafeResponseDto.toDto(list.getContent().get(0));
        log.info("[GetBestCafe] 카페 조회 완료!");
        return ResponseEntity.ok().body(dto);
    }

}
