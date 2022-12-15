package com.example.orderservice.client;

import com.example.orderservice.vo.ResponseMusic;
import com.example.orderservice.vo.ResponseOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "music-service")
public interface MusicServiceClient {

    @GetMapping("/music-service/musics/{musicId}")
    ResponseMusic getMusics(@PathVariable String musicId);

}
