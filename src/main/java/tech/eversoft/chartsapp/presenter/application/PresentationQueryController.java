package tech.eversoft.chartsapp.presenter.application;

import org.springframework.beans.factory.annotation.Autowired;
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

    @CrossOrigin("*")
    @PostMapping("/presentation/dayByDay")
    public DayByDayPresentation getDayByDayPresentation(@RequestBody TimeframeQuery timeframeQuery) {
        return dayByDayPresentationFactory.create(timeframeQuery.getBeginning().atStartOfDay(), timeframeQuery.getEnd().atStartOfDay());
    }

    @CrossOrigin("*")
    @PostMapping("/presentation/singleDay")
    public SingleDayPresentation singleDayPresentation(@RequestBody DateQuery dateQuery) {
        return singleDayPresentationFactory.create(dateQuery.getDate().atStartOfDay());
    }

}
