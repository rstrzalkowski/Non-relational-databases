package pl.lodz.nbd.repository.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Updates;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import pl.lodz.nbd.model.Client;
import pl.lodz.nbd.repository.AbstractMongoRepository;
import pl.lodz.nbd.repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClientRepository extends AbstractMongoRepository implements Repository<Client> {
    public ClientRepository() {
        Document index = new Document("personal_id", 1);
        IndexOptions options = new IndexOptions().unique(true);
        clientCollection.createIndex(index, options);
    }

    MongoCollection<Client> clientCollection = mongoDatabase.getCollection("clients", Client.class);

    public boolean add(Client client) {
        MongoCollection<Client> clientCollection = mongoDatabase.getCollection("clients", Client.class);
        return clientCollection.insertOne(client).wasAcknowledged();
    }

    public List<Client> getAll() {
        return clientCollection.find().into(new ArrayList<>());
    }


    public void remove(Client client) {
        Bson filter = Filters.eq("_id", client.getUuid());
        clientCollection.findOneAndDelete(filter);
    }


    public Client getById(UUID id) {
        Bson filter = Filters.eq("_id", id);
        return mongoDatabase.getCollection("clients", Client.class).find(filter).first();
    }


    public Client getClientByPersonalId(String personalId) {
        Bson filter = Filters.eq("personal_id", personalId);
        return mongoDatabase.getCollection("clients", Client.class).find(filter).first();
    }

    public boolean update(Client client) {
        Bson filter = Filters.eq("_id", client.getUuid());
        Bson update = Updates.combine(
                Updates.set("first_name", client.getFirstName()),
                Updates.set("last_name", client.getLastName()),
                Updates.set("clientType", client.getClientType()),
                Updates.set("address", client.getAddress())

        );
        return mongoDatabase.getCollection("clients", Client.class).updateOne(filter, update).wasAcknowledged();
    }


//
//    @Override
//    public Client update(Client client) {
//        try (EntityManager em = EntityManagerCreator.getEntityManager()) {
//            em.getTransaction().begin();
//            Client newClient = em.merge(client);
//            em.getTransaction().commit();
//            return newClient;
//        } catch (Exception e) {
//            return null;
//        }
//    }
}
