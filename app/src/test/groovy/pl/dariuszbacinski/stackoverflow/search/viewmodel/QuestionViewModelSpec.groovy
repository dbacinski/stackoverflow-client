package pl.dariuszbacinski.stackoverflow.search.viewmodel

import org.robospock.RoboSpecification
import pl.dariuszbacinski.stackoverflow.search.model.Order
import pl.dariuszbacinski.stackoverflow.search.model.Question
import pl.dariuszbacinski.stackoverflow.search.model.QuestionService
import pl.dariuszbacinski.stackoverflow.search.model.Sort
import rx.Observable

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
            objectUnderTest.questions.add(new Question())
        when:
            objectUnderTest.searchByTitle("")
        then:
            objectUnderTest.questions.isEmpty()
    }

    def "update questions for valid query"() {
        given:
            QuestionViewModel objectUnderTest = [questionServiceMock]
            Question expectedQuestion = new Question()
            questionServiceMock.searchByTitle(_ as String, _ as Sort, _ as Order) >> Observable.just([expectedQuestion])
        when:
            objectUnderTest.searchByTitle("test")
        then:
            objectUnderTest.questions.contains(expectedQuestion)
    }
}
