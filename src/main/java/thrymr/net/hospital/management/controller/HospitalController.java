package thrymr.net.hospital.management.controller;

import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import thrymr.net.hospital.management.custom.exception.ApiResponse;
import thrymr.net.hospital.management.dto.AppUserDto;
import thrymr.net.hospital.management.dto.HospitalDto;
import thrymr.net.hospital.management.service.HospitalService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class HospitalController {

@Autowired
private HospitalService hospitalService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/hospital/update/{id}")
    public ApiResponse saveAndUpdate(@PathVariable Long id, @RequestBody List<HospitalDto> hospitalDtoList){
        return hospitalService.saveAndUpdate(id, hospitalDtoList);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/hospital/save")
    public ApiResponse save(@RequestBody HospitalDto dto) throws JOSEException {
        return hospitalService.save(dto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(value = "/hospital/update")
    public ApiResponse update(@RequestBody HospitalDto dto) throws JOSEException {
        return hospitalService.save(dto);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/hospital/delete-by-id/{id}")
    public boolean deleteById(@PathVariable Long id){
        return hospitalService.deleteById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'DOCTOR')")
    @GetMapping(value = "/hospital/get-all")
    public ApiResponse getAll(){
        return hospitalService.getAll();
    }

}
