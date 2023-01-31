package thrymr.net.hospital.management.service.impl;

import com.nimbusds.jose.JOSEException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import thrymr.net.hospital.management.configuration.JwtTokenUtils;
import thrymr.net.hospital.management.custom.exception.ApiResponse;
import thrymr.net.hospital.management.dto.AppUserDto;
import thrymr.net.hospital.management.dto.HospitalDto;
import thrymr.net.hospital.management.dto.SearchDto;
import thrymr.net.hospital.management.entity.AppUser;
import thrymr.net.hospital.management.entity.Hospital;

import thrymr.net.hospital.management.repository.AppUserRepo;
import thrymr.net.hospital.management.repository.HospitalRepo;
import thrymr.net.hospital.management.service.AppUserService;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;


@Service
public class AppUserImplementation implements AppUserService {


    @Autowired
    private BCryptPasswordEncoder passwordConversion;

    @Autowired
    private AppUserRepo appUserRepo;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private HospitalRepo hospitalRepo;

    @Autowired
    private EntityManager entityManager;


    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ApiResponse save(AppUserDto appUserDto) {    //save and update hospital and user by Admin
        AppUser user = appUserDtoToEntity(appUserDto);
        appUserRepo.save(user);
        return new ApiResponse(HttpStatus.OK.value(), "Registration is done", "SUCCESS");
    }


    public ApiResponse signIn(AppUserDto loginDto) throws JOSEException {    //sign in users
        AppUser apUser = appUserRepo.findByEmail(loginDto.getEmail());

        if (apUser == null) {
            return new ApiResponse(HttpStatus.UNAUTHORIZED.value(), "Email id is wrong");
        }
        if (passwordConversion.matches(loginDto.getPassword(), apUser.getPassword())) {
            AppUserDto appUserDto = appUserEntityToDto(apUser);
            appUserDto.setToken(jwtTokenUtils.getToken(apUser));
            return new ApiResponse(HttpStatus.OK.value(), appUserDto);
        } else {
            return new ApiResponse(HttpStatus.UNAUTHORIZED.value(), "Password is wrong");
        }
    }

    public List<AppUserDto> associate(Long id, List<AppUserDto> appUserList) {
        Optional<Hospital> hospitalOptional = hospitalRepo.findById(id);
        List<AppUserDto> appUserList1 = new ArrayList<AppUserDto>();
        for (AppUserDto userDto : appUserList) {
            AppUser appUser = appUserRepo.findByEmail(userDto.getEmail());
            appUser.getHospitalList().add(hospitalOptional.get());
            appUserRepo.save(appUser);
            AppUserDto appUserDto = appUserEntityToDto(appUser);
            appUserList1.add(appUserDto);
        }
        return appUserList1;
    }


    public AppUserDto disassociate(Long id, AppUserDto appUserList) {
        Optional<Hospital> hospitalOptional = hospitalRepo.findById(id);
        AppUser appUser = appUserRepo.findByEmail(appUserList.getEmail());
        appUser.getHospitalList().remove(hospitalOptional.get());
        appUserRepo.save(appUser);
        return appUserEntityToDto(appUser);
    }



    @Override
    public boolean deleteById(Long id) {  //delete doctor or user by Admin
        try {
            appUserRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


//    @Override
//    public List<AppUserDto> findAllBySearch(String keyword) {
//        List<AppUser> appUser = appUserRepo.findAll();
//        List<AppUser> appUserList = null;
//        SearchDto searchDto =new SearchDto();
//        List<SearchDto> searchDtos = new ArrayList<SearchDto>();
//        for(AppUser appUser1 : appUser){
//               searchDto.setName(appUser1.getName());
//               searchDto.setEducation(appUser1.getEducation());
//               searchDto.setDoctorSpecialization(appUser1.getSpecialization());
//               searchDto.setHospitalName(appUser1.getHospitalList().stream().map(Hospital::getName).toList().toString());
//               searchDtos.add(searchDto);
//
//           }       } else if (searchDto1.getHospitalName().equals(searchDto12.getHospitalName())) {
//            for(SearchDto searchDto1 : searchDtos) {
//               if(keyword.equalsIgnoreCase(searchDto1.getName())){
//                   appUserList = appUser.stream().filter(appUser2 -> appUser2.getName().equalsIgnoreCase(keyword)).toList();
//               }
//               else if(keyword.equalsIgnoreCase(searchDto1.getEducation())){
//                   appUserList = appUser.stream().filter(appUser2 -> appUser2.getEducation().equalsIgnoreCase(keyword)).toList();
//               }
//               else if(keyword.equalsIgnoreCase(searchDto1.getDoctorSpecialization())){
//                   appUserList = appUser.stream().filter(appUser2 -> appUser2.getSpecialization().equalsIgnoreCase(keyword)).toList();
//               }
//               else if(keyword.equalsIgnoreCase(searchDto1.getHospitalName())){
//                   appUserList = appUser.stream().filter(appUser2 -> appUser2.getHospitalList().stream().filter(app->app.getName().equalsIgnoreCase(keyword)).isParallel()).toList();
//               }
//           }
//
//           return appUserList.stream().map(appUser1 -> modelMapper.map(appUser1, AppUserDto.class)).toList();
//    }

//
//    public List<AppUserDto> findAllBySearch(SearchDto searchDto1) {
//        List<AppUser> appUser = appUserRepo.findAll();
//        List<AppUser> appUserList = null;
//        SearchDto searchDto = new SearchDto();
//        List<SearchDto> searchDtos = new ArrayList<SearchDto>();
//        for (AppUser appUser1 : appUser) {
//            searchDto.setName(appUser1.getName());
//            searchDto.setEducation(appUser1.getEducation());
//            searchDto.setDoctorSpecialization(appUser1.getSpecialization());
//            searchDto.setHospitalName(appUser1.getHospitalList().stream().map(Hospital::getName).toList().toString());
//            searchDtos.add(searchDto);
//        }
//        for (SearchDto searchDto12 : searchDtos) {
//
//            Predicate<AppUser> appUserPredicate = appUser1 -> appUser1.getName().equalsIgnoreCase(searchDto1.getName());
//            Predicate<AppUser> appUserPredicate1 = appUser1 -> appUser1.getEducation().equalsIgnoreCase(searchDto1.getEducation());
//            Predicate<AppUser> appUserPredicate2 = appUser1 -> appUser1.getSpecialization().equalsIgnoreCase(searchDto1.getDoctorSpecialization());
//            Predicate<AppUser> appUserPredicate3 = appUser1 -> appUser1.getHospitalList().stream().filter(hospital -> hospital.getName().equalsIgnoreCase(searchDto1.getName())).isParallel();
//            appUserList = appUser.stream().filter(appUserPredicate.or(appUserPredicate1).or(appUserPredicate2).or(appUserPredicate3)).toList();
//
////                appUserList = appUser.stream().filter(appUser2 -> appUser2.getName().equalsIgnoreCase(searchDto1.getName())).toList();
//
////            } else if (searchDto1.getEducation().equals(searchDto12.getEducation())) {
////                appUserList = appUser.stream().filter(appUser2 -> appUser2.getEducation().equalsIgnoreCase(searchDto1.getEducation())).toList();
////            } else if (searchDto1.getDoctorSpecialization().equals(searchDto12.getDoctorSpecialization())) {
////                appUserList = appUser.stream().filter(appUser2 -> appUser2.getSpecialization().equalsIgnoreCase(searchDto1.getDoctorSpecialization())).toList();
////
////            } else if (searchDto1.getHospitalName().equals(searchDto12.getHospitalName())) {
////
////                appUserList = appUser.stream().filter(appUser2 -> appUser2.getHospitalList().stream().filter(app -> app.getName().equalsIgnoreCase(searchDto1.getHospitalName())).isParallel()).toList();
////
////            }
////        for (SearchDto searchDto12 : searchDtos) {
////
//
//
//        }
//        return appUserList.stream().map(this::appUserEntityToDto).toList();
//
//    }


    public Page<AppUserDto> search(SearchDto searchDto, Integer  offset, Integer pageSize) {
        List<AppUser> appUserList = new ArrayList<AppUser>();
        if(searchDto.getEducation() != null && searchDto.getName() != null && searchDto.getDoctorSpecialization() != null && searchDto.getHospitalName() != null) {
            appUserList = appUserRepo.findAllByNameAndEducationAndSpecializationAndHospitalListName(searchDto.getName(), searchDto.getEducation(), searchDto.getDoctorSpecialization(), searchDto.getHospitalName());
        }else if(searchDto.getName() != null && searchDto.getEducation() != null && searchDto.getDoctorSpecialization() != null){
            appUserList = appUserRepo.findAllByNameAndEducationAndSpecialization(searchDto.getName(), searchDto.getEducation(), searchDto.getDoctorSpecialization());
        }else if(searchDto.getName() != null && searchDto.getEducation() != null && searchDto.getHospitalName() != null){
            appUserList = appUserRepo.findAllByNameAndEducationAndHospitalListName(searchDto.getName(), searchDto.getEducation(), searchDto.getHospitalName());
        }else if(searchDto.getEducation() != null && searchDto.getHospitalName() != null && searchDto.getDoctorSpecialization() != null){
            appUserList = appUserRepo.findAllByEducationAndSpecializationAndHospitalListName(searchDto.getEducation(), searchDto.getHospitalName(), searchDto.getDoctorSpecialization());
        } else if(searchDto.getName() != null && searchDto.getEducation() != null){
            appUserList = appUserRepo.findAllByNameAndEducation(searchDto.getName(), searchDto.getEducation());
        } else if(searchDto.getEducation() != null && searchDto.getDoctorSpecialization() != null){
            appUserList = appUserRepo.findAllByEducationAndSpecialization(searchDto.getEducation(), searchDto.getDoctorSpecialization());
        }else if (searchDto.getDoctorSpecialization() != null && searchDto.getHospitalName() != null){
            appUserList = appUserRepo.findAllBySpecializationAndHospitalListName(searchDto.getDoctorSpecialization() , searchDto.getHospitalName());
        }else if( searchDto.getName() != null &&searchDto.getHospitalName() != null){
            appUserList = appUserRepo.findAllByNameAndHospitalListName( searchDto.getName(), searchDto.getHospitalName());
        } else if(searchDto.getName() != null){
            appUserList = appUserRepo.findAllByName(searchDto.getName());
        } else if(searchDto.getEducation() != null){
            appUserList = appUserRepo.findAllByEducation(searchDto.getEducation());
        } else if(searchDto.getDoctorSpecialization() != null){
            appUserList =  appUserRepo.findAllBySpecialization(searchDto.getDoctorSpecialization());
        } else if(searchDto.getHospitalName() != null){
            appUserList = appUserRepo.findAllByHospitalListName(searchDto.getHospitalName());
        }

        return new PageImpl<AppUserDto>(appUserList.stream().map(this::appUserEntityToDto).collect(Collectors.toList()),PageRequest.of(offset,pageSize),0L);
    }

    public List<AppUserDto> searchBy(SearchDto searchDto){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<AppUser> criteriaQuery = criteriaBuilder.createQuery(AppUser.class);
        Root<AppUser> root = criteriaQuery.from(AppUser.class);
        Join<AppUser, Hospital> appUserHospitalJoin = root.join("hospitalList");
        String name = searchDto.getName();
        String education = searchDto.getEducation();
        String specialization = searchDto.getDoctorSpecialization();
        String name1 =  searchDto.getHospitalName();
        List<Predicate> searchCriteria = new ArrayList<Predicate>();
        if (name != null) {
            searchCriteria.add(criteriaBuilder.equal(root.get("name"), name));
        }if (education != null) {
            searchCriteria.add(criteriaBuilder.equal(root.get("education"), education));
        }if (specialization != null) {
            searchCriteria.add(criteriaBuilder.equal(root.get("specialization"), specialization));
        }
        if (name1 != null) {
            searchCriteria.add(criteriaBuilder.equal(appUserHospitalJoin.get("name"),name1));
        }
        criteriaQuery.select(root).where(criteriaBuilder.and(searchCriteria.toArray(new Predicate[0])));
        TypedQuery<AppUser> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList().stream().map(this::appUserEntityToDto).toList();
    }


    public AppUser appUserDtoToEntity(AppUserDto appUserDto) {
        AppUser appUser1 = new AppUser();
        if (appUser1.getHospitalList() != null) {
            appUser1.setId(appUserDto.getId());
        }
        appUser1.setEmail(appUserDto.getEmail());
        appUser1.setName(appUserDto.getName());
        appUser1.setEducation(appUserDto.getEducation());
        appUser1.setSpecialization(appUserDto.getSpecialization());
        appUser1.setRoleType(appUserDto.getRoleType());
        appUser1.setPassword(passwordConversion.encode(appUserDto.getPassword()));
        if (appUserDto.getHospitalList() != null) {
            appUser1.setHospitalList(appUserDto.getHospitalList().stream().map(this::hospitalDtoToEntity).toList());
        }

        return appUser1;


    }

    public AppUserDto appUserEntityToDto(AppUser appUser) {
        AppUserDto appUserDto = new AppUserDto();
        appUserDto.setId(appUser.getId());
        appUserDto.setEmail(appUser.getEmail());
        appUserDto.setName(appUser.getName());
        appUserDto.setEducation(appUser.getEducation());
        appUserDto.setSpecialization(appUser.getSpecialization());
        appUserDto.setRoleType(appUser.getRoleType());

        if(appUser.getHospitalList() != null) {

            appUserDto.setHospitalList(appUser.getHospitalList().stream().map(this::hospitalEntityToDto).toList());
        }

        return appUserDto;
    }

    public Hospital hospitalDtoToEntity(HospitalDto hospitalDto) {
        Hospital hospital = new Hospital();
        if (hospitalDto.getHospitalId() != null) {
            hospital.setHospitalId(hospitalDto.getHospitalId());
        }
        hospital.setName(hospitalDto.getName());
        hospital.setAddress(hospitalDto.getAddress());
        hospital.setContactNumber(hospitalDto.getContactNumber());
        return hospital;

    }
    public HospitalDto hospitalEntityToDto(Hospital hospital){
        HospitalDto hospitalDto = new HospitalDto();
        hospitalDto.setHospitalId(hospital.getHospitalId());
        hospitalDto.setName(hospital.getName());
        hospitalDto.setAddress(hospital.getAddress());
        hospitalDto.setContactNumber(hospital.getContactNumber());
        return hospitalDto;
    }
}