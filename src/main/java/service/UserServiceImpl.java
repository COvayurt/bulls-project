package service;

import model.User;
import mongodb.dao.UserDAOImpl;
import mongodb.dao.api.UserDAO;
import service.api.UserService;

import java.util.List;


public class UserServiceImpl implements UserService {

   private UserDAO userDAO = new UserDAOImpl();

   public boolean checkAccessToken(String accessToken){
      return userDAO.findUserByAccessToken(accessToken);
   }

   public List<User> findAllUsers(){
      return userDAO.findAllUser();
   }

   public boolean updateUser(User user){
      return userDAO.updateUser(user);
   }

   public User loginUser(String username, String password){
      return userDAO.findUserByUsernamePassword(username, password);
   }

   public boolean registerUser(String username, String password, String nameSurname, String email){
      User user = new User();
      user.setNameSurname(nameSurname);
      user.setUsername(username);
      user.setPassword(password);
      user.setEmail(email);
      return userDAO.registerUser(user);
   }

}
