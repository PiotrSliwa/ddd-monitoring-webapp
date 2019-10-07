package tech.eversoft.chartsapp.availability.domain;

import java.time.LocalDateTime;
import java.util.List;

public interface AvailabilityRepository {
    List<Availability> findByDataDateBetween(LocalDateTime min, LocalDateTime max);
}
