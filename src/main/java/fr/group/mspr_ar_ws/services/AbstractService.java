package fr.group.mspr_ar_ws.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.group.mspr_ar_ws.models.Customer;
import fr.group.mspr_ar_ws.security.MsprJwtUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class AbstractService {
    protected static final Logger logger = LoggerFactory.getLogger(MsprJwtUtils.class);

    @Value("${app.baseUrl}")
    protected String baseUrl;

    protected static String handleResponse(HttpResponse httpResponse) throws IOException {
        int status = httpResponse.getStatusLine().getStatusCode();
        if (status>=200 && status<=300){
            HttpEntity httpEntity = httpResponse.getEntity();
            return httpEntity!=null ? EntityUtils.toString(httpEntity):null;
        }else{
            logger.warn("Error when getting response from crm api !",status);
            return null;
        }
    }
    protected List<?> getResponse(String path,Class c) throws IOException {
        HttpGet httpGet = new HttpGet(baseUrl+path);
        ResponseHandler<String> response = CustomerService::handleResponse;
        String json = getClosable().execute(httpGet,response);
        if (json!=null) {
            ObjectMapper o = new ObjectMapper();
            return Collections.singletonList(o.readValue(json, c));
        }
        return null;
    }

    protected CloseableHttpClient getClosable(){
        return HttpClients.createDefault();
    }
}
