package cafeSubscription.coffee.domain.user.service;

import cafeSubscription.coffee.domain.user.UserRole;
import cafeSubscription.coffee.domain.user.dto.RegisterDTO;
import cafeSubscription.coffee.domain.user.entity.Business;
import cafeSubscription.coffee.domain.user.entity.User;
import cafeSubscription.coffee.domain.user.mapper.BusinessRegisterMapper;
import cafeSubscription.coffee.domain.user.mapper.RegisterMapper;
import cafeSubscription.coffee.domain.user.repository.BusinessRepository;
import cafeSubscription.coffee.domain.user.repository.RegisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
            throw new IllegalStateException("이미 사용 중인 이메일입니다.");
        }

        // 닉네임 중복 체크
        if (registerRepository.findByUsername(registerDTO.getUsername()).isPresent()) {
            throw new IllegalStateException("이미 사용 중인 닉네임입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(registerDTO.getPassword());

        // 사용자 엔티티 생성
        User user = RegisterMapper.toEntity(registerDTO, encodedPassword);


        //사장님으로 가입인지 판별
        if (registerDTO.getBusinessNumber() != null &&
                registerDTO.getBusinessName() != null &&
                registerDTO.getBankAccount() != null &&
                registerDTO.getOpeningDate() != null) {
            user.setRole(UserRole.owner);

        } else {
            user.setRole(UserRole.customer);

        }

        // 사용자 저장
        User savedUser = registerRepository.save(user);


        // 비즈니스 사용자일 경우 비즈니스 정보 저장
        if (user.getRole() == UserRole.owner) {
            Business business = BusinessRegisterMapper.toBusinessEntity(registerDTO, savedUser);
            businessRepository.save(business);

        }

        return savedUser;

        }

    }

