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
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuthRegisterService {

    private final RegisterRepository registerRepository;
    private final BusinessRepository businessRepository;


    public Optional<User> findUserByOauthProviderId(String oauthProviderId) {
        return registerRepository.findByOauthProviderId(oauthProviderId);
    }

    @Transactional
    public User registerOAuthUser(OAuthRegisterDTO oauthRegisterDTO) {
        log.info("신규 OAuth 사용자 저장 중...");

        // 새로운 사용자 저장
        User user = OAuthRegisterMapper.toOAuthEntity(oauthRegisterDTO);
        User savedUser = registerRepository.save(user);

        log.info("신규 OAuth 사용자 저장 성공: {}", savedUser);
        return savedUser;
    }

    @Transactional
    public User updateOAuthUserByProviderId(String oauthProviderId, String nickname, boolean isBusinessUser, OAuthRegisterDTO oauthRegisterDTO) {
        //닉네임 중복 확인 로직 추가
        if(registerRepository.findByNickName(nickname).isPresent()){
            throw new CustomException(ErrorCode.DUPLICATE_USER_NICKNAME);
        }

        User user = registerRepository.findByOauthProviderId(oauthProviderId).orElseThrow(() -> new CustomException(ErrorCode.NON_EXISTENT_USER));

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


