package thrymr.net.hospital.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import thrymr.net.hospital.management.entity.AppUser;
import thrymr.net.hospital.management.entity.Hospital;
import thrymr.net.hospital.management.enums.RoleType;

import java.util.List;

public interface HospitalRepo extends JpaRepository<Hospital, Long> {



}
