package pl.dariuszbacinski.stackoverflow.component
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.view.View
import android.widget.SearchView
import org.hamcrest.Matcher

import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import static org.hamcrest.Matchers.allOf

public class QueryAction implements ViewAction {
    private String stringToBeSet;

    public QueryAction(String value) {
        this.stringToBeSet = value;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Matcher<View> getConstraints() {
        return allOf(isDisplayed(), isAssignableFrom(SearchView.class));
    }

    @Override
    public void perform(UiController uiController, View view) {
        ((SearchView) view).setQuery(stringToBeSet, true);
    }

    @Override
    public String getDescription() {
        return "replace query";
    }
}

