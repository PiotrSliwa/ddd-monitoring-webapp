package tech.eversoft.chartsapp.availability.domain;

import java.time.LocalDateTime;
import java.util.List;

public interface AvailabilityRepository {
    List<Availability> findByCompanyNameEqualAndDataDateBetween(String companyName, LocalDateTime min, LocalDateTime max);
}
