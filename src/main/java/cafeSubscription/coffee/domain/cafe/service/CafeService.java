package cafeSubscription.coffee.domain.cafe.service;

import cafeSubscription.coffee.domain.cafe.dto.CafeDTO;
import cafeSubscription.coffee.domain.cafe.dto.request.SearchAttributes;
import cafeSubscription.coffee.domain.cafe.entity.Cafe;
import cafeSubscription.coffee.domain.cafe.repository.CafeRepository;
import cafeSubscription.coffee.domain.cafe.search.SearchType;
import cafeSubscription.coffee.domain.cafe.search.SearchTypeExecutor;
import cafeSubscription.coffee.domain.diary.repositoty.DiaryRepository;
import cafeSubscription.coffee.global.config.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CafeService {

    // 카페 거리순 조회
    private final SearchTypeExecutor searchTypeExecutor;
    private final CafeRepository cafeRepository;
    private final DiaryRepository diaryRepository;

    public Cafe save(Cafe cafe) {
        return cafeRepository.save(cafe);
    }

    // 카페 전체 조회
    public List<CafeDTO> findAll() {
        return cafeRepository.findAll()
                .stream()
                .map(CafeDTO::fromEntity) // DTO로 변환
                .collect(Collectors.toList());
    }

    // 특정 ID로 카페 조회
    public CafeDTO findById(Long cafeId) {
        Cafe cafe = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new IllegalArgumentException("Cafe not found"));
        return CafeDTO.fromEntity(cafe); // DTO로 변환
    }

    public Cafe update(long cafeId, Cafe updatedCafe) {
        updatedCafe.setCafeId(cafeId);
        return cafeRepository.save(updatedCafe);
        //TODO - 예외처리
    }

    @Transactional
    public void delete(long cafeId) {
        diaryRepository.deleteByCafeCafeId(cafeId); // 관련 `Diary` 레코드 삭제
        cafeRepository.deleteById(cafeId);
        //TODO - 예외처리
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