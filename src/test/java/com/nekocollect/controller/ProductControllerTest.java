package com.nekocollect.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nekocollect.dto.ProductDTO;
import com.nekocollect.exception.ResourceNotFoundException;
import com.nekocollect.model.Product;
import com.nekocollect.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
private ProductService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldListProducts() throws Exception {

        Product product = new Product();
        product.setId(1L);
        product.setName("Goku");

        when(service.findAll())
                .thenReturn(List.of(product));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Goku"));
    }

    @Test
    void shouldFindProductById() throws Exception {

        Product product = new Product();
        product.setId(1L);
        product.setName("Naruto");

        when(service.findById(1L))
                .thenReturn(product);

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Naruto"));
    }

    @Test
    void shouldCreateProduct() throws Exception {

        ProductDTO dto = new ProductDTO();
        dto.setName("Luffy");
        dto.setCharacterName("Luffy");
        dto.setPrice(199.90);
        dto.setStock(10);
        dto.setManufacturer("Bandai");

        Product product = new Product();
        product.setName("Luffy");

        when(service.save(any(Product.class)))
                .thenReturn(product);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Luffy"));
    }

    @Test
    void shouldUpdateProduct() throws Exception {

        ProductDTO dto = new ProductDTO();
        dto.setName("Vegeta");
        dto.setCharacterName("Vegeta");
        dto.setPrice(249.90);
        dto.setStock(5);
        dto.setManufacturer("Banpresto");

        Product product = new Product();
        product.setName("Vegeta");

        when(service.update(eq(1L), any(Product.class)))
                .thenReturn(product);

        mockMvc.perform(put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Vegeta"));
    }

    @Test
    void shouldDeleteProduct() throws Exception {

        doNothing().when(service).delete(1L);

        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldFindByCharacter() throws Exception {

        Product product = new Product();
        product.setCharacterName("Goku");

        when(service.findByCharacter("Goku"))
                .thenReturn(List.of(product));

        mockMvc.perform(
                        get("/products/character")
                                .param("name", "Goku"))
                .andExpect(status().isOk());
    }
@Test
void shouldReturn404WhenProductNotFound() throws Exception {

    when(service.findById(999L))
            .thenThrow(
                    new ResourceNotFoundException("Produto não encontrado")
            );

    mockMvc.perform(get("/products/999"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.erro")
                    .value("Produto não encontrado"));
}
@Test
void shouldReturn400WhenValidationFails() throws Exception {

    String invalidJson = """
        {
            "name": "",
            "characterName": "",
            "price": 0,
            "stock": -1,
            "manufacturer": ""
        }
        """;

    mockMvc.perform(post("/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(invalidJson))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.name")
        .value("O nome do produto é obrigatório"))
            .andExpect(jsonPath("$.characterName")
                    .value("O nome do personagem é obrigatório"))
            .andExpect(jsonPath("$.stock")
                    .value("O estoque não pode ser negativo"))
            .andExpect(jsonPath("$.manufacturer")
                    .value("O fabricante é obrigatório"));
}

}