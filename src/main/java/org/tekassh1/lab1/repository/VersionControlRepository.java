package org.tekassh1.lab1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tekassh1.lab1.entity.Repository;

import java.util.List;

@org.springframework.stereotype.Repository
public interface VersionControlRepository extends JpaRepository<Repository, Long> {
    List<Repository> findAllByName(String name);
}
