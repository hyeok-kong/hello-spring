package hello.hellospring.dto;


import hello.hellospring.domain.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

public class MemberDto {

    @Getter
    public static class Request {

        @NotEmpty
        @Email
        private String email;

        @NotEmpty
        private String password;

        @NotEmpty
        private String nickname;

        @Builder
        public Request(String email, String password, String nickname) {
            this.email = email;
            this.password = password;
            this.nickname = nickname;
        }

        public Member toEntity() {
            return Member.builder()
                    .email(email)
                    .nickname(nickname)
                    .build();
        }

        public Member toEntity(String encryptedPassword) {
            return Member.builder()
                    .email(email)
                    .password(encryptedPassword)
                    .nickname(nickname)
                    .build();
        }
    }
}
