package com.laptoppstore.service;

import com.laptoppstore.entity.Order;
import java.util.List;

public interface OrderService {
    List<Order> findAll();
    Order findById(Long id);
    Order save(Order order);
    void deleteById(Long id);
}
