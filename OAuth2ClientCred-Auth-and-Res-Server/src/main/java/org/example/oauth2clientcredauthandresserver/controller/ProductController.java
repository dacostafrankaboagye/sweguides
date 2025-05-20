package org.example.oauth2clientcredauthandresserver.controller;

import org.example.oauth2clientcredauthandresserver.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final List<Product> listProducts = new ArrayList<>(){
        {
            add(new Product(1, "Product 1", 100));
            add(new Product(2, "Product 2", 200));
            add(new Product(3, "Product 3", 300));
        }
    };

    @GetMapping
    public ResponseEntity<?> list() {
        if(listProducts.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listProducts);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Product product) {
        Integer newProductId = listProducts.size() + 1;
        product.setId(newProductId);
        listProducts.add(product);

        URI uri = URI.create("/api/products/" + newProductId);
        return ResponseEntity.created(uri).body(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer productId, @RequestBody Product product) {
        product.setId(productId);
        if(!listProducts.contains(product)){
            return ResponseEntity.notFound().build();
        }
        int index = listProducts.indexOf(product);
        listProducts.set(index, product);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer productId) {

        // we have overridden the hashCode and toString methods
            // that is why we can do this.
            // Java's List.contains(Object o) and List.remove(Object o) rely on the equals() method to check object equality.
        Product product = new Product();
        product.setId(productId);
        if(!listProducts.contains(product)){
            return ResponseEntity.notFound().build();
        }
        listProducts.remove(product);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Integer productId) {
        Product product = new Product();
        product.setId(productId);
        if(!listProducts.contains(product)){
            return ResponseEntity.notFound().build();
        }
        int index = listProducts.indexOf(product);
        Product fullProduct = listProducts.get(index);

        return ResponseEntity.ok(fullProduct);
    }
}
