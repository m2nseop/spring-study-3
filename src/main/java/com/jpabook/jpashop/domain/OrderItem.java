package com.jpabook.jpashop.domain;

import static jakarta.persistence.FetchType.LAZY;

import com.jpabook.jpashop.domain.item.Item;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문 (당시) 가격

    private int count; // 주문 (당시) 수량

    // @NoArgsConstructor(access = AccessLevel.PROTECTED) 로 아래 생성자를 대체 가능
//    protected OrderItem() {
//        // 다른 곳에서 new를 통해 OrderItem을 생성하지 못하게 막는다.
//        // OrderItem생성은 오직 createOrderItem으로만 생성할 수 있게끔 한다.
//        // 더 나은 유지보수를 위해서 ㅇㅇ
//    }

    //==생성 메서드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }

    //==비즈니스 로직==//
    // OrderItem의 cancel 로직은 item의 재고를 복구해주는 기능
    public void cancel() {
        getItem().addStock(count);
    }

    //==조회 로직==//

    /**
     * 주문상품 전체 가격 조히
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
