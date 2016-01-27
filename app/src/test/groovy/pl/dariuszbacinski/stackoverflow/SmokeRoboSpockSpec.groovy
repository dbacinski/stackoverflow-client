package pl.dariuszbacinski.stackoverflow

import org.robospock.RoboSpecification

class SmokeRoboSpockSpec extends RoboSpecification {

    def 'always pass'() {
        expect:
            true
    }
}