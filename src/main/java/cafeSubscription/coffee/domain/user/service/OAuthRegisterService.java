package cafeSubscription.coffee.domain.user.service;

import cafeSubscription.coffee.domain.user.UserRole;
import cafeSubscription.coffee.domain.user.dto.OAuthRegisterDTO;
import cafeSubscription.coffee.domain.user.entity.Business;
import cafeSubscription.coffee.domain.user.entity.User;
import cafeSubscription.coffee.domain.user.mapper.OAuthBusinessRegisterMapper;
import cafeSubscription.coffee.domain.user.mapper.OAuthRegisterMapper;
import cafeSubscription.coffee.domain.user.repository.BusinessRepository;
import cafeSubscription.coffee.domain.user.repository.RegisterRepository;
import cafeSubscription.coffee.global.exception.CustomException;
import cafeSubscription.coffee.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthRegisterService {

    private final RegisterRepository registerRepository;
    private final BusinessRepository businessRepository;

    @Transactional
    public User registerOAuthUser(OAuthRegisterDTO oauthRegisterDTO) {

        // 사용자 존재 여부 확인 (oauthProviderId 기준)
        User existingUser = registerRepository.findByOauthProviderId(oauthRegisterDTO.getOauthProviderId()).orElse(null);

        if (existingUser != null) {
            return existingUser; // 바로 로그인 처리
        }

        // 사용자 엔티티 생성
        User user = OAuthRegisterMapper.toOAuthEntity(oauthRegisterDTO);

        if (user.getNickName() == null || user.getNickName().isEmpty()) {
            throw new CustomException(ErrorCode.INVALID_USER_DATA);
        }


        // 비즈니스 사용자 정보유무 확인
        if (isBusinessUser(oauthRegisterDTO)) {
            user.setRole(UserRole.owner);
            User savedUser = registerRepository.save(user);

            // 비즈니스 정보 저장
            Business business = OAuthBusinessRegisterMapper.toOAuthBusinessEntity(oauthRegisterDTO, savedUser);
            businessRepository.save(business);

            return savedUser;
        } else {

            user.setRole(UserRole.customer);
            return registerRepository.save(user);
        }
    }

    // 비즈니스 사용자 정보가 있는지 여부 체크 메서드
    private boolean isBusinessUser(OAuthRegisterDTO oauthRegisterDTO) {
        return oauthRegisterDTO.getBusinessNumber() != null &&
                oauthRegisterDTO.getBName() != null &&
                oauthRegisterDTO.getBankAccount() != null &&
                oauthRegisterDTO.getOpeningDate() != null;
    }
}

