package thrymr.net.hospital.management.controller;

import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import thrymr.net.hospital.management.custom.exception.ApiResponse;
import thrymr.net.hospital.management.dto.AppUserDto;

import thrymr.net.hospital.management.dto.SearchDto;
import thrymr.net.hospital.management.entity.AppUser;
import thrymr.net.hospital.management.service.AppUserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class UserController {
@Autowired
AppUserService appUserService;
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/user/save")
    public ApiResponse save(@RequestBody AppUserDto dto) throws JOSEException {
        return appUserService.save(dto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(value = "/user/update")
    public ApiResponse update(@RequestBody AppUserDto dto) throws JOSEException {
        return appUserService.save(dto);
    }


    @PostMapping(value = "/user/sign-in")
    public ApiResponse signIn(@RequestBody AppUserDto loginDto) throws JOSEException {
        return appUserService.signIn(loginDto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(value = "/user/delete-by-id/{id}")
    public boolean deleteById(@PathVariable Long id){
        return appUserService.deleteById(id);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(value = "/user-hospital/associate/{id}")
    public List<AppUserDto> associate(@PathVariable Long id, @RequestBody List<AppUserDto> appUserDtoList) throws JOSEException{
        return appUserService.associate(id, appUserDtoList);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(value = "/user-hospital/disassociate/{id}")
    public AppUserDto disassociate(@PathVariable Long id, @RequestBody AppUserDto appUserDto) {
        return appUserService.disassociate(id, appUserDto);
    }



@PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(path = "/user-hospital/search/{offset}/{pageSize}")
public List<AppUserDto> searchBy(@RequestBody SearchDto searchDto, @PathVariable Integer  offset, @PathVariable Integer pageSize){
    return appUserService.search(searchDto, offset, pageSize);
}
    @PreAuthorize("hasAuthority('ADMIN')")
@GetMapping(path = "/user-hospital/search")
    public List<AppUserDto> search(@RequestBody SearchDto searchDto){
        return appUserService.searchBy(searchDto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(path = "/user-hospital/search-user/{pageNo}")
    public ApiResponse searchBySpecification(@RequestBody SearchDto searchDto, @PathVariable Integer pageNo){
        return appUserService.searchByUser(searchDto, pageNo);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(path = "/user-hospital/search-specification")
    public ApiResponse searchBySpecification(@RequestBody SearchDto searchDto){
        return appUserService.searchBySpecification(searchDto);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(path = "/user-hospital/search-priority")
    public ApiResponse searchByPriority(@RequestBody SearchDto searchDto){
        return appUserService.searchBySpecification(searchDto);
    }
    }

