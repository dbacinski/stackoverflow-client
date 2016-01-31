package pl.dariuszbacinski.stackoverflow.search.model;

public enum Sort {

    ACTIVITY("activity"),
    VOTES("votes"),
    CREATION("creation"),
    RELEVANCE("relevance");

    private final String code;

    Sort(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }

    public static Sort fromString(String sortString) {
        if (sortString != null) {
            for (Sort sort : Sort.values()) {
                if (sortString.equalsIgnoreCase(sort.code)) {
                    return sort;
                }
            }
        }
        return ACTIVITY;
    }
}
