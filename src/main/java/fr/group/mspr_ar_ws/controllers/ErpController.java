package fr.group.mspr_ar_ws.controllers;

import fr.group.mspr_ar_ws.models.Product;
import fr.group.mspr_ar_ws.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/erp")
public class ErpController {

    @Autowired
    ProductService productService;

    @GetMapping("/products")
    @PreAuthorize("hasRole('RETAILER')")
    public ResponseEntity<List<Product>> getProducts(){
        return ResponseEntity.ok(productService.getProducts());
    }
    @GetMapping("/products/{id_product}")
    @PreAuthorize("hasRole('RETAILER')")
    public ResponseEntity<Product> getProduct(@PathVariable long id_product){
        return ResponseEntity.ok(productService.getProductDetails(id_product));
    }
}
