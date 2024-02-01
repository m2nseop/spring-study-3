package com.jpabook.jpashop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue // 식별자 및 값을 자동으로 넣어준다.
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded // 내장타입임을 명시
    private Address address;

    @OneToMany(mappedBy = "member") // order테이블의 member필드에 의해서 맵핑된거야 // 관계중 을로 설정
    // 즉 읽기전용 컬럼된것이다. // 여기에 값을 변화시킨다고 한들 orders의 member에는 영향을 끼치지 않는다.
    private List<Order> orders = new ArrayList<>();
}
