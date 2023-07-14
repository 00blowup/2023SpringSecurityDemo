package hello.securitydemo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserJoinRequestDto {
    private String userName;
    private String password;
}
