package com.example.orderservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseOrder {
    private String orderID;
    private String musicID;
    private String musicArtistName;
    private String musicGenre;
    private Integer musicQty;
    private Integer musicPrice;
    private Integer totalPrice;
    private Date createdAt;

}
