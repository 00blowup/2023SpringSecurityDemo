package hello.securitydemo.service;

import hello.securitydemo.domain.User;
import hello.securitydemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public String join(String userName, String password) {
        // userName 중복 체크
        userRepository.findByUserName(userName)
                .ifPresent(user -> {
                    throw new RuntimeException(userName + " already exists!");
                });
        // 이미 있는 유저명일 경우 예외 발생

        // 예외가 없었다면 저장 수행
        userRepository.save(
                User.builder()
                        .userName(userName)
                        .password(encoder.encode(password))
                        .build()
        );

        return "SUCCESS";
    }
}
