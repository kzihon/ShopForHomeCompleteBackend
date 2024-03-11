package com.api.server.product;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
  private Long id;
  private String name;
  private double price;
  private String description;
  @Enumerated(EnumType.STRING)
  private CategoryType category;
  private Integer numberInStock;
  private String supplier;
  //private MultipartFile image;
}
