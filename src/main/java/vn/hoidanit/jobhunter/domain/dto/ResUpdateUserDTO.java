package vn.hoidanit.jobhunter.domain.dto;

import java.time.Instant;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vn.hoidanit.jobhunter.domain.Enum.GenderEnum;


@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResUpdateUserDTO {
    long id;
    String name;
    GenderEnum gender;
    String address;
    int age;
    Instant updatedAt;
}
