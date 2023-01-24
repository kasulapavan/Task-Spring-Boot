package thrymr.net.hospital.management.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import thrymr.net.hospital.management.entity.AppUser;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HospitalDto {


    private Long hospitalId;

    private String name;

    private String address;
    private String  contactNumber;

//    private List<AppUserDto> appUserList = new ArrayList<AppUserDto>();



}
