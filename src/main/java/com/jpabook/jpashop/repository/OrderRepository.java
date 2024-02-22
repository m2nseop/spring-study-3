package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.Order;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    // 상품 검색 쿼리
//    public List<Order> findAll(OrderSearch orderSearch) {}
}
