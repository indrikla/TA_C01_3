package apap.tk.si_retail.repository;

import apap.tk.si_retail.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDB extends JpaRepository<UserModel, Long> {
    UserModel findByUsername(String username);
}
