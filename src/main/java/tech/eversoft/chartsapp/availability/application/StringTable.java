package tech.eversoft.chartsapp.availability.application;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class StringTable {
    @NonNull private final List<StringRow> rows;
}
