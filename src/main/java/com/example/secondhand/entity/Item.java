package com.example.secondhand.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private Integer id;
    private Integer userId;
    private String title;
    private String description;
    private BigDecimal price;
    private String category;
    private String status; // 在售/已售/下架
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 关联用户信息（非数据库字段）
    private String username;
}