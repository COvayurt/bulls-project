package mongodb.dao.api;

import model.User;

import java.util.List;

public interface UserDAO {

    boolean updateUser(User user);

    boolean findUserByAccessToken(String accessToken);

    User findUserByUsernamePassword(String username, String password);

    User findUserByUsername(String username);

    List<User> findAllUser();

    boolean registerUser(User user);

}
