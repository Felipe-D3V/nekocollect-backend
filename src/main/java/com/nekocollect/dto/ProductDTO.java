package com.nekocollect.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProductDTO {

    @NotBlank(message = "O nome do produto é obrigatório")
    @Size(min = 3, max = 100,
            message = "O nome deve ter entre 3 e 100 caracteres")
    private String name;

    @NotBlank(message = "O nome do personagem é obrigatório")
    private String characterName;

    @NotNull(message = "O preço é obrigatório")
    @Min(value = 1,
            message = "O preço deve ser maior que zero")
    private Double price;

    @NotNull(message = "O estoque é obrigatório")
    @Min(value = 0,
            message = "O estoque não pode ser negativo")
    private Integer stock;

    @NotBlank(message = "O fabricante é obrigatório")
    private String manufacturer;

    // Getters e Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}