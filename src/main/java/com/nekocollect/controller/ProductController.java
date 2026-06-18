package com.nekocollect.controller;

import com.nekocollect.dto.ProductDTO;
import com.nekocollect.model.Product;
import com.nekocollect.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(
            summary = "Cadastrar produto",
            description = "Cria um novo action figure no sistema"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Produto criado com sucesso"
    )
    public Product create(@Valid @RequestBody ProductDTO dto) {

        Product product = new Product();

        product.setName(dto.getName());
        product.setCharacterName(dto.getCharacterName());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setManufacturer(dto.getManufacturer());

        return service.save(product);
    }

    @GetMapping
    @Operation(
            summary = "Listar produtos",
            description = "Retorna todos os produtos cadastrados"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de produtos retornada com sucesso"
    )
    public List<Product> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar produto por ID",
            description = "Retorna um produto específico pelo seu ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Produto encontrado"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Produto não encontrado"
    )
    public Product findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar produto",
            description = "Atualiza os dados de um produto existente"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Produto atualizado com sucesso"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Produto não encontrado"
    )
    public Product update(
            @PathVariable Long id,
            @Valid @RequestBody ProductDTO dto) {

        Product product = new Product();

        product.setName(dto.getName());
        product.setCharacterName(dto.getCharacterName());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setManufacturer(dto.getManufacturer());

        return service.update(id, product);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir produto",
            description = "Remove um produto do sistema"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Produto removido com sucesso"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Produto não encontrado"
    )
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/character")
    @Operation(
            summary = "Buscar por personagem",
            description = "Retorna todos os produtos de um personagem específico"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Produtos encontrados"
    )
    public List<Product> findByCharacter(
            @RequestParam String name) {

        return service.findByCharacter(name);
    }
}