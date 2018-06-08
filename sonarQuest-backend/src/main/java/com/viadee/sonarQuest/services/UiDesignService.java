package com.viadee.sonarQuest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viadee.sonarQuest.entities.UiDesign;
import com.viadee.sonarQuest.entities.User;
import com.viadee.sonarQuest.repositories.UiDesignRepository;

@Service
public class UiDesignService {

    @Autowired
    private UiDesignRepository uiDesignRepository;

    public UiDesign createUiDesign(final User user, final String name) {
        final UiDesign ui = new UiDesign();
        ui.setName(name);
        ui.setUser(user);
        return uiDesignRepository.save(ui);
    }

    public UiDesign save(final UiDesign uiDesign) {
        return uiDesignRepository.save(uiDesign);
    }

    public UiDesign updateUiDesign(final User user, final String name) {
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
