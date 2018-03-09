package com.viadee.sonarQuest.controllers;

import com.viadee.sonarQuest.dtos.ArtefactDto;
import com.viadee.sonarQuest.entities.Artefact;
import com.viadee.sonarQuest.entities.Level;
import com.viadee.sonarQuest.repositories.ArtefactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/artefact")
public class ArtefactController  {

    private ArtefactRepository artefactRepository;

    @Autowired
    public ArtefactController(ArtefactRepository artefactRepository) {
        this.artefactRepository = artefactRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<ArtefactDto> getAllArtefacts(){
        return this.artefactRepository.findAll().stream().map(artefact -> toArtefactDto(artefact)).collect(Collectors.toList());
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ArtefactDto getArtefactById(@PathVariable(value = "id") Long id){
        Artefact artefact = this.artefactRepository.findOne(id);
        ArtefactDto artefactDto = null;
        if(artefact != null){
            artefactDto = this.toArtefactDto(artefact);
        }
        return artefactDto;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ArtefactDto createArtefact(@RequestBody ArtefactDto artefactDto) {
        Artefact artefact = new Artefact();
        artefact = this.artefactRepository.save(new Artefact(artefactDto.getName(),artefactDto.getIcon(),artefact.getPrice(),artefactDto.getMinLevel(),artefactDto.getSkills()));
        return this.toArtefactDto(artefact);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ArtefactDto updateArtefact(@PathVariable(value = "id") Long id, @RequestBody ArtefactDto artefactDto) {
        Artefact artefact = this.artefactRepository.findOne(id);
        if (artefact != null) {
            artefact.setName(artefactDto.getName());
            artefact.setIcon(artefactDto.getIcon());
            artefact.setPrice(artefactDto.getPrice());
            artefact.setMinLevel(artefactDto.getMinLevel());
            artefact.setSkills(artefactDto.getSkills());
            artefact = this.artefactRepository.save(artefact);
        }
        return this.toArtefactDto(artefact);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteArtefact(@PathVariable(value = "id") Long id) {
        Artefact artefact = this.artefactRepository.findOne(id);
        if (artefact != null) {
            this.artefactRepository.delete(id);
        }
    }

    private ArtefactDto toArtefactDto(Artefact artefact) {
        ArtefactDto artefactDto=null;
        if(artefact!=null){
            artefactDto= new ArtefactDto(artefact.getId(),artefact.getName(),artefact.getIcon(),artefact.getPrice(),artefact.getMinLevel(),artefact.getSkills());
        }
        return artefactDto;
    }

}
