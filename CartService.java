package com.pickleball.service;

import com.pickleball.model.Order;
import com.pickleball.model.Product;
import com.pickleball.repository.OrderRepository;
import com.pickleball.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CartService {

    private Map<Long, Integer> cart = new HashMap<>();
    private double totalAmount = 0;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    // Thêm sản phẩm vào giỏ hàng
    public void addToCart(Long productId, int quantity) {
        cart.put(productId, cart.getOrDefault(productId, 0) + quantity);
        updateTotalAmount();
    }

    // Xem giỏ hàng
    public Map<Product, Integer> viewCart() {
        Map<Product, Integer> cartItems = new HashMap<>();
        for (Long productId : cart.keySet()) {
            Product product = productRepository.findById(productId).orElse(null);
            cartItems.put(product, cart.get(productId));
        }
        return cartItems;
    }

    // Cập nhật tổng giá trị giỏ hàng
    private void updateTotalAmount() {
        totalAmount = 0;
        for (Long productId : cart.keySet()) {
            Product product = productRepository.findById(productId).orElse(null);
            if (product != null) {
                totalAmount += product.getPrice() * cart.get(productId);
            }
        }
    }

    // Thực hiện thanh toán và tạo đơn hàng
    public Order checkout(Long userId) {
        Order order = new Order(userId, totalAmount, "Completed");
        orderRepository.save(order);
        cart.clear(); // Sau khi thanh toán, giỏ hàng sẽ trống
        return order;
    }

    // Lấy tổng tiền giỏ hàng
    public double getTotalAmount() {
        return totalAmount;
    }
}
