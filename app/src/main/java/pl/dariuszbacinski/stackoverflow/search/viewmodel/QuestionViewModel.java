package pl.dariuszbacinski.stackoverflow.search.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;

import java.util.Collections;
import java.util.List;

import hugo.weaving.DebugLog;
import me.tatarka.bindingcollectionadapter.ItemView;
import pl.dariuszbacinski.stackoverflow.BR;
import pl.dariuszbacinski.stackoverflow.R;
import pl.dariuszbacinski.stackoverflow.search.model.Order;
import pl.dariuszbacinski.stackoverflow.search.model.Question;
import pl.dariuszbacinski.stackoverflow.search.model.QuestionFiltersStorage;
import pl.dariuszbacinski.stackoverflow.search.model.QuestionService;
import pl.dariuszbacinski.stackoverflow.search.model.Sort;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

import static pl.dariuszbacinski.stackoverflow.search.model.Order.ASCENDING;
import static pl.dariuszbacinski.stackoverflow.search.model.Sort.ACTIVITY;

public class QuestionViewModel extends BaseObservable{

    final QuestionService questionService;
    final ObservableList<QuestionItemViewModel> questions = new ObservableArrayList<>();
    final ItemView itemView = ItemView.of(BR.itemViewModel, R.layout.list_item_question);
    final ObservableBoolean loading = new ObservableBoolean(false);
    OrderViewModel order = new OrderViewModel(ASCENDING);
    SortViewModel sort = new SortViewModel(ACTIVITY);
    String query = "";

    public QuestionViewModel(QuestionService questionService) {
        this.questionService = questionService;
    }

    public Subscription searchByTitle(String query) {
        this.query = query;
        return searchWithStoredParameters();
    }

    public Subscription changeOrder(Order order) {
        this.order.setOrder(order);
        return searchWithStoredParameters();
    }

    public Subscription changeSort(Sort sort) {
        this.sort.setSort(sort);
        return searchWithStoredParameters();
    }

    public Subscription searchWithStoredParameters() {
        return reloadQuestionsObservable(query, sort.getSort(), order.getOrder()).subscribe(new QuestionSubscriber(questions, loading));
    }

    public QuestionFiltersStorage saveState() {
        QuestionFiltersStorage questionFiltersStorage = new QuestionFiltersStorage();
        questionFiltersStorage.setQuery(query);
        questionFiltersStorage.setSort(sort.getSort().toString());
        questionFiltersStorage.setOrder(order.getOrder().toString());
        return questionFiltersStorage;
    }

    public void restoreState(QuestionFiltersStorage questionFiltersStorage) {
        query = questionFiltersStorage.getQuery();
        order.setOrder(Order.fromString(questionFiltersStorage.getOrder()));
        sort.setSort(Sort.fromString(questionFiltersStorage.getSort()));
        searchWithStoredParameters();
    }

    Observable<List<QuestionItemViewModel>> reloadQuestionsObservable(String query, Sort sort, Order order) {
        loading.set(true);
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

    public ObservableBoolean getLoading() {
        return loading;
    }

    public OrderViewModel getOrder() {
        return order;
    }

    public SortViewModel getSort() {
        return sort;
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

    @DebugLog
    static class QuestionSubscriber extends Subscriber<List<QuestionItemViewModel>> {

        ObservableList<QuestionItemViewModel> questions;
        ObservableBoolean loading;

        public QuestionSubscriber(ObservableList<QuestionItemViewModel> questions, ObservableBoolean loading) {
            this.questions = questions;
            this.loading = loading;
        }

        @Override
        public void onCompleted() {
            loading.set(false);
        }

        @Override
        public void onError(Throwable e) {
            //TODO show error on UI
            loading.set(false);
            questions.clear();
        }

        @Override
        public void onNext(List<QuestionItemViewModel> newQuestions) {
            questions.clear();
            questions.addAll(newQuestions);
        }
    }
}
