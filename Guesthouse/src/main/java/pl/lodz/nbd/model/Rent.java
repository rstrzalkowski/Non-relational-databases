package pl.lodz.nbd.model;

import com.sun.istack.NotNull;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@NamedQueries({
        @NamedQuery(name="Rent.getAll",
                query="SELECT r FROM Rent r")
})
@Data
@NoArgsConstructor
public class Rent {

    @Id
    @GeneratedValue(generator = "rentId")
    @NotNull
    @Column(name = "rent_id")
    private Long id;

    @NotNull
    @Column(name = "begin_time")
    private Date beginTime;

    @NotNull
    @Column(name = "end_time")
    private Date endTime;

    @NotNull
    @Column
    private boolean board;

    @NotNull
    @Column(name = "final_cost")
    private double finalCost;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    public Rent(Date beginTime, Date endTime, boolean board, double finalCost, Client client, Room room) {
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.board = board;
        this.finalCost = finalCost;
        this.client = client;
        this.room = room;
    }
}
