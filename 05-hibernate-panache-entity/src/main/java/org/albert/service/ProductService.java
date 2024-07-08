package org.albert.service;

import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.albert.entity.Product;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProductService
{
    @Transactional
    public Optional<Product> updateProduct(Long id, Product product)
    {
        return Product.findByIdOptional(id)
                .map(entity -> {
                    Product prod = (Product) entity;
                    prod.name = product.name;
                    prod.price = product.price;
                    prod.persist();
                    return prod;
                });
    }

    @Transactional
    public boolean delete(Long id)
    {
        return Product.deleteById(id);
    }

    public Optional<Product> findByNameOptional(String name)
    {
        return Product.find("SELECT p FROM Product p WHERE p.name = ?1", name)
                .singleResultOptional();
    }

    public List<Product> getAll()
    {
//        return Product.listAll();
        return Product.listAll(Sort.by("name", Sort.Direction.Ascending));
    }

    public Optional<Product> findByIdOptional(Long id)
    {
        return Product.findByIdOptional(id);
    }

    @Transactional
    public boolean save(Product product)
    {
        Product.persist(product);
        return product.isPersistent();
    }
}
