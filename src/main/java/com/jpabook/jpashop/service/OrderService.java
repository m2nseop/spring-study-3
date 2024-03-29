package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.Delivery;
import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.domain.Order;
import com.jpabook.jpashop.domain.OrderItem;
import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.repository.ItemRepository;
import com.jpabook.jpashop.repository.MemberRepository;
import com.jpabook.jpashop.repository.OrderRepository;
import com.jpabook.jpashop.repository.OrderSearch;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장
        orderRepository.save(order);
        // Order 엔티티 필드의 Delivery와 OrderItem에 cascade all 조건을 걸어놨기 때문에
        // Order를 persist(영속화, 디비에 저장)하게 되면 Delivery, OrderItem도 같이 persist된다.
        // Delivery, OrderItem은 Order외에 다른곳과 연관관계가 없기 때문에 cascade를 사용했다.

        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        // 주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        // 주문취소
        order.cancel();
        // 무언가 따로 조취를 취하지 않아도 객체가 변경됨을 jpa가 감지하면 알아서 업데이트 쿼리가 날라간다.
        // 원래 mybatis라면 업데이트 쿼리를 따로 작성해줘야하지만 jpa는 그렇지 않다. 이것이 jpa의 장점이다.
    }

    // 검색
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByString(orderSearch);
    }
}
