package pl.dariuszbacinski.stackoverflow.search.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import java.util.List;

import hugo.weaving.DebugLog;
import pl.dariuszbacinski.stackoverflow.search.model.Order;
import pl.dariuszbacinski.stackoverflow.search.model.Question;
import pl.dariuszbacinski.stackoverflow.search.model.QuestionService;
import pl.dariuszbacinski.stackoverflow.search.model.Sort;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

import static pl.dariuszbacinski.stackoverflow.search.model.Order.ASCENDING;
import static pl.dariuszbacinski.stackoverflow.search.model.Sort.ACTIVITY;

public class QuestionViewModel {

    final QuestionService questionService;
    ObservableList<Question> questions = new ObservableArrayList<>();
    Order order = ASCENDING;
    Sort sort = ACTIVITY;
    String query = "";

    public QuestionViewModel(QuestionService questionService) {
        this.questionService = questionService;
    }

    public Subscription searchByTitle(String query) {
        this.query = query;
        return reloadQuestions(query, order, sort);
    }

    //TODO create UI
    public Subscription changeOrder(Order order) {
        this.order = order;
        return reloadQuestions(query, order, sort);
    }

    //TODO create UI
    public Subscription changeSort(Sort sort) {
        this.sort = sort;
        return reloadQuestions(query, order, sort);
    }

    private Subscription reloadQuestions(String query, Order order, Sort sort) {
        if (query != null && !query.isEmpty()) {
            return questionService.searchByTitle(query, sort, order).subscribe(new QuestionSubscriber(questions));
        } else {
            questions.clear();
            return Subscriptions.empty();
        }
    }

    @DebugLog
    static class QuestionSubscriber extends Subscriber<List<Question>> {

        ObservableList<Question> questions;

        public QuestionSubscriber(ObservableList<Question> questions) {
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
        public void onNext(List<Question> newQuestions) {
            questions.clear();
            questions.addAll(newQuestions);
        }
    }
}
