package pl.lodz.nbd.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import pl.lodz.nbd.common.MyValidator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NamedQueries({
        @NamedQuery(name = "Rent.getAll",
                query = "SELECT r FROM Rent r"),
        @NamedQuery(name = "Rent.getByRoomNumber",
                query = "SELECT r FROM Rent r WHERE r.room.roomNumber = :roomNumber"),
        @NamedQuery(name = "Rent.getByClientPersonalId",
                query = "SELECT r FROM Rent r WHERE r.client.personalId = :personalId"),
        @NamedQuery(name = "Rent.getRentsColliding",
                query = "SELECT r FROM Rent r WHERE (r.room.roomNumber = :roomNumber AND ((r.beginTime between :beginDate and :endDate) OR (r.endTime between :beginDate and :endDate)))")
})
@Data
@NoArgsConstructor
public class Rent extends AbstractEntity {

    @Id
    @GeneratedValue(generator = "rentId")
    @Column(name = "rent_id")
    private Long id;

    @NotNull
    @Column(name = "begin_time")
    private LocalDateTime beginTime;

    @NotNull
    @Column(name = "end_time")
    private LocalDateTime endTime;

    @NotNull
    @Column
    private boolean board;

    @NotNull
    @Column(name = "final_cost")
    private double finalCost;

    @NotNull
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "client_id")
    private Client client;

    @NotNull
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "room_id")
    private Room room;

    public Rent(LocalDateTime beginTime, LocalDateTime endTime, boolean board, double finalCost, Client client, Room room) {
        if (beginTime.isAfter(endTime)) throw new RuntimeException("Wrong chronological order");
        this.setUuid(UUID.randomUUID());
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.board = board;
        this.finalCost = finalCost;
        this.client = client;
        this.room = room;
        MyValidator.validate(this);
    }
}
