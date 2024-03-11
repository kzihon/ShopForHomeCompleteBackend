package com.api.server.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long productId;
  private String name;
  private double price;
  private String description;
  @Enumerated(EnumType.STRING)
  private CategoryType category;
  private Integer numberInStock;
  private String supplier;
  @OneToOne
  private ImageModel imageModel;
//
//  @Column(columnDefinition = "longblob")
//  private byte[] image;

}
