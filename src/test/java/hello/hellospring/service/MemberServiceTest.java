package hello.hellospring.service;

import hello.hellospring.common.exception.DuplicateElementException;
import hello.hellospring.domain.Member;
import hello.hellospring.dto.MemberDto;
import hello.hellospring.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @InjectMocks
    MemberService memberService;

    @Mock
    MemberRepository memberRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    MemberDto.Request request;

    @BeforeEach
    void setUp() {
        request = MemberDto.Request.builder()
                .email("test@admin.com")
                .password("password")
                .nickname("테스트 멤버")
                .build();
    }

    @Test
    @DisplayName("회원 가입 성공 테스트")
    void 회원가입_성공_테스트() {
        //given
        when(passwordEncoder.encode(any())).thenReturn("password");

        //when
        memberService.saveMember(request);

        //then
        verify(memberRepository, times(1)).save(any(Member.class));
    }


    @Test
    @DisplayName("동일한 이메일이 존재하지 않으면 예외가 발생하지 않는다")
    void 이메일_중복_발생X() {
        //given
        when(memberRepository.existsByEmail(any())).thenReturn(false);

        //when
        memberService.checkUniqueEmail(request.getEmail());

        //then
        verify(memberRepository, times(1)).existsByEmail(any(String.class));
    }

    @Test
    @DisplayName("이메일이 이미 존재하는 경우 예외를 발생시킨다")
    void 이메일_중복_발생() {
        //given
        when(memberRepository.existsByEmail(any())).thenReturn(true);

        //then
        Assertions.assertThrows(DuplicateElementException.class, () -> memberService.checkUniqueEmail(request.getEmail()));
    }

    @Test
    @DisplayName("이메일이 중복일 경우 회원 가입에 실패한다")
    void 회원가입_실패_테스트_중복() {
        //given
        when(memberRepository.existsByEmail(any())).thenReturn(true);

        //then
        Assertions.assertThrows(DuplicateElementException.class, () -> memberService.saveMember(request));
    }
}