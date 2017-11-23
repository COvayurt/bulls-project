package service;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.User;
import mongodb.dao.UserDAOImpl;
import mongodb.dao.api.UserDAO;
import service.api.UserService;


public class UserServiceImpl implements UserService {

   private UserDAO userDAO = new UserDAOImpl();

   public boolean registerUser(String username, String password, String nameSurname, String email){

      System.setProperty("java.net.preferIPv4Stack" , "true");
      MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://bulls:bulls*%4010@ds117136.mlab.com:17136/heroku_4gkwzvlq"));

      MongoDatabase database = mongoClient.getDatabase("heroku_4gkwzvlq");
      MongoCollection collection = database.getCollection("User");

      User user = new User();
      user.setNameSurname(nameSurname);
      user.setUsername(username);
      user.setPassword(password);
      user.setEmail(email);
      return userDAO.registerUser(user, collection);
   }

}
