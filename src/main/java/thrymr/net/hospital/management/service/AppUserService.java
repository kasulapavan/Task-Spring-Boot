package thrymr.net.hospital.management.service;

import com.nimbusds.jose.JOSEException;
import thrymr.net.hospital.management.custom.exception.ApiResponse;
import thrymr.net.hospital.management.dto.AppUserDto;
import thrymr.net.hospital.management.dto.SearchDto;
import thrymr.net.hospital.management.entity.AppUser;

import java.util.List;

public interface AppUserService {

    ApiResponse save(AppUserDto appUserDto);

    public ApiResponse signIn(AppUserDto loginDto) throws JOSEException;

    boolean deleteById(Long id);
    public List<AppUserDto> associate(Long id, List<AppUserDto> appUserDtoList) throws JOSEException;

    public AppUserDto disassociate(Long id, AppUserDto appUserList);

    public List<AppUserDto> findAllBySearch(SearchDto keyword);




}
