package com.example.projectsale.order.controller.admin;

import com.example.projectsale.constant.SystemConstant;
import com.example.projectsale.order.constant.OrderConstant;
import com.example.projectsale.order.dto.OrderDto;
import com.example.projectsale.order.dto.UpdateOrderDTO;
import com.example.projectsale.order.dto.request.OrderSearchDtoRequest;
import com.example.projectsale.order.service.OrderService;
import com.example.projectsale.utils.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController(value = "adminOrder")
@RequiredArgsConstructor
@RequestMapping(SystemConstant.API_ADMIN + SystemConstant.VERSION_ONE + OrderConstant.API_ORDER)
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<Response> getAllOrders(@RequestBody OrderSearchDtoRequest request) {
        return orderService.getAllOrders(request);
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderDto>> getAll() {
        return ResponseEntity.ok(orderService.getAll());
    }

    @PutMapping
    public ResponseEntity<OrderDto> updateOrder(@RequestBody UpdateOrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.updateStatusOrder(orderDTO));
    }

}
