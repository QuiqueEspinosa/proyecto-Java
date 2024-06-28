package com.ucc.crudorders.service;

import com.ucc.crudorders.model.Product;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class ProductsProvider {


    public boolean isProductAvailable(String productSku) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8083/api/products/" + productSku;  // Suponiendo que el microservicio de productos está en el puerto 8083
        try {
            ResponseEntity<Product> response = restTemplate.getForEntity(url, Product.class);
            return !response.getBody().getSku().isEmpty();
        } catch (Exception e) {
            // Manejar la excepción adecuadamente
            return false;
        }
    }
}
