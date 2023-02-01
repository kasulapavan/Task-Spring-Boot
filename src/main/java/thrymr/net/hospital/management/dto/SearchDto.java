package thrymr.net.hospital.management.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SearchDto {
  private   String name;
  private   String education;
    private String hospitalName;
private String specialization;

  public SearchDto(String name, String education, String hospitalName, String doctorSpecialization) {
    this.name = name;
    this.education = education;
    this.hospitalName = hospitalName;
    this.specialization = doctorSpecialization;

  }
}
