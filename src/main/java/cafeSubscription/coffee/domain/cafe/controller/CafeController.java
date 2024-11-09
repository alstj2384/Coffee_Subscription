package cafeSubscription.coffee.domain.cafe.controller;

import cafeSubscription.coffee.domain.cafe.service.CafeService;
import cafeSubscription.coffee.domain.cafe.dto.request.SearchAttributes;
import cafeSubscription.coffee.domain.cafe.search.SearchType;
import cafeSubscription.coffee.domain.cafe.entity.Cafe;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cafe")
@Slf4j
public class CafeController {
    private final CafeService cafeService;

    // 판매량, 거리순, 이름순 ...
    @PostMapping("/list")
    public ResponseEntity<Page<Cafe>> getCafeList(@RequestParam(value = "type") SearchType searchType, @RequestBody SearchAttributes searchAttributes){
        log.info("[GetCafeList] Requested..");

        Page<Cafe> list = cafeService.getCafeList(searchType, searchAttributes);

        log.info("[GetCafeList] OK!");
        return ResponseEntity.ok().body(list);
    }
}
