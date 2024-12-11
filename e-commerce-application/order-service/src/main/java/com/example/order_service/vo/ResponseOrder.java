package com.example.order_service.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseOrder {

    private String productId;
    private String productName;
    private Integer quantity;
    private Integer totalPrice;
    private Date createdAt;

    private String orderId;
}
