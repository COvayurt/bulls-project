package service.api;

import model.User;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();

    boolean updateUser(User user);

    boolean registerUser(String username, String password, String nameSurname, String email);

    User loginUser(String username, String password);
}
