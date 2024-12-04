package com.example.projectsale.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Paging {

    private int currentPage;

    private int limitPage;
//    private int totalPages;  // Tổng số trang
//
//    /**
//     * Tính tổng số trang dựa trên tổng số bản ghi và số mục mỗi trang.
//     * @param totalRecords Tổng số bản ghi
//     */
//    public void calculateTotalPages(int totalRecords) {
//        if (limitPage > 0) {
//            this.totalPages = (int) Math.ceil((double) totalRecords / limitPage);
//        } else {
//            this.totalPages = 0;
//        }
//    }
}
