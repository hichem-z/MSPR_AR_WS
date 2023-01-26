package fr.group.mspr_ar_ws.services;

import fr.group.mspr_ar_ws.models.Order;
import fr.group.mspr_ar_ws.utils.WSPath;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService extends AbstractService<Order>{
    public List<Order> getOrdersByClient(long idClient){
        String path = WSPath.CUSTOMERS.getName()+"/"+idClient+WSPath.ORDERS.getName();
        try{
            return  getList(path, Order[].class);
        }catch (IOException e) {
            logger.error("Error after getting orders of client "+idClient,e.getCause());
            return new ArrayList<>();
        }
    }
}
