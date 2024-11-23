package com.example.projectsale.category.service;

import com.example.projectsale.category.dto.ProductStockDto;
import com.example.projectsale.category.dto.request.CategoryCreateRequest;
import com.example.projectsale.category.entity.Category;
import com.example.projectsale.category.mapper.CategoryDtoMapper;
import com.example.projectsale.category.repo.CategoryRepo;
import com.example.projectsale.enums.SystemEnumStatus;
import com.example.projectsale.utils.response.Response;
import com.example.projectsale.utils.response.ResponseUtil;
import com.example.projectsale.exception.ApiRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;
    private final ResponseUtil responseUtil;
    private final CategoryDtoMapper categoryDtoMapper;

    @Override
    public ResponseEntity<Response> createCategory(CategoryCreateRequest request) {
        // Logic tạo danh mục
        // Kiểm tra xem danh mục đã tồn tại chưa
        String categoryName = request.getName().trim().toLowerCase();
        if (categoryRepo.existsCategoryByName(categoryName)) {
            return responseUtil.responseError("CTG_001");
        }

        Category category = Category.builder()
                .name(categoryName)
                .description(request.getDescription())
                .status(SystemEnumStatus.ACTIVE)
                .build();

        category = categoryRepo.save(category);

        return responseUtil.responseSuccess("CM_002", categoryDtoMapper.apply(category));
    }

    @Override
    public ResponseEntity<Response> updateCategory(UUID categoryId, CategoryCreateRequest request) {
        // Logic cập nhật danh mục
        Category category = getCategory(categoryId);
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setStatus(request.getStatus());
        category.setIsDeleted(request.getIsDeleted());

        categoryRepo.save(category);

        return responseUtil.responseSuccess("CM_003", categoryDtoMapper.apply(category));
    }

    @Override
    public ResponseEntity<Response> deleteCategory(UUID categoryId) {
        // Logic xóa danh mục
        Category category = getCategory(categoryId);
        category.setStatus(SystemEnumStatus.NO_ACTIVE);
        category.setIsDeleted(true);
        categoryRepo.save(category);

        return responseUtil.responseSuccess("CM_004", categoryDtoMapper.apply(category));
    }

    @Override
    public ResponseEntity<Response> getCategories(SystemEnumStatus status, int currentPage, int limitPage) {
        // Logic lấy danh sách danh mục
        // Tìm kiếm danh mục theo trạng thái và phân trang
        var pageable = Pageable.ofSize(limitPage).withPage(currentPage - 1);
        var categories = categoryRepo.findCategoriesByStatus(status, pageable);
        return responseUtil.responsesSuccess("CTG_003", categories.getContent(), pageable);
    }

    @Override
    public ResponseEntity<Response> getCategoryWithProductStock(UUID categoryId) {
        // Logic lấy sản phẩm và số lượng tồn kho trong danh mục
        List<ProductStockDto> productStockList = categoryRepo.findCategoryWithProductStock(categoryId);
        return responseUtil.responseSuccess("CM_005", productStockList);
    }

    private Category getCategory(UUID categoryId) {
        return categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ApiRequestException("CTG_002"));
    }
}
