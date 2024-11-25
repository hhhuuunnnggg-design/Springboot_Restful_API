package vn.hoidanit.jobhunter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.dto.Meta;
import vn.hoidanit.jobhunter.domain.dto.ResultPaginationDTO;
import vn.hoidanit.jobhunter.repository.UserServiceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class UserServices {
    // cái này nó tự như Autorite
    private final UserServiceRepository userServiceRepository;

    public UserServices(UserServiceRepository userServiceRepository) {
        this.userServiceRepository = userServiceRepository;
    }

    public User handleCreateUser(User user) {
        return this.userServiceRepository.save(user);
    }

    public void handleDeleteUser(Long id) {
        this.userServiceRepository.deleteById(id);
    }

    // tìm nhiều giá trị
    public ResultPaginationDTO fetchAllUser(Pageable pageable) {
        Page<User> pageUsers = this.userServiceRepository.findAll(pageable);
        ResultPaginationDTO rs=new ResultPaginationDTO();
        Meta mt=new Meta();

        mt.setPage(pageUsers.getNumber()+1);
        mt.setPageSize(pageUsers.getSize());

        mt.setPages(pageUsers.getTotalPages());
        mt.setTotal(pageUsers.getTotalElements());

        rs.setMeta(mt);
        rs.setResult(pageUsers.getContent());

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

    // public User handleGetUserByUserName(String username) {
    // return this.userServiceRepository.findByUsername(username);
    // }
}
