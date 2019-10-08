package tech.eversoft.chartsapp.availability.application;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import tech.eversoft.chartsapp.availability.domain.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class StringTableAvailabilityRepository implements AvailabilityRepository {

    @NonNull
    @Qualifier("availabilityTable")
    private StringTable stringTable;

    @Override
    public List<Availability> findByCompanyNameEqualAndDataDateBetween(String companyName, LocalDateTime min, LocalDateTime max) {
        return stringTable.getRows().stream()
                .map(StringRow::getFieldValues)
                .filter(v -> v.get("COMPANY_NAME").equals(companyName))
                .filter(v -> isBetween(v.get("DATA_DATE"), min, max))
                .map(this::parseAvailability)
                .collect(Collectors.toList());
    }

    private static boolean isBetween(String dateTimeString, LocalDateTime min, LocalDateTime max) {
        var dateTime = parseDateTime(dateTimeString);
        return (dateTime.isBefore(max) || dateTime.isEqual(max)) && (dateTime.isAfter(min) || dateTime.isEqual(min));
    }

    private Availability parseAvailability(Map<String, String> raw) {
        return Availability.builder()
                .id(new AvailabilityId(Integer.parseInt(raw.get("AVAILABILITY_ID"))))
                .date(new DataDate(new DataDateId(Integer.parseInt(raw.get("DATA_DATE_ID"))), parseDateTime(raw.get("DATA_DATE"))))
                .location(new Location(new LocationId(Integer.parseInt(raw.get("LOCATION_ID"))), raw.get("LOCATION_NAME")))
                .currentIndex(new AvailabilityIndex(Integer.parseInt(raw.get("AVAILABILITY_INDEX"))))
                .lastMonthIndex(new AvailabilityIndex(Integer.parseInt(raw.get("LAST_MONTH_AVAILABILITY_INDEX"))))
                .roomStatistics(new RoomStatistics(Integer.parseInt(raw.get("QTY_ROOMS")), Integer.parseInt(raw.get("QTY_ROOM_ISSUES"))))
                .build();
    }

    private static LocalDateTime parseDateTime(String raw) {
        var formatter = DateTimeFormatter.ofPattern("M/d/yy H:m");
        return LocalDateTime.from(formatter.parse(raw));
    }
}
