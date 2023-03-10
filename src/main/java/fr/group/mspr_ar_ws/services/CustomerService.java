package fr.group.mspr_ar_ws.services;

import fr.group.mspr_ar_ws.models.Customer;
import fr.group.mspr_ar_ws.models.Order;
import fr.group.mspr_ar_ws.models.Product;
import fr.group.mspr_ar_ws.utils.WSPath;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService extends AbstractService<Customer>{


    public List<Customer> getCustomers(){
        try{
            return  getList(WSPath.CUSTOMERS.getName(), Customer[].class);
        }catch (IOException e) {
            logger.error("Error after getting customers list",e.getCause());
            return new ArrayList<>();
        }
    }
}
