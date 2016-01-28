package pl.dariuszbacinski.stackoverflow.smoke

import org.robospock.RoboSpecification

class SmokeRoboSpockSpec extends RoboSpecification {

    def 'always pass'() {
        expect:
            true
    }
}