package vn.hoidanit.jobhunter.domain.response;

import java.time.Instant;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vn.hoidanit.jobhunter.domain.Enum.GenderEnum;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResCreateUserDTO {
    long id;
    String name;
    String email;
    GenderEnum gender;
    String address;
    int age;
    Instant createdAt;
}