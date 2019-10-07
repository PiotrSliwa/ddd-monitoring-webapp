package tech.eversoft.chartsapp.availability.application;

import lombok.Data;
import lombok.NonNull;

import java.util.Map;

@Data
public class StringRow {
    @NonNull private final Map<String, String> fieldValues;
}
