package pl.dariuszbacinski.stackoverflow.search;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import com.jakewharton.rxbinding.widget.RxSearchView;

import hugo.weaving.DebugLog;
import pl.dariuszbacinski.stackoverflow.R;
import pl.dariuszbacinski.stackoverflow.databinding.ActivitySearchBinding;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class SearchActivity extends AppCompatActivity {

    CompositeSubscription subscriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subscriptions = new CompositeSubscription();
        ActivitySearchBinding searchBinding = ActivitySearchBinding.inflate(getLayoutInflater());
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
        subscriptions.add(RxSearchView.queryTextChanges(searchView).throttleLast(300L, MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new RequestQuestions()));
    }

    @DebugLog
    class RequestQuestions implements Action1<CharSequence> {

        @Override
        public void call(CharSequence charSequence) {
            //TODO pass call to model

        }
    }
}
