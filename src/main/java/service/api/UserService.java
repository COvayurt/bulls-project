package service.api;

import model.User;

public interface UserService {

    boolean updateUser(User user);

    boolean registerUser(String username, String password, String nameSurname, String email);

    User loginUser(String username, String password);
}
