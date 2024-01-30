package com.jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository { // Entity를 찾아주는 역할

    @PersistenceContext // 스프링 부트가 알아서 EntityManager를 주입해준다.
    private EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId();
        // 커맨드랑 쿼리를 분리해라 // 사이드 이펙트를 발생시키는 커맨드이기 때문에 리턴값을 왠만하면 안만들어준다.
        // 대신에 member의 id정도는 차후 조회용으로 반환시켜준다.
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}
