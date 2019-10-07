package tech.eversoft.chartsapp.presenter.application;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class TimeframeQuery {
    @NonNull
    private final LocalDate beginning;

    @NonNull
    private final LocalDate end;
}
