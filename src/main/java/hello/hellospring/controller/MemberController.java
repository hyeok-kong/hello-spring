package hello.hellospring.controller;

import hello.hellospring.common.annotation.LoginRequired;
import hello.hellospring.common.exception.UnAuthenticationException;
import hello.hellospring.domain.Member;
import hello.hellospring.dto.LoginRequestDto;
import hello.hellospring.dto.MemberDto;
import hello.hellospring.service.LoginService;
import hello.hellospring.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static hello.hellospring.common.HttpStatusResponseEntity.RESPONSE_OK;

@Slf4j
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<HttpStatus> registerMember(@RequestBody @Valid MemberDto.Request memberDto) {
        memberService.saveMember(memberDto);
        return RESPONSE_OK;
    }

    @PostMapping("/login")
    public ResponseEntity<HttpStatus> login(@RequestBody @Valid LoginRequestDto requestDto) {
        Member member = memberService.findMemberByEmail(requestDto.getEmail());
        log.debug("member : {}, {}", requestDto.getEmail(), requestDto.getPassword());
        if (!memberService.validatePassword(requestDto.getPassword(), member.getPassword())) {
            throw new UnAuthenticationException();
        }
        log.debug("login success");
        loginService.login(member.getId());
        return RESPONSE_OK;
    }

    @LoginRequired
    @PostMapping("/logout")
    public ResponseEntity<HttpStatus> logout() {
        loginService.logout();
        return RESPONSE_OK;
    }

    @GetMapping("/{email}/exists")
    public ResponseEntity<HttpStatus> checkUniqueEmail(@PathVariable String email) {
        memberService.checkUniqueEmail(email);
        return RESPONSE_OK;
    }
}
