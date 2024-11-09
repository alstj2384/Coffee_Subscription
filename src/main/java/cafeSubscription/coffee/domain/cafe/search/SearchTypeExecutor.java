package cafeSubscription.coffee.domain.cafe.search;

import cafeSubscription.coffee.domain.cafe.dto.request.SearchAttributes;
import cafeSubscription.coffee.domain.cafe.entity.Cafe;
import cafeSubscription.coffee.domain.cafe.repository.CafeRepository;
import cafeSubscription.coffee.domain.coupon.repository.CouponRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
@Slf4j
public class SearchTypeExecutor {
    private final CafeRepository cafeRepository;
    private final CouponRepository couponRepository;
    private final Map<SearchType, Function<SearchAttributes, Page<Cafe>>> executor = new HashMap<>();

    @PostConstruct
    private void init(){
        executor.put(SearchType.sales, this::salesSearch);
        executor.put(SearchType.distance, this::distanceSearch);
    }

    public Page<Cafe> executeSearch(SearchType searchType, SearchAttributes searchAttributes){
        log.info("[ExecuteSearch] type : {}", searchType);
        return executor.getOrDefault(searchType, this::salesSearch).apply(searchAttributes);
    }

    private Page<Cafe> distanceSearch(SearchAttributes searchAttributes){
        log.info("[DistanceSearch] Processing..");
        PageRequest pageable = PageRequest.of(searchAttributes.getPage(), 10);
        return cafeRepository.findCafesByProximity(searchAttributes.getLat(), searchAttributes.getLon(), pageable);
    }

    private Page<Cafe> salesSearch(SearchAttributes searchAttributes){
        log.info("[SalesSearch] Processing..");
        PageRequest pageable = PageRequest.of(searchAttributes.getPage(), 10);
        return couponRepository.findCafesWithMostCoupons(pageable);
    }
}
