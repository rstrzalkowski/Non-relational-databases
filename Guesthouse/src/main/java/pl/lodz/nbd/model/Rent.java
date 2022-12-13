package pl.lodz.nbd.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.lodz.nbd.table.RentByClient;
import pl.lodz.nbd.table.RentByRoom;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@EqualsAndHashCode
public class Rent {

    private LocalDate beginTime;
    private LocalDate endTime;
    private boolean board;
    private double finalCost;
    private Client client;
    private Room room;

    public Rent(LocalDate beginTime, LocalDate endTime, boolean board, double finalCost, Client client, Room room) {
        if (beginTime.isAfter(endTime)) throw new RuntimeException("Wrong chronological order");
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.board = board;
        this.finalCost = finalCost;
        this.client = client;
        this.room = room;
    }

    public RentByRoom toRentByRoom() {
        return new RentByRoom(room.getRoomNumber(), endTime, beginTime, board, finalCost, client.getPersonalId());
    }

    public RentByClient toRentByClient() {
        return new RentByClient(room.getRoomNumber(), endTime, beginTime, board, finalCost, client.getPersonalId());
    }
}
