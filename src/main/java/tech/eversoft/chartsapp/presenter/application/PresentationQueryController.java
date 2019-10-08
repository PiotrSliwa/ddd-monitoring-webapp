package tech.eversoft.chartsapp.presenter.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tech.eversoft.chartsapp.presenter.domain.DayByDayPresentation;
import tech.eversoft.chartsapp.presenter.domain.DayByDayPresentationFactory;
import tech.eversoft.chartsapp.presenter.domain.SingleDayPresentation;
import tech.eversoft.chartsapp.presenter.domain.SingleDayPresentationFactory;

@RestController
public class PresentationQueryController {
    @Autowired
    private DayByDayPresentationFactory dayByDayPresentationFactory;

    @Autowired
    private SingleDayPresentationFactory singleDayPresentationFactory;

    @Autowired
    private CompanyNameFromAuthenticationResolver resolver;

    @PreAuthorize("hasAuthority('DAY_BY_DAY_VIEWER')")
    @CrossOrigin("*")
    @PostMapping("/presentation/dayByDay")
    public DayByDayPresentation getDayByDayPresentation(@RequestBody TimeframeQuery timeframeQuery,
                                                        Authentication authentication) {
        var companyName = resolver.resolveCompanyName(authentication);
        return dayByDayPresentationFactory.create(companyName,
                                                  timeframeQuery.getBeginning().atStartOfDay(),
                                                  timeframeQuery.getEnd().atStartOfDay());
    }

    @PreAuthorize("hasAuthority('SINGLE_DAY_VIEWER')")
    @CrossOrigin("*")
    @PostMapping("/presentation/singleDay")
    public SingleDayPresentation singleDayPresentation(@RequestBody DateQuery dateQuery, Authentication authentication) {
        var companyName = resolver.resolveCompanyName(authentication);
        return singleDayPresentationFactory.create(companyName,
                                                   dateQuery.getDate().atStartOfDay());
    }

}
