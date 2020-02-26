package com.viadee.sonarquest.entities;

import lombok.Data;

@Data
public class UserToWorldDto {
    private Long userId;
    private Long worldId;
    private String worldName;
    private Boolean joined;
}