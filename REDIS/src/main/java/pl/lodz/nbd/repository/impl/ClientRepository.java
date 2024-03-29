package pl.lodz.nbd.repository.impl;

import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import pl.lodz.nbd.model.Client;
import pl.lodz.nbd.repository.AbstractMongoRepository;
import pl.lodz.nbd.repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ClientRepository extends AbstractMongoRepository implements Repository<Client> {

    MongoCollection<Client> clientCollection = mongoDatabase.getCollection("clients", Client.class);

    public ClientRepository() {
        clientCollection.drop();
        clientCollection = mongoDatabase.getCollection("clients", Client.class);
        Document index = new Document("personal_id", 1);
        IndexOptions options = new IndexOptions().unique(true);
        clientCollection.createIndex(index, options);
    }


    public Optional<Client> add(Client client) {
        Optional<Client> optionalClient;
        try {
            if (clientCollection.insertOne(client).wasAcknowledged()) {
                optionalClient = Optional.of(client);
            } else {
                optionalClient = Optional.empty();
            }
            return optionalClient;
        } catch (MongoWriteException e) {
            return Optional.empty();
        }
    }

    public List<Client> getAll() {
        return clientCollection.find().into(new ArrayList<>());
    }


    public void remove(Client client) {
        Bson filter = Filters.eq("_id", client.getUuid());
        clientCollection.findOneAndDelete(filter);
    }


    public Optional<Client> getById(UUID id) {
        Bson filter = Filters.eq("_id", id);
        return Optional.ofNullable(mongoDatabase.getCollection("clients", Client.class).find(filter).first());
    }


    public Optional<Client> getClientByPersonalId(String personalId) {
        Bson filter = Filters.eq("personal_id", personalId);
        return Optional.ofNullable(mongoDatabase.getCollection("clients", Client.class).find(filter).first());
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

}
