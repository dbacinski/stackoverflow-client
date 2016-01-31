package pl.dariuszbacinski.stackoverflow.smoke

import android.support.test.rule.ActivityTestRule
import org.junit.Rule
import pl.dariuszbacinski.stackoverflow.search.view.SearchActivity
import spock.lang.Specification

public class SmokeSpockSpec extends Specification {

    @Rule
    ActivityTestRule<SearchActivity> searchActivityActivityTestRule = new ActivityTestRule(SearchActivity)

    def 'always pass'() {
        expect:
            searchActivityActivityTestRule.activity
    }
}