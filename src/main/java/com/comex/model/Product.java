package com.comex.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Product {
    private Long id;

    private String name;
    private String description;
    private double price;
    private List<Category> categories = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Category> getCategories() {
        return Collections.unmodifiableList(categories);
    }

    public void adicionaCategoria(Category categoria) {
        // verifica se a categoria já foi adicionada com base no id
        for (Category categoriaDaLista : categories) {
            if (categoriaDaLista.getId().equals(categoria.getId())) {
                return;
            }
        }

        this.categories.add(categoria);
    }

    @Override
    public String toString() {
        return "Produto{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", description='" + description + '\'' +
               ", price=" + price +
               ", categories=" + categories +
               '}';
    }
}