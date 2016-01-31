package pl.dariuszbacinski.stackoverflow.search.viewmodel;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import lombok.Data;
import pl.dariuszbacinski.stackoverflow.search.view.SearchActivity;

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

    public void openDetails(View view) {
        //TODO send event to activity and then open details
        //TODO launch details activity
        view.getContext().startActivity(new Intent(view.getContext(), SearchActivity.class));
    }
}
