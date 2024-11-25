package vn.hoidanit.jobhunter.controller;

import java.util.List;
import java.util.Optional;

import com.turkraft.springfilter.boot.Filter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.dto.ResultPaginationDTO;
import vn.hoidanit.jobhunter.service.UserServices;
import vn.hoidanit.jobhunter.util.annotation.ApiMessage;
import vn.hoidanit.jobhunter.util.error.idInvalidException;


@RestController
@RequestMapping(value = "/users")
public class UserController {
    // không nên sử dụng annotation @autorite
    private final UserServices userService;

    private final PasswordEncoder passwordEncoder; // gọi interface mã hóa passwword

    public UserController(UserServices userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // tìm all user
    @GetMapping("")
    @ApiMessage("fetch all users")
    public ResponseEntity<?> getAllUser(
            @Filter Specification<User> spec,
            Pageable pageable ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.fetchAllUser(spec,pageable));
    }



    // thêm mới
    @PostMapping("")
    public ResponseEntity<User> createUser(@RequestBody User posUser) {
        String hashPassword = this.passwordEncoder.encode(posUser.getPassword()); // kỹ thuật hashPassword
        posUser.setPassword(hashPassword);
        User newUser = this.userService.handleCreateUser(posUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    // xóa
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) throws idInvalidException {
        if (id >= 1500) {
            throw new idInvalidException("id khong duoc > 1500");
        }
        this.userService.handleDeleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("đã xóa thành công");
    }

    // tìm 1 giá trị
    @GetMapping("/{id}")
    public ResponseEntity<User> getFindByIdUser(@PathVariable("id") Long id) throws idInvalidException {
        if (id >= 1500) {
            throw new idInvalidException("id khong duoc > 1500");
        }
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.handleFindByIdUser(id));
    }

    // cập nhật
    @PutMapping("")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User udUser = this.userService.handleUpdateUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(udUser);
    }

}
