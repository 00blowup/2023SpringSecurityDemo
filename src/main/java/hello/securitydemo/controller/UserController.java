package hello.securitydemo.controller;

import hello.securitydemo.domain.dto.UserJoinRequestDto;
import hello.securitydemo.domain.dto.UserLoginRequestDto;
import hello.securitydemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<String> join (@RequestBody UserJoinRequestDto requestDto) {
        userService.join(requestDto.getUserName(), requestDto.getPassword());
        return ResponseEntity.ok().body("successfully joined");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login (@RequestBody UserLoginRequestDto requestDto) {
        String token = userService.login(requestDto.getUserName(), requestDto.getPassword());
        return ResponseEntity.ok().body("successfully logged in (token: " + token + ")");
    }

    @PostMapping("/authtest")
    public ResponseEntity<String> authtest (Authentication authentication) {
        return ResponseEntity.ok().body("userName: " + authentication.getName());
    }
}
