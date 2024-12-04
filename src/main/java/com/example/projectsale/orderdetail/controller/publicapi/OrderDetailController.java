package com.example.projectsale.orderdetail.controller.publicapi;

import com.example.projectsale.constant.SystemConstant;
import com.example.projectsale.orderdetail.dto.OrderDetailDto;
import com.example.projectsale.orderdetail.service.OrderDetailService;
import com.example.projectsale.utils.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(SystemConstant.API_PUBLIC + SystemConstant.VERSION_ONE + "/order")
@RequiredArgsConstructor
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    @PostMapping
    public ResponseEntity<Response> createOrderDetails(@RequestBody List<OrderDetailDto> orderDetailDtos) {
        return orderDetailService.createOrderDetail(orderDetailDtos);
    }
//    public ResponseEntity<Response> createOrderDetail(@RequestHeader("cartSessionId") String cartSessionId,
//                                                      @RequestBody OrderDetailDto orderDetailDto) {
//        return orderDetailService.createOrderDetail(cartSessionId, orderDetailDto);
//    }
}
