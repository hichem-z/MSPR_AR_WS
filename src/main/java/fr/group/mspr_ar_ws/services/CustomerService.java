package fr.group.mspr_ar_ws.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.group.mspr_ar_ws.models.Customer;
import fr.group.mspr_ar_ws.models.Order;
import fr.group.mspr_ar_ws.models.Product;
import fr.group.mspr_ar_ws.security.MsprJwtUtils;
import fr.group.mspr_ar_ws.utils.WSPath;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CustomerService extends AbstractService{


    public List<Customer> getCustomers(){
        try{
            return  (List<Customer>) getResponse(WSPath.CUSTOMERS.getName(), Customer[].class);
        }catch (IOException e) {
            logger.error("Error after getting customers list",e.getCause());
            return new ArrayList<>();
        }
    }
    public List<Order> getOrdersByClient(long idClient){
        String path = WSPath.CUSTOMERS.getName()+"/"+idClient+WSPath.ORDERS.getName();
        try{
            return (List<Order>) getResponse(path, Order[].class);
        }catch (IOException e) {
            logger.error("Error after getting orders of client "+idClient,e.getCause());
            return new ArrayList<>();
        }

    }
    public List<Product> getProductsOfOrder(long idClient,long idOrder){
        String path = WSPath.CUSTOMERS.getName()+"/"+idClient+WSPath.ORDERS.getName()+"/"+idOrder+WSPath.PRODUCTS.getName();
        try {
            return (List<Product>) getResponse(path, Product[].class);
        } catch (IOException e) {
            logger.error("Error after getting products of order "+idOrder,e.getCause());
            return new ArrayList<>();
        }

    }
}
