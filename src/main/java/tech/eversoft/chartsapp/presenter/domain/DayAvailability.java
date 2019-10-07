package tech.eversoft.chartsapp.presenter.domain;

import lombok.Data;
import lombok.NonNull;
import tech.eversoft.chartsapp.availability.domain.AvailabilityIndex;
import tech.eversoft.chartsapp.availability.domain.RoomStatistics;

@Data
public class DayAvailability {
    @NonNull private final AvailabilityIndex index;
    @NonNull private final AvailabilityDifference lastMonthDifference;
    @NonNull private final RoomStatistics roomStatistics;
}
