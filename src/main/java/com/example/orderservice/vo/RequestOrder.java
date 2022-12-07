package com.example.orderservice.vo;

import lombok.Data;

@Data
public class RequestOrder {
    private String musicID;
    private Integer musicQty;
}
