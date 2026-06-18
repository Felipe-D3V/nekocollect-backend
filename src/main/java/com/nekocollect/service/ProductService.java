package com.nekocollect.service;

import com.nekocollect.exception.ResourceNotFoundException;
import com.nekocollect.model.Product;
import com.nekocollect.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> findAll() {
        return repository.findAll();
    }

    public Product save(Product product) {
        return repository.save(product);
    }

    public Product findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
    new ResourceNotFoundException("Produto não encontrado"));
    }

    public Product update(Long id, Product product) {

        Product existing = findById(id);

        existing.setName(product.getName());
        existing.setCharacterName(product.getCharacterName());
        existing.setPrice(product.getPrice());
        existing.setStock(product.getStock());
        existing.setManufacturer(product.getManufacturer());

        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<Product> findByCharacter(String name) {
        return repository.findByCharacterName(name);
    }
}