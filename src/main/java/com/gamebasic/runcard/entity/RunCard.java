package com.gamebasic.runcard.entity;

import com.gamebasic.game.entity.Game;
import jakarta.persistence.*; // 상위 라이브러리의 하위 전부 호출
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "run_cards", indexes = @Index(name = "idx_run_card_game", columnList = "game_id"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RunCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Game Entity 참조중
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @Column(nullable = false)
    private String cardType;

    @Column(nullable = false)
    private int acquiredFloor;

    public RunCard(Game game, String cardType, int acquiredFloor) {
        this.game = game;
        this.cardType = cardType;
        this.acquiredFloor = acquiredFloor;
    }
}
