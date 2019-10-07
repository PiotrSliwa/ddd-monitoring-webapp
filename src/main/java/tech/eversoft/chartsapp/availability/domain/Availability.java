package tech.eversoft.chartsapp.availability.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class Availability {
    @NonNull private final AvailabilityId id;
    @NonNull private final DataDate date;
    @NonNull private final Location location;
    @NonNull private final AvailabilityIndex currentIndex;
    @NonNull private final AvailabilityIndex lastMonthIndex;
    @NonNull private final RoomStatistics roomStatistics;
}
