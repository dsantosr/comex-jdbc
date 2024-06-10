package com.comex.service;

import com.comex.dao.ProductDAO;
import com.comex.exception.EntityNotFoundException;
import com.comex.model.Product;

import java.util.List;

public class ProductService {

    private ProductDAO productDao;

    public ProductService() {
        this.productDao = new ProductDAO();
    }

    public void createProduct(Product newProduct) {
        System.out.println("Validando dados de produto...");

        this.productDao.create(newProduct);
    }

    public List<Product> listProducts() {
        return this.productDao.listAll();
    }

    public void updateProduct(Product produto) {
        System.out.println("Validando dados do produto...");
        this.productDao.update(produto);
    }

    public void deleteProductByID(long id) throws EntityNotFoundException {
        Product produtoParaExcluir = productDao.read(id);
        if (produtoParaExcluir == null) {
            throw new EntityNotFoundException("Produto não está cadastrado: " + id);
        }

        this.productDao.delete(produtoParaExcluir);
    }
}
