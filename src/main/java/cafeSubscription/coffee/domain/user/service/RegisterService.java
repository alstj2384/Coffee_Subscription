package cafeSubscription.coffee.domain.user.service;

import cafeSubscription.coffee.domain.user.UserRole;
import cafeSubscription.coffee.domain.user.dto.RegisterDTO;
import cafeSubscription.coffee.domain.user.entity.Business;
import cafeSubscription.coffee.domain.user.entity.User;
import cafeSubscription.coffee.domain.user.mapper.BusinessRegisterMapper;
import cafeSubscription.coffee.domain.user.mapper.RegisterMapper;
import cafeSubscription.coffee.domain.user.repository.BusinessRepository;
import cafeSubscription.coffee.domain.user.repository.RegisterRepository;
import cafeSubscription.coffee.global.config.CustomException;
import cafeSubscription.coffee.global.config.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterService {

    private final RegisterRepository registerRepository;
    private final BusinessRepository businessRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User register(RegisterDTO registerDTO) {
        // 이메일 중복 체크
        if (registerRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_USER_EMAIL);
        }

        // 아이디 중복 체크
        if (registerRepository.findByUsername(registerDTO.getUsername()).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_USER_USERNAME);
        }

        // 닉네임 중복 체크
        if (registerRepository.findByNickName(registerDTO.getNickName()).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_USER_NICKNAME);
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(registerDTO.getPassword());
        log.info("암호화된 비밀번호: {}", encodedPassword);

        // 사용자 엔티티 생성
        User user = RegisterMapper.toEntity(registerDTO, encodedPassword);
        assignUserRole(user, registerDTO);

        // 사용자 저장
        User savedUser = registerRepository.save(user);
        log.info("사용자 저장 성공: {}", savedUser);

        // 비즈니스 사용자일 경우 비즈니스 정보 저장
        if (user.getRole() == UserRole.owner) {
            Business business = BusinessRegisterMapper.toBusinessEntity(registerDTO, savedUser);
            businessRepository.save(business);
            log.info("비즈니스 사용자 저장 성공: {}", business);
        }

        return savedUser;
    }

    private void assignUserRole(User user, RegisterDTO registerDTO) {
        if (isBusinessUser(registerDTO)) {
            user.setRole(UserRole.owner);
        } else {
            user.setRole(UserRole.customer);
        }
    }

    private boolean isBusinessUser(RegisterDTO registerDTO) {
        return registerDTO.getBusinessNumber() != null &&
                registerDTO.getBName() != null &&
                registerDTO.getBankAccount() != null &&
                registerDTO.getOpeningDate() != null;
    }
}

