package tech.eversoft.chartsapp.availability.domain;

import lombok.NonNull;
import lombok.Value;

@Value
public class Location {
    @NonNull LocationId id;
    @NonNull String name;

    @Override
    public String toString() {
        return name;
    }
}
