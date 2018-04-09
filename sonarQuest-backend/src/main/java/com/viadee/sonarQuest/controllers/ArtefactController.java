package com.viadee.sonarQuest.controllers;

import com.viadee.sonarQuest.dtos.ArtefactDto;
import com.viadee.sonarQuest.entities.Artefact;
import com.viadee.sonarQuest.entities.Level;
import com.viadee.sonarQuest.repositories.ArtefactRepository;
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
    private ArtefactService artefactService;

    @RequestMapping(method = RequestMethod.GET)
    public List<ArtefactDto> getAllArtefacts(){
        return this.artefactService.getArtefacts();
    }


    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ArtefactDto getArtefactById(@PathVariable(value = "id") Long id){
        return this.artefactService.getArtefact(id);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ArtefactDto createArtefact(@RequestBody ArtefactDto artefactDto) {
    	return this.artefactService.createArtefact(artefactDto);
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ArtefactDto updateArtefact(@PathVariable(value = "id") Long id, @RequestBody ArtefactDto artefactDto) {
    	return this.artefactService.updateArtefact(id, artefactDto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteArtefact(@PathVariable(value = "id") Long id) {
        Artefact artefact = this.artefactRepository.findOne(id);
        if (artefact != null) {
            this.artefactRepository.delete(id);
        }
    }
    
    

  

}
