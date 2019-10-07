package tech.eversoft.chartsapp.availability.domain;

import lombok.NonNull;
import lombok.Value;

@Value
public class AvailabilityIndex {
    @NonNull Integer value;
}
