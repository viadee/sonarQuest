package com.viadee.sonarquest.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.viadee.sonarquest.entities.Artefact;
import com.viadee.sonarquest.entities.Level;
import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.repositories.ArtefactRepository;

@ExtendWith(MockitoExtension.class)
public class ArtefactServiceTest {

	@InjectMocks
	private ArtefactService service;

	@Mock
	private ArtefactRepository artefactRepository;

	@Mock
	private LevelService levelService;

	@Mock
	private UserService userService;

	private Artefact mockArtefact() {
		final Artefact artefact = new Artefact();
		artefact.setName("Mighty staff of fiery doom");
		return artefact;
	}

	@Test
	public void testGetArtefacts() {
		// given
		final List<Artefact> savedArtefacts = new ArrayList<>();
		final Artefact artefact = new Artefact();
		savedArtefacts.add(artefact);
		when(artefactRepository.findAll()).thenReturn(savedArtefacts);
		// When
		final List<Artefact> artefacts = service.getArtefacts();
		// Then
		assertEquals(artefact, artefacts.get(0));
	}

	@Test
	public void testGetArtefactsForMarketplace() {
		// Given
		final List<Artefact> savedArtefacts = new ArrayList<>();
		final Artefact artefact = new Artefact();
		savedArtefacts.add(artefact);
		when(artefactRepository.findByQuantityIsGreaterThanEqual(1L)).thenReturn(savedArtefacts);
		// When
		final List<Artefact> artefactsForMarketplace = service.getArtefactsForMarketplace();
		// Then
		assertEquals(artefact, artefactsForMarketplace.get(0));
	}

	@Test
	public void testGetArtefact() {
		// given
		final Artefact savedArtefact = new Artefact();
		when(artefactRepository.findById(1L)).thenReturn(Optional.of(savedArtefact));
		// When
		final Artefact artefact = service.getArtefact(1L);
		// Then
		assertSame(savedArtefact, artefact);
	}

	@Test
	public void testCreateArtefact_minLevelDoesNotYetExist() {
		// given
		final Artefact artefact = mockArtefact();
		final Level minLevel = mockLevel(1);
		artefact.setMinLevel(minLevel);
		when(levelService.findByLevel(1)).thenReturn(null);
		when(artefactRepository.save(artefact)).thenReturn(artefact);
		// when
		final Artefact newArtefact = service.createArtefact(artefact);
		// then
		assertSame(minLevel, newArtefact.getMinLevel());
	}

	@Test
	public void testCreateArtefact_minLevelAlreadyExists() {
		final Artefact artefact = mockArtefact();
		final Level minLevel = mockLevel(1);
		artefact.setMinLevel(minLevel);
		final Level savedLevel = mockLevel(1);
		when(levelService.findByLevel(1)).thenReturn(savedLevel);
		when(artefactRepository.save(artefact)).thenReturn(artefact);
		// when
		final Artefact newArtefact = service.createArtefact(artefact);
		// then
		assertSame(savedLevel, newArtefact.getMinLevel());
	}

	private Level mockLevel(final int level) {
		final Level minLevel = new Level();
		minLevel.setLevelNumber(level);
		return minLevel;
	}

	/**
	 * Testcase is Artefact with Price 7, Qty 1, MinLevel 1<br>
	 * User with Gold 10, Level 2<br>
	 * Result: user has 3 Gold left, Qty is 0 after purchase
	 */
	@Test
	public void testBuyArtefact_Ok() {
		// Given
		final Artefact artefact = mockArtefact();
		artefact.setId(69L);
		artefact.setPrice(7L);
		artefact.setQuantity(1L);
		artefact.setMinLevel(mockLevel(1));
		final User user = new User();
		user.setId(1L);
		user.setGold(10L);
		user.setLevel(mockLevel(2));
		when(artefactRepository.findById(69L)).thenReturn(Optional.of(artefact));
		when(artefactRepository.save(artefact)).thenReturn(artefact);
		when(userService.save(user)).thenReturn(user);
		// When
		final Artefact boughtArtefact = service.buyArtefact(artefact.getId(), user);
		// Then
		assertSame(boughtArtefact, artefact);
		assertEquals(Long.valueOf(0), boughtArtefact.getQuantity());
		assertEquals(Long.valueOf(3), user.getGold());
	}

	/**
	 * Testcase is Artefact with Price 19, Qty 1, MinLevel 1<br>
	 * User with Gold 10, Level 2<br>
	 * Result: user has 10 Gold left, Qty is 1, purchase is cancelled (null
	 * returned)
	 */
	@Test
	public void testBuyArtefact_InStock_UserCannotAfford() {
		// Given
		final Artefact artefact = mockArtefact();
		artefact.setId(69L);
		artefact.setPrice(19L);
		artefact.setQuantity(1L);
		artefact.setMinLevel(mockLevel(1));
		final User user = new User();
		user.setId(1L);
		user.setGold(10L);
		user.setLevel(mockLevel(2));
		when(artefactRepository.findById(69L)).thenReturn(Optional.of(artefact));
		// When
		final Artefact boughtArtefact = service.buyArtefact(artefact.getId(), user);
		// Then
		assertSame(null, boughtArtefact);
		assertEquals(Long.valueOf(1), artefact.getQuantity());
		assertEquals(Long.valueOf(10), user.getGold());
	}

	/**
	 * Testcase is Artefact with Price 7, Qty 0, MinLevel 1<br>
	 * User with Gold 10, Level 2<br>
	 * Result: user has 10 Gold left, Qty is 0, purchase is cancelled (null
	 * returned)
	 */
	@Test
	public void testBuyArtefact_ItemSoldOut() {
		// Given
		final Artefact artefact = mockArtefact();
		artefact.setId(69L);
		artefact.setPrice(7L);
		artefact.setQuantity(0L);
		artefact.setMinLevel(mockLevel(1));
		final User user = new User();
		user.setId(1L);
		user.setGold(10L);
		user.setLevel(mockLevel(2));
		when(artefactRepository.findById(69L)).thenReturn(Optional.of(artefact));
		// When
		final Artefact boughtArtefact = service.buyArtefact(artefact.getId(), user);
		// Then
		assertSame(null, boughtArtefact);
		assertEquals(Long.valueOf(0), artefact.getQuantity());
		assertEquals(Long.valueOf(10), user.getGold());
	}

	/**
	 * Testcase is Artefact with Price 7, Qty 1, MinLevel 2<br>
	 * User with Gold 10, Level 1<br>
	 * Result: user has 10 Gold left, Qty is 1, purchase is cancelled (null
	 * returned)
	 */
	@Test
	public void testBuyArtefact_userLevelNotHighEnough() {
		// Given
		final Artefact artefact = mockArtefact();
		artefact.setId(69L);
		artefact.setPrice(7L);
		artefact.setQuantity(1L);
		artefact.setMinLevel(mockLevel(2));
		final User user = new User();
		user.setId(1L);
		user.setGold(10L);
		user.setLevel(mockLevel(1));
		when(artefactRepository.findById(69L)).thenReturn(Optional.of(artefact));
		// When
		final Artefact boughtArtefact = service.buyArtefact(artefact.getId(), user);
		// Then
		assertSame(null, boughtArtefact);
		assertEquals(Long.valueOf(1), artefact.getQuantity());
		assertEquals(Long.valueOf(10), user.getGold());
	}

	/**
	 * Testcase is Artefact with Price 7, Qty 1, MinLevel 1<br>
	 * User with Gold 10, Level 1 but already owns the artefact<br>
	 * Result: user has 10 Gold left, Qty is 1, purchase is cancelled (null
	 * returned)
	 */
	@Test
	public void testBuyArtefact_userAlreadyOwnsArtefact() {
		// Given
		final Artefact artefact = mockArtefact();
		artefact.setId(69L);
		artefact.setPrice(7L);
		artefact.setQuantity(1L);
		artefact.setMinLevel(mockLevel(1));
		final User user = new User();
		user.setId(1L);
		user.setGold(10L);
		user.setLevel(mockLevel(2));
		user.getArtefacts().add(artefact);
		when(artefactRepository.findById(69L)).thenReturn(Optional.of(artefact));
		// When
		final Artefact boughtArtefact = service.buyArtefact(artefact.getId(), user);
		// Then
		assertSame(null, boughtArtefact);
		assertEquals(Long.valueOf(1), artefact.getQuantity());
		assertEquals(Long.valueOf(10), user.getGold());
	}

}
