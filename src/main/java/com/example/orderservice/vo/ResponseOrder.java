package com.example.orderservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseOrder {
    private String orderId;
    private int musicQty;
    private int musicTotalPrice;
    private int musicPrice;
    private ResponseMusic music;

}
