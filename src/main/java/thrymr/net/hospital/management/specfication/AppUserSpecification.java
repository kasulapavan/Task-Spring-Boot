package thrymr.net.hospital.management.specfication;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import thrymr.net.hospital.management.custom.exception.ApiResponse;
import thrymr.net.hospital.management.dto.SearchDto;
import thrymr.net.hospital.management.entity.AppUser;
import thrymr.net.hospital.management.entity.Hospital;

import java.util.ArrayList;
import java.util.List;

@Component
public class AppUserSpecification {

    public static Specification<AppUser> hasName(String name) {
        return (((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("name"), name);
        }));
    }

    public static Specification<AppUser> education(String education) {
        return (((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("education"), education);
        }));
    }

    public static Specification<AppUser> specialization(String specialization) {
        return (((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("specialization"), specialization);
        }));
    }

    public static Specification<AppUser> hospitalName(String name) {
        return (((root, query, criteriaBuilder) -> {
            Join<AppUser, Hospital> appUserHospitalJoin = root.join("hospitalList");
            return criteriaBuilder.equal(appUserHospitalJoin.get("name"), name);
        }));
    }

    public static Specification<AppUser> search(SearchDto searchDto) {
        return (root, query, criteriaBuilder) -> {
            String name = searchDto.getName();
            String education = searchDto.getEducation();
            String specialization = searchDto.getSpecialization();
            String name1 = searchDto.getHospitalName();
            List<Predicate> searchCriteria = new ArrayList<Predicate>();

            Join<AppUser, Hospital> appUserHospitalJoin = root.join("hospitalList");
            List<Order> orders = new ArrayList<Order>();

            if (name != null) {
                searchCriteria.add(criteriaBuilder.equal(root.get("name"), name));
                orders.add(criteriaBuilder.asc(root.get("name")));
            }
            if (education != null) {
                searchCriteria.add(criteriaBuilder.equal(root.get("education"), education));
                orders.add(criteriaBuilder.asc(root.get("education")));
            }
            if (specialization != null) {
                searchCriteria.add(criteriaBuilder.equal(root.get("specialization"), specialization));
                orders.add(criteriaBuilder.asc(root.get("specialization")));
            }
            if (name1 != null) {
                searchCriteria.add(criteriaBuilder.equal(appUserHospitalJoin.get("name"), name1));
                orders.add(criteriaBuilder.asc(appUserHospitalJoin.get("name")));
            }
            query.orderBy(orders);
            return criteriaBuilder.and(searchCriteria.toArray(new Predicate[0]));
        };
    }


}
