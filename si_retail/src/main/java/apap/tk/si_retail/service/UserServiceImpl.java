package apap.tk.si_retail.service;

import apap.tk.si_retail.model.UserModel;
import apap.tk.si_retail.repository.UserDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDB userDB;

    @Override
    public UserModel addUser(UserModel user) {
        String pass = encrypt(user.getPassword());
        user.setPassword(pass);
        return userDB.save(user);
    }

    @Override
    public UserModel findUserByUsername(String username) {
        List<UserModel> listUser = userDB.findAll();
        for (UserModel um : listUser) {
            if (um.getUsername().equals(username)) {
                return um;
            }
        }
        return null;
    }

    @Override
    public List<UserModel> getListUser() {
        return userDB.findAll();
    }

    @Override
    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    @Override
    public void deleteUser(UserModel user) {
        userDB.delete(user);
    }

    @Override
    public void updateUser(UserModel user) {
        userDB.save(user);
    }

    @Override
    public boolean checkIfValidOldPassword(UserModel user, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (passwordEncoder.matches(password, user.getPassword())) {
            return true;
        }
        return false;
    }

    @Override
    public UserModel changeUserPassword(UserModel user, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(password));
        return userDB.save(user);
    }
}
