package pl.dariuszbacinski.stackoverflow.search.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class ResponseContainer<T> {

    @SerializedName("items")
    List<T> items;
    @SerializedName("has_more")
    boolean hasMore;
}
