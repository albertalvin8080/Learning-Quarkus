package org.albert.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
public class Product
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

