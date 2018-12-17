package com.viadee.sonarQuest.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.viadee.sonarQuest.entities.Level;
import com.viadee.sonarQuest.entities.Permission;
import com.viadee.sonarQuest.entities.Role;
import com.viadee.sonarQuest.entities.RoleName;
import com.viadee.sonarQuest.entities.User;
import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private WorldService worldService;

	@Autowired
	private LevelService levelService;

	@Autowired
	private PermissionService permissionService;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		final User user = findByUsername(username);
		final Set<Permission> permissions = permissionService.getAccessPermissions(user);

		final List<SimpleGrantedAuthority> authoritys = permissions.stream()
				.map(berechtigung -> new SimpleGrantedAuthority(berechtigung.getPermission()))
				.collect(Collectors.toList());

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true,
				true, true, true, authoritys);
	}

	public User findByUsername(final String username) {
		return userRepository.findByUsername(username);
	}

	private User findById(final Long id) {
		return userRepository.findOne(id);
	}

	public World updateUsersCurrentWorld(final User user, final Long worldId) {
		final World world = worldService.findById(worldId);
		user.setCurrentWorld(world);
		userRepository.saveAndFlush(user);
		return user.getCurrentWorld();
	}

	public synchronized User save(final User user) {
		User toBeSaved = null;
		String username = user.getUsername();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if (user.getId() == null) {
			// Only the password hash needs to be saved
			String password = encoder.encode(user.getPassword());
			Role role = user.getRole();
			RoleName roleName = role.getName();
			Role userRole = roleService.findByName(roleName);
			toBeSaved = usernameFree(username) ? user : null;
			if (toBeSaved != null) {
				toBeSaved.setPassword(password);
				toBeSaved.setRole(userRole);
				toBeSaved.setCurrentWorld(user.getCurrentWorld());
				toBeSaved.setGold(0l);
				toBeSaved.setXp(0l);
				toBeSaved.setLevel(levelService.getLevelByUserXp(0l));
			}
		} else {
			toBeSaved = findById(user.getId());
			if (toBeSaved != null) {
				if (!username.equals(toBeSaved.getUsername()) && usernameFree(username)) {
					toBeSaved.setUsername(username);
				}
				// change password only if it differs from the old one
				String oldPassHash = toBeSaved.getPassword();
				if (!encoder.matches(user.getPassword(), oldPassHash)) {
					String password = encoder.encode(user.getPassword());
					toBeSaved.setPassword(password);
					LOGGER.info("The password for user " + user.getUsername() + " (id: " + user.getId()
							+ ") has been changed.");
				}
				toBeSaved.setAboutMe(user.getAboutMe());
				toBeSaved.setPicture(user.getPicture());
				toBeSaved.setCurrentWorld(user.getCurrentWorld());
				toBeSaved.setWorlds(user.getWorlds());
				toBeSaved.setGold(user.getGold());
				toBeSaved.setXp(user.getXp());
				toBeSaved.setLevel(levelService.getLevelByUserXp(user.getXp()));
			}
		}

		return toBeSaved != null ? userRepository.saveAndFlush(toBeSaved) : null;
	}

	private boolean usernameFree(final String username) {
		return userRepository.findByUsername(username) == null;
	}

	public void delete(final Long userId) {
		final User user = findById(userId);
		userRepository.delete(user);
	}

	public User findById(final long userId) {
		return userRepository.findOne(userId);
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public List<User> findByRole(final RoleName roleName) {
		return findAll().stream().filter(user -> user.getRole().getName() == roleName).collect(Collectors.toList());
	}

	public Level getLevel(final long xp) {
		return levelService.getLevelByUserXp(xp);
	}

}
