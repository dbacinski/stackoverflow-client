package pl.dariuszbacinski.stackoverflow.search.viewmodel;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.net.Uri;
import android.view.View;

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

    public void openDetails(View view) {
        //TODO send event to activity and then open details
        if (link != null && !link.isEmpty()) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(link));
            view.getContext().startActivity(i);
        }
    }
}
