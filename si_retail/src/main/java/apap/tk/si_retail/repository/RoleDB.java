package apap.tk.si_retail.repository;

import apap.tk.si_retail.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDB extends JpaRepository<RoleModel, Long> {
}