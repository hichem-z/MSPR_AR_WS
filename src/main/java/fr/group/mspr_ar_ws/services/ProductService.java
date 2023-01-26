package fr.group.mspr_ar_ws.services;

import fr.group.mspr_ar_ws.models.Product;
import fr.group.mspr_ar_ws.utils.WSPath;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService extends AbstractService<Product>{
    public List<Product> getProducts(){
        try{
            return  getList(WSPath.PRODUCTS.getName(), Product[].class);
        }catch (IOException e) {
            logger.error("Error after getting products list",e.getCause());
            return new ArrayList<>();
        }
    }
    public Product getProductDetails(long idProduct){
        String path = WSPath.PRODUCTS.getName()+"/"+idProduct;
        try{
            return getObject(path, Product.class);
        }catch (IOException e) {
            logger.error("Error after getting details of product "+idProduct,e.getCause());
            return new Product();
        }

    }
    public List<Product> getProductsOfOrder(long idClient,long idOrder){
        String path = WSPath.CUSTOMERS.getName()+"/"+idClient+WSPath.ORDERS.getName()+"/"+idOrder+WSPath.PRODUCTS.getName();
        try {
            return getList(path, Product[].class);
        } catch (IOException e) {
            logger.error("Error after getting products of order "+idOrder,e.getCause());
            return new ArrayList<>();
        }
    }
}
