package thrymr.net.hospital.management.service.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import thrymr.net.hospital.management.custom.exception.ApiResponse;
import thrymr.net.hospital.management.dto.HospitalDto;
import thrymr.net.hospital.management.entity.AppUser;
import thrymr.net.hospital.management.entity.Hospital;
import thrymr.net.hospital.management.enums.RoleType;
import thrymr.net.hospital.management.repository.AppUserRepo;
import thrymr.net.hospital.management.repository.HospitalRepo;
import thrymr.net.hospital.management.service.HospitalService;

import java.util.List;
import java.util.Optional;


@Service
public class HospitalServiceImplementation  implements HospitalService {

    @Autowired
    private HospitalRepo hospitalRepo;
    @Autowired
    private AppUserRepo appUserRepo;

    @Autowired
    private BCryptPasswordEncoder passwordConversion;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUserImplementation appUserImplementation;

    public ApiResponse save(HospitalDto hospitalDtoList) {
        Hospital hospital = appUserImplementation.hospitalDtoToEntity(hospitalDtoList);
        hospitalRepo.save(hospital);
        return new ApiResponse(HttpStatus.OK.value(), "Registration is done", "SUCCESS");

    }


    public ApiResponse saveAndUpdate(Long id, List<HospitalDto> hospitalDtoList) {   //save or assign doctor to more hospitals
        Optional<AppUser> appUserOptional = appUserRepo.findById(id);
        if (appUserOptional.isPresent()) {

            List<Hospital> hospitalList = hospitalDtoList.stream().map(hospitalDto -> modelMapper.map(hospitalDto, Hospital.class)).toList();
            AppUser appUser = appUserOptional.get();
            appUser.getHospitalList().addAll(hospitalList);
            appUserRepo.save(appUser);
            return new ApiResponse(HttpStatus.OK.value(), "updated Successfully", "SUCCESS");
        }
        else return new ApiResponse(HttpStatus.NOT_FOUND.value(), "USER NOT FOUND");
    }


    @Override
    public boolean deleteById(Long id) {  //delete hospital by Admin
      try{
          hospitalRepo.deleteById(id);
          return true;
      }catch (Exception e){
          e.printStackTrace();
          return false;
      }
    }
//Admin should be able to see all Hospitals and associated Doctors of each Hospital
    //The doctor should be able to access only the associated hospitals for him

    public ApiResponse getAll(Integer pageNumber) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return new ApiResponse(HttpStatus.UNAUTHORIZED.value(), "UNAUTHORIZED");
        }
        AppUser user = (AppUser) authentication.getPrincipal();
        if (user != null) {
            if (user.getRoleType().equals(RoleType.ADMIN)) {
                List<AppUser> appUsers = appUserRepo.findAll(PageRequest.of(pageNumber,20)).toList();
                return new ApiResponse(HttpStatus.OK.value(), appUsers);
            } else if (user.getRoleType().equals(RoleType.DOCTOR)) {
                AppUser appUsers = appUserRepo.findByEmail(user.getEmail());
                return new ApiResponse(HttpStatus.OK.value(), appUsers.getHospitalList());
            }
        }
         return new ApiResponse(HttpStatus.UNAUTHORIZED.value(), "UNAUTHORIZED");

    }

}
