package com.example.projectsale.orderdetail.service;

import com.example.projectsale.orderdetail.dto.OrderDetailDto;
import com.example.projectsale.utils.response.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderDetailService {

    ResponseEntity<Response> createOrderDetail(List<OrderDetailDto> orderDetailDtos);

}
