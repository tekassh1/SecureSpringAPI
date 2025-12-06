package org.tekassh1.lab1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.tekassh1.lab1.dto.GetRepositorySourceCodeRequest;
import org.tekassh1.lab1.dto.SaveSourceCodeRequest;
import org.tekassh1.lab1.entity.Repository;
import org.tekassh1.lab1.service.VersionControlService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class VersionControlController {

    private final VersionControlService versionControlService;

    @PostMapping("/data")
    public void saveSourceCode(@RequestBody SaveSourceCodeRequest saveSourceCodeRequest) {
        versionControlService.saveSourceCode(
                saveSourceCodeRequest.getRepoName(), saveSourceCodeRequest.getFileName(), saveSourceCodeRequest.getSourceCode());
    }

    @GetMapping("/data")
    public List<Repository> loadSourceCode(@RequestBody GetRepositorySourceCodeRequest getRepositorySourceCodeRequest) {
        return versionControlService.getRepoSourceCode(getRepositorySourceCodeRequest.getRepoName());
    }
}
