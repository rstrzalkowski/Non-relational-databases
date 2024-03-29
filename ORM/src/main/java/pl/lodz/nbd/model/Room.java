package pl.lodz.nbd.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.nbd.common.MyValidator;

import java.util.UUID;

@Entity
@NamedQueries({
        @NamedQuery(name = "Room.getAll",
                query = "SELECT r FROM Room r"),
        @NamedQuery(name = "Room.getByRoomNumber",
                query = "SELECT r FROM Room r WHERE r.roomNumber = :roomNumber")
})
@Data
@NoArgsConstructor
public class Room extends AbstractEntity {

    @Id
    @GeneratedValue(generator = "roomId")
    @Column(name = "room_id")
    private Long id;

    @NotNull
    @Column(name = "room_number", unique = true)
    @Min(value = 1)
    private int roomNumber;

    @NotNull
    @Column
    @Min(value = 1)
    private double price;

    @NotNull
    @Column
    @Min(value = 1)
    private int size;

    public Room(int roomNumber, double price, int size) {
        this.setUuid(UUID.randomUUID());
        this.roomNumber = roomNumber;
        this.price = price;
        this.size = size;
        MyValidator.validate(this);
    }
}
