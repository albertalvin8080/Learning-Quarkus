package org.albert.service;

import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.albert.entity.Product;
import org.albert.repository.ProductRepository;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProductService
{
    private final Logger log = Logger.getLogger(ProductService.class);

    @Inject
    ProductRepository productRepository;

    @Transactional
    public Optional<Product> updateProduct(Long id, Product product)
    {
        log.debugf("Updating product %s", product.getName());
        return productRepository.findByIdOptional(id)
                .map(prod -> {
                    prod.setName(product.getName());
                    prod.setPrice(product.getPrice());
                    productRepository.persist(prod);
                    return prod;
                });
    }

    @Transactional
    public boolean delete(Long id)
    {
        return productRepository.deleteById(id);
    }

    public Optional<Product> findByNameOptional(String name)
    {
        log.infof("Searching for product %s", name);
        return productRepository.find("SELECT p FROM Product p WHERE p.name = ?1", name)
                .singleResultOptional();
    }

    public List<Product> getAll()
    {
//        return productRepository.listAll();
        return productRepository.listAll(Sort.by("name", Sort.Direction.Ascending));
    }

    public Optional<Product> findByIdOptional(Long id)
    {
        return productRepository.findByIdOptional(id);
    }

    @Transactional
    public boolean save(Product product)
    {
        productRepository.persist(product);
        return productRepository.isPersistent(product);
    }
}
