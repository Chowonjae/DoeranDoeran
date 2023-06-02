package com.purple.hello.dto.tool;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class AwsS3DTO {
    private String key;
    private String path;

    @Builder
    public AwsS3DTO(String key, String path) {
        this.key = key;
        this.path = path;
    }
}
