package com.mjahoo.playground.resource;

import com.mjahoo.playground.entity.Customer;
import com.mjahoo.playground.entity.CustomerOrder;
import com.mjahoo.playground.entity.OrderItem;

import java.time.Instant;
import java.util.List;

public class EntityDtoConverter {
    public record CustomerDto(String id, String name, List<CustomerOrderDto> orders) {
    }

    public record CustomerOrderDto(String id, Instant date, List<OrderItemDto> orderItems) {
    }

    public record OrderItemDto(int quantity, String product) {
    }

    public static CustomerDto customerDto(final Customer customer) {
        return new CustomerDto(
                customer.getId().toString(),
                customer.getName(),
                customerOrderDto(customer.getOrders()));
    }

    public static List<CustomerOrderDto> customerOrderDto(final List<CustomerOrder> customerOrders) {
        return customerOrders.stream().map(EntityDtoConverter::customerOrderDto).toList();
    }

    public static CustomerOrderDto customerOrderDto(final CustomerOrder customerOrder) {
        return new CustomerOrderDto(
                customerOrder.getId().toString(),
                customerOrder.getOrderDate(),
                orderItem(customerOrder.getOrderItems()));
    }

    private static List<OrderItemDto> orderItem(final List<OrderItem> orderItems) {
        return orderItems.stream().map(EntityDtoConverter::orderItem).toList();
    }

    private static OrderItemDto orderItem(final OrderItem orderItem) {
        return new OrderItemDto(orderItem.getQuantity(), orderItem.getProduct().getName());
    }
}
