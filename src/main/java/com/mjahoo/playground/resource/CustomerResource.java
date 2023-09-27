package com.mjahoo.playground.resource;

import com.mjahoo.playground.entity.Customer;
import com.mjahoo.playground.entity.CustomerOrder;
import com.mjahoo.playground.entity.OrderItem;
import com.mjahoo.playground.entity.Product;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustomerResource {

    private final EntityManager entityManager;

    @Autowired
    public CustomerResource(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @GetMapping("/insert")
    @Transactional
    public ResponseEntity<?> insertSampleData() {
        var product1 = new Product();
        product1.setId(UUID.randomUUID());
        product1.setName("Product One");
        var product2 = new Product();
        product2.setId(UUID.randomUUID());
        product2.setName("Product Two");
        entityManager.persist(product1);
        entityManager.persist(product2);

        var customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("John Smith");

        var order = new CustomerOrder();
        order.setId(UUID.randomUUID());
        order.setCustomer(customer);
        order.setOrderDate(Instant.now());

        var item1 = new OrderItem();
        item1.setId(UUID.randomUUID());
        item1.setProduct(product1);
        item1.setOrder(order);
        item1.setQuantity(1);

        var item2 = new OrderItem();
        item2.setId(UUID.randomUUID());
        item2.setProduct(product2);
        item2.setOrder(order);
        item2.setQuantity(2);

        order.setOrderItems(List.of(item1, item2));
        customer.setOrders(List.of(order));

        entityManager.persist(customer);

        return ResponseEntity.ok(Map.of("status", "OK"));
    }

    @GetMapping
    @Transactional
    public ResponseEntity<?> getAll() {
        List<Customer> customers = entityManager.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
        var response = customers.stream().map(EntityDtoConverter::customerDto).toList();
        return ResponseEntity.ok(response);
    }


}
