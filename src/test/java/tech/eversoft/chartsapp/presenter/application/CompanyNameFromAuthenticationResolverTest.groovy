package tech.eversoft.chartsapp.presenter.application

import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import spock.lang.Specification

class CompanyNameFromAuthenticationResolverTest extends Specification {
    def "ResolveCompanyName"() {
        given:
        def authentication = Mock(Authentication) {
            getAuthorities() >> [
                    Mock(GrantedAuthority) { getAuthority() >> 'SOME_AUTHORITY_1' },
                    Mock(GrantedAuthority) { getAuthority() >> 'ACCESS_COMPANY_NAME' },
                    Mock(GrantedAuthority) { getAuthority() >> 'SOME_AUTHORITY_2' }
            ]
        }
        def cut = new CompanyNameFromAuthenticationResolver()

        expect:
        'COMPANY_NAME' == cut.resolveCompanyName(authentication)
    }
}
