package thrymr.net.hospital.management.repository;

import org.springframework.context.event.ApplicationListenerMethodAdapter;
import org.springframework.data.jpa.repository.JpaRepository;
import thrymr.net.hospital.management.entity.AppUser;
import thrymr.net.hospital.management.enums.RoleType;

import java.util.List;
import java.util.Optional;

public interface AppUserRepo extends JpaRepository<AppUser, Long> {

    AppUser findByEmail(String email);

    List<AppUser> findAllByNameAndEducationAndSpecializationAndHospitalListName(String name, String education, String specialization, String hospitalName);
  List<AppUser> findAllByName(String name);

  List<AppUser> findAllByEducation(String education);

  List<AppUser> findAllBySpecialization(String specialization);

  List<AppUser> findAllByHospitalListName(String hospitalName);


  List<AppUser> findAllByNameAndEducationAndSpecialization(String name, String education, String specialization);
    List<AppUser> findAllByNameAndEducationAndHospitalListName(String name,String education,String hospitalName);

    List<AppUser> findAllByEducationAndSpecializationAndHospitalListName(String education, String specialization, String hospitalName);
    List<AppUser>  findAllByNameAndEducation(String name, String education);
    List<AppUser> findAllByEducationAndSpecialization(String education, String specialization);

    List<AppUser> findAllBySpecializationAndHospitalListName(String specialization, String hospitalName);

    List<AppUser> findAllByNameAndHospitalListName(String name, String hospitalName);
}


