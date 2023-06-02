package com.purple.hello.dto.tool;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HistoryTypeDTO {
    String typeName;
    int count;

    @Builder
    public HistoryTypeDTO(String typeName, int count){
        this.typeName = typeName;
        this.count = count;
    }
}
