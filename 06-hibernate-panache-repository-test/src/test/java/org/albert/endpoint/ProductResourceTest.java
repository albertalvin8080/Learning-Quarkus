package org.albert.endpoint;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.*;

@QuarkusTest
@Tag("integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductResourceTest
{

    @Test
    @Order(1)
    void getAll_ReturnsListOfProducts_WhenSuccessful()
    {
    }

    @Test
    @Order(1)
    void findById_ReturnsAProduct_WhenSuccessful()
    {
    }

    @Test
    @Order(1)
    void findByName()
    {
    }

    @Test
    @Order(2)
    void save()
    {
    }

    @Test
    @Order(3)
    void update()
    {
    }

    @Test
    @Order(4)
    void delete()
    {
    }
}