package tech.eversoft.chartsapp.presenter.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import tech.eversoft.chartsapp.availability.domain.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
public class SingleDayPresentationFactory {
    @NonNull private AvailabilityRepository availabilityRepository;

    public SingleDayPresentation create(LocalDateTime dateTime) {
        var dayTimeMidnight = dateTime.truncatedTo(ChronoUnit.DAYS);
        var availabilities = availabilityRepository.findByDataDateBetween(dayTimeMidnight, dateTime);
        var total = collect(availabilities);
        var locationAvailabilities = groupByLocation(availabilities);
        return new SingleDayPresentation(total, locationAvailabilities);
    }

    private static DayAvailability collect(List<Availability> availabilities) {
        var index = new AvailabilityIndex(average(availabilities, a -> a.getCurrentIndex().getValue()));
        var lastMonthIndex = new AvailabilityIndex(average(availabilities, a -> a.getLastMonthIndex().getValue()));
        var lastMonthDifference = new AvailabilityDifference(lastMonthIndex, index);
        var roomStatistics = sumRoomStatistics(availabilities);
        return new DayAvailability(index, lastMonthDifference, roomStatistics);
    }

    private static RoomStatistics sumRoomStatistics(List<Availability> availabilities) {
        var quantityOfRooms = sum(availabilities, a -> a.getRoomStatistics().getQuantityOfRooms());
        var quantityOfRoomIssues = sum(availabilities, a -> a.getRoomStatistics().getQuantityOfRoomIssues());
        return new RoomStatistics(quantityOfRooms, quantityOfRoomIssues);
    }

    private static int average(List<Availability> availabilities, Function<Availability, Integer> getter) {
        return availabilities.stream().collect(Collectors.averagingInt(getter::apply)).intValue();
    }

    private static int sum(List<Availability> availabilities, Function<Availability, Integer> getter) {
        return availabilities.stream().mapToInt(getter::apply).sum();
    }

    private static Map<Location, DayAvailability> groupByLocation(List<Availability> availabilities) {
        var listsGroupedByLocation = new HashMap<Location, List<Availability>>();
        for (var availability : availabilities) {
            var locationAvailabilities = listsGroupedByLocation.computeIfAbsent(availability.getLocation(), l -> new ArrayList<>());
            locationAvailabilities.add(availability);
        }
        var result = new HashMap<Location, DayAvailability>();
        for (var e : listsGroupedByLocation.entrySet()) {
            result.put(e.getKey(), collect(e.getValue()));
        }
        return result;
    }
}
