package com.gamebasic.game.controller;

import com.gamebasic.game.dto.*;
import com.gamebasic.game.service.GameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; // 상위 라이브러리로 통합하여 사용

import java.util.List;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/games") // 상위에서 라우팅을 했기 때문에 하위에서는 생략
public class GameController {
    private final GameService gameService;

    @GetMapping("/games")
    public ResponseEntity<List<GameSummaryResponse>> getGames() {
        // List<Object>는 임시 구현이며, Lv 7에서 제대로 고칩니다.
        // List.of()는 빈 목록을 돌려주는 임시 구현이며, Lv 7에서 제대로 고칩니다.
        return ResponseEntity.ok(gameService.getGames());
    }

    @PostMapping("/games")
    public ResponseEntity<GameDetailResponse> createGame(@Valid @RequestBody CreateRequest request) {
        GameDetailResponse created = gameService.createGame(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // TODO (Lv 6): 진행과 전체 덱 저장. 주석을 풀고 구현하세요.
    @PutMapping("/games/{gameId}/progress")
    public ResponseEntity<GameDetailResponse> updateProgress(
            @PathVariable Long gameId,
            @Valid @RequestBody ProgressRequest request
    ) {
        return ResponseEntity.ok(gameService.updateProgress(gameId, request));
    }

    // lv7 :
    @GetMapping("/games/{gameId}")
    public ResponseEntity<GameDetailResponse> getGame(@PathVariable Long gameId) {
        return ResponseEntity.ok(gameService.getGame(gameId));
    }

    // lv8 : 이름 변경 api
    @PatchMapping("/games/{gameId}")
    public ResponseEntity<GameDetailResponse> renameGame(
            @PathVariable Long gameId,
            @Valid @RequestBody RenameRequest request
    ) {
        gameService.renameGame(gameId, request); // @Valid RenameRequest
        return ResponseEntity.noContent().build();
    }

    // lv8 : 삭제
    @DeleteMapping("/games/{gameId}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long gameId) {
        gameService.deleteGame(gameId);
        return ResponseEntity.noContent().build();
    }
}
