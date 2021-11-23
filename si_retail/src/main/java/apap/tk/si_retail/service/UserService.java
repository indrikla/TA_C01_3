package apap.tk.si_retail.service;

import apap.tk.si_retail.model.UserModel;

import java.util.List;

public interface UserService {
    UserModel addUser(UserModel user);
    UserModel findUserByUsername(String username);
    List<UserModel> getListUser();
    String encrypt(String password);
    void deleteUser(UserModel user);
    boolean checkIfValidOldPassword(UserModel user, String password);
    UserModel changeUserPassword(UserModel user, String password);
}