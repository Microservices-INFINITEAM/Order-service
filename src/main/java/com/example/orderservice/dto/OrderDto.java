package com.example.orderservice.dto;

import com.example.orderservice.vo.ResponseMusic;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class OrderDto implements Serializable {

    private String userId;
    private String orderId;
    private String musicId;
    private Integer musicQty;
    private Integer musicTotalPrice;
    private ResponseMusic music;
    private Integer musicPrice;


}
