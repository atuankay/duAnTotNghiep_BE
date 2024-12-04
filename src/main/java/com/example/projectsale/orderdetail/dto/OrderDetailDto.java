package com.example.projectsale.orderdetail.dto;

import com.example.projectsale.order.entity.Order;
import com.example.projectsale.product.entity.Product;
import com.example.projectsale.user.entity.User;
import lombok.Getter;
import lombok.Setter;


import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class OrderDetailDto {

    private BigDecimal price;

    private Integer quantity;

    private String size;

    private String color;

    private User user;

    private Order order;

    private String productId;

    private String email;

    private String fullName;

    private String phoneNumber;

    private String address;

    private Date orderDate;

    private BigDecimal totalPrice;

    private String description;
}
