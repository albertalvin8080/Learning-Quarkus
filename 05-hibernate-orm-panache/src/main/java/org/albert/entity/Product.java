package org.albert.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
public class Product extends PanacheEntityBase
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long id;
    // All fields should be public.
    @NotEmpty(message = "Name must not be empty.")
    @Column(unique = true, nullable = false)
    public String name;
    @NotNull(message = "Price must not be null.")
    @Column(nullable = false)
    public BigDecimal price;
}

/*
 * This one must not have an @Id because the class PanacheEntity already provides one.
 * */
//@Entity
//public class Product extends PanacheEntity
//{
//    // All fields should be public.
//    @Column(unique = true)
//    public String name;
//    public BigDecimal price;
//}
