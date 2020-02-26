package com.viadee.sonarquest.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDto {
    private String message;
    private Long userId;

    public MessageDto() {
    }

    public MessageDto(String message, Long userId) {
        this.message = message;
        this.userId = userId;
    }
}
