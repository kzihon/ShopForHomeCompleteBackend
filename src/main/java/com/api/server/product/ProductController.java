package com.api.server.product;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @GetMapping
  public ResponseEntity<List<Product>> getProducts() {
    return productService.getProducts();
  }

  @PostMapping(value="/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  @PreAuthorize("hasAuthority('admin:create')")
  public ResponseEntity<Product> createProduct(@RequestPart("product") Product product,
                                               @RequestPart("imageFile") MultipartFile file) throws IOException {

    try {
      Product p = productService.saveproduct(product, file);
      return ResponseEntity.status(HttpStatus.CREATED).body(p);
    } catch (Exception e) {
      System.out.println(e.getMessage());

    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }

  @GetMapping("/{id}")
  // @PreAuthorize("hasAnyAuthority('admin:read', 'customer:read')")
  public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) throws IOException {
    return ResponseEntity.status(HttpStatus.OK).body( productService.getProductById(id));
  }

  @GetMapping("/byCategory")
  public ResponseEntity<List<Product>> getProductsByCategory(
      @RequestParam(required = true) String category) {
    CategoryType prodCategory = CategoryType.valueOf(category);
    return productService.getProductsByCategory(prodCategory);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasAuthority('admin:update')")
  public ResponseEntity<Product> updateProduct(
      @PathVariable Long id,
      @RequestPart("product") Product product,
                                               @RequestPart("imageFile") MultipartFile file) throws IOException {

    return  ResponseEntity.status(HttpStatus.OK).body(productService.updateProduct(id, product, file));
  }

  @PutMapping("/updateWithNoImage/{id}")
  @PreAuthorize("hasAuthority('admin:update')")
  public ResponseEntity<Product> updateProductWithOutImage(
          @PathVariable Long id,
          @RequestBody ProductWithOutImage productWithOutImage
          )  {

    return  ResponseEntity.status(HttpStatus.OK).body(productService.updateProductWithOutImage(id, productWithOutImage));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('admin:delete')")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    return productService.deleteProduct(id);
  }
}
