package hello.hellospring.common.aop;

import hello.hellospring.common.exception.UnAuthorizationException;
import hello.hellospring.domain.Comment;
import hello.hellospring.domain.Member;
import hello.hellospring.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class OwnerCheckAspect {

    private final LoginService loginService;

    @Before("@annotation(hello.hellospring.common.annotation.OwnerOnly) && args(.., comment)")
    public void checkOwnership(Comment comment) {
        Member member = loginService.getLoggedInMember();
        if (member != comment.getMember()) {
            throw new UnAuthorizationException();
        }
    }
}
