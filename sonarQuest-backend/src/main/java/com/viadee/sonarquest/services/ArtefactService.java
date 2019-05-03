package com.viadee.sonarquest.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viadee.sonarquest.entities.Artefact;
import com.viadee.sonarquest.entities.Level;
import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.repositories.ArtefactRepository;

@Service
public class ArtefactService {

	private enum PurchaseResult {
		NOT_ENOUGH_GOLD, MIN_LEVEL_NOT_HIGH_ENOUGH, ITEM_SOLD_OUT, ITEM_ALREADY_BOUGHT, SUCCESS
	}

	protected static final Log LOGGER = LogFactory.getLog(ArtefactService.class);

	@Autowired
	private ArtefactRepository artefactRepository;

	@Autowired
	private LevelService levelService;

	@Autowired
	private UserService userService;

	public List<Artefact> getArtefacts() {
		return  artefactRepository.findAll();
		
	}

	public List<Artefact> getArtefactsForMarketplace() {
		List<Artefact> artefacts =  artefactRepository.findByQuantityIsGreaterThanEqual((long) 1);
		return artefacts.stream().filter(artefact -> artefact.isOnMarketplace()).collect(Collectors.toList());
	}

	public Artefact getArtefact(final long id) {
		return artefactRepository.findOne(id);
	}

	@Transactional
	public Artefact createArtefact(final Artefact artefact) {
		LOGGER.info("Creating new artefact " + artefact.getName());
		Level minLevel = artefact.getMinLevel();
		Level existingLevel = levelService.findByLevel(minLevel.getLevelNumber());
		if (existingLevel != null) {
			artefact.setMinLevel(existingLevel);
		} else {
			LOGGER.info("Artefact Level " + minLevel.getLevelNumber() + " does not exist yet - creating it...");
			levelService.createLevel(minLevel);
			artefact.setMinLevel(minLevel);
		}
		artefact.setOnMarketplace(true);
		return artefactRepository.save(artefact);
	}

	@Transactional
	public Artefact updateArtefact(final Long id, final Artefact artefactDto) {
		LOGGER.info("Updating artefact " + id);
		final Artefact artefact = artefactRepository.findOne(id);
		artefact.setName(artefactDto.getName());
		artefact.setIcon(artefactDto.getIcon());
		artefact.setPrice(artefactDto.getPrice());
		artefact.setDescription(artefactDto.getDescription());
		artefact.setQuantity(artefactDto.getQuantity());
		artefact.setSkills(artefactDto.getSkills());
		int minLevel = artefactDto.getMinLevel().getLevelNumber();
		artefact.setMinLevel(artefactDto.getMinLevel());
		return artefactRepository.save(artefact);
	}

	@Transactional
	public synchronized Artefact buyArtefact(Artefact artefactToBuy, final User user) {
		Artefact artefact = artefactToBuy;
		LOGGER.info("UserId " + user.getId() + " tries to buy artefactId " + artefactToBuy.getId());

		final Level minLevel = artefact.getMinLevel();
		final Level devLevel = user.getLevel();

		PurchaseResult reason = PurchaseResult.SUCCESS;

		final long gold = user.getGold() - artefact.getPrice();
		// If developer has TOO LITTLE GOLD left after the purchase, Then the purchase
		// is canceled
		if (gold < 0) {
			reason = PurchaseResult.NOT_ENOUGH_GOLD;
		}
		// If the artefact is SOLD OUT, then the purchase is canceled
		else if (artefact.getQuantity() < 1) {
			reason = PurchaseResult.ITEM_SOLD_OUT;
		}
		// When the LEVEL of the developer is too low, then the purchase is canceled
		else if (minLevel.getLevelNumber() > devLevel.getLevelNumber()) {
			reason = PurchaseResult.MIN_LEVEL_NOT_HIGH_ENOUGH;
		} else {
			// If the developer has ALREADY BOUGHT the Artefact, Then the purchase is
			// canceled
			for (final Artefact a : user.getArtefacts()) {
				if (a.equals(artefact)) {
					reason = PurchaseResult.ITEM_ALREADY_BOUGHT;
					break;
				}
			}
		}

		if (reason == PurchaseResult.SUCCESS) {
			user.getArtefacts().add(artefact);
			user.setGold(gold);
			userService.save(user);

			artefact.setQuantity(artefact.getQuantity() - 1);
			artefact = artefactRepository.save(artefact);
			LOGGER.info("UserId " + user.getId() + " successfully bought artefactId " + artefactToBuy.getId());
		} else {
			artefact = null;
			LOGGER.info(String.format("UserId %s could not buy artefactId %s, Reason %s", user.getId(),
					artefactToBuy.getId(), reason));
		}
		return artefact;
	}

	public void payoutArtefact(Artefact artefact) {
		for (User user : artefact.getUsers()) {
			user.getArtefacts().remove(artefact);
			user.addGold(artefact.getPrice());
			userService.save(user);
		}
		artefactRepository.delete(artefact);
		LOGGER.info("Artefact '"+artefact.getName()+"' has been deleted by the Gamemaster.The purchase price was paid to the useres.");
	}
	
	public void removeArtefactFromMarketplace(Artefact artefact) {
		artefact.setOnMarketplace(false);
		artefactRepository.save(artefact);
		LOGGER.info("Artefact '"+artefact.getName()+"' has been removed form the marketplace by the Gamemaster.");
	}

}
