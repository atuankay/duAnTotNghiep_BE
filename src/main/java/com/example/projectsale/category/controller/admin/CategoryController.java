package com.example.projectsale.category.controller.admin;

import com.example.projectsale.category.constant.CategoryConstant;
import com.example.projectsale.category.dto.request.CategoryCreateRequest;
import com.example.projectsale.category.service.CategoryService;
import com.example.projectsale.constant.SystemConstant;
import com.example.projectsale.enums.SystemEnumStatus;
import com.example.projectsale.utils.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(SystemConstant.API_ADMIN + SystemConstant.VERSION_ONE + CategoryConstant.API_CATEGORY)
public class CategoryController {

    private final CategoryService categoryService;

    @PreAuthorize("hasRole('adminMaster')")
    @PostMapping(CategoryConstant.API_CREATE)
    public ResponseEntity<Response> createCategory(@RequestBody CategoryCreateRequest request) {
        return categoryService.createCategory(request);
    }

    @PreAuthorize("hasRole('adminMaster')")
    @PutMapping(CategoryConstant.API_UPDATE)
    public ResponseEntity<Response> updateCategory(@PathVariable UUID categoryId, CategoryCreateRequest request) {
        return categoryService.updateCategory(categoryId, request);
    }

    @PreAuthorize("hasRole('adminMaster')")
    @DeleteMapping(CategoryConstant.API_DELETE)
    public ResponseEntity<Response> deleteCategory(@PathVariable UUID categoryId) {
        return categoryService.deleteCategory(categoryId);
    }

    @GetMapping(CategoryConstant.API_LIST)
    public ResponseEntity<Response> getCategories(
            @RequestParam(SystemConstant.STATUS) Optional<SystemEnumStatus> status,
            @RequestParam(SystemConstant.CURRENT_PAGE) Optional<Integer> currentPage,
            @RequestParam(SystemConstant.LIMIT_PAGE) Optional<Integer> limitPage) {
        return categoryService.getCategories(status.orElse(SystemEnumStatus.ACTIVE),
                currentPage.orElse(1), limitPage.orElse(8));
    }

    @GetMapping(CategoryConstant.API_PRODUCT_STOCK)
    public ResponseEntity<Response> getCategoryWithProductStock(@PathVariable UUID categoryId) {
        return categoryService.getCategoryWithProductStock(categoryId);
    }
}
