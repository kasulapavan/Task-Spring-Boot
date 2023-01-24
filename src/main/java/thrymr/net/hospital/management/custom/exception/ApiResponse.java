package thrymr.net.hospital.management.custom.exception;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import thrymr.net.hospital.management.dto.AppUserDto;

@JsonInclude(JsonInclude.Include.NON_NULL)

@Getter
@Setter

@AllArgsConstructor
public class ApiResponse {

    private int code;

    private String message;

    private Object payLoad;

    public ApiResponse(int code, String message) {
        this.code = code;
        this.message = message;
}

           public ApiResponse(int code, Object payLoad) {
            this.code = code;
            this.payLoad = payLoad;
        }


}
