package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.item.Book;
import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.repository.ItemRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true) // 기본적으로 전체 readOnly = true 적용 // 조회를 바탕으로 하겠다.
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional // @Transactional을 다시 오버라이딩 하여 해당 로직은 readOnly=false 하도록 한다. // 그래야 디비에 반영이 되니까
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Transactional
    public Item updateItem(Long itemId, Book param) {
        // id를 기반으로 디비에 있는 영속성 상태인 엔티티를 가져와서
        // 파라미터로 넘겨받은 준영속성 엔티티인 param에 들어있는 값으로
        // 영속성 엔티티의 값을 변경시킨다.
        // 그러면 jpa가 변경감지(dirty checking)을 하여 db에 변경된 값이 반영된다.
        Item findItem = itemRepository.findOne(itemId);
        findItem.setPrice(param.getPrice());
        findItem.setName(param.getName());
        findItem.setStockQuantity(param.getStockQuantity());
        return findItem;
    }

    // 조회용 로직이니 readOnly=true그대로 냅둔다.
    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    // 조회용 로직이니 readOnly=true그대로 냅둔다.
    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
