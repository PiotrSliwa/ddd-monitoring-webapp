package tech.eversoft.chartsapp.presenter.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import tech.eversoft.chartsapp.availability.domain.AvailabilityIndex;
import tech.eversoft.chartsapp.availability.domain.AvailabilityRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
public class DayByDayPresentationFactory {
    @NonNull private AvailabilityRepository availabilityRepository;

    public DayByDayPresentation create(LocalDateTime beginning, LocalDateTime end) {
        var beginningMidnight = beginning.truncatedTo(ChronoUnit.DAYS);
        var availabilities = availabilityRepository.findByDataDateBetween(beginningMidnight, end);
        var indexesGroupedByDay = new HashMap<LocalDateTime, List<AvailabilityIndex>>();
        for (var availability : availabilities) {
            var dayGroup = indexesGroupedByDay.computeIfAbsent(availability.getDate().getDateTime().truncatedTo(ChronoUnit.DAYS),
                                                               d -> new ArrayList<>());
            dayGroup.add(availability.getCurrentIndex());
        }
        var dayByDayAvailability = new ArrayList<AvailabilityIndex>();
        for (var day = beginningMidnight; day.isBefore(end); day = day.plusDays(1)) {
            var dayGroup = indexesGroupedByDay.get(day);
            dayByDayAvailability.add(getDayAvailability(dayGroup));
        }
        return new DayByDayPresentation(dayByDayAvailability);
    }

    private static AvailabilityIndex getDayAvailability(List<AvailabilityIndex> dayGroup) {
        if (dayGroup != null) {
            var averageAvailability = dayGroup.stream().collect(Collectors.averagingInt(AvailabilityIndex::getValue)).intValue();
            return new AvailabilityIndex(averageAvailability);
        }
        return null;
    }
}
