package vn.hoidanit.jobhunter.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.dto.*;
import vn.hoidanit.jobhunter.repository.UserServiceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class UserServices {

    UserServiceRepository userServiceRepository;


    public User handleCreateUser(User user) {
        return this.userServiceRepository.save(user);
    }

    public void handleDeleteUser(Long id) {
        this.userServiceRepository.deleteById(id);
    }

    // tìm nhiều giá trị
    public ResultPaginationDTO fetchAllUser(Specification<User> spec, Pageable pageable) {
        Page<User> pageUsers = this.userServiceRepository.findAll(spec, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        Meta mt = new Meta();

        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());

        mt.setPages(pageUsers.getTotalPages());
        mt.setTotal(pageUsers.getTotalElements());

        rs.setMeta(mt);
        //rs.setResult(pageUsers.getContent());
        // remove sensitive data

        List<ResUserDTO> listUser = pageUsers.getContent()
                .stream().map(item -> new ResUserDTO(
                        item.getId(),
                        item.getEmail(),
                        item.getUsername(),
                        item.getGender(),
                        item.getAddress(),
                        item.getAge(),
                        item.getUpdatedAt(),
                        item.getCreatedAt()))
                .collect(Collectors.toList());

        rs.setResult(listUser);

        return rs;
    }

    // tìm 1 giá trị
    public User handleFindByIdUser(Long id) {
        Optional<User> UserOption = this.userServiceRepository.findById(id);
        if (UserOption.isPresent()) {
            return UserOption.get();
        }
        return null;
    }

    public User handleUpdateUser(User updateUser) {
        User currentUser = this.handleFindByIdUser(updateUser.getId());
        if (currentUser != null) {
            currentUser.setEmail(updateUser.getEmail());
            currentUser.setPassword(updateUser.getPassword());
            currentUser.setUsername(updateUser.getUsername());
            // updateUser
            return this.userServiceRepository.save(currentUser);
        }
        return currentUser;
    }

    public User handleGetUserByUserName(String username) {
        return this.userServiceRepository.findByEmail(username);
    }

    public ResCreateUserDTO convertToResCreateUserDTO(User user) {
        ResCreateUserDTO rs = new ResCreateUserDTO();
        rs.setId(user.getId());
        rs.setUsername(user.getUsername());
        rs.setEmail(user.getEmail());
        rs.setGender(user.getGender());
        rs.setAddress(user.getAddress());
        rs.setAge(user.getAge());
        rs.setCreatedAt(user.getCreatedAt());
        return rs;
    }

    public ResUpdateUserDTO convertToResUpdateUserDTO(User user) {
        ResUpdateUserDTO rs = new ResUpdateUserDTO();
        rs.setId(user.getId());
        rs.setUsername(user.getUsername());
        rs.setGender(user.getGender());
        rs.setAddress(user.getAddress());
        rs.setAge(user.getAge());
        rs.setUpdatedAt(user.getUpdatedAt());
        return rs;
    }

    public boolean isEmailExist(String email) {
        return this.userServiceRepository.existsByEmail(email);
    }

    // caâp nhật refet token
    public void updateUserToken(String token, String email) {
        User currentUser = this.handleGetUserByUserName(email);
        if (currentUser != null) {
            currentUser.setRefreshToken(token);
            this.userServiceRepository.save(currentUser);
        }
    }
}
