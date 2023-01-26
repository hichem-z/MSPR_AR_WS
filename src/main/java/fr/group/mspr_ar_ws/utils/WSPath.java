package fr.group.mspr_ar_ws.utils;

public enum WSPath {
    CUSTOMERS("/customers"),
    ORDERS("/orders"),
    PRODUCTS("/products");

    private String name ;

    WSPath(String s) {
        this.name = s;
    }

    public String getName() {
        return name;
    }
}
