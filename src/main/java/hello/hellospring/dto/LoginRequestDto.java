package hello.hellospring.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginRequestDto {

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

}
