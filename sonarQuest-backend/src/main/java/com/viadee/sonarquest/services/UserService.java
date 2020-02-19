package com.viadee.sonarquest.services;

import com.viadee.sonarquest.entities.*;
import com.viadee.sonarquest.repositories.UserRepository;
import com.viadee.sonarquest.repositories.WorldRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private static final Log LOGGER = LogFactory.getLog(UserService.class);

	private final RoleService roleService;

	private final UserRepository userRepository;

	private final WorldService worldService;

	private final LevelService levelService;

	private final PermissionService permissionService;

	private final WorldRepository worldRepository;

	public UserService(RoleService roleService, UserRepository userRepository, WorldService worldService, LevelService levelService, PermissionService permissionService, WorldRepository worldRepository) {
		this.roleService = roleService;
		this.userRepository = userRepository;
		this.worldService = worldService;
		this.levelService = levelService;
		this.permissionService = permissionService;
		this.worldRepository = worldRepository;
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

	private User findById(final Long id) throws ResourceNotFoundException{
		return userRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
	}

	public World updateUsersCurrentWorld(final User user, final Long worldId) {
		final World world = worldService.findById(worldId);
		user.setLastTavernVisit(null);
		user.setCurrentWorld(world);
		userRepository.saveAndFlush(user);
		return user.getCurrentWorld();
	}

	@Transactional
	public synchronized User save(final User user) {
		User toBeSaved;
		final String username = user.getUsername();
		final String mail = user.getMail();
		final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if (user.getId() == null) {
			// Only the password hash needs to be saved
			final String password = encoder.encode(user.getPassword());
			final Role role = user.getRole();
			final RoleName roleName = role.getName();
			final Role userRole = roleService.findByName(roleName);
			toBeSaved = usernameFree(username) ? user : null;
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
				setPassword(user, toBeSaved, encoder);
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

	private void setUsername(User toBeSaved, final String username) {
		if (!username.equals(toBeSaved.getUsername()) && usernameFree(username)) {
			toBeSaved.setUsername(username);
		}
	}

	private void setMail(User toBeSaved, final String mail) {
		if (toBeSaved.getMail() != null) {
			if (!mail.equals(toBeSaved.getMail()) && mailFree(mail)) {
				toBeSaved.setMail(mail);
			}
		} else if (mailFree(mail)) {
			toBeSaved.setMail(mail);
		}

	}

	private void setPassword(final User user, User toBeSaved, final BCryptPasswordEncoder encoder) {
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

	private boolean usernameFree(final String username) {
		return userRepository.findByUsername(username) == null;
	}

	private boolean mailFree(final String mail) {
		return userRepository.findByMail(mail) == null;
	}

	public void delete(final Long userId) {
		final User user = findById(userId);
		userRepository.delete(user);
	}

	@NotNull
	public User findById(final long userId) throws ResourceNotFoundException {
		return userRepository.findById(userId).orElseThrow(ResourceNotFoundException::new);
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

	@Transactional
	public void updateLastLogin(final String username) {
		final User user = findByUsername(username);
		user.setLastLogin(Timestamp.valueOf(LocalDateTime.now()));
		save(user);
	}

	public Boolean updateUserToWorld(List<UserToWorldDto> userToWorlds) throws ResourceNotFoundException {
		userToWorlds.forEach(userToWorld -> {
			User user = userRepository.findById(userToWorld.getUserId()).orElseThrow(ResourceNotFoundException::new);
			if (userToWorld.getJoined()) {
				World world = worldRepository.findById(userToWorld.getWorldId()).orElseThrow(ResourceNotFoundException::new);
				user.addWorld(world);
			} else {
				if (user.getCurrentWorld() != null && user.getCurrentWorld().getId().equals(userToWorld.getWorldId())) {
					user.setCurrentWorld(null);
				}
				user.removeWorld(worldRepository.findById(userToWorld.getWorldId()).orElseThrow(ResourceNotFoundException::new));
			}

			userRepository.save(user);
		});

		return true;
	}

	public void updateLastTavernVisit(String username, Timestamp lastVisit) {
		User user = userRepository.findByUsername(username);
		if (user != null) {
			user.setLastTavernVisit(lastVisit);
			userRepository.save(user);
		}

	}
}
