package fr.group.mspr_ar_ws.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.lang.Nullable;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {
    private long id;
    private String createdAt;
    private String title;
    private long customerId;

    public Order(long id, String createdAt, String title, long customerId) {
        this.id = id;
        this.createdAt = createdAt;
        this.title = title;
        this.customerId = customerId;
    }

    public Order() {
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getId() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getTitle() {
        return title;
    }

    public long getCustomerId() {
        return customerId;
    }
}
