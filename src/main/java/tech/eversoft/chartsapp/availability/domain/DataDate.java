package tech.eversoft.chartsapp.availability.domain;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
public class DataDate {
    @NonNull private final DataDateId id;
    @NonNull private final LocalDateTime dateTime;
}
