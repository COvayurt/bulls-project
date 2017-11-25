package mongodb.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import model.User;
import mongodb.dao.api.UserDAO;
import org.bson.Document;
import util.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDAOImpl implements UserDAO {


    public boolean updateUser(User user) {

//        System.setProperty("java.net.preferIPv4Stack", "true");
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://bulls:bulls*%4010@ds117136.mlab.com:17136/heroku_4gkwzvlq"));

        MongoDatabase database = mongoClient.getDatabase("heroku_4gkwzvlq");
        MongoCollection collection = database.getCollection("User");

        if (user != null) {
            Document searchQuery = new Document().append("username", user.getUsername());
            Document bullsTickerDbObject = Utils.marshallUser(user);
            collection.replaceOne(searchQuery, bullsTickerDbObject);
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }


    public boolean findUserByAccessToken(String accessToken, String username) {
//        System.setProperty("java.net.preferIPv4Stack", "true");
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://bulls:bulls*%4010@ds117136.mlab.com:17136/heroku_4gkwzvlq"));

        MongoDatabase database = mongoClient.getDatabase("heroku_4gkwzvlq");
        MongoCollection collection = database.getCollection("User");
        BasicDBObject field = new BasicDBObject();
        field.put("accessToken", accessToken);
        field.put("username", username);
        MongoCursor cursor = collection.find(field).iterator();

        while (cursor.hasNext()) {
            User user = Utils.unmarshallUser((Document) cursor.next());
            Date now = new Date();

            if (user.getTokenExpirationTime() > now.getTime()) {
                return Boolean.TRUE;
            }
        }

        return Boolean.FALSE;
    }

    public User findUserByUsernamePassword(String username, String password) {
//        System.setProperty("java.net.preferIPv4Stack", "true");
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://bulls:bulls*%4010@ds117136.mlab.com:17136/heroku_4gkwzvlq"));

        MongoDatabase database = mongoClient.getDatabase("heroku_4gkwzvlq");
        MongoCollection collection = database.getCollection("User");
        BasicDBObject field = new BasicDBObject();
        field.put("username", username);
        field.put("password", password);
        MongoCursor cursor = collection.find(field).iterator();

        while (cursor.hasNext()) {
            User user = Utils.unmarshallUser((Document) cursor.next());
            return user;
        }

        return null;
    }

    public User findUserByUsername(String username) {
//        System.setProperty("java.net.preferIPv4Stack", "true");
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://bulls:bulls*%4010@ds117136.mlab.com:17136/heroku_4gkwzvlq"));

        MongoDatabase database = mongoClient.getDatabase("heroku_4gkwzvlq");
        MongoCollection collection = database.getCollection("User");
        BasicDBObject field = new BasicDBObject();
        field.put("username", username);
        MongoCursor cursor = collection.find(field).iterator();

        while (cursor.hasNext()) {
            User user = Utils.unmarshallUser((Document) cursor.next());
            return user;
        }

        return null;
    }


    public List<User> findAllUser() {
//        System.setProperty("java.net.preferIPv4Stack", "true");
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://bulls:bulls*%4010@ds117136.mlab.com:17136/heroku_4gkwzvlq"));

        MongoDatabase database = mongoClient.getDatabase("heroku_4gkwzvlq");
        MongoCollection collection = database.getCollection("User");
        MongoCursor cursor = collection.find().iterator();
        List<User> userList = new ArrayList<>();
        while (cursor.hasNext()) {
            User user = Utils.unmarshallUser((Document) cursor.next());
            userList.add(user);
        }

        return userList;
    }

    public boolean registerUser(User user) {

//        System.setProperty("java.net.preferIPv4Stack", "true");
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://bulls:bulls*%4010@ds117136.mlab.com:17136/heroku_4gkwzvlq"));

        MongoDatabase database = mongoClient.getDatabase("heroku_4gkwzvlq");
        MongoCollection collection = database.getCollection("User");

        if (user != null) {
            Document bullsTickerDbObject = Utils.marshallUser(user);
            collection.insertOne(bullsTickerDbObject);
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }




}
