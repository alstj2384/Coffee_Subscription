package cafeSubscription.coffee.domain.user.service;

import cafeSubscription.coffee.domain.user.dto.RegisterDTO;
import cafeSubscription.coffee.domain.user.entity.User;
import cafeSubscription.coffee.domain.user.mapper.RegisterMapper;
import cafeSubscription.coffee.domain.user.repository.RegisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final RegisterRepository registerRepository;
    private final PasswordEncoder passwordEncoder;


    public User join(RegisterDTO registerDTO) {
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

        // 사용자 엔티티 생성 (Mapper 사용)
        User user = RegisterMapper.toEntity(registerDTO, encodedPassword);

        // 사용자 저장
        return registerRepository.save(user);
    }

}
