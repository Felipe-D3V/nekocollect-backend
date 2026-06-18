package com.nekocollect.repository;

import com.nekocollect.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository
        extends JpaRepository<Product, Long> {

    List<Product> findByCharacterName(String characterName);
}