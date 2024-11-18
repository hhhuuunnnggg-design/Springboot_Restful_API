package vn.hoidanit.jobhunter.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.util.error.idInvalidException;

@RestController
public class UserController {
    // không nên sử dụng annotation @autorite
    private final UserService userService;

    private final PasswordEncoder passwordEncoder; // gọi interface mã hóa passwword

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // thêm mới
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User posUser) {
        String hashPassword = this.passwordEncoder.encode(posUser.getPassword()); // kỹ thuật hashPassword
        posUser.setPassword(hashPassword);
        User newUser = this.userService.handleCreateUser(posUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    // xóa
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) throws idInvalidException {
        if (id >= 1500) {
            throw new idInvalidException("id khong duoc > 1500");
        }
        this.userService.handleDeleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("đã xóa thành công");
    }

    // tìm 1 giá trị
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getFindByIdUser(@PathVariable("id") Long id) throws idInvalidException {
        if (id >= 1500) {
            throw new idInvalidException("id khong duoc > 1500");
        }
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.handleFindByIdUser(id));
    }

    // tìm all user
    @GetMapping("/users")
    public ResponseEntity<List<User>> getMethodName() {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.handleFindAllUser());
    }

    // cập nhật
    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User udUser = this.userService.handleUpdateUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(udUser);
    }

}
