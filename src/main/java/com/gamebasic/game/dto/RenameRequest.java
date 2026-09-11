package com.gamebasic.game.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class RenameRequest {
    @NotBlank
    @Size(min=2, max=12) // 크기가 2에서 12사이
    private String playerName;
}
