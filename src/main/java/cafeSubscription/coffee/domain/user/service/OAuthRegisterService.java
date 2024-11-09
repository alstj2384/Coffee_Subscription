package cafeSubscription.coffee.domain.user.service;

import cafeSubscription.coffee.domain.user.UserRole;
import cafeSubscription.coffee.domain.user.dto.OAuthRegisterDTO;
import cafeSubscription.coffee.domain.user.entity.Business;
import cafeSubscription.coffee.domain.user.entity.User;
import cafeSubscription.coffee.domain.user.mapper.OAuthRegisterMapper;
import cafeSubscription.coffee.domain.user.repository.BusinessRepository;
import cafeSubscription.coffee.domain.user.repository.RegisterRepository;
import cafeSubscription.coffee.global.config.CustomException;
import cafeSubscription.coffee.global.config.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthRegisterService {

    private final RegisterRepository registerRepository;
    private final BusinessRepository businessRepository;

    private static final Logger log = LoggerFactory.getLogger(OAuthRegisterService.class);

    @Transactional
    public User registerOAuthUser(OAuthRegisterDTO oauthRegisterDTO) {
        log.info("OAuth Register DTO: {}", oauthRegisterDTO);

        // 사용자 존재 여부 확인 (oauthProviderId 기준)
        User existingUser = registerRepository.findByOauthProviderId(oauthRegisterDTO.getOauthProviderId()).orElse(null);

        if (existingUser != null) {
            return existingUser; // 기존 사용자 반환
        }

        // 새로운 사용자 저장
        User user = OAuthRegisterMapper.toOAuthEntity(oauthRegisterDTO);
        return registerRepository.save(user);
    }

    @Transactional
    public User updateOAuthUser(Long userId, String nickname, boolean isBusinessUser, OAuthRegisterDTO oauthRegisterDTO) {
        User user = registerRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.NON_EXISTENT_USER));

        // 사용자 정보 업데이트
        user.setNickName(nickname);

        if (isBusinessUser) {
            user.setRole(UserRole.owner);
            Business business = new Business();
            business.setUser(user);
            business.setBusinessNumber(oauthRegisterDTO.getBusinessNumber());
            business.setBName(oauthRegisterDTO.getBName());
            business.setBankAccount(oauthRegisterDTO.getBankAccount());
            business.setOpeningDate(oauthRegisterDTO.getOpeningDate());
            businessRepository.save(business);
        } else {
            user.setRole(UserRole.customer);
        }

        return registerRepository.save(user);
    }
}


