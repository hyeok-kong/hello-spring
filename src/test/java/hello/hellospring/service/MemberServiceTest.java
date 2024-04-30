package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    @DisplayName("Member를 저장한다.")
    void 회원가입() {
        //given
        Member member = Member.builder()
                .email("test")
                .password("test")
                .nickname("test")
                .build();
        //when
        Long memberId = memberService.save(member);

        //then
        assertThat(member).isEqualTo(memberRepository.findById(memberId).orElse(new Member()));
    }
}