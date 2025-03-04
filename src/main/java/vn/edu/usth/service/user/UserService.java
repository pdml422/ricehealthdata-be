package vn.edu.usth.service.user;

import java.util.List;

import vn.edu.usth.exception.UserNotFoundException;
import vn.edu.usth.model.User;

public interface UserService {
    User getUserById(int id) throws UserNotFoundException;

    List<User> getAllUsers();

    User getUserByUsername(String username) throws UserNotFoundException;

    User addUser(User user) throws UserNotFoundException;

    User updateUser(int id, User user) throws UserNotFoundException;

    void deleteUser(int id) throws UserNotFoundException;
}
