package com.example.projectsale.category.constant;

public class CategoryConstant {

    // Base API URL for Category
    public static final String API_CATEGORY = "/category";

    // Specific endpoints for Category API
    public static final String API_CREATE = "/create";         // POST: /category/create
    public static final String API_UPDATE = "/{categoryId}";   // PUT: /category/{categoryId}
    public static final String API_DELETE = "/{categoryId}";   // DELETE: /category/{categoryId}
    public static final String API_LIST = "/list";              // GET: /category/list
    public static final String API_PRODUCT_STOCK = "/{categoryId}/product-stock"; // GET: /category/{categoryId}/product-stock
}
