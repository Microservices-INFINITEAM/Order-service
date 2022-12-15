package com.example.orderservice.vo;

import lombok.Data;

@Data
public class RequestOrder {
 private String musicId;
 private int musicQty;
 private int musicPrice;
}
