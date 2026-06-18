package com.nekocollect.service;

import com.nekocollect.exception.ResourceNotFoundException;
import com.nekocollect.model.Product;
import com.nekocollect.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService service;

    @Test
    void shouldReturnAllProducts() {

        Product product = new Product();
        product.setName("Goku");

        when(repository.findAll())
                .thenReturn(List.of(product));

        List<Product> result = service.findAll();

        assertEquals(1, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void shouldSaveProduct() {

        Product product = new Product();
        product.setName("Naruto");

        when(repository.save(product))
                .thenReturn(product);

        Product result = service.save(product);

        assertEquals("Naruto", result.getName());
        verify(repository).save(product);
    }

    @Test
    void shouldFindProductById() {

        Product product = new Product();
        product.setName("Luffy");

        when(repository.findById(1L))
                .thenReturn(Optional.of(product));

        Product result = service.findById(1L);

        assertEquals("Luffy", result.getName());
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {

        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> service.findById(1L)
        );
    }

    @Test
    void shouldDeleteProduct() {

        service.delete(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void shouldFindProductsByCharacter() {

        Product product = new Product();
        product.setCharacterName("Goku");

        when(repository.findByCharacterName("Goku"))
                .thenReturn(List.of(product));

        List<Product> result =
                service.findByCharacter("Goku");

        assertEquals(1, result.size());
    }

    @Test
    void shouldUpdateProduct() {

        Product existing = new Product();
        existing.setName("Old");

        Product updated = new Product();
        updated.setName("New");

        when(repository.findById(1L))
                .thenReturn(Optional.of(existing));

        when(repository.save(any(Product.class)))
                .thenReturn(existing);

        Product result =
                service.update(1L, updated);

        assertEquals("New", result.getName());
    }
}