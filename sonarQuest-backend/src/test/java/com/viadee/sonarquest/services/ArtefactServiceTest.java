package com.viadee.sonarquest.services;

import com.viadee.sonarquest.entities.Artefact;
import com.viadee.sonarquest.entities.Level;
import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.repositories.ArtefactRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArtefactServiceTest {

	@InjectMocks
	private ArtefactService service;

	@Mock
	private ArtefactRepository artefactRepository;

	@Mock
	private LevelService levelService;

	private Artefact mockArtefact() {
		Artefact artefact = new Artefact();
		artefact.setName("Mighty staff of fiery doom");
		return artefact;
	}

	@Test
	public void testGetArtefacts() {
		// given
		List<Artefact> savedArtefacts = new ArrayList<>();
		Artefact artefact = new Artefact();
		savedArtefacts.add(artefact);
		when(artefactRepository.findAll()).thenReturn(savedArtefacts);
		// When
		List<Artefact> artefacts = service.getArtefacts();
		// Then
		assertEquals(artefact, artefacts.get(0));
	}

	@Test
	public void testGetArtefactsForMarketplace() {
		// Given
		List<Artefact> savedArtefacts = new ArrayList<>();
		Artefact artefact = new Artefact();
		savedArtefacts.add(artefact);
		when(artefactRepository.findByQuantityIsGreaterThanEqual(1L)).thenReturn(savedArtefacts);
		// When
		List<Artefact> artefactsForMarketplace = service.getArtefactsForMarketplace();
		// Then
		assertEquals(artefact, artefactsForMarketplace.get(0));
	}

	@Test
	public void testGetArtefact() {
		// given
		Artefact savedArtefact = new Artefact();
		when(artefactRepository.findById(1L)).thenReturn(Optional.of(savedArtefact));
		// When
		Artefact artefact = service.getArtefact(1L);
		// Then
		assertSame(savedArtefact, artefact);
	}

	@Test
	public void testCreateArtefact_minLevelDoesNotYetExist() {
		// given
		Artefact artefact = mockArtefact();
		Level minLevel = mockLevel(1);
		artefact.setMinLevel(minLevel);
		when(levelService.findByLevel(1)).thenReturn(null);
		when(artefactRepository.save(artefact)).thenReturn(artefact);
		// when
		Artefact newArtefact = service.createArtefact(artefact);
		// then
		assertSame(minLevel, newArtefact.getMinLevel());
	}

	@Test
	public void testCreateArtefact_minLevelAlreadyExists() {
		Artefact artefact = mockArtefact();
		Level minLevel = mockLevel(1);
		artefact.setMinLevel(minLevel);
		Level savedLevel = mockLevel(1);
		when(levelService.findByLevel(1)).thenReturn(savedLevel);
		when(artefactRepository.save(artefact)).thenReturn(artefact);
		// when
		Artefact newArtefact = service.createArtefact(artefact);
		// then
		assertSame(savedLevel, newArtefact.getMinLevel());
	}

	private Level mockLevel(int level) {
		Level minLevel = new Level();
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
		Artefact artefact = mockArtefact();
		artefact.setId(69L);
		artefact.setPrice(7L);
		artefact.setQuantity(1L);
		artefact.setMinLevel(mockLevel(1));
		User user = new User();
		user.setId(1L);
		user.setGold(10L);
		user.setLevel(mockLevel(2));
		when(artefactRepository.save(artefact)).thenReturn(artefact);
		// When
		Artefact boughtArtefact = service.buyArtefact(artefact.getId(), user);
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
		Artefact artefact = mockArtefact();
		artefact.setId(69L);
		artefact.setPrice(19L);
		artefact.setQuantity(1L);
		artefact.setMinLevel(mockLevel(1));
		User user = new User();
		user.setId(1L);
		user.setGold(10L);
		user.setLevel(mockLevel(2));
		when(artefactRepository.save(artefact)).thenReturn(artefact);
		// When
		Artefact boughtArtefact = service.buyArtefact(artefact.getId(), user);
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
		Artefact artefact = mockArtefact();
		artefact.setId(69L);
		artefact.setPrice(7L);
		artefact.setQuantity(0L);
		artefact.setMinLevel(mockLevel(1));
		User user = new User();
		user.setId(1L);
		user.setGold(10L);
		user.setLevel(mockLevel(2));
		when(artefactRepository.save(artefact)).thenReturn(artefact);
		// When
		Artefact boughtArtefact = service.buyArtefact(artefact.getId(), user);
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
		Artefact artefact = mockArtefact();
		artefact.setId(69L);
		artefact.setPrice(7L);
		artefact.setQuantity(1L);
		artefact.setMinLevel(mockLevel(2));
		User user = new User();
		user.setId(1L);
		user.setGold(10L);
		user.setLevel(mockLevel(1));
		when(artefactRepository.save(artefact)).thenReturn(artefact);
		// When
		Artefact boughtArtefact = service.buyArtefact(artefact.getId(), user);
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
		Artefact artefact = mockArtefact();
		artefact.setId(69L);
		artefact.setPrice(7L);
		artefact.setQuantity(1L);
		artefact.setMinLevel(mockLevel(1));
		User user = new User();
		user.setId(1L);
		user.setGold(10L);
		user.setLevel(mockLevel(2));
		user.getArtefacts().add(artefact);
		when(artefactRepository.save(artefact)).thenReturn(artefact);
		// When
		Artefact boughtArtefact = service.buyArtefact(artefact.getId(), user);
		// Then
		assertSame(null, boughtArtefact);
		assertEquals(Long.valueOf(1), artefact.getQuantity());
		assertEquals(Long.valueOf(10), user.getGold());
	}

}
