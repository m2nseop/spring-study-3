package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.item.Book;
import jakarta.persistence.EntityManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemUpdateTest {

    @Autowired
    EntityManager em;

    @Test
    public void updateTest() throws Exception {
        Book book = em.find(Book.class, 1L);

        //Transaction
        book.setName("dafsfd");

        //변경감지 == dirty checking
        //Transaction commit -> 변경사항 반영

        //ex) Order의 cancel() 을 보면 따로 entity manager를 사용하지 않았는데도 변경사항이 Tx 커밋시점에 반영되어 flush된다.
        // flush 시점에 변경사항이 감지되어 이에 대한 쿼리가 생성되어 db에 날라가고 tx 커밋되어 변경사항이 영구히 저장되는 순서이다.
    }
}
