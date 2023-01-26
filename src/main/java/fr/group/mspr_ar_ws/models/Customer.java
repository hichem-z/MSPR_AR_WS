package fr.group.mspr_ar_ws.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer {
    private long id;
    private String createdAt;
    private String name;
    private String username;
    private String firstName;
    private String lastName;
    private Address address;
    private Profile profile;
    private Company company;
    @JsonIgnore
    private List<Order> orders;

    public Customer(long id, String createdAt, String name, String username, String firstName, String lastName, Address address, Profile profile, Company company) {
        this.id = id;
        this.createdAt = createdAt;
        this.name = name;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.profile = profile;
        this.company = company;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public long getId() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Address getAddress() {
        return address;
    }

    public Profile getProfile() {
        return profile;
    }

    public Company getCompany() {
        return company;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Customer() {
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public static class Address{
        private String postalCode;
        private String city;

        public Address(String postalCode, String city) {
            this.postalCode = postalCode;
            this.city = city;
        }

        public Address() {
        }

        public String getPostalCode() {
            return postalCode;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }
    }
    public static class Profile{
        private String firstName;
        private String lastName;

        public Profile(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public Profile() {
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }
    public static class Company{
        private String companyName;

        public Company() {
        }

        public String getCompanyName() {
            return companyName;
        }

        public Company(String companyName) {
            this.companyName = companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }
    }
}

