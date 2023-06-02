package com.purple.hello.dto.tool;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class HistoryTypePythonDTO {
    Integer memberCount;
    List<HistoryTypeDTO> types;
    List<HistoryTypeDTO> feeds;
    List<HistoryCurrent> current;

    @Builder
    public HistoryTypePythonDTO(Integer memberCount, List<HistoryTypeDTO> types, List<HistoryTypeDTO> feeds, List<HistoryCurrent> current){
        this.memberCount = memberCount;
        this.feeds = feeds;
        this.types = types;
        this.current = current;
    }
}
