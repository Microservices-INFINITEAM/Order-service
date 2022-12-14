package com.example.orderservice.jpa;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "orders")
public class OrderEntity implements Serializable {  // 직렬화 : 가지고 있는 객체를 네트워크로 전송하고나 데이터베이스에 보관하기 위해 마샬링/언마샬링 작업을 함
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length =120, unique= false)
    private String musicId;

    @Column(nullable = false)
    private Integer musicQty;

    @Column(nullable = false)
    private Integer musicTotalPrice;

    @Column(nullable = false)
    private Integer musicPrice;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false, unique = true)
    private String orderId;


    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Date createdAt;
}
