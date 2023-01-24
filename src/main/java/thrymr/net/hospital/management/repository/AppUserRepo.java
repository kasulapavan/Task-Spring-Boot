package thrymr.net.hospital.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import thrymr.net.hospital.management.entity.AppUser;
import thrymr.net.hospital.management.enums.RoleType;

import java.util.List;
import java.util.Optional;

public interface AppUserRepo extends JpaRepository<AppUser, Long> {

    AppUser findByEmail(String email);





}
