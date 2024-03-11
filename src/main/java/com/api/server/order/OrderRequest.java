package com.api.server.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.api.server.product.ProductRequest;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
  private List<ProductRequest> OrderItems;
}
