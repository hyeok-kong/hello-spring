package hello.hellospring.service;

import hello.hellospring.domain.Member;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final HttpSession httpSession;
    private final MemberService memberService;
    private static final String MEMBER_ID = "MEMBER_ID";

    public void login(long id) {
        httpSession.setAttribute(MEMBER_ID, id);
    }

    public void logout() {
        httpSession.removeAttribute(MEMBER_ID);
    }

    public Long getLoggedInMemberId() {
        return (Long) httpSession.getAttribute(MEMBER_ID);
    }

    public Member getLoggedInMember() {
        long memberId = (Long) httpSession.getAttribute(MEMBER_ID);
        return memberService.findMemberById(memberId);
    }
}
