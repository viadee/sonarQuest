package com.viadee.sonarquest.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventUserDto {
    private List<UserDto> userDtos;
    private List<EventDto> eventDtos;
}
