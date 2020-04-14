package io.younis.transactional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addSuccess(String email, String address) {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setEmail(email);
        user.setAddress(address);

        return userRepository.save(user);
    }

    public List<User> all() {
        return userRepository.findAll();
    }

    public void addFail(String email, String address) {
        this.saveUser(email, address);
    }

    @Transactional(rollbackFor = Exception.class)
    private void saveUser(String email, String address) {

        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setEmail(email);
        user.setAddress(address);

        userRepository.save(user);
        throw new RuntimeException("something broke");
    }
}
