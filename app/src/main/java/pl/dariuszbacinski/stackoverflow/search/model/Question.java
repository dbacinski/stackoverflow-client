package pl.dariuszbacinski.stackoverflow.search.model;

import com.google.gson.annotations.SerializedName;

import lombok.Data;


@Data
public class Question {

    @SerializedName("title")
    String title;
    @SerializedName("answer_count")
    String answerCount;
    @SerializedName("is_answered")
    String isAnswered;
    @SerializedName("link")
    String link;
    @SerializedName("owner")
    Owner owner;
}
