package com.gamebasic.runcard.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RunCardRequest {
    // TODO (Lv 5): API 명세의 카드 필드 제약을 Bean Validation 어노테이션으로 붙이세요.
    @NotBlank // 공백값 허용x, 빈 값 x, null x
    private String cardType;

    @Min(0)
    @Max(10) // 0~10
    private Integer acquiredFloor;
}
