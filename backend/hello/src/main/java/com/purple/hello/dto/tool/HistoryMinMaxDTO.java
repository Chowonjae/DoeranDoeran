package com.purple.hello.dto.tool;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HistoryMinMaxDTO {
    int min;
    int max;

    @Builder
    public HistoryMinMaxDTO(int min, int max){
        this.min = min;
        this.max = max;
    }
}
