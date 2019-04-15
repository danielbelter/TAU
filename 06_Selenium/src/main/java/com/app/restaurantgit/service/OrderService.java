package com.app.restaurantgit.service;

import com.app.restaurantgit.model.Customer;
import com.app.restaurantgit.model.Meal;
import com.app.restaurantgit.model.Order;
import com.app.restaurantgit.model.OrderStatus;
import com.app.restaurantgit.repository.CustomerRepository;
import com.app.restaurantgit.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    CustomerRepository customerRepository;

    public List<Meal> getAllMealsForOrder(Order order) {
        List<Meal> retList = new ArrayList<>();
        for (Meal m : order.getMeals()) {
            retList.add(m);
        }
        return retList;
    }

    // test for heroku
    public BigDecimal priceForOrder(Order order) {
        BigDecimal price = new BigDecimal(0);
        for (Meal m : order.getMeals()) {
            price = price.add(m.getPrice());
        }
        return price;

    }

    public List<Order> getAllOrdersWithDoneStatus() {
        List<Order> filteredList = orderRepository.findAll().stream()
                .sorted(Comparator.comparing(Order::getPriority))
                .filter(order -> order.getOrderStatus().equals(OrderStatus.ZREALIZOWANE))
                .collect(Collectors.toList());
        return filteredList;

    }

    public List<Order> findAllByOrderStatusIsNotContaining(){
        List<Order> filteredList = orderRepository.findAll().stream()
                .filter(order -> !order.getOrderStatus().equals(OrderStatus.ZREALIZOWANE))
                .sorted(Comparator.comparing(Order::getPriority).reversed())
                .collect(Collectors.toList());
        return filteredList;
    }


}
