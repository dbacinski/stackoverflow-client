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
}
