package com.example.projectsale.product.service;

import com.example.projectsale.order.entity.Order;
import com.example.projectsale.orderdetail.entity.OrderDetail;
import com.example.projectsale.product.dto.ProductDto;
import com.example.projectsale.product.entity.Product;
import com.example.projectsale.product.mapper.ProductDtoMapper;
import com.example.projectsale.product.repo.ProductRepo;
import com.example.projectsale.product.specification.ProductSpecifications;
import com.example.projectsale.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.example.projectsale.orderdetail.repo.OrderDetailRepo;
import com.example.projectsale.user.repo.UserRepo;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductMemberService {

    private final ProductRepo productRepo;
    private final ProductDtoMapper dtoMapper;
    private final UserRepo userRepo; // Inject UserRepo
    private final OrderDetailRepo orderDetailRepo; // Inject OrderDetailRepo

    public List<ProductDto> getProductsByPage(int page, int size, String search, String color, Double minPrice, Double maxPrice, String sortBy, String sortOrder) {
        // Kiểm tra giá trị hợp lệ cho page và size
        if (page < 0) {
            return null;  // Nếu page < 0, trả về null hoặc xử lý theo cách của bạn
        }
        if (size <= 0) {
            return null;  // Nếu size <= 0, trả về null hoặc xử lý theo cách của bạn
        }

        // Kiểm tra sortBy và sortOrder
        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "name";  // Mặc định sắp xếp theo tên
        }

        if (sortOrder == null || sortOrder.isEmpty()) {
            sortOrder = "asc";  // Mặc định sắp xếp theo thứ tự tăng dần
        }

        // Tạo Sort đối tượng để sắp xếp theo sortBy và sortOrder
        Sort sort = Sort.by(sortBy);
        sort = sortOrder.equalsIgnoreCase("desc") ? sort.descending() : sort.ascending();

        // Tạo PageRequest với trang và số sản phẩm mỗi trang
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        // Tạo Specification cho các bộ lọc tìm kiếm
        Specification<Product> spec = Specification.where(ProductSpecifications.hasName(search != null ? search : ""))
                .and(ProductSpecifications.hasColor(color != null ? color : ""))
                .and(ProductSpecifications.hasPriceGreaterThan(minPrice != null ? minPrice : 0.0))
                .and(ProductSpecifications.hasPriceLessThan(maxPrice != null ? maxPrice : Double.MAX_VALUE));

        // Lấy các sản phẩm từ repository với Specification và Pageable
        Page<Product> productPage = productRepo.findAll(spec, pageRequest);

        // Chuyển đổi thành ProductDto và trả về
        return productPage.getContent().stream()
                .map(dtoMapper::apply)
                .collect(Collectors.toList());
    }

    public void addProductToCart(UUID productId) {
        // Giả sử người dùng hiện tại là người đang đăng nhập. Lấy user từ session hoặc context
        User user = userRepo.findById(UUID.fromString("user-id-example"))
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Lấy sản phẩm từ productRepo
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Kiểm tra xem người dùng có giỏ hàng chưa (có thể cần thêm logic để kiểm tra giỏ hàng)
        Order order = new Order(); // Tạo hoặc lấy order hiện tại của người dùng

        // Tạo orderDetail từ sản phẩm và thông tin giỏ hàng
        OrderDetail orderDetail = OrderDetail.builder()
                .product(product)
                .user(user)
                .order(order)
                .price(product.getPrice()) // Đảm bảo giá đúng
                .quantity(1) // Giả sử thêm một sản phẩm vào giỏ hàng
                .size("M") // Có thể thêm thông tin size nếu có
                .color(product.getColor()) // Thêm màu sắc
                .build();

        // Lưu OrderDetail vào cơ sở dữ liệu
        orderDetailRepo.save(orderDetail);
    }
}
