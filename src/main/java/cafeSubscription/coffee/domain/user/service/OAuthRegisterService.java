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

        // 이메일 중복 체크
        if (registerRepository.findByEmail(oauthRegisterDTO.getEmail()).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_USER_EMAIL);
        }

        // 닉네임 중복 체크
        if (registerRepository.findByNickName(oauthRegisterDTO.getNickName()).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_USER_NICKNAME);
        }

        // 사용자 엔티티 생성
        User user = OAuthRegisterMapper.toOAuthEntity(oauthRegisterDTO);


        if (user.getNickName() == null || user.getNickName().isEmpty()) {
            throw new CustomException(ErrorCode.INVALID_USER_DATA);
        }


        // 비즈니스 사용자일 경우 역할 변경 및 비즈니스 정보 추가 저장
        if (oauthRegisterDTO.getBusinessNumber() != null &&
                oauthRegisterDTO.getBName() != null &&
                oauthRegisterDTO.getBankAccount() != null &&
                oauthRegisterDTO.getOpeningDate() != null) {
            user.setRole(UserRole.owner);
        }else{
            user.setRole(UserRole.customer);
        }

        User savedUser = registerRepository.save(user);

        if (user.getRole() == UserRole.owner) {
            Business business = OAuthBusinessRegisterMapper.toOAuthBusinessEntity(oauthRegisterDTO, savedUser);
            businessRepository.save(business);

        }

        // 사용자 저장
        return savedUser;
    }
}

