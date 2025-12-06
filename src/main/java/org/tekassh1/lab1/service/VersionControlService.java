package org.tekassh1.lab1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tekassh1.lab1.entity.Repository;
import org.tekassh1.lab1.repository.VersionControlRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class VersionControlService {
    private final VersionControlRepository versionControlRepository;

    @Transactional(rollbackFor = Exception.class)
    public void saveSourceCode(String repositoryName, String fileName, String sourceCode) {
        Repository record = Repository.builder()
                .username(Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName())
                .name(repositoryName)
                .fileName(fileName)
                .sourceCode(sourceCode)
                .build();
        versionControlRepository.save(record);
    }

    @Transactional
    public List<Repository> getRepoSourceCode(String repositoryName) {
        return versionControlRepository.findAllByName(repositoryName);
    }
}
