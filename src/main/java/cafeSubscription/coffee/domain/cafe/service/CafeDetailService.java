package cafeSubscription.coffee.domain.cafe.service;


import cafeSubscription.coffee.domain.cafe.DTO.CafeDetailsDTO;
import cafeSubscription.coffee.domain.cafe.entity.Cafe;
import cafeSubscription.coffee.domain.cafe.mapper.CafeDetailsMapper;
import cafeSubscription.coffee.domain.cafe.repository.CafeRepository;
import cafeSubscription.coffee.global.config.CustomException;
import cafeSubscription.coffee.global.config.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CafeDetailService {
    private final CafeRepository cafeRepository;

    public CafeDetailsDTO getCafeDetails(Long cafeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName(); // 현재 로그인된 사용자의 이메일

        Cafe cafe = cafeRepository.findByCafeId(cafeId)
                .orElseThrow(() -> new CustomException(ErrorCode.CAFE_NOT_FOUND));

        // 이메일로 소유자 확인
        if (!cafe.getBusiness().getUser().getEmail().equals(currentUserEmail)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        return CafeDetailsMapper.toCafeDetailDto(cafe);
    }
}
