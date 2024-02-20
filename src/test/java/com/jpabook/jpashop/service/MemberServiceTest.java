package com.jpabook.jpashop.service;

import static org.junit.Assert.*;

import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class) // Junit 실행할 때 spring도 같이 엮어서 실행하겠다는 뜻
@SpringBootTest // springboot를 띄운상태로 테스트하겠다는 뜻 // @Autowired등등을 위함
@Transactional // rollback을 위함 // 실행한 로직에 대해 db저장 x
// 왜 롤백해야하는가? // 테스트를 반복적으로 해야하므로 같은 코드를 반복적으로 했을 때 문제가 될 수 있는 코드들을 위해 롤백을 한다.
// 물론 테스트 어노테이션과 같이 쓰일때만이다 ㅋㅋ
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
//    @Rollback(false) // 디비에 저장
    public void 회원가입() throws Exception{
        // given
        Member member = new Member();
        member.setName("kim");

        // when
        Long saveId = memberService.join(member);
        // 데이터베이스 트랜잭션이 딱 정확하게 커밋을 하는 순간
        // 플러쉬라는게 되면서 jpa 영속성 컨텍스트에 있는 이 멤버 객체가 insert가 만들어지면서 db에 insert가 낙나다.
        // 그런데 스프링에서 트랜잭셔널은 기본적으로 트랜잭션 커밋을 안하고 롤백을 해버린다.

        // then
//        em.flush(); // 영속성 컨텍스트가 insert 쿼리를 날림 // insert코드를 볼 수 있음
        assertEquals(member, memberRepository.findOne(saveId));

    }

    @Test
    public void 중복_회원_예외() throws Exception{
        // given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        // when
        memberService.join(member1);
        try {
            memberService.join(member2);
        } catch (IllegalStateException e) {
            return ;
        }
        // then
        fail("예외가 발생해야 한다.");
    }

    // 위코드와 같다.
    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외2() throws Exception{
        // given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        // when
        memberService.join(member1);
        memberService.join(member2);
        // then
        fail("예외가 발생해야 한다.");
    }
}