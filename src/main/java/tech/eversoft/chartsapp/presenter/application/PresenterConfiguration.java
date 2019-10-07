package tech.eversoft.chartsapp.presenter.application;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.eversoft.chartsapp.availability.domain.AvailabilityRepository;
import tech.eversoft.chartsapp.presenter.domain.DayByDayPresentationFactory;
import tech.eversoft.chartsapp.presenter.domain.SingleDayPresentationFactory;

@Configuration
@AllArgsConstructor
public class PresenterConfiguration {
    private AvailabilityRepository availabilityRepository;

    @Bean
    public DayByDayPresentationFactory dayByDayPresentationFactory() {
        return new DayByDayPresentationFactory(availabilityRepository);
    }

    @Bean
    public SingleDayPresentationFactory singleDayPresentationFactory() {
        return new SingleDayPresentationFactory(availabilityRepository);
    }

}
