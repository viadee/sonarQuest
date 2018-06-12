package com.viadee.sonarQuest.services;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.viadee.sonarQuest.entities.RoleName;
import com.viadee.sonarQuest.entities.User;
import com.viadee.sonarQuest.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final User user = findByUsername(username);
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), true, true, true, true, Collections.emptyList());
    }

    public User findByUsername(final String username) {
        return userRepository.findByUsername(username);
    }

    private User findById(final Long id) {
        return userRepository.findOne(id);
    }

    public User save(final User user) {
        User toBeSaved = null;
        if (user.getId() == null) {
            toBeSaved = usernameFree(user.getUsername()) ? user : null;
        } else {
            toBeSaved = findById(user.getId());
            if (!user.getUsername().equals(toBeSaved.getUsername()) && usernameFree(toBeSaved.getUsername())) {
                toBeSaved.setUsername(user.getUsername());
            }
            toBeSaved.setAboutMe(user.getAboutMe());
            toBeSaved.setPicture(user.getPicture());
        }

        return toBeSaved != null ? userRepository.saveAndFlush(toBeSaved) : null;
    }

    private boolean usernameFree(final String username) {
        return userRepository.findByUsername(username) == null;
    }

    public void delete(final User user) {
        userRepository.delete(user);
    }

    public User findById(final long userId) {
        return userRepository.findOne(userId);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findByRole(final RoleName roleName) {
        return findAll().stream()
                .filter(user -> user.getRole().getName() == roleName)
                .collect(Collectors.toList());
    }

    public long getLevel(final long xp) {
        return calculateLevel(xp, 1);
    }

    private long calculateLevel(final long xp, final long level) {
        final long step = 10;
        long xpForNextLevel = 0;

        for (long i = 1; i <= level; i++) {
            xpForNextLevel = xpForNextLevel + step;
        }

        // Termination condition: Level 200 or when XP is smaller than the required XP to the higher level
        if (level == 200 || (xp < xpForNextLevel)) {
            return level;
        } else {
            return calculateLevel(xp, level + 1);
        }
    }

}
