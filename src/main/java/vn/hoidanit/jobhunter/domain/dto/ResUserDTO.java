package vn.hoidanit.jobhunter.domain.dto;

import java.time.Instant;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.hoidanit.jobhunter.domain.Enum.genderEnum;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResUserDTO {
     long id;
     String email;
     String name;
     genderEnum gender;
     String address;
     int age;
     Instant updatedAt;
     Instant createdAt;
}
