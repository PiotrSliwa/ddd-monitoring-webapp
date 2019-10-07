package tech.eversoft.chartsapp.availability.domain;

import lombok.NonNull;
import lombok.Value;

@Value
public class RoomStatistics {
    @NonNull Integer quantityOfRooms;
    @NonNull Integer quantityOfRoomIssues;
}
