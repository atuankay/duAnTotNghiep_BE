package com.example.projectsale.order.service;

import com.example.projectsale.enums.StatusOrder;
import com.example.projectsale.inventory.dto.response.InventoryIndexDtoResponse;
import com.example.projectsale.inventory.entity.Inventory;
import com.example.projectsale.order.dto.OrderDto;
import com.example.projectsale.order.dto.UpdateOrderDTO;
import com.example.projectsale.order.dto.request.OrderSearchDtoRequest;
import com.example.projectsale.order.entity.Order;
import com.example.projectsale.order.event.OrderDischargeEvent;
import com.example.projectsale.order.mapper.OrderDtoMapper;
import com.example.projectsale.order.repo.OrderRepo;
import com.example.projectsale.utils.AbsServiceUtil;
import com.example.projectsale.utils.PageableUtil;
import com.example.projectsale.utils.response.Response;
import com.example.projectsale.utils.response.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl extends AbsServiceUtil implements OrderService {

    private final OrderRepo orderRepo;
    private final OrderDtoMapper orderDtoMapper;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ResponseUtil responseUtil;


    @Override
    public ResponseEntity<Response> getAllOrders(OrderSearchDtoRequest request) {
        Pageable pageable = PageableUtil.of(request.getCurrentPage(), request.getLimitPage());
        Page<Order> all = orderRepo.findAll(request, pageable);
        List<OrderDto> list = all.stream()
                .map(orderDtoMapper)
                .toList();
        return responseUtil.responsesSuccess("OR_002", list, pageable(pageable, all.getTotalPages()));
    }

    @Override
    public List<OrderDto> getAll() {
        List<Order> all = orderRepo.findAll();
        List<OrderDto> list = all.stream()
                .map(orderDtoMapper)
                .toList();
        return list;
    }

    @Override
    public OrderDto updateOrder(String orderId, Order order) {
        Optional<Order> existingOrderOpt = orderRepo.findById(UUID.fromString(orderId));
        if (existingOrderOpt.isEmpty()) {
            throw new RuntimeException("Order not found with id: " + orderId);
        }

        Order existingOrder = existingOrderOpt.get();

        existingOrder.setFullName(order.getFullName());
        existingOrder.setEmail(order.getEmail());
        existingOrder.setPhoneNumber(order.getPhoneNumber());
        existingOrder.setAddress(order.getAddress());
        existingOrder.setTotalPrice(order.getTotalPrice());
        existingOrder.setDescription(order.getDescription());


        Order updatedOrder = orderRepo.save(existingOrder);


        return orderDtoMapper.apply(updatedOrder);
    }

    @Override
    public void cancelOrder(String orderId) {

    }

    @Override
    public OrderDto updateStatusOrder(UpdateOrderDTO updatOrders) {
        Order order = orderRepo.findById(updatOrders.getOrderId()).orElseThrow();

        if (updatOrders.getStatus().equals(StatusOrder.PENDING.toString())) {
            order.setStatusOrder(StatusOrder.PENDING);
        } else if (order.getStatusOrder().equals(StatusOrder.PENDING)) {
            order.setStatusOrder(StatusOrder.PENDING);
        } else if (order.getStatusOrder().equals(StatusOrder.COMPLETED)) {
            order.setStatusOrder(StatusOrder.COMPLETED);
        } else if (order.getStatusOrder().equals(StatusOrder.REFUNDED)) {
            order.setStatusOrder(StatusOrder.REFUNDED);
        }
        order = orderRepo.save(order);
        return orderDtoMapper.apply(order);
    }

//    @Override
//    public void cancelOrder(UUID orderId) {
//        Optional<Order> existingOrderOpt = orderRepo.findById(orderId);
//        if (existingOrderOpt.isEmpty()) {
//            throw new RuntimeException("Order not found with id: " + orderId);
//        }
//
//        Order existingOrder = existingOrderOpt.get();
//        existingOrder.setStatus("CANCELED");
//        orderRepo.save(existingOrder);
//
//        log.info("Order {} has been canceled", orderId);
//    }
}
