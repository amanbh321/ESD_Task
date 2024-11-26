package com.aman.esd_task.service;

import lombok.RequiredArgsConstructor;
import com.aman.esd_task.dto.ProductRequest;
import com.aman.esd_task.dto.ProductResponse;
import com.aman.esd_task.entity.Product;
import com.aman.esd_task.exception.ProductNotFoundException;
import com.aman.esd_task.mapper.ProductMapper;
import com.aman.esd_task.repo.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo repo;
    private final ProductMapper mapper;

    public String createProduct(ProductRequest request) {
        Product product = mapper.requestToEntity(request);
        repo.save(product);
        return "Product created";
    }

    public List<ProductResponse> getAllProducts(Double lowPrice, Double highPrice, Integer limit) {
        return repo.findByPriceRange(lowPrice, highPrice, limit)
                .stream()
                .map(mapper::entityToResponse)
                .toList();
    }

    public ProductResponse updateProduct(Long productId, ProductRequest request) {
        Product selectedProduct = repo.findById(productId).orElse(null);
        if (selectedProduct == null) throw new ProductNotFoundException("product id");
        selectedProduct.setName(request.productName());
        selectedProduct.setPrice(request.price());

        Product updatedProduct = repo.save(selectedProduct);

        return mapper.entityToResponse(updatedProduct);
    }

    public void deleteProduct(Long productId) {
        Product selectedProduct = repo.findById(productId).orElse(null);
        if (selectedProduct == null) throw new ProductNotFoundException("product id");
        repo.delete(selectedProduct);
    }

    public ProductResponse getProduct(Long productId) {
        Product selectedProduct = repo.findById(productId).orElse(null);
        if (selectedProduct == null) throw new ProductNotFoundException("product id");
        return mapper.entityToResponse(selectedProduct);
    }
}
