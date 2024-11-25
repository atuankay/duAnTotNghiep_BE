package com.example.projectsale.product.constants;

public class ProductConstants {
    // Tiền tố cho các API riêng biệt
    public static final String API_ADMIN_PRODUCTS = "/admin/products";
    public static final String API_MEMBER_PRODUCTS = "/member/products";
    public static final String API_PUBLIC_PRODUCTS = "/public/products";

    // Các endpoint cụ thể cho admin, member và public
    public static final String API_GET_ALL_PRODUCTS = "/all"; // Common cho cả 3 loại
    public static final String API_ADD_PRODUCT = "/add"; // Dành cho admin
    public static final String API_UPDATE_PRODUCT = "/update/{id}"; // Dành cho admin
    public static final String API_DELETE_PRODUCT = "/delete/{id}"; // Dành cho admin

    // Các endpoint khác (nếu cần) cho frontend (public)
    public static final String API_GET_PRODUCT_BY_ID = "/{id}"; // Lấy sản phẩm theo ID
    public static final String API_ADD_TO_CART = "/add-to-cart/{productId}"; // Thêm sản phẩm vào giỏ hàng (public)
}
