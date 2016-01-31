package pl.dariuszbacinski.stackoverflow.search.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import java.util.Collections;
import java.util.List;

import hugo.weaving.DebugLog;
import me.tatarka.bindingcollectionadapter.ItemView;
import pl.dariuszbacinski.stackoverflow.BR;
import pl.dariuszbacinski.stackoverflow.R;
import pl.dariuszbacinski.stackoverflow.search.model.Order;
import pl.dariuszbacinski.stackoverflow.search.model.Question;
import pl.dariuszbacinski.stackoverflow.search.model.QuestionService;
import pl.dariuszbacinski.stackoverflow.search.model.Sort;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

import static pl.dariuszbacinski.stackoverflow.search.model.Order.ASCENDING;
import static pl.dariuszbacinski.stackoverflow.search.model.Sort.ACTIVITY;

public class QuestionViewModel {

    final QuestionService questionService;
    final ObservableList<QuestionItemViewModel> questions = new ObservableArrayList<>();
    final ItemView itemView = ItemView.of(BR.itemViewModel, R.layout.list_item_question);
    Order order = ASCENDING;
    Sort sort = ACTIVITY;
    String query = "";

    public QuestionViewModel(QuestionService questionService) {
        this.questionService = questionService;
    }

    public Subscription searchByTitle(String query) {
        this.query = query;
        return reloadQuestionsObservable(query, sort, order).subscribe(new QuestionSubscriber(questions));
    }

    //TODO create UI
    public Subscription changeOrder(Order order) {
        this.order = order;
        return reloadQuestionsObservable(query, sort, order).subscribe(new QuestionSubscriber(questions));
    }

    //TODO create UI
    public Subscription changeSort(Sort sort) {
        this.sort = sort;
        return reloadQuestionsObservable(query, sort, order).subscribe(new QuestionSubscriber(questions));
    }

    Observable<List<QuestionItemViewModel>> reloadQuestionsObservable(String query, Sort sort, Order order) {
        if (query != null && !query.isEmpty()) {
            return questionService.searchByTitle(query, sort, order)
                    .map(new MapQuestionToViewModel())
                    .toList()
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            return Observable.just(Collections.<QuestionItemViewModel>emptyList());
        }
    }

    public ObservableList<QuestionItemViewModel> getQuestions() {
        return questions;
    }

    public ItemView getItemView() {
        return itemView;
    }

    @DebugLog
    static class QuestionSubscriber extends Subscriber<List<QuestionItemViewModel>> {

        ObservableList<QuestionItemViewModel> questions;

        public QuestionSubscriber(ObservableList<QuestionItemViewModel> questions) {
            this.questions = questions;
        }

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            //TODO show error on UI
            questions.clear();
        }

        @Override
        public void onNext(List<QuestionItemViewModel> newQuestions) {
            questions.clear();
            questions.addAll(newQuestions);
        }
    }

    static class MapQuestionToViewModel implements Func1<Question, QuestionItemViewModel> {
        @Override
        public QuestionItemViewModel call(Question question) {
            QuestionItemViewModel itemViewModel = new QuestionItemViewModel();
            itemViewModel.ownerName = question.getOwner().getDisplayName();
            itemViewModel.ownerImageUrl = question.getOwner().getProfileImage();
            itemViewModel.title = question.getTitle();
            itemViewModel.answerCount = Integer.toString(question.getAnswerCount());
            itemViewModel.isAnswered = question.isAnswered();
            itemViewModel.link = question.getLink();
            return itemViewModel;
        }
    }
}
