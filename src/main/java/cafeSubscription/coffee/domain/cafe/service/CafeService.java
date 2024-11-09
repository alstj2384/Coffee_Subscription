package cafeSubscription.coffee.domain.cafe.service;

import cafeSubscription.coffee.domain.cafe.DTO.request.SearchAttributes;
import cafeSubscription.coffee.domain.cafe.entity.Cafe;
import cafeSubscription.coffee.domain.cafe.repository.CafeRepository;
import cafeSubscription.coffee.domain.cafe.search.SearchType;
import cafeSubscription.coffee.domain.cafe.search.SearchTypeExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CafeService {

    // 카페 거리순 조회
    private final SearchTypeExecutor searchTypeExecutor;
    private final CafeRepository cafeRepository;

    public Cafe save(Cafe cafe) {
        return cafeRepository.save(cafe);
    }

    public List<Cafe> findAll() {
        return cafeRepository.findAll();
    }

    public Cafe findById(long cafeId) {
        return cafeRepository.findById(cafeId).orElse(null); // 카페가 없으면 null 반환
    }

    public void delete(long cafeId) {
        cafeRepository.deleteById(cafeId);
    }

    public Cafe update(long cafeId, Cafe updatedCafe) {
        updatedCafe.setCafeId(cafeId);
        return cafeRepository.save(updatedCafe);
    }


    // 카페 판매량순 조회 -> 쿠폰 개수로 조회함
    public Page<Cafe> getCafeList(SearchType searchType, SearchAttributes searchAttributes) {
        log.info("[GetCafeList] Requested... ");

        Page<Cafe> cafeList = searchTypeExecutor.executeSearch(searchType, searchAttributes);

        // debug log
        cafeList.getContent().forEach(cafe -> log.debug("[CafeName] {}", cafe.getCafeName()));

        log.info("[GetCafeList] OK!");
        return cafeList;

    }
}