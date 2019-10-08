package tech.eversoft.chartsapp.presenter.application;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class CompanyNameFromAuthenticationResolver {

    public String resolveCompanyName(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .filter(a -> a.getAuthority().matches("^ACCESS_.+"))
                .findFirst()
                .orElseThrow()
                .getAuthority()
                .replaceFirst("^ACCESS_", "");
    }

}
