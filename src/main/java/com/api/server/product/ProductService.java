package com.api.server.product;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProductService {
  private final String FOLDER_PATH="/Users/samanthaoh/Desktop/cogent/ShopForHome/src/assets/backendImages/";

  private final ProductRepository productRepository;
  @Autowired
  private ImageModelRepository imageModelRepository;

  public ResponseEntity<List<Product>> getProducts() {
    List<Product> products = productRepository.findAll();
    return ResponseEntity.ok(products);
  }

  public ProductDTO getProductById(long id) throws IOException {
    Product product=productRepository.findById(id) .orElseThrow(() -> new RuntimeException("Product not found"));
    ImageModel imageModel=product.getImageModel();

    ProductDTO productDTO=new ProductDTO(
            product.getProductId(),
            product.getName(),
            product.getPrice(),
            product.getDescription(),
            product.getCategory(),
            product.getNumberInStock(),
            product.getSupplier(),
            imageModel
    );

    return productDTO;
  }

  public Product saveproduct(Product p ,MultipartFile file) throws IOException {
    String filePath=FOLDER_PATH+file.getOriginalFilename();

    ImageModel imageModel=imageModelRepository.save(ImageModel.builder()
            .name(file.getOriginalFilename())
            .type(file.getContentType())
            .filePath(filePath).build());

    file.transferTo(new File(filePath));
    imageModelRepository.save(imageModel);
    p.setImageModel(imageModel);

    return productRepository.save(p);
  }

  public Product updateProduct(long product_id, Product p, MultipartFile file) throws IllegalStateException, IOException {
    String filePath = FOLDER_PATH + file.getOriginalFilename();
    Optional<ImageModel> imageModel = imageModelRepository.findByName(filePath);
           // .orElseThrow(() -> new RuntimeException("Image not found"));

    Product product = productRepository.findById(product_id)
            .orElseThrow(() -> new RuntimeException("Product not found"));

    System.out.println("gooddd");

    // ImageModel existingImageModel = product.getImageModel();

    product.setName(p.getName());
    product.setPrice(p.getPrice());
    product.setDescription(p.getDescription());
    product.setCategory(p.getCategory());
    product.setNumberInStock(p.getNumberInStock());
    product.setSupplier(p.getSupplier());

    if (imageModel.isEmpty()) {
        ImageModel imageModel1 = imageModelRepository.save(ImageModel.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .filePath(filePath).build());
                file.transferTo(new File(filePath));
        product.setImageModel(imageModel1);
    } else {
        product.setImageModel(imageModel.get());
    }
    return productRepository.save(product);
}

public Product updateProductWithOutImage(Long product_id, ProductWithOutImage productWithOutImage) {
  Product product = productRepository.findById(product_id)
          .orElseThrow(() -> new RuntimeException("Product not found"));

  product.setName(productWithOutImage.getName());
  product.setPrice(productWithOutImage.getPrice());
  product.setCategory(productWithOutImage.getCategory());
  product.setDescription(productWithOutImage.getDescription());
  product.setSupplier(productWithOutImage.getSupplier());
  return productRepository.save(product);

}

  public ResponseEntity<Void> deleteProduct(Long id) {
    if (!productRepository.existsById(id))
      return ResponseEntity.notFound().build();

    productRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  public ResponseEntity<List<Product>> getProductsByCategory(CategoryType category) {
    List<Product> products = productRepository.findProductsByCategory(category);

    if (products.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(products);
  }

}
