package pl.lodz.nbd.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.nbd.common.MyValidator;

@Entity
@NamedQueries({
        @NamedQuery(name = "Room.getAll",
                query = "SELECT r FROM Room r"),
        @NamedQuery(name = "Room.getByRoomNumber",
                query = "SELECT r FROM Room r WHERE r.roomNumber = :roomNumber")
})
@Data
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(generator = "roomId")
    @Column(name = "room_id")
    private Long id;

    @NotNull
    @Column(name = "room_number", unique = true)
    private int roomNumber;

    @NotNull
    @Column
    private double price;

    @NotNull
    @Column
    private int size;

    public Room(int roomNumber, double price, int size) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.size = size;
        MyValidator.validate(this);
    }
}
