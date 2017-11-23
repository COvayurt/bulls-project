package service.api;

public interface UserService {

    boolean registerUser(String username, String password, String nameSurname, String email);

}
