package org.tekassh1.lab1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tekassh1.lab1.entity.User;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    User findByUsername(String login);
}
