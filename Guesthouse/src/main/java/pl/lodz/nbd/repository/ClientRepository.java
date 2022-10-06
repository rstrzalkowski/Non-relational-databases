package pl.lodz.nbd.repository;

import jakarta.persistence.EntityManager;
import pl.lodz.nbd.model.Address;
import pl.lodz.nbd.model.Client;

import java.util.List;

public class ClientRepository implements Repository<Client> {
    @Override
    public void add(Client client, EntityManager em) {
        em.persist(client);
    }

    @Override
    public void remove(Client client, EntityManager em) {
        em.remove(client);
    }

    @Override
    public Client getById(Long id, EntityManager em) {
        return em.find(Client.class, id);
    }

    @Override
    public List<Client> getAll(EntityManager em) {
        return em.createNamedQuery("Client.getAll", Client.class).getResultList();
    }

    public Client getClientByPersonalId(String personalId, EntityManager em) {
        List<Client> result = em.createNamedQuery("Client.getByPersonalId", Client.class).setParameter("personalId", personalId).getResultList();

        if (result.isEmpty()) {
            return null;
        } else {
            return result.get(0);
        }
    }
}
