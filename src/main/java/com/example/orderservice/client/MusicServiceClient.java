package com.example.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="catalog-service") // 바꿔야됨
public class MusicServiceClient {
}
