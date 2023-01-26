package fr.group.mspr_ar_ws.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractService<T> {
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
    protected T getObject(String path, Class<T> c) throws IOException {
        HttpGet httpGet = new HttpGet(baseUrl+path);
        ResponseHandler<String> response = CustomerService::handleResponse;
        String json = getClosable().execute(httpGet,response);
        if (json!=null) {
            ObjectMapper o = new ObjectMapper();
            return o.readValue(json, c);
        }
        return null;
    }
    protected List<T> getList(String path, Class<T[]> c) throws IOException {
        HttpGet httpGet = new HttpGet(baseUrl+path);
        ResponseHandler<String> response = CustomerService::handleResponse;
        String json = getClosable().execute(httpGet,response);
        if (json!=null) {
            ObjectMapper o = new ObjectMapper();
            return List.of(o.readValue(json, c));
        }
        return null;
    }
    protected CloseableHttpClient getClosable(){
        return HttpClients.createDefault();
    }
}
