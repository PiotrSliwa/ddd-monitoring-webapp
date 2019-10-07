package tech.eversoft.chartsapp.presenter.domain;

import lombok.Data;
import lombok.NonNull;
import tech.eversoft.chartsapp.availability.domain.AvailabilityIndex;

@Data
public class AvailabilityDifference {
    @NonNull private final AvailabilityIndex difference;

    public AvailabilityDifference(@NonNull AvailabilityIndex previous,
                                  @NonNull AvailabilityIndex current)
    {
        difference = new AvailabilityIndex(current.getValue() - previous.getValue());
    }
}
