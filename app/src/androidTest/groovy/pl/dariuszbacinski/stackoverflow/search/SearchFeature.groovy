package pl.dariuszbacinski.stackoverflow.search

import groovy.transform.TypeChecked
import pl.dariuszbacinski.stackoverflow.R
import pl.dariuszbacinski.stackoverflow.component.QueryAction

import static android.support.test.espresso.Espresso.onView
import static android.support.test.espresso.action.ViewActions.actionWithAssertions
import static android.support.test.espresso.matcher.ViewMatchers.withId

@TypeChecked
public class SearchFeature {

    public static void searchByQuestionName(String query) {
        onView withId(R.id.action_search) perform actionWithAssertions(new QueryAction(query))
    }
}
