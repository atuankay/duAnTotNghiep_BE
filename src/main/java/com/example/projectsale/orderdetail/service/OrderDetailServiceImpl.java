package com.example.projectsale.orderdetail.service;

import com.example.projectsale.cartitem.dto.Cart;
import com.example.projectsale.constant.SystemConstant;
import com.example.projectsale.enums.StatusOrder;
import com.example.projectsale.enums.SystemEnumStatus;
import com.example.projectsale.exception.ApiRequestException;
import com.example.projectsale.inventory.repo.InventoryRepo;
import com.example.projectsale.inventory.service.InventoryService;
import com.example.projectsale.order.entity.Order;
import com.example.projectsale.order.repo.OrderRepo;
import com.example.projectsale.orderdetail.dto.OrderDetailDto;
import com.example.projectsale.orderdetail.entity.OrderDetail;
import com.example.projectsale.orderdetail.repo.OrderDetailRepo;
import com.example.projectsale.product.entity.Product;
import com.example.projectsale.product.repo.ProductRepo;
import com.example.projectsale.utils.AbsServiceUtil;
import com.example.projectsale.utils.response.Response;
import com.example.projectsale.utils.response.ResponseUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderDetailServiceImpl extends AbsServiceUtil implements OrderDetailService {

    private final ProductRepo productRepo;

    private final OrderRepo orderRepo;

    private final OrderDetailRepo orderDetailRepo;

    private final ResponseUtil responseUtil;

    private final ObjectMapper objectMapper;

    private final RedisTemplate<String, String> redisTemplate;

    private final InventoryRepo inventoryRepo;

    private final InventoryService inventoryService;


//    @Override
//    public ResponseEntity<Response> createOrderDetail(String cartSessionId, OrderDetailDto orderDetailDto) {
//        try {
//            String data = redisTemplate.opsForValue().get(cartSessionId);
//            if (data == null) {
//                return responseUtil.responseError("ER_002");
//            }
//            TypeReference<Cart> cartData = new TypeReference<>() {
//            };
//            Cart cart = objectMapper.readValue(data, cartData);
//
//            List<OrderDetail> orderDetails = new ArrayList<>();
//
//            cart.getItems().forEach((id, cartItem) -> {
//                log.info(String.valueOf(cartItem.getProductId()));
//                Product product = productRepo.findByProductCondition(cartItem.getProductId(), cartItem.getQuantity())
//                        .orElseThrow(() -> new ApiRequestException("ER_001"));
//                inventoryService.updateInventory(cartItem.getProductId(), cartItem.getSize(),
//                        cartItem.getColor(), cartItem.getQuantity());
//                OrderDetail build = OrderDetail.builder()
//                        .quantity(cartItem.getQuantity())
//                        .price(product.getPrice())
//                        .user(getUser())
//                        .product(product)
//                        .status(SystemEnumStatus.ACTIVE)
//                        .isDeleted(SystemConstant.IS_DELETED_ACTIVE)
//                        .build();
//
//                orderDetails.add(build);
//            });
//
//            BigDecimal totalPrice = orderDetails.stream()
//                    .map(value -> value.getPrice()
//                            .multiply(new BigDecimal(value.getQuantity())))
//                    .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//            Order order = Order.builder()
//                    .user(getUser())
//                    .email(orderDetailDto.getEmail())
//                    .address(orderDetailDto.getAddress())
//                    .orderDate(new Date())
//                    .fullName(orderDetailDto.getFullName())
//                    .description(orderDetailDto.getDescription())
//                    .phoneNumber(orderDetailDto.getPhoneNumber())
//                    .totalPrice(totalPrice)
//                    .status(SystemEnumStatus.ACTIVE)
//                    .isDeleted(SystemConstant.IS_DELETED_ACTIVE)
//                    .build();
//
//            orderDetails.forEach(orderDetail -> {
//                orderDetail.setOrder(order);
//            });
//
//            orderDetailRepo.saveAll(orderDetails);
//            orderRepo.save(order);
//            log.info(orderDetails.toString());
//
//            redisTemplate.delete(cartSessionId);
//
//            return responseUtil.responseSuccess("OR_001");
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("Order fail!..");
//        }
//    }

    @Override
    public ResponseEntity<Response> createOrderDetail(List<OrderDetailDto> orderDetailDtos) {
        try {
            log.info("Start processing order details with DTOs: {}", orderDetailDtos);

            List<OrderDetail> orderDetails = new ArrayList<>();

            for (OrderDetailDto dto : orderDetailDtos) {
                log.info("Processing DTO: {}", dto);

                // Lấy Product dựa trên productId
                Product product = productRepo.findById(UUID.fromString(dto.getProductId()))
                        .orElseThrow(() -> {
                            log.error("Product not found for ID: {}", dto.getProductId());
                            return new ApiRequestException("ER_001: Product not found.");
                        });


                log.info("Found product: {}", product);

                // Cập nhật inventory
                inventoryService.updateInventory(
                        product.getId(),
                        dto.getSize(),
                        dto.getColor(),
                        dto.getQuantity()
                );
                log.info("Inventory updated for product ID: {}", product.getId());

                // Tạo OrderDetail
                OrderDetail orderDetail = OrderDetail.builder()
                        .quantity(dto.getQuantity())
                        .price(dto.getPrice())
                        .user(getUser())
                        .product(product)
                        .size(dto.getSize())
                        .color(dto.getColor())
                        .status(SystemEnumStatus.ACTIVE)
                        .isDeleted(SystemConstant.IS_DELETED_ACTIVE)
                        .build();

                log.info("Created OrderDetail: {}", orderDetail);

                orderDetails.add(orderDetail);
            }

            // Tính tổng giá
            BigDecimal totalPrice = orderDetails.stream()
                    .map(orderDetail -> orderDetail.getPrice().multiply(new BigDecimal(orderDetail.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            log.info("Total price calculated: {}", totalPrice);

            // Tạo Order
            Order order = Order.builder()
                    .user(getUser())
                    .email(orderDetailDtos.get(0).getEmail())
                    .address(orderDetailDtos.get(0).getAddress())
                    .orderDate(new Date())
                    .fullName(orderDetailDtos.get(0).getFullName())
                    .description(orderDetailDtos.get(0).getDescription())
                    .phoneNumber(orderDetailDtos.get(0).getPhoneNumber())
                    .totalPrice(totalPrice)
                    .statusOrder(StatusOrder.PENDING)
                    .status(SystemEnumStatus.ACTIVE)
                    .isDeleted(SystemConstant.IS_DELETED_ACTIVE)
                    .build();

            orderDetails.forEach(orderDetail -> orderDetail.setOrder(order));

            // Lưu dữ liệu vào DB
            orderDetailRepo.saveAll(orderDetails);
            log.info("Saved order details: {}", orderDetails);

            orderRepo.save(order);
            log.info("Saved order: {}", order);

            return responseUtil.responseSuccess("OR_001");
        } catch (Exception e) {
            log.error("Error while creating order details: {}", e.getMessage(), e);
            throw new RuntimeException("Order creation failed!");
        }
    }



}
