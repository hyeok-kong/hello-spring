package hello.hellospring.common.interceptor;

import hello.hellospring.common.annotation.LoginRequired;
import hello.hellospring.common.exception.UnAuthenticationException;
import hello.hellospring.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class LoginCheckInterceptor implements HandlerInterceptor {

    private final LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod && ((HandlerMethod) handler).hasMethodAnnotation(LoginRequired.class)) {
            Long memberId = loginService.getLoggedInMemberId();
            if (memberId == null) {
                throw new UnAuthenticationException();
            }
        }
        return true;
    }
}
