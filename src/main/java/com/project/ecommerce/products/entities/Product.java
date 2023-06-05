package com.project.ecommerce.products.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String  name;
    private String description ;
    @Column(name = "product_price")
    private long productPrice;
    private String size;
    private String colour;
    private String brand;
    private long availQuantity;
    private long warehouseStock;

}
