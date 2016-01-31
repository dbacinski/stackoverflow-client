package pl.dariuszbacinski.stackoverflow.search.view;

import me.tatarka.bindingcollectionadapter.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter.ItemViewArg;
import pl.dariuszbacinski.stackoverflow.search.viewmodel.QuestionItemViewModel;

public class QuestionListAdapter  extends BindingRecyclerViewAdapter<QuestionItemViewModel> {

    public QuestionListAdapter(ItemViewArg<QuestionItemViewModel> arg) {
        super(arg);
    }

}
