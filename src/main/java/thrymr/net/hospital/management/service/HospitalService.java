package thrymr.net.hospital.management.service;

import thrymr.net.hospital.management.custom.exception.ApiResponse;
import thrymr.net.hospital.management.dto.AppUserDto;
import thrymr.net.hospital.management.dto.HospitalDto;

import java.util.List;

public interface HospitalService {

    boolean deleteById(Long id);

    public ApiResponse saveAndUpdate(Long id, List<HospitalDto> hospitalDtoList);

    public ApiResponse getAll();

    public ApiResponse save(HospitalDto hospitalDtoList);


}
