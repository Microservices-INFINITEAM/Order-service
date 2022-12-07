package com.example.orderservice.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderDto implements Serializable {
//    private String productId;
//    private Integer qty;
//    private Integer unitPrice;
//    private Integer totalPrice;

    private String orderId;
    private String userId;

    private String musicID;
    private Integer musicQty;
    private String musicName;
    private String musicArtistName;
    private String musicGenre;
    private Integer musicPrice;
    private Integer totalPrice;
}
