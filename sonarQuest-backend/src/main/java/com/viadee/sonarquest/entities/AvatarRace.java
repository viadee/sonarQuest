package com.viadee.sonarquest.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "Avatar_Race")
public class AvatarRace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "avatarRace", cascade = CascadeType.ALL)
    private List<User> users;

    public AvatarRace(final String name) {
        this.name = name;
    }

    public AvatarRace(final String name, final List<User> users) {
        this.name = name;
        this.users = users;
    }

}
