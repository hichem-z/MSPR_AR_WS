package fr.group.mspr_ar_ws.repository;

import fr.group.mspr_ar_ws.models.Role;
import fr.group.mspr_ar_ws.models.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleEnum name);

}
