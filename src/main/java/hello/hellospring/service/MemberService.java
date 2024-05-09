package hello.hellospring.service;


import hello.hellospring.common.exception.DuplicateElementException;
import hello.hellospring.domain.Member;
import hello.hellospring.dto.MemberDto;
import hello.hellospring.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void saveMember(MemberDto.Request request) {
        checkUniqueEmail(request.getEmail());
        Member entity = request.toEntity(passwordEncoder.encode(request.getPassword()));
        memberRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public void checkUniqueEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new DuplicateElementException("Duplicate Email");
        }
    }

    @Transactional(readOnly = true)
    public boolean checkUniqueNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    @Transactional(readOnly = true)
    public boolean validatePassword(String inputPassword, String realPassword) {
        return passwordEncoder.matches(inputPassword, realPassword);
    }

    @Transactional(readOnly = true)
    public Member findMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Transactional(readOnly = true)
    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(NoSuchElementException::new);
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
}
