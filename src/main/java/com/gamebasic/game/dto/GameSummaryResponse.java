package com.gamebasic.game.dto;

import com.gamebasic.game.entity.Game;
import com.gamebasic.game.entity.GamePhase;
import com.gamebasic.game.entity.GameStatus;
import lombok.Getter;

@Getter
public class GameSummaryResponse {
    private final Long id;
    private final String playerName;
    private final int currentHp;
    private final int currentFloor;
    private final GamePhase phase;
    private final GameStatus status;

    public GameSummaryResponse(
            Long id,
            String playerName,
            int currentHp,
            int currentFloor,
            GamePhase phase,
            GameStatus status
    ){
        this.id = id;
        this.playerName = playerName;
        this.currentHp = currentHp;
        this.currentFloor = currentFloor;
        this.phase = phase;
        this.status = status;
    }

    // ai 추가: Game 엔티티를 변환하는 정적 팩토리 메서드
    public static GameSummaryResponse from(Game game) {
        return new GameSummaryResponse(
                game.getId(),
                game.getPlayerName(),
                game.getCurrentHp(),
                game.getCurrentFloor(),
                game.getPhase(),
                game.getStatus()
        );
    }
}
