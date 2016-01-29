package pl.dariuszbacinski.stackoverflow.search

import android.support.test.rule.ActivityTestRule
import groovy.transform.TypeChecked
import org.junit.Rule
import pl.dariuszbacinski.stackoverflow.search.view.SearchActivity
import spock.lang.Specification

import static pl.dariuszbacinski.stackoverflow.search.SearchFeature.searchByQuestionName

@TypeChecked
public class SearchSpec extends Specification {

    @Rule
    ActivityTestRule<SearchActivity> searchActivityActivityTestRule = new ActivityTestRule(SearchActivity)

    def 'search by question name'() {
        given:
            def query = 'rxjava'
        when:
            searchByQuestionName query
        then:
            true //TODO verify list when ready
    }
}
