package tech.eversoft.chartsapp.presenter.application;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.eversoft.chartsapp.availability.domain.AvailabilityRepository;
import tech.eversoft.chartsapp.presenter.domain.DayByDayPresentationRepository;
import tech.eversoft.chartsapp.presenter.domain.SingleDayPresentationRepository;

@Configuration
@AllArgsConstructor
public class PresenterConfiguration {
    private AvailabilityRepository availabilityRepository;

    @Bean
    public DayByDayPresentationRepository dayByDayPresentationFactory() {
        return new DayByDayPresentationRepository(availabilityRepository);
    }

    @Bean
    public SingleDayPresentationRepository singleDayPresentationFactory() {
        return new SingleDayPresentationRepository(availabilityRepository);
    }

}
