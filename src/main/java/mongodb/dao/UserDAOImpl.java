package mongodb.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import model.BullsTicker;
import model.User;
import mongodb.dao.api.BullsTickerDAO;
import mongodb.dao.api.UserDAO;
import org.bson.Document;
import org.bson.types.Decimal128;

import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {


    private Gson gson = new GsonBuilder().create();


    public User findUserByUserId(String username) {
        System.setProperty("java.net.preferIPv4Stack", "true");
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://bulls:bulls*%4010@ds117136.mlab.com:17136/heroku_4gkwzvlq"));

        MongoDatabase database = mongoClient.getDatabase("heroku_4gkwzvlq");
        MongoCollection collection = database.getCollection("User");
        BasicDBObject field = new BasicDBObject();
        field.put("username", username);
        MongoCursor cursor = collection.find(field).iterator();

        while (cursor.hasNext()) {
            User user = unmarshallUser((Document) cursor.next());
            return user;
        }

        return null;
    }


    public List<User> findAllUser() {
        System.setProperty("java.net.preferIPv4Stack", "true");
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://bulls:bulls*%4010@ds117136.mlab.com:17136/heroku_4gkwzvlq"));

        MongoDatabase database = mongoClient.getDatabase("heroku_4gkwzvlq");
        MongoCollection collection = database.getCollection("User");
        MongoCursor cursor = collection.find().iterator();
        List<User> userList = new ArrayList<>();
        while (cursor.hasNext()) {
            User user = unmarshallUser((Document) cursor.next());
            userList.add(user);
        }

        return userList;
    }

    public boolean registerUser(User user, MongoCollection collection) {

        if (user != null) {
            Document bullsTickerDbObject = marshallUser(user);
            collection.insertOne(bullsTickerDbObject);
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }


    private User unmarshallUser(Document tickerMongoDbDoc) {
        User user = new User();
        user.setUsername((String) tickerMongoDbDoc.get("username"));
        user.setPassword((String) tickerMongoDbDoc.get("password"));
        user.setEmail((String) tickerMongoDbDoc.get("email"));
        user.setAccessToken((String) tickerMongoDbDoc.get("accessToken"));
        user.setNameSurname((String) tickerMongoDbDoc.get("nameSurname"));
        user.setTokenExpirationTime((Long) tickerMongoDbDoc.get("tokenExpirationTime"));
//        user.setUserFollowTickers((List<BullsTicker>)tickerMongoDbDoc.get("userFollowTickers"));

        return user;
    }

    private Document marshallUser(User user) {
        return new Document("username", user.getUsername())
                .append("password", user.getPassword())
                .append("email", user.getEmail())
                .append("nameSurname", user.getNameSurname())
                .append("accessToken", user.getAccessToken())
                .append("tokenExpirationTime", user.getTokenExpirationTime());
//                .append("userFollowTickers", user.getUserFollowTickers());
    }

}