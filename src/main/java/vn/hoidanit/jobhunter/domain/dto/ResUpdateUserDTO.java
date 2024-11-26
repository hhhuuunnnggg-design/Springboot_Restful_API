package vn.hoidanit.jobhunter.domain.dto;

import java.time.Instant;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vn.hoidanit.jobhunter.domain.Enum.genderEnum;


@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResUpdateUserDTO {
    long id;
    String username;
    genderEnum gender;
    String address;
    int age;
    Instant updatedAt;
}
