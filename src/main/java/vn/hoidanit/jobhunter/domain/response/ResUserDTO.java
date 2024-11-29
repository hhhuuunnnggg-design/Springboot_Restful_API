package vn.hoidanit.jobhunter.domain.response;

import java.time.Instant;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.hoidanit.jobhunter.domain.Enum.GenderEnum;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResUserDTO {
     long id;
     String email;
     String name;
     GenderEnum gender;
     String address;
     int age;
     Instant updatedAt;
     Instant createdAt;
}
