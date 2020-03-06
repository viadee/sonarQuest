package com.viadee.sonarquest.entities;

import lombok.Data;

@Data
public class UserToWorldConnection {
    private Boolean joined;
    private User user;
    private World world;
}
