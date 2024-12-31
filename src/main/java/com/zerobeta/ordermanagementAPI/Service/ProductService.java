package com.zerobeta.ordermanagementAPI.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zerobeta.ordermanagementAPI.Repository.ProductRepo;
import com.zerobeta.ordermanagementAPI.Common.Enums.OrderStatus;
import com.zerobeta.ordermanagementAPI.Model.Product;

@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public Product getProduct(Long id) {
        return productRepo.findById(id).orElse(null);
    }

    public Product addProduct(Product product) {
        return productRepo.save(product);
    }

    public void updateNewOrdersToDispatched() {
        List<Product> products = productRepo.findByStatus(OrderStatus.NEW);
        for (Product product : products) {
            product.setStatus(OrderStatus.DISPATCHED);
            productRepo.save(product);
        }
    }
}
