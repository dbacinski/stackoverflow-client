package pl.dariuszbacinski.stackoverflow.search.model;

public enum Order {

    ASCENDING("asc"),
    DESCENDING("desc");

    private final String code;

    Order(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
