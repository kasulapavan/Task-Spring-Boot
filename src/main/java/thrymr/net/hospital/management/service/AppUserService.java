package thrymr.net.hospital.management.service;
import com.nimbusds.jose.JOSEException;
import thrymr.net.hospital.management.custom.exception.ApiResponse;
import thrymr.net.hospital.management.dto.AppUserDto;
import thrymr.net.hospital.management.dto.SearchDto;


import java.util.List;

public interface AppUserService {

    ApiResponse save(AppUserDto appUserDto);

     ApiResponse signIn(AppUserDto loginDto) throws JOSEException;

    boolean deleteById(Long id);
     List<AppUserDto> associate(Long id, List<AppUserDto> appUserDtoList) throws JOSEException;

     AppUserDto disassociate(Long id, AppUserDto appUserList);


    List<AppUserDto> search(SearchDto searchDto, Integer  offset, Integer pageSize);

     ApiResponse searchByUser(SearchDto searchDto, Integer pageNo);
     List<AppUserDto> searchBy(SearchDto searchDto);
     ApiResponse searchBySpecification(SearchDto searchDto);
     ApiResponse searchByPriority(SearchDto searchDto, Integer offset, Integer pageSize);
}
