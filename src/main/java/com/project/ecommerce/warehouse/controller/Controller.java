package com.project.ecommerce.warehouse.controller;
import com.project.ecommerce.Constants;
import com.project.ecommerce.exception.SHIPMENTEMPTYEXCEPTION;
import com.project.ecommerce.exception.WAREHOUSEEMPTYEXCEPTION;
import com.project.ecommerce.orders.entities.Order;
import com.project.ecommerce.products.entities.Product;
import com.project.ecommerce.warehouse.entities.Shipment;
import com.project.ecommerce.warehouse.repository.Warehousedao;
import com.project.ecommerce.warehouse.entities.Warehouse;
import com.project.ecommerce.warehouse.service.Warehouseservice;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;


@RestController
@Log4j2
@RequestMapping("/api/v1")
public class Controller {


    @Autowired
    private Warehouseservice warehouseservice;

    @Autowired
    private Warehousedao warehousedao;

    @PostMapping(Constants.ADD_NEW_WAREHOUSE)
    public Warehouse addNewWarehouse(@RequestBody Warehouse warehouse) {
        return this.warehouseservice.addnewwarehouseimpl(warehouse);
    }

    @GetMapping(Constants.GET_ALL_PRODUCTS_IN_WAREHOUSE)
    public List<Product> getproductsinwarehouse(@PathVariable Long warehouseId) {
        Warehouse warehouse = warehousedao.findById(warehouseId).get();
        return warehouse.getProducts();
    }
    @GetMapping(Constants.GET_PROFIT_SELL_OF_WAREHOUSE)
    public String getprofitfromwarehouse(@PathVariable Long warehouseId){
        Warehouse warehouse= warehousedao.findById(warehouseId).get();
        return "the overall quantity sell from " + warehouse.getName() + " is " + warehouse.getTotalQuantitySell() + " and Total profit earn is " + warehouse.getOverallSellWarehouse();
    }


    @GetMapping(Constants.SHOW_ALL_WAREHOUSE)
    public List<Warehouse> getAllWarehouse() throws WAREHOUSEEMPTYEXCEPTION {
        return this.warehouseservice.getallwarehouseimpl();
    }

    @GetMapping(Constants.GET_TOTAL_STOCK_OF_WAREHOUSE_BY_ID)
    public String getQuantity(@PathVariable String wareHouseId) {
        Long id = Long.parseLong(wareHouseId);
        return warehouseservice.getquantityimpl(id);

    }

    @DeleteMapping(Constants.DELETE_WAREHOUSE_BY_ID)
    public String deleteWarehouse(@PathVariable String wareHouseId) throws NoSuchElementException {
        Long id = Long.parseLong(wareHouseId);
        this.warehouseservice.deletewarehouseimpl(id);
        return "Done";
    }

    @PutMapping(Constants.ADD_STOCK_OF_PRODUCT)
    public String addStock(@PathVariable String productid ,@PathVariable Long stock) {


            return this.warehouseservice.addstockimpl(productid,stock);
        }



        @PutMapping(Constants.UPDATE_PRODUCT_AFTER_ORDER)
        public String updateProduct (@RequestBody Order order){

            return this.warehouseservice.Updateproduct(order);

        }

    @PutMapping(Constants.UPDATE_PROFIT_OF_WAREHOUSES_AFTER_ORDER)
    public String updateProfit(@RequestBody Order order){

        return this.warehouseservice.updateprofit(order);
    }

    @GetMapping(Constants.FIND_WAREHOUSE_FROM_PRODUCT)
    public String findwarehousefromproduct(@PathVariable String id){
                return this.warehouseservice.findwarehousefromproduct(id);
    }
    @PutMapping(Constants.UPDATE_AVAILABLE_STOCK_FROM_WAREHOUSE_STOCK)
    public String updateavailablequantity(){
        return this.warehouseservice.updateavailablequantity();
    }

    @GetMapping(Constants.GET_ALL_SHIPMENT)
    public List<Shipment> getallshipment() throws SHIPMENTEMPTYEXCEPTION {
        return this.warehouseservice.getallshipment();
    }

    @PutMapping(Constants.SET_SHIPMENT_TO_ORDER)
    public String addshipmenttoorder(@RequestBody Order order) {
        return this.warehouseservice.addshipmenttoorder(order);
        }



    @PostMapping(Constants.ADD_NEW_SHIPMENT)
    public Shipment addnewshipment(@RequestBody Shipment shipment){
        return this.warehouseservice.addnewshipment(shipment);
    }

    @GetMapping("/account/{warehouseId}")
    public String getoverallprofitofwarehouse(@PathVariable Long warehouseId){
        return this.warehouseservice.getoverallprofit(warehouseId);
    }


}