package com.example.orderservice.controller;

import com.example.orderservice.client.MusicServiceClient;
import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.jpa.OrderEntity;
import com.example.orderservice.kafka.KafkaProducer;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.vo.RequestOrder;
import com.example.orderservice.vo.ResponseMusic;
import com.example.orderservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.Order;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order-service")
public class OrderController {

    OrderService orderService;
    Environment env;
    KafkaProducer kafkaProducer;

    @Autowired
    public OrderController(
            OrderService orderService,
            Environment env,
            KafkaProducer kafkaProducer){
        this.orderService=orderService;
        this.env=env;
        this.kafkaProducer=kafkaProducer;
    }

    @GetMapping("/health_check")
    public String status(){
        return String.format("It's working in order service on PORT %s", env.getProperty("local.server.port"));
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable String userId, @RequestBody RequestOrder order){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        ResponseMusic music = orderService.getMusic(order.getMusicId());
        if(music.getMusicStock()>order.getMusicQty()){
            OrderDto orderDto = mapper.map(order, OrderDto.class);
            orderDto.setUserId(userId);
            OrderDto createdOrder = orderService.createOrder(orderDto);

            ResponseOrder responseOrder = mapper.map(createdOrder, ResponseOrder.class);

            kafkaProducer.send("example-order-topic",orderDto);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
        }
        else{
            return null;
        }
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrders(@PathVariable String userId){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Iterable<OrderEntity> orders = orderService.getOrdersByUserId(userId);
        List<ResponseOrder> returnOrders = new ArrayList<>();

        orders.forEach(orderEntity -> returnOrders.add(mapper.map(orderEntity, ResponseOrder.class)));

        return ResponseEntity.status(HttpStatus.OK).body(returnOrders);
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<ResponseOrder> getOrder(@PathVariable String orderId){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        OrderDto orderDto = orderService.getOrderByOrderId(orderId);
        ResponseOrder returnOrder = mapper.map(orderDto, ResponseOrder.class);

        return ResponseEntity.status(HttpStatus.OK).body(returnOrder);
    }

    @DeleteMapping("/{orderId}")
    public String deleteOrder(@PathVariable String orderId){
        OrderDto orderDto=orderService.deleteByOrderId(orderId);
        kafkaProducer.send("back-order-topic",orderDto);
        return orderId+"is deleted.";
    }

    @DeleteMapping("/{userId}/orders")
    public void deleteUsersOrder(@PathVariable String userId){
        orderService.deleteUsersOrder(userId);
    }
}
