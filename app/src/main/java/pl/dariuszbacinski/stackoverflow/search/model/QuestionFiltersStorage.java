package pl.dariuszbacinski.stackoverflow.search.model;

public class QuestionFiltersStorage {
    String query = "";
    Sort sort = Sort.ACTIVITY;
    Order order = Order.ASCENDING;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getSort() {
        return sort.toString();
    }

    public void setSort(String sort) {
        this.sort = Sort.fromString(sort);
    }

    public String getOrder() {
        return order.toString();
    }

    public void setOrder(String order) {
        this.order = Order.fromString(order);
    }
}
