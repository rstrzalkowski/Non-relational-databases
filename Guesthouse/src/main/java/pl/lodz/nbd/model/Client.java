package pl.lodz.nbd.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.nbd.common.MyValidator;

import java.util.UUID;

@Entity
@NamedQueries({
        @NamedQuery(name = "Client.getAll",
                query = "SELECT c FROM Client c"),
        @NamedQuery(name = "Client.getByPersonalId",
                query = "SELECT c FROM Client c WHERE c.personalId = :personalId")
})
@Data
@NoArgsConstructor
public class Client extends AbstractEntity {

    @Id
    @GeneratedValue(generator = "clientId")
    @Column(name = "client_id")
    private Long clientId;

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Column(name = "personal_id", unique = true)
    private String personalId;

    @NotNull
    @Embedded
    private Address address;

    public Client(String firstName, String lastName, String personalId, Address address) {
        this.setUuid(UUID.randomUUID());
        this.firstName = firstName;
        this.lastName = lastName;
        this.personalId = personalId;
        this.address = address;
        MyValidator.validate(this);
    }
}
