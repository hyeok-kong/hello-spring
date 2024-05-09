package hello.hellospring.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final HttpSession httpSession;
    private static final String MEMBER_ID = "MEMBER_ID";

    public void login(long id) {
        httpSession.setAttribute(MEMBER_ID, id);
    }

    public void logout() {
        httpSession.removeAttribute(MEMBER_ID);
    }
}
