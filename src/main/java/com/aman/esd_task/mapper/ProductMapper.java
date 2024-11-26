package com.aman.esd_task.mapper;

import com.aman.esd_task.dto.ProductRequest;
import com.aman.esd_task.dto.ProductResponse;
import com.aman.esd_task.entity.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    public Product requestToEntity(ProductRequest productRequest) {
        return Product.builder()
                .name(productRequest.productName())
                .price(productRequest.price())
                .build();
    }

    public ProductResponse entityToResponse(Product product) {
        return new ProductResponse(product.getName(), product.getPrice());
    }
}
