package hello.securitydemo.service;

import hello.securitydemo.domain.User;
import hello.securitydemo.repository.UserRepository;
import hello.securitydemo.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    private Long expireTimeMs = 1000 * 60 * 60L;    // 1시간

    @Value("${jwt.key}")
    private String key;

    public String join(String userName, String password) {
        // userName 중복 체크
        userRepository.findByUserName(userName)
                .ifPresent(user -> {
                    throw new RuntimeException("usename " + userName + " already exists!");
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

    public String login(String userName, String password) {
        // 존재하지 않는 회원명을 입력한 상황의 예외처리
        User selectedUser = userRepository.findByUserName(userName)
                .orElseThrow(() -> new RuntimeException("username not found!"));

        // 잘못된 비밀번호를 입력한 상황의 예외처리
        if(!encoder.matches(password, selectedUser.getPassword())) throw new RuntimeException("wrong password!");

        // 로그인 성공
        String token = JwtTokenUtil.createToken(selectedUser.getUserName(), key, expireTimeMs);

        // 리턴
        return token;

    }
}
