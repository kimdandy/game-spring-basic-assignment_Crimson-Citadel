package com.gamebasic.game.service;

import com.gamebasic.game.dto.*;
import com.gamebasic.game.entity.Game;
import com.gamebasic.game.repository.GameRepository;
import com.gamebasic.runcard.dto.CardResponse;
import com.gamebasic.runcard.dto.RunCardRequest;
import com.gamebasic.runcard.entity.RunCard;
import com.gamebasic.runcard.repository.RunCardRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Component //Bean 자동 등록
public class GameService {

    private final GameRepository gameRepository;
    private final RunCardRepository runCardRepository;

    @Transactional
    // 기존에는 읽기만(readOnly = true) 가능하여 저장자체가 안되는 상황이었음
    // 접근 및 수정을 위하여 읽기 모드를 false로 변경
    // default가 false 이므로 삭제
    public GameDetailResponse createGame(CreateRequest request) {
        Game game = gameRepository.save(new Game(request.getPlayerName()));
        saveDeck(game, request.getDeck());
        List<RunCard> cards = runCardRepository.findAllByGameOrderByIdAsc(game);
        List<CardResponse> deck = new ArrayList<>();
        for (RunCard card : cards) {
            deck.add(new CardResponse(card.getId(), card.getCardType(), card.getAcquiredFloor()));
        }
        return new GameDetailResponse(
                game.getId(),
                game.getPlayerName(),
                game.getCurrentHp(),
                game.getCurrentFloor(),
                game.getPhase(),
                game.getStatus(),
                deck
        );
    }

    private void saveDeck(Game game, List<RunCardRequest> deck) {
        List<RunCard> cards = new ArrayList<>();
        for (RunCardRequest card : deck) {
            cards.add(new RunCard(game, card.getCardType(), card.getAcquiredFloor()));
        }
        runCardRepository.saveAll(cards);
    }

    private Game findGame(Long gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public GameDetailResponse updateProgress(Long gameId, ProgressRequest request) {
        Game game = findGame(gameId);
        game.updateProgress(
                request.getCurrentHp(),
                request.getCurrentFloor(),
                request.getPhase(),
                request.getStatus()
        );
        // 요청의 deck은 저장할 덱 전체이므로 기존 카드를 모두 지우고 요청 순서대로 다시 저장합니다.
        runCardRepository.deleteAllByGame(game);
        saveDeck(game, request.getDeck());
        List<RunCard> cards = runCardRepository.findAllByGameOrderByIdAsc(game);
        List<CardResponse> deck = new ArrayList<>();
        for (RunCard card : cards) {
            deck.add(new CardResponse(card.getId(), card.getCardType(), card.getAcquiredFloor()));
        }
        return new GameDetailResponse(
                game.getId(),
                game.getPlayerName(),
                game.getCurrentHp(),
                game.getCurrentFloor(),
                game.getPhase(),
                game.getStatus(),
                deck
        );
    }

    // TODO (Lv 7): 게임 목록 조회. 주석을 풀고 구현하세요.
    @Transactional(readOnly = true)
    public List<GameSummaryResponse> getGames() {
        return gameRepository.findAllByOrderByIdDesc().stream().map(GameSummaryResponse::from).toList(); // 내림차순으로 정렬한 게임 정보 목록
    }

    // TODO (Lv 7): 게임 상세 조회. 주석을 풀고 구현하세요.
    @Transactional(readOnly = true)
    public GameDetailResponse getGame(Long gameId) {
//        return gameRepository.findById(gameId).orElseThrow(() ->
//                new IllegalArgumentException("정보 없음")
//        );
        Game game = findGame(gameId); // 입력받은 Id를 통해 해당 게임 정보 찾기
        List<RunCard> cards = runCardRepository.findAllByGameOrderByIdAsc(game); // 오름차순으로 정렬한 카드 목록
        List<CardResponse> deck = new ArrayList<>();
        for (RunCard card : cards) {
            deck.add(new CardResponse(card.getId(), card.getCardType(), card.getAcquiredFloor()));
        } // 덱에 해당 정보 확보
        return new GameDetailResponse(
                game.getId(),
                game.getPlayerName(),
                game.getCurrentHp(),
                game.getCurrentFloor(),
                game.getPhase(),
                game.getStatus(),
                deck
        );
    }

    // TODO (Lv 8): 플레이어 이름 변경 — 변경 감지로 수정
    @Transactional //영속성 컨텍스트의 변경감지 적용
    public void renameGame(Long gameId, @Valid RenameRequest request){ // 특별히 반환할 게 없으니 void로 처리
        Game game = findGame(gameId);

        if(!Objects.equals(request.getPlayerName(), game.getPlayerName())){ // 같지않다면 변환 처리
            game.rename(request.getPlayerName());
        }

    }

    // TODO (Lv 8): 게임 삭제
    @Transactional
    public void deleteGame(Long gameId){
        Game game = findGame(gameId);
        runCardRepository.deleteAllByGame(game);
        gameRepository.delete(game); // 내장된 delete 사용
    }
}
