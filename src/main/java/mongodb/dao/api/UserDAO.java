package mongodb.dao.api;

import com.mongodb.client.MongoCollection;
import model.BullsTicker;
import model.User;

import java.util.List;

public interface UserDAO {

    User findUserByUserId(String username);

    List<User> findAllUser();

    boolean registerUser(User user, MongoCollection collection);

}
