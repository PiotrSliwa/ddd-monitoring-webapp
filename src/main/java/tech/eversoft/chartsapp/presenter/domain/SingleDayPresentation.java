package tech.eversoft.chartsapp.presenter.domain;

import lombok.Data;
import lombok.NonNull;
import tech.eversoft.chartsapp.availability.domain.Location;

import java.util.Map;

@Data
public class SingleDayPresentation {
    @NonNull private final DayAvailability total;
    @NonNull private final Map<Location, DayAvailability> locationAvailabilities;
}
