package com.example.projectsale.product.controller.publicapi;

import com.example.projectsale.product.constants.ProductConstants;
import com.example.projectsale.product.dto.ProductDto;
import com.example.projectsale.product.service.ProductPublicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("publicProductController")
@RequestMapping(ProductConstants.API_PUBLIC_PRODUCTS)
@RequiredArgsConstructor
public class ProductController {

    private final ProductPublicService productPublicService;

    @GetMapping(ProductConstants.API_GET_ALL_PRODUCTS)
    public ResponseEntity<List<ProductDto>> getProductsByPage(
            @RequestParam(defaultValue = "0") int page,  // Trang mặc định là 0
            @RequestParam(defaultValue = "8") int size,  // Số sản phẩm mỗi trang là 8
            @RequestParam(required = false) String search, // Tìm kiếm theo tên sản phẩm
            @RequestParam(required = false) String color,  // Lọc theo màu sắc
            @RequestParam(required = false) Double minPrice, // Lọc theo giá min
            @RequestParam(required = false) Double maxPrice, // Lọc theo giá max
            @RequestParam(defaultValue = "name") String sortBy,  // Sắp xếp theo trường
            @RequestParam(defaultValue = "asc") String sortOrder // Thứ tự sắp xếp
    ) {
        // Kiểm tra giá trị hợp lệ cho page và size
        if (page < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // Nếu page < 0, trả về 400
        }
        if (size <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // Nếu size <= 0, trả về 400
        }

        // Kiểm tra sortBy và sortOrder
        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "name";  // Mặc định sắp xếp theo tên
        }

        if (sortOrder == null || sortOrder.isEmpty()) {
            sortOrder = "asc";  // Mặc định sắp xếp theo thứ tự tăng dần
        }

        // Gọi service để lấy sản phẩm với các tham số đã lọc, sắp xếp và phân trang
        List<ProductDto> products = productPublicService.getProductsByPage(
                page, size, search, color, minPrice, maxPrice, sortBy, sortOrder
        );

        // Trả về danh sách sản phẩm với mã trạng thái 200 OK
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
