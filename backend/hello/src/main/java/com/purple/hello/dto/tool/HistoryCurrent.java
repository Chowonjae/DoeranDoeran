package com.purple.hello.dto.tool;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HistoryCurrent {
    int currentDaily;
    int currentKnow;
    int currentGame;

    @Builder
    public HistoryCurrent(int currentDaily, int currentGame, int currentKnow){
        this.currentDaily = currentDaily;
        this.currentGame = currentGame;
        this.currentKnow = currentKnow;
    }
}
