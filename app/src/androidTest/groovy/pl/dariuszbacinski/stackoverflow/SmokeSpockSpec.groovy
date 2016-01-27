package pl.dariuszbacinski.stackoverflow

import android.support.test.rule.ActivityTestRule
import org.junit.Rule
import spock.lang.Specification

public class SmokeSpockSpec extends Specification {

    @Rule
    ActivityTestRule<SearchActivity> locationActivityRule = new ActivityTestRule(SearchActivity)

    def 'always pass'() {
        expect:
            searchActivityActivityTestRule.activity
    }
}