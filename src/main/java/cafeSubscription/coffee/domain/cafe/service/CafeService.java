package cafeSubscription.coffee.domain.cafe.service;

import cafeSubscription.coffee.domain.cafe.search.SearchTypeExecutor;
import cafeSubscription.coffee.domain.cafe.dto.request.SearchAttributes;
import cafeSubscription.coffee.domain.cafe.search.SearchType;
import cafeSubscription.coffee.domain.cafe.entity.Cafe;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CafeService {

    // 카페 거리순 조회
    private final SearchTypeExecutor searchTypeExecutor;

    // 카페 판매량순 조회 -> 쿠폰 개수로 조회함
    public Page<Cafe> getCafeList(SearchType searchType, SearchAttributes searchAttributes){
        log.info("[GetCafeList] Requested... ");

        Page<Cafe> cafeList = searchTypeExecutor.executeSearch(searchType, searchAttributes);

        // debug log
        cafeList.getContent().forEach(cafe -> log.debug("[CafeName] {}", cafe.getCafeName()));

        log.info("[GetCafeList] OK!");
        return cafeList;

    }




}
