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
import pl.dariuszbacinski.stackoverflow.search.model.QuestionService;
import pl.dariuszbacinski.stackoverflow.search.viewmodel.QuestionViewModel;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

import static java.util.concurrent.TimeUnit.SECONDS;

public class SearchActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    CompositeSubscription subscriptions;
    QuestionViewModel questionViewModel = new QuestionViewModel(new QuestionService());
    ActivitySearchBinding searchBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subscriptions = new CompositeSubscription();
        searchBinding = ActivitySearchBinding.inflate(getLayoutInflater());
        searchBinding.setViewModel(questionViewModel);
        searchBinding.executePendingBindings();
        searchBinding.questionsRefresh.setOnRefreshListener(this);
        setSupportActionBar(searchBinding.toolbar);
        setContentView(searchBinding.getRoot());
    }

    @Override
    protected void onDestroy() {
        subscriptions.unsubscribe();
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
        subscriptions.add(RxSearchView.queryTextChanges(searchView).debounce(1L, SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new RequestQuestions()));
    }

    @Override
    public void onRefresh() {
        subscriptions.add(questionViewModel.searchWithStoredParameters());
    }

    @DebugLog
    class RequestQuestions implements Action1<CharSequence> {

        @Override
        public void call(CharSequence charSequence) {
            subscriptions.add(questionViewModel.searchByTitle(charSequence.toString()));
        }
    }
}
