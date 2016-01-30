package pl.dariuszbacinski.stackoverflow.search.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import lombok.Data;

@Data
public class QuestionItemViewModel extends BaseObservable {
    //XXX lombok getters are not visible for data binding
    @Bindable
    public String title;
    @Bindable
    public String answerCount;
    @Bindable
    public boolean isAnswered;
    @Bindable
    public String link;
    @Bindable
    public String ownerName;
    @Bindable
    public String ownerImageUrl;
}
