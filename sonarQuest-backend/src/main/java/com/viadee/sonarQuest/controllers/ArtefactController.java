package com.viadee.sonarQuest.controllers;

import com.viadee.sonarQuest.dtos.ArtefactDto;
import com.viadee.sonarQuest.entities.Artefact;
import com.viadee.sonarQuest.entities.Developer;
import com.viadee.sonarQuest.repositories.ArtefactRepository;
import com.viadee.sonarQuest.repositories.DeveloperRepository;
import com.viadee.sonarQuest.services.ArtefactService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artefact")
public class ArtefactController  {

    @Autowired
    private ArtefactRepository artefactRepository;
    
    @Autowired
    private DeveloperRepository developerRepository;
    
    @Autowired
    private ArtefactService artefactService;

    @RequestMapping(method = RequestMethod.GET)
    public List<ArtefactDto> getAllArtefacts(){
        return artefactService.getArtefacts();
    }
    
    @RequestMapping(value = "/forMarketplace/", method = RequestMethod.GET)
    public List<ArtefactDto> getArtefactsforMarkteplace(){
        return artefactService.getArtefactsforMarkteplace();
    }


    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ArtefactDto getArtefactById(@PathVariable(value = "id") Long id){
        return artefactService.getArtefact(id);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ArtefactDto createArtefact(@RequestBody ArtefactDto artefactDto) {
    	return artefactService.createArtefact(artefactDto);
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ArtefactDto updateArtefact(@PathVariable(value = "id") Long id, @RequestBody ArtefactDto artefactDto) {
    	return artefactService.updateArtefact(id, artefactDto);
    }

    @CrossOrigin
    @RequestMapping(value = "/{artefact_id}/boughtBy/{developer_id}", method = RequestMethod.PUT)
    public boolean buyArtefact(@PathVariable(value = "artefact_id") Long artefact_id, @PathVariable(value = "developer_id") Long developer_id) {
    	Artefact  a = artefactRepository.findOne(artefact_id);
    	Developer d = developerRepository.findOne(developer_id);

        return artefactService.buyArtefact(a, d) != null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteArtefact(@PathVariable(value = "id") Long id) {
        if (artefactRepository.findOne(id) != null) {
            artefactRepository.delete(id);
        }
    }
    
    

  

}
