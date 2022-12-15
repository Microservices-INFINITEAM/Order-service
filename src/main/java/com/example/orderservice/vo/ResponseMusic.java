package com.example.orderservice.vo;

import lombok.Data;

@Data
public class ResponseMusic {
    private String musicName;
    private String musicArtistName;
    private String musicGenre;
    private Integer musicStock;
}
