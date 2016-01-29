package pl.dariuszbacinski.stackoverflow.search.model

import rx.Observable
import spock.lang.Specification

class QuestionServiceSpec extends Specification {

    QuestionApiService questionApiServiceMock = Mock()

    def "validate null query"() {
        given:
            QuestionService objectUnderTest = new QuestionService(questionApiServiceMock)
        when:
            objectUnderTest.searchByTitle(null, Sort.ACTIVITY, Order.ASCENDING)
        then:
            thrown IllegalArgumentException
    }

    def "validate empty query"() {
        given:
            QuestionService objectUnderTest = new QuestionService(questionApiServiceMock)
        when:
            objectUnderTest.searchByTitle(null, Sort.ACTIVITY, Order.ASCENDING)
        then:
            thrown IllegalArgumentException
    }

    def "validate correct query"() {
        given:
            QuestionService objectUnderTest = new QuestionService(questionApiServiceMock)
            questionApiServiceMock.searchByTitle(_ as String, _ as Sort, _ as Order) >> Observable.empty()
        when:
            objectUnderTest.searchByTitle("rxjava", Sort.ACTIVITY, Order.ASCENDING)
        then:
            notThrown IllegalArgumentException
    }
}
