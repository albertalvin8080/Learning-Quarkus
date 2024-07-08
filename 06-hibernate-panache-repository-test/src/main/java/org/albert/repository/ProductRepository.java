package org.albert.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.albert.entity.Product;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<Product>
{
}
