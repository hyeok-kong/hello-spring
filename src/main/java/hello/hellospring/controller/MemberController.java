package hello.hellospring.controller;

import hello.hellospring.dto.MemberDto;
import hello.hellospring.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static hello.hellospring.common.HttpStatusResponseEntity.RESPONSE_OK;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<HttpStatus> registerMember(@RequestBody @Valid MemberDto.Request memberDto) {
        memberService.checkUniqueEmail(memberDto.getEmail());
        memberService.saveMember(memberDto);
        return RESPONSE_OK;
    }

    @GetMapping("/{email}/exists")
    public ResponseEntity<HttpStatus> checkUniqueEmail(@PathVariable String email) {
        memberService.checkUniqueEmail(email);
        return RESPONSE_OK;
    }
}
