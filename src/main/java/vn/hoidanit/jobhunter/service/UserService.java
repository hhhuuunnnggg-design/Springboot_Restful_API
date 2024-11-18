package vn.hoidanit.jobhunter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.repository.UserServiceRepository;

@Service
public class UserService {
    // cái này nó tự như Autorite
    private final UserServiceRepository userServiceRepository;

    public UserService(UserServiceRepository userServiceRepository) {
        this.userServiceRepository = userServiceRepository;
    }

    public User handleCreateUser(User user) {
        return this.userServiceRepository.save(user);
    }

    public void handleDeleteUser(Long id) {
        this.userServiceRepository.deleteById(id);
    }

    // tìm 1 giá trị
    public User handleFindByIdUser(Long id) {
        Optional<User> UserOption = this.userServiceRepository.findById(id);
        if (UserOption.isPresent()) {
            return UserOption.get();
        }
        return null;
    }

    // tìm nhiều giá trị
    public List<User> handleFindAllUser() {
        return this.userServiceRepository.findAll();
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
