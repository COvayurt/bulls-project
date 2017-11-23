package mongodb.dao.api;

import com.mongodb.client.MongoCollection;
import model.BullsTicker;
import model.User;

import java.util.List;

public interface UserDAO {

    List<User> findAllUser();

    void insertUser(User user, MongoCollection collection);
}
