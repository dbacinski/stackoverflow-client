package pl.dariuszbacinski.stackoverflow.search.model;

public enum Order {

    ASCENDING("asc"),
    DESCENDING("desc");

    final String code;

    Order(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }

    public static Order fromString(String orderString) {
        if (orderString != null) {
            for (Order order : Order.values()) {
                if (orderString.equalsIgnoreCase(order.code)) {
                    return order;
                }
            }
        }
        return ASCENDING;
    }
}
