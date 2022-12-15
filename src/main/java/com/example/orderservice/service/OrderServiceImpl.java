package com.example.orderservice.service;

import com.example.orderservice.client.MusicServiceClient;
import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.jpa.OrderEntity;
import com.example.orderservice.jpa.OrderRepository;
import com.example.orderservice.vo.ResponseMusic;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final MusicServiceClient musicServiceClient;

    @Autowired
    public OrderServiceImpl(
            OrderRepository orderRepository,
            MusicServiceClient musicServiceClient
    ) {
        this.orderRepository = orderRepository;
        this.musicServiceClient = musicServiceClient;
    }



    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setMusicTotalPrice(orderDto.getMusicQty() * orderDto.getMusicPrice());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        OrderEntity orderEntity = mapper.map(orderDto, OrderEntity.class);

        orderRepository.save(orderEntity);

        ResponseMusic music = musicServiceClient.getMusics(orderEntity.getMusicId());
        orderDto.setMusic(music);


        return orderDto;
    }

    @Override
    public OrderDto getOrderByOrderId(String orderId) {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);

        OrderDto orderDto = new ModelMapper().map(orderEntity, OrderDto.class);

        ResponseMusic music = musicServiceClient.getMusics(orderEntity.getMusicId());
        orderDto.setMusic(music);

        return orderDto;
    }

    @Override
    public Iterable<OrderEntity> getOrdersByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }

    public Iterable<OrderEntity> getOrderByUserIdAll() {return orderRepository.findAll();}

    @Override
    public OrderDto deleteByOrderId(String orderId) {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        OrderDto orderDto = new ModelMapper().map(orderEntity, OrderDto.class);
        orderRepository.delete(orderEntity);

        return orderDto;
    }

    @Override
    public ResponseMusic getMusic(String musicId) {
        ResponseMusic music = musicServiceClient.getMusics(musicId);
        return music;
    }

    @Override
    @Transactional
    public void deleteUsersOrder(String userId) {
        orderRepository.deleteByUserId(userId);
    }


}
