package com.viadee.sonarquest.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.viadee.sonarquest.entities.Permission;
import com.viadee.sonarquest.entities.Role;
import com.viadee.sonarquest.entities.RoleName;
import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.entities.UserToWorldConnection;
import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

    private static final Log LOGGER = LogFactory.getLog(UserService.class);

    private final RoleService roleService;

    private final UserRepository userRepository;

    private final LevelService levelService;

    private final PermissionService permissionService;

    final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService(final RoleService roleService, final UserRepository userRepository,
            final LevelService levelService, final PermissionService permissionService) {
        this.roleService = roleService;
        this.userRepository = userRepository;
        this.levelService = levelService;
        this.permissionService = permissionService;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final User user = findByUsername(username);
        final Set<Permission> permissions = permissionService.getAccessPermissions(user);

        final List<SimpleGrantedAuthority> authorities = permissions.stream()
                .map(berechtigung -> new SimpleGrantedAuthority(berechtigung.getPermission()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true,
                true, true, true, authorities);
    }

    public User findByUsername(final String username) {
        return userRepository.findByUsername(username);
    }

    private User findById(final Long id) throws ResourceNotFoundException {
        return userRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public User updateUser(final User user) {
        return userRepository.saveAndFlush(user);
    }

    @Transactional
    public synchronized User save(final User user) {
        User toBeSaved;
        final String username = user.getUsername();
        final String mail = user.getMail();

        if (user.getId() == null) {
            // Only the password hash needs to be saved
            final String password = encoder.encode(user.getPassword());
            final Role role = user.getRole();
            final RoleName roleName = role.getName();
            final Role userRole = roleService.findByName(roleName);
            toBeSaved = isUsernameAvailable(username) ? user : null;
            if (toBeSaved != null) {
                setMail(toBeSaved, mail);
                toBeSaved.setPassword(password);
                toBeSaved.setRole(userRole);
                toBeSaved.setCurrentWorld(user.getCurrentWorld());
                toBeSaved.setGold(0L);
                toBeSaved.setXp(0L);
                toBeSaved.setLevel(levelService.getLevelByUserXp(0L));
            }
        } else {
            toBeSaved = findById(user.getId());
            if (toBeSaved != null) {
                final Role role = user.getRole();
                final RoleName roleName = role.getName();
                final Role userRole = roleService.findByName(roleName);
                setUsername(toBeSaved, username);
                setMail(toBeSaved, mail);
                setPassword(user, toBeSaved);
                toBeSaved.setRole(userRole);
                toBeSaved.setAboutMe(user.getAboutMe());
                toBeSaved.setPicture(user.getPicture());
                toBeSaved.setCurrentWorld(user.getCurrentWorld());
                toBeSaved.setWorlds(user.getWorlds());
                toBeSaved.setGold(user.getGold());
                toBeSaved.setXp(user.getXp());
                toBeSaved.setLevel(levelService.getLevelByUserXp(user.getXp()));
                toBeSaved.setAdventures(user.getAdventures());
                toBeSaved.setArtefacts(user.getArtefacts());
                toBeSaved.setAvatarClass(user.getAvatarClass());
                toBeSaved.setParticipations(user.getParticipations());
                toBeSaved.setUiDesign(user.getUiDesign());
            }
        }

        return toBeSaved != null ? userRepository.saveAndFlush(toBeSaved) : null;
    }

    private void setUsername(final User toBeSaved, final String username) {
        if (!username.equals(toBeSaved.getUsername()) && isUsernameAvailable(username)) {
            toBeSaved.setUsername(username);
        }
    }

    private void setMail(final User toBeSaved, final String mail) {
        if (toBeSaved.getMail() != null) {
            if (!mail.equals(toBeSaved.getMail()) && isUserEmailAvailable(mail)) {
                toBeSaved.setMail(mail);
            }
        } else if (isUserEmailAvailable(mail)) {
            toBeSaved.setMail(mail);
        }

    }

    private void setPassword(final User user, final User toBeSaved) {
        // if there are identical hashes in the pw fields, do not touch them
        if (!toBeSaved.getPassword().equals(user.getPassword())) {
            // change password only if it differs from the old one
            final String oldPassHash = toBeSaved.getPassword();
            if (!oldPassHash.equals(user.getPassword()) || !encoder.matches(user.getPassword(), oldPassHash)) {
                final String password = encoder.encode(user.getPassword());
                toBeSaved.setPassword(password);
                LOGGER.info("The password for user " + user.getUsername() + " (id: " + user.getId()
                        + ") has been changed.");
            }
        }
    }

    private boolean isUsernameAvailable(final String username) {
        return userRepository.findByUsername(username) == null;
    }

    private boolean isUserEmailAvailable(final String mail) {
        return userRepository.findByMail(mail) == null;
    }

    public void delete(final Long userId) {
        final User user = findById(userId);
        userRepository.delete(user);
    }

    public User findById(final long userId) throws ResourceNotFoundException {
        return userRepository.findById(userId).orElseThrow(ResourceNotFoundException::new);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public void updateLastLogin(final String username) {
        final User user = findByUsername(username);
        user.setLastLogin(Timestamp.valueOf(LocalDateTime.now()));
        save(user);
    }

    public void updateUserToWorld(final List<UserToWorldConnection> userToWorldConnections) {
        userToWorldConnections.forEach(this::updateUserWorldConnection);
    }

    private User updateUserWorldConnection(final UserToWorldConnection userToWorld) {
        final User user = userToWorld.getUser();
        final World world = userToWorld.getWorld();
        if (userToWorld.getJoined()) {
            user.addWorld(world);
        } else {
            user.removeWorld(world);
            if (user.getCurrentWorld() != null && Objects.equals(user.getCurrentWorld(), world)) {
                user.setCurrentWorld(null);
            }
        }
        return userRepository.save(user);
    }

    public void updateLastTavernVisit(final String username, final Timestamp lastVisit) {
        final User user = userRepository.findByUsername(username);
        if (user != null) {
            user.setLastTavernVisit(lastVisit);
            userRepository.save(user);
        }
    }
}
