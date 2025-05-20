package org.example.oauth2clientcredauthandresserver.controller;

import org.example.oauth2clientcredauthandresserver.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final List<Product> listProducts = new ArrayList<>();

    @GetMapping
    public ResponseEntity<?> list() {
        if(listProducts.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listProducts);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Product product) {
        listProducts.add(product);
        return ResponseEntity.ok(product);
    }
}
