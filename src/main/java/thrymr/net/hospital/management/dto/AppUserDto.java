package thrymr.net.hospital.management.dto;


import lombok.*;
import thrymr.net.hospital.management.entity.AppUser;
import thrymr.net.hospital.management.entity.Hospital;
import thrymr.net.hospital.management.enums.RoleType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDto  {

    private Long id;

    private String email;

    private String name;

    private String education;

    private String specialization;

    private String password;

    private RoleType roleType;
    private String token;

    private List<HospitalDto> hospitalList = new ArrayList<HospitalDto>();




}
