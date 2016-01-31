package pl.dariuszbacinski.stackoverflow.smoke

import spock.lang.Specification

class SmokeSpockSpec extends Specification {

    def 'always pass'() {
        expect:
            true
    }
}