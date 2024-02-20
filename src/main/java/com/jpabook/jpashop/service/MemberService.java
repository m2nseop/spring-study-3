package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true) // jpa의 데이터변경은 transaction안에서 아루어져야한다.
// 조회하는 경우에 @Transactional(readOnly = true)을 선언하면 성능을 더 최적화한다.
// spring꺼로 쓰는것을 추천
@RequiredArgsConstructor // 생성자 코드 자체를 생략 가능 // final 멤버 변수를 알아서 의존관계주입해줌
public class MemberService {

    private final MemberRepository memberRepository;

//    @Autowired // 생성자가 1개이면 @Autowired 생략가능
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    /**
     * 회원 가입
     */

    @Transactional // readOnly = false가 디폴트이다.
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    // 멀티쓰레드같은 동시에 회원가입을 시도하려는 상황이 발생할 수 있기 때문에
    // 아래처럼 회원이름 중복처리 비즈니스 로직을 만들었다고 해도
    // 데이터베이스에 member -> name 컬럼에 unique 제약조건으로 잡아두는 것이 안전하다.
    private void validateDuplicateMember(Member member) {
        List<Member> result = memberRepository.findByName(member.getName());
        if (!result.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
