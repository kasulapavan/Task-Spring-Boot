package thrymr.net.hospital.management.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Hospital {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hospitalId;
    @Column(columnDefinition = "varchar(20)")
    private String name;
    @Column(columnDefinition = "TEXT")
    private String address;
    @Column(columnDefinition = "varchar(10)")
    private String  contactNumber;
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.DETACH
            },
            mappedBy = "hospitalList")

    @JsonIgnore
    private List<AppUser> appUserList = new ArrayList<AppUser>();

}
