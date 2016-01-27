package pl.dariuszbacinski.stackoverflow

import spock.lang.Specification

class SmokeSpockSpec extends Specification {

    def 'always pass'() {
        expect:
            true
    }
}