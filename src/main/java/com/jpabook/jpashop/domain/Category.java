package com.jpabook.jpashop.domain;

import com.jpabook.jpashop.domain.item.Item;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item", // 중간 테이블 이름 설정
            joinColumns = @JoinColumn(name = "category_id"), // 내 테이블에서 맵핑되는 컬림
            inverseJoinColumns = @JoinColumn(name = "item_id")) // 상대 테이블(Item)에서 맵핑되는 컬럼
    // M2M은 중간 테이블을 명시해줘야한다.
    private List<Item> items = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();
}
