package pl.dariuszbacinski.stackoverflow.search.viewmodel

import org.robospock.RoboSpecification
import pl.dariuszbacinski.stackoverflow.search.model.*
import rx.Observable
import rx.observers.TestSubscriber

class QuestionViewModelSpec extends RoboSpecification {

    QuestionService questionServiceMock = Mock()

    def "do not call service for empty query"() {
        given:
            QuestionViewModel objectUnderTest = [questionServiceMock]
        when:
            objectUnderTest.searchByTitle("")
        then:
            0 * questionServiceMock.searchByTitle(_ as String, _ as Sort, _ as Order)
    }

    def "do not call service for null query"() {
        given:
            QuestionViewModel objectUnderTest = [questionServiceMock]
        when:
            objectUnderTest.searchByTitle(null)
        then:
            0 * questionServiceMock.searchByTitle(_ as String, _ as Sort, _ as Order)
    }

    def "clear questions list for empty query"() {
        given:
            QuestionViewModel objectUnderTest = [questionServiceMock]
            objectUnderTest.questions.add(new QuestionItemViewModel())
        when:
            objectUnderTest.searchByTitle("")
        then:
            objectUnderTest.questions.isEmpty()
    }

    def "update questions for valid query"() {
        given:
            QuestionViewModel objectUnderTest = [questionServiceMock]
            Question question = new Question()
            question.owner = new Owner()
            question.owner.displayName = "John"
            questionServiceMock.searchByTitle(_ as String, _ as Sort, _ as Order) >> Observable.just(question)
            TestSubscriber<List<QuestionItemViewModel>> subscriber = new TestSubscriber<>()
        when:
            objectUnderTest.reloadQuestionsObservable("any", Sort.ACTIVITY, Order.ASCENDING).subscribe(subscriber)
        then:
            subscriber.awaitTerminalEvent()
            subscriber.assertNoErrors()
            subscriber.getOnNextEvents().first().first().ownerName == "John"
    }
}
