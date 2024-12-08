package com.example.projectsale.order.service;

import com.example.projectsale.order.dto.OrderDto;
import com.example.projectsale.order.dto.UpdateOrderDTO;
import com.example.projectsale.order.dto.request.OrderSearchDtoRequest;
import com.example.projectsale.order.entity.Order;
import com.example.projectsale.utils.response.Response;
import org.springframework.http.ResponseEntity;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

public interface OrderService {

    ResponseEntity<Response> getAllOrders(OrderSearchDtoRequest request);
    List<OrderDto> getAll();


    OrderDto updateOrder(String orderId, Order order);

    void cancelOrder(String orderId);
    OrderDto updateStatusOrder(UpdateOrderDTO updateOrderDTO);
}
