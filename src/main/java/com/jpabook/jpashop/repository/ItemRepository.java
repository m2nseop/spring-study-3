package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.item.Item;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) { // jpa에 저장하기 전까지 아이템은 id값이 없다 == 새로운 아이템이다.
            em.persist(item); // 새로 저장하는 객체이다.
        } else {
            em.merge(item); // 기존에 있던 아이템이라면? -> 업데이트
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }
}
