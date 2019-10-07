package tech.eversoft.chartsapp.presenter.application;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class DateQuery {
    @NonNull private LocalDate date;
}
