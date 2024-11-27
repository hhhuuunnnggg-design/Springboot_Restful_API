package vn.hoidanit.jobhunter.controller;

import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.dto.ResCreateUserDTO;
import vn.hoidanit.jobhunter.service.UserServices;
import vn.hoidanit.jobhunter.util.annotation.ApiMessage;
import vn.hoidanit.jobhunter.util.error.IdInvalidException;


@RestController
@RequestMapping(value = "/users")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserController {

    private UserServices userService;
    private PasswordEncoder passwordEncoder; // gọi interface mã hóa passwword


    // thêm mới
    @PostMapping("")
    @ApiMessage("Create a new user")
    public ResponseEntity<ResCreateUserDTO> createUser(@Valid @RequestBody User posUser) throws IdInvalidException {
        String hashPassword = this.passwordEncoder.encode(posUser.getPassword()); // kỹ thuật hashPassword
        posUser.setPassword(hashPassword);
        boolean isEmailExist = this.userService.isEmailExist(posUser.getEmail());
        if (isEmailExist) {
            throw new IdInvalidException(
                    "Email " + posUser.getEmail() + "đã tồn tại, vui lòng sử dụng email khác.");
        }
        User newUser = this.userService.handleCreateUser(posUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.convertToResCreateUserDTO(newUser));
    }

    // tìm all user
    @GetMapping("")
    @ApiMessage("fetch all users")
    public ResponseEntity<?> getAllUser(
            @Filter Specification<User> spec,
            Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.fetchAllUser(spec, pageable));
    }


    // xóa
    @DeleteMapping("/{id}")
    @ApiMessage(value = "delete a user")
    public ResponseEntity<String> deleteUser(@PathVariable long id) throws IdInvalidException {
        if (id >= 1500) {
            throw new IdInvalidException("id khong duoc > 1500");
        }
        this.userService.handleDeleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("đã xóa thành công");
    }

    // tìm 1 giá trị
    @GetMapping("/{id}")
    public ResponseEntity<User> getFindByIdUser(@PathVariable("id") Long id) throws IdInvalidException {
        if (id >= 1500) {
            throw new IdInvalidException("id khong duoc > 1500");
        }
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.handleFindByIdUser(id));
    }

    // cập nhật
    @PutMapping("")
    @ApiMessage(value = "update usser")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        User udUser = this.userService.handleUpdateUser(user);
        if (udUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("không tifm thấy id");
        }
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.convertToResUpdateUserDTO(udUser));
    }

}
