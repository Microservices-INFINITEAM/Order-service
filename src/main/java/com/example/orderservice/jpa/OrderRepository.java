package com.example.orderservice.jpa;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
    OrderEntity findByOrderId(String orderId);

    Iterable<OrderEntity> findByUserId(String userId);


    void deleteByUserId(String userId);
//    void removeByUserId(String userId);
}
