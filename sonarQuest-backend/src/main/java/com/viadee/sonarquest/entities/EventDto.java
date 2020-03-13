package com.viadee.sonarquest.entities;

import java.sql.Timestamp;

import com.viadee.sonarquest.constants.EventState;
import com.viadee.sonarquest.constants.EventType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@NoArgsConstructor
public class EventDto {

    private Long id;
    private EventType type;
    private String title;
    private String story;
    private EventState state;
    private String image;
    private String headline;
    private Long worldId;
    private Long userId;
    private Timestamp timestamp;

    public EventDto(Event event) {
        if (event.getUser() != null) {
            this.userId = event.getUser().getId();
        } else {
            this.userId = null;
        }

        if (event.getWorld() != null) {
            this.worldId = event.getWorld().getId();
        } else {
            this.worldId = null;
        }

        this.id = event.getId();
        this.type = event.getType();
        this.title = event.getTitle();
        this.story = event.getStory();
        this.state = event.getState();
        this.headline = event.getHeadline();
        this.image = event.getImage();
        this.timestamp = event.getTimestamp();
    }

}
