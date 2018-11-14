package com.invivoo.catalog.interfaces;

import com.invivoo.catalog.domain.product.Product;
import com.invivoo.catalog.domain.product.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/catalog/product", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    private final static Logger logger = LoggerFactory.getLogger(ProductController.class);

    private ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("s")
    public ResponseEntity<List<Product>> getAll() {
        logger.debug("GET products");
        return ResponseEntity.ok(productRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> get(@PathVariable Long id) {
        logger.debug("GET products/{}", id);
        return productRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @PostMapping
    public ResponseEntity<Product> post(@RequestBody Product product) {
        logger.debug("POST products/{}", product);
        productRepository.save(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity put(@RequestBody Product product) {
        logger.debug("PUT products/{}", product);
        Long productId = product.getId();
        if (productId == null || productDoesNotExistsById(productId)) {
            return ResponseEntity.notFound().build();
        }
        productRepository.save(product);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable(value = "id") Long id) {
        logger.debug("DELETE products/{}", id);
        if (productDoesNotExistsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private boolean productDoesNotExistsById(Long productId) {
        return !productRepository.existsById(productId);
    }
}
