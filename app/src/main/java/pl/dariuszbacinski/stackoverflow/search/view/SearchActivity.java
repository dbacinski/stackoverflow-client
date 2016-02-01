package pl.dariuszbacinski.stackoverflow.search.view;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import com.jakewharton.rxbinding.widget.RxSearchView;

import hugo.weaving.DebugLog;
import pl.dariuszbacinski.stackoverflow.R;
import pl.dariuszbacinski.stackoverflow.databinding.ActivitySearchBinding;
import pl.dariuszbacinski.stackoverflow.search.model.QuestionFiltersStorage;
import pl.dariuszbacinski.stackoverflow.search.model.QuestionService;
import pl.dariuszbacinski.stackoverflow.search.viewmodel.QuestionViewModel;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.Subscriptions;

import static java.util.concurrent.TimeUnit.SECONDS;

public class SearchActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String KEY_QUERY = "query";
    private static final String KEY_ORDER = "order";
    private static final String KEY_SORT = "sort";
    Subscription networkSubscription = Subscriptions.empty();
    Subscription inputSubscription = Subscriptions.empty();
    QuestionViewModel questionViewModel;
    ActivitySearchBinding searchBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchBinding = ActivitySearchBinding.inflate(getLayoutInflater());
        questionViewModel = new QuestionViewModel(new QuestionService());
        questionViewModel.restoreState(getQueryFilterStorage(savedInstanceState));
        searchBinding.setViewModel(questionViewModel);
        searchBinding.executePendingBindings();
        searchBinding.questionsRefresh.setOnRefreshListener(this);
        setSupportActionBar(searchBinding.toolbar);
        setContentView(searchBinding.getRoot());
    }

    private QuestionFiltersStorage getQueryFilterStorage(Bundle savedInstanceState) {
        QuestionFiltersStorage questionFiltersStorage = new QuestionFiltersStorage();

        if (savedInstanceState != null) {
            questionFiltersStorage.setQuery(savedInstanceState.getString(KEY_QUERY));
            questionFiltersStorage.setSort(savedInstanceState.getString(KEY_SORT));
            questionFiltersStorage.setOrder(savedInstanceState.getString(KEY_ORDER));
        }

        return questionFiltersStorage;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        QuestionFiltersStorage questionFiltersStorage = questionViewModel.saveState();
        savedInstanceState.putString(KEY_QUERY, questionFiltersStorage.getQuery());
        savedInstanceState.putString(KEY_SORT, questionFiltersStorage.getSort());
        savedInstanceState.putString(KEY_ORDER, questionFiltersStorage.getOrder());
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        networkSubscription.unsubscribe();
        inputSubscription.unsubscribe();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI | EditorInfo.IME_FLAG_NO_FULLSCREEN | EditorInfo.IME_ACTION_SEARCH);
        searchView.setIconified(false);
        subscribeToSearchViewQueries(searchView);
        return true;
    }

    void subscribeToSearchViewQueries(SearchView searchView) {
        inputSubscription = RxSearchView.queryTextChanges(searchView).debounce(1L, SECONDS).skip(1).observeOn(AndroidSchedulers.mainThread()).subscribe(new RequestQuestions());
    }

    @Override
    public void onRefresh() {
        setNetworkSubscription(questionViewModel.searchWithStoredParameters());
    }

    @DebugLog
    void setNetworkSubscription(Subscription subscription) {
        this.networkSubscription.unsubscribe();
        this.networkSubscription = subscription;
    }

    @DebugLog
    class RequestQuestions implements Action1<CharSequence> {

        @Override
        public void call(CharSequence charSequence) {
            setNetworkSubscription(questionViewModel.searchByTitle(charSequence.toString()));
        }
    }
}
