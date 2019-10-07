package tech.eversoft.chartsapp.presenter.domain;

import lombok.Data;
import lombok.NonNull;
import tech.eversoft.chartsapp.availability.domain.AvailabilityIndex;

import java.util.List;

@Data
public class DayByDayPresentation {
    @NonNull private final List<AvailabilityIndex> dayByDayAvailabilities;
}
