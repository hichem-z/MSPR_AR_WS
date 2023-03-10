package fr.group.mspr_ar_ws.controllers;

import fr.group.mspr_ar_ws.models.Customer;
import fr.group.mspr_ar_ws.models.Order;
import fr.group.mspr_ar_ws.models.Product;
import fr.group.mspr_ar_ws.services.CustomerService;
import fr.group.mspr_ar_ws.services.OrderService;
import fr.group.mspr_ar_ws.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/crm")
public class CrmController {

    @Autowired
    CustomerService customerService;
    @Autowired
    ProductService productService;
    @Autowired
    OrderService orderService;
    @GetMapping("/customers")
    @PreAuthorize("hasRole('WEB_SHOP')")
    public ResponseEntity<List<Customer>> getCustomers(){
        return ResponseEntity.ok(customerService.getCustomers());
    }
    @GetMapping("/customers/{id_client}/orders")
    @PreAuthorize("hasRole('WEB_SHOP')")
    public ResponseEntity<List<Order>> getOrdersOfClient(@PathVariable long id_client){
        return ResponseEntity.ok(orderService.getOrdersByClient(id_client));
    }
    @GetMapping("/customers/{id_client}/orders/{id_order}/products")
    @PreAuthorize("hasRole('WEB_SHOP')")
    public ResponseEntity<List<Product>> getProductsOfOrder(@PathVariable long id_client,@PathVariable long id_order){
        return ResponseEntity.ok(productService.getProductsOfOrder(id_client, id_order));
    }

}
