package com.viadee.sonarquest.services;

import org.springframework.stereotype.Service;

import com.viadee.sonarquest.entities.UiDesign;
import com.viadee.sonarquest.entities.UiDesignName;
import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.repositories.UiDesignRepository;

@Service
public class UiDesignService {

    private final UiDesignRepository uiDesignRepository;

    public UiDesignService(final UiDesignRepository uiDesignRepository) {
        this.uiDesignRepository = uiDesignRepository;
    }

    private UiDesign createUiDesign(final User user, final UiDesignName name) {
        final UiDesign ui = new UiDesign();
        ui.setName(name);
        ui.setUser(user);
        return uiDesignRepository.save(ui);
    }

    public UiDesign save(final UiDesign uiDesign) {
        return uiDesignRepository.save(uiDesign);
    }

    public UiDesign updateUiDesign(final User user, final UiDesignName name) {
        UiDesign uiDesign = uiDesignRepository.findByUser(user);
        if (uiDesign == null) {
            uiDesign = createUiDesign(user, name);
        } else {
            uiDesign.setName(name);
            uiDesign = uiDesignRepository.save(uiDesign);
        }
        return uiDesign;
    }
}
