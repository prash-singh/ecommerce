package com.project.ecommerce.orders.services;

import com.project.ecommerce.Constants;
import com.project.ecommerce.orders.dto.OrderDTO;
import com.project.ecommerce.orders.entities.Cart;
import com.project.ecommerce.orders.entities.CartItems;
import com.project.ecommerce.orders.entities.Order;
import com.project.ecommerce.orders.entities.OrderItems;
import com.project.ecommerce.orders.repository.OrderItemsRepository;
import com.project.ecommerce.orders.repository.OrderRepository;
import com.project.ecommerce.products.services.ProductServices;
import com.project.ecommerce.warehouse.service.Warehouseservice;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Log4j2
public class OrderServiceImplementation implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartService cartService;
    @Autowired
    private OrderItemsRepository orderItemsRepository;
    @Autowired
    private Warehouseservice warehouseservice;
    @Autowired
    private ProductServices productServices;

    public List<Order> getOrder(String customerId){
            return this.orderRepository.findOrderByCustomerId(customerId);
    }
    public Order placeOrder(Order o){
        o.getOrderItems().stream().forEach(
                orderItems -> this.orderItemsRepository.save(orderItems)
        );
        this.orderRepository.save(o);

        return o;
    }

    public String placeOrder(OrderDTO ord){
        Order o = new Order();
        o.setOrderStatus(true);
        o.setDate((new Timestamp(new Date().getTime())).toString());
        o.setShippingAddress(ord.getShippingAddressId());
        o.setCustomerId(ord.getCustomerId());
        o.setOrderItems(new ArrayList<>());
        List<CartItems> cartItems = this.cartService.getByUserId(ord.getCustomerId()).getCartItems();
        if(cartItems == null || cartItems.isEmpty()){
            log.info("Error no item found in Cart");
            return "Error no item found in Cart";
        }
        double total = 0;
        for (CartItems c: cartItems) {
            OrderItems orderItems = new OrderItems();
            total += c.getPrice();
            orderItems.setProductItemId(c.getProductId());
            if(this.productServices.getProduct(c.getProductId()).getAvailQuantity() < c.getQuantity()){
                return "Error: Reduce Quantity of product in cart to place order";
            }
            orderItems.setPrice(c.getPrice());
            orderItems.setQuantity(c.getQuantity());
            o.getOrderItems().add(orderItems);
            this.orderItemsRepository.save(orderItems);
        }
        o.setOrderTotal(total);
        this.orderRepository.save(o);
        this.cartService.removeAllItems(ord.getCustomerId());
        this.warehouseservice.Updateproduct(o);
        this.warehouseservice.addshipmenttoorder(o);
        log.error(o);
        log.info("Deleted and placed");
        return "Order placed";
    }

    public String returnOrder(String customerId, String orderId){
        Order o = this.orderRepository.findOrderById(customerId,orderId);
        o.setOrderStatus(false);
        this.orderRepository.save(o);
        return "Order Request Placed";
    }
}
