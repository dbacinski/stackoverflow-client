package pl.dariuszbacinski.stackoverflow.search.model;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Owner {

    @SerializedName("display_name")
    String displayName;
    @SerializedName("profile_image")
    String profileImage;
}
