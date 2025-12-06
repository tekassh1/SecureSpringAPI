package org.tekassh1.lab1.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tekassh1.lab1.entity.User;
import org.tekassh1.lab1.repository.UsersRepository;

@Service
@AllArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;

    @Transactional(rollbackFor = Exception.class)
    public void create(User user) {
        if (usersRepository.findByUsername(user.getUsername()) != null) {
            throw new EntityNotFoundException("Such username already exists!");
        }
        usersRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User get(String username) throws EntityNotFoundException {
        User user = usersRepository.findByUsername(username);
        if (user == null) {
            throw new EntityNotFoundException("Such username does not exist!");
        }
        return user;
    }

    @Transactional(readOnly = true)
    public boolean exists(String username) {
        User user = usersRepository.findByUsername(username);
        if (user == null) {
            return false;
        }
        return true;
    }

    @Transactional(readOnly = true)
    public User getByUsername(String username) {
        User user = usersRepository.findByUsername(username);
        if (user == null) {
            throw new EntityNotFoundException("User not found!");
        }
        return user;
    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }
}
