package com.viadee.sonarquest.services;

import com.viadee.sonarquest.entities.Artefact;
import com.viadee.sonarquest.entities.Level;
import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.repositories.ArtefactRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtefactService {

    public ArtefactService(ArtefactRepository artefactRepository, LevelService levelService, UserService userService) {
        this.artefactRepository = artefactRepository;
        this.levelService = levelService;
        this.userService = userService;
    }

    private enum PurchaseResult {
        NOT_ENOUGH_GOLD, MIN_LEVEL_NOT_HIGH_ENOUGH, ITEM_SOLD_OUT, ITEM_ALREADY_BOUGHT, SUCCESS
    }

    protected static final Log LOGGER = LogFactory.getLog(ArtefactService.class);

    private final ArtefactRepository artefactRepository;

    private final LevelService levelService;

    private final UserService userService;

    public List<Artefact> getArtefacts() {
        return artefactRepository.findAll();

    }

    public List<Artefact> getArtefactsForMarketplace() {
        List<Artefact> artefacts = artefactRepository.findByQuantityIsGreaterThanEqual((long) 1);
        return artefacts.stream().filter(Artefact::isOnMarketplace).collect(Collectors.toList());
    }

    public Artefact getArtefact(final long artefactId) {
        return artefactRepository.findById(artefactId).orElseThrow(ResourceNotFoundException::new);
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
    public Artefact updateArtefact(final Long artefactId, final Artefact artefactDto) {
        LOGGER.info("Updating artefact " + artefactId);
        final Artefact artefact = artefactRepository.findById(artefactId).orElseThrow(ResourceNotFoundException::new);
        artefact.setName(artefactDto.getName());
        artefact.setIcon(artefactDto.getIcon());
        artefact.setPrice(artefactDto.getPrice());
        artefact.setDescription(artefactDto.getDescription());
        artefact.setQuantity(artefactDto.getQuantity());
        artefact.setSkills(artefactDto.getSkills());
        artefact.setMinLevel(artefactDto.getMinLevel());
        artefact.setUsers(artefactDto.getUsers());
        artefact.setOnMarketplace(artefactDto.isOnMarketplace());
        return artefactRepository.save(artefact);
    }

    @Transactional
    public synchronized Artefact buyArtefact(final Long artefactId, final User user) {
        Artefact artefact = artefactRepository.findById(artefactId).orElseThrow(ResourceNotFoundException::new);
        LOGGER.info("UserId " + user.getId() + " tries to buy artefactId " + artefact.getId());

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
            LOGGER.info("UserId " + user.getId() + " successfully bought artefactId " + artefact.getId());
        } else {
            artefact = null;
            LOGGER.info(String.format("UserId %s could not buy artefactId %s, Reason %s", user.getId(),
                    artefactId, reason));
        }
        return artefact;
    }

    @Transactional
    public void payoutArtefact(Long artefactId) {
        final Artefact artefact = artefactRepository.findById(artefactId).orElseThrow(ResourceNotFoundException::new);
        for (User user : artefact.getUsers()) {
            user.getArtefacts().remove(artefact);
            user.addGold(artefact.getPrice());
            userService.save(user);
        }
        artefactRepository.delete(artefact);
        LOGGER.info("Artefact '" + artefact.getName() + "' has been deleted by the game master.The purchase price was paid to the users.");
    }

    @Transactional
    public void removeArtefactFromMarketplace(final Long artefactId) {
        final Artefact artefact = artefactRepository.findById(artefactId).orElseThrow(ResourceNotFoundException::new);
        artefact.setOnMarketplace(false);
        artefactRepository.save(artefact);
        LOGGER.info("Artefact '" + artefact.getName() + "' has been removed form the marketplace by the game master.");
    }

    @Transactional
	public void deleteArtefact(final Long artefactId) {
    	artefactRepository.deleteById(artefactId);
	}

}
