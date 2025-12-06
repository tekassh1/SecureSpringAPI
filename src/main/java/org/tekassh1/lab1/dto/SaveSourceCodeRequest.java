package org.tekassh1.lab1.dto;

import lombok.Data;

@Data
public class SaveSourceCodeRequest {
    private String repoName;
    private String fileName;
    private String sourceCode;
}
