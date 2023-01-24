package thrymr.net.hospital.management.entity;


import jakarta.persistence.*;
import lombok.*;

import thrymr.net.hospital.management.enums.RoleType;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppUser  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @Column(name = "email", unique = true)
    private String email;
    @Column(columnDefinition = "varchar(20)")
    private String name;
    @Column(columnDefinition = "varchar(20)")
    private String education;
    @Column(columnDefinition = "varchar(40)")
    private String specialization;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private RoleType roleType;

    private String password;


    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.DETACH
            })
    private List<Hospital> hospitalList = new ArrayList<Hospital>();


}
