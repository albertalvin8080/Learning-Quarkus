package org.albert.endpoint.unit;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.albert.endpoint.ProductResource;
import org.albert.entity.Product;
import org.albert.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class ProductResourceTest
{
    @InjectMock
    ProductRepository productRepository;

    @Inject
    ProductResource productResource;

    private Product product;

    @BeforeEach
    void setUp()
    {
        product = new Product();
        product.setId(1L);
        product.setName("TV");
        product.setPrice(BigDecimal.valueOf(100.50));
    }

    @Test
    void getAll_ReturnsListOfProduct_WhenSuccessful()
    {
        Mockito.when(productRepository.listAll(ArgumentMatchers.any()))
                .thenReturn(List.of(product));

        final Response response = productResource.getAll();
        final List<Product> productList = (List<Product>) response.getEntity();

        assertNotNull(productList);
        assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
        assertEquals(product.getId(), productList.get(0).getId());
        assertEquals(product.getName(), productList.get(0).getName());
        assertEquals(product.getPrice(), productList.get(0).getPrice());
    }

    @Test
    void findById_ReturnsProduct_WhenSuccessful()
    {
        Mockito.when(productRepository.findByIdOptional(product.getId()))
                .thenReturn(Optional.of(product));

        final Response response = productResource.findById(product.getId());
        final Product entity = (Product) response.getEntity();

        assertNotNull(entity);
        assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
        assertEquals(product.getId(), entity.getId());
        assertEquals(product.getName(), entity.getName());
        assertEquals(product.getPrice(), entity.getPrice());
    }

    @Test
    void findById_ReturnsNotFound_WhenNoProductHasBeenFound()
    {
        Mockito.when(productRepository.findByIdOptional(product.getId()))
                .thenReturn(Optional.empty());

        final Response response = productResource.findById(product.getId());
        final Product entity = (Product) response.getEntity();

        assertNull(entity);
        assertEquals(response.getStatus(), Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    void save_ReturnsProductLocationHeader_WhenSuccessful()
    {
        Mockito.doNothing().when(productRepository)
                .persist(ArgumentMatchers.any(Product.class));
        Mockito.when(productRepository.isPersistent(product))
                .thenReturn(true);

        final Response response = productResource.save(product);

        assertNotNull(response);
        assertNull(response.getEntity());
        assertEquals(response.getStatus(), Response.Status.CREATED.getStatusCode());
        assertEquals(response.getLocation(), URI.create("/product/" + product.getId()));
    }

    @Test
    void save_ReturnsBadRequest_WhenTheProductCannotBeSaved()
    {
        Mockito.doNothing().when(productRepository)
                .persist(ArgumentMatchers.any(Product.class));
        Mockito.when(productRepository.isPersistent(product))
                .thenReturn(false);

        final Response response = productResource.save(product);

        assertNotNull(response);
        assertNull(response.getEntity());
        assertEquals(response.getStatus(), Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    void findByName_ReturnsProduct_WhenSuccessful()
    {
        final PanacheQuery<Product> panacheQuery = Mockito.mock(PanacheQuery.class);
        // Not necessary for this case.
//        Mockito.when(panacheQuery.page(Mockito.any()))
//                .thenReturn(panacheQuery);
        Mockito.when(panacheQuery.singleResultOptional())
                .thenReturn(Optional.of(product));

        String query = "SELECT p FROM Product p WHERE p.name = ?1";
        Mockito.when(productRepository.find(query, product.getName()))
                .thenReturn(panacheQuery);

        final Response response = productResource.findByName(product.getName());
        final Product entity = (Product) response.getEntity();

        assertNotNull(entity);
        assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
        assertEquals(product.getId(), entity.getId());
        assertEquals(product.getName(), entity.getName());
        assertEquals(product.getPrice(), entity.getPrice());
    }

    @Test
    void findByName_ReturnsNotFound_WhenNotProductHasBeenFound()
    {
        final PanacheQuery<Product> panacheQuery = Mockito.mock(PanacheQuery.class);
        Mockito.when(panacheQuery.singleResultOptional())
                .thenReturn(Optional.empty());

        String query = "SELECT p FROM Product p WHERE p.name = ?1";
        Mockito.when(productRepository.find(query, product.getName()))
                .thenReturn(panacheQuery);

        final Response response = productResource.findByName(product.getName());
        final Product entity = (Product) response.getEntity();

        assertNull(entity);
        assertEquals(response.getStatus(), Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    void delete_ReturnsNoContent_WhenSuccessful()
    {
        Mockito.when(productRepository.deleteById(product.getId()))
                .thenReturn(true);

        final Response response = productResource.delete(product.getId());

        assertNotNull(response);
        assertNull(response.getEntity());
        assertEquals(response.getStatus(), Response.Status.NO_CONTENT.getStatusCode());
    }

    @Test
    void delete_ReturnsNotFound_WhenNoProductHasBeenFound()
    {
        Mockito.when(productRepository.deleteById(product.getId()))
                .thenReturn(false);

        final Response response = productResource.delete(product.getId());

        assertNotNull(response);
        assertNull(response.getEntity());
        assertEquals(response.getStatus(), Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    void update_ReturnsUpdatedProduct_WhenSuccessful()
    {
        Mockito.doNothing().when(productRepository)
                .persist(ArgumentMatchers.any(Product.class));

        Mockito.when(productRepository.findByIdOptional(product.getId()))
                .thenReturn(Optional.of(product));

        Product newProduct = new Product();
        newProduct.setName("GPU");
        newProduct.setPrice(BigDecimal.valueOf(99.99));

        final Response response = productResource.update(product.getId(), newProduct);
        final Product entity = (Product) response.getEntity();

        assertNotNull(entity);
        assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
        assertEquals(product.getId(), entity.getId());
        assertEquals(newProduct.getName(), entity.getName());
        assertEquals(newProduct.getPrice(), entity.getPrice());
    }

    @Test
    void update_ReturnsNotFound_WhenNoProductHasBeenFound()
    {
        Mockito.doNothing().when(productRepository)
                .persist(ArgumentMatchers.any(Product.class));

        Mockito.when(productRepository.findByIdOptional(product.getId()))
                .thenReturn(Optional.empty());

        Product newProduct = new Product();
        newProduct.setName("GPU");
        newProduct.setPrice(BigDecimal.valueOf(99.99));

        final Response response = productResource.update(product.getId(), newProduct);
        final Product entity = (Product) response.getEntity();

        assertNull(entity);
        assertEquals(response.getStatus(), Response.Status.NOT_FOUND.getStatusCode());
    }
}