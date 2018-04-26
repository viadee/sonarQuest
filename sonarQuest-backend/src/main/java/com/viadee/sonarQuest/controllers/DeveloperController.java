package com.viadee.sonarQuest.controllers;

import com.google.common.io.Files;
import com.viadee.sonarQuest.SonarQuestApplication;
import com.viadee.sonarQuest.dtos.DeveloperDto;
import com.viadee.sonarQuest.entities.Developer;
import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.repositories.DeveloperRepository;
import com.viadee.sonarQuest.repositories.WorldRepository;
import com.viadee.sonarQuest.services.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.security.CodeSource;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/developer")
public class DeveloperController {

    private DeveloperRepository developerRepository;
    
    private WorldRepository worldRepository;
    
    private DeveloperService developerService;

    @Autowired
    public DeveloperController(DeveloperRepository developerRepository, DeveloperService developerService, WorldRepository worldRepository) {
        this.developerRepository = developerRepository;
        this.developerService = developerService;
        this.worldRepository = worldRepository;
    }

    /**
     * Get All Developers
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<DeveloperDto> getAllDevelopers() {
        return this.developerService.findActiveDevelopers().stream().map(developer -> this.developerService.toDeveloperDto(developer)).collect(Collectors.toList());
    }

    /**
     * Get a Developer by Id
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public DeveloperDto getDeveloperByID(@PathVariable(value = "id") Long id) {
        Developer developer = this.developerRepository.findById(id);
        DeveloperDto developerDto = null;
        if (developer != null) {
            developerDto = this.developerService.toDeveloperDto(developer);
        }
        return developerDto;
    }


    /**
     * Creates a Developer from a DTO
     *
     * @param developerDto
     * @return
     */
    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public DeveloperDto createDeveloper(@RequestBody DeveloperDto developerDto) {
    	return this.developerService.toDeveloperDto(this.developerService.createDeveloper(developerDto));
    }
    
    
    
    /**
     * 
     * @param world_id
     * @param developer_id
     * @return DeveloperDto
     */
    @CrossOrigin
    @RequestMapping(value = "/{id}/updateWorld/{world_id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.CREATED)
    public DeveloperDto updateWorld(@PathVariable(value = "id") Long developer_id, @PathVariable(value = "world_id") Long  world_id) {
    	World 		w = this.worldRepository.findById(world_id);
    	Developer 	d = this.developerRepository.findById(developer_id);
    	
    	d = this.developerService.setWorld(d,w);

        return this.developerService.toDeveloperDto(d);
    }


    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public DeveloperDto updateDeveloper(@PathVariable(value = "id") Long id, @RequestBody DeveloperDto developerDto) {
        return this.developerService.updateDeveloper(developerDto);
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteDeveloper(@PathVariable(value = "id") Long id) {
    	Developer developer = developerRepository.findById(id);	
    	developerService.deleteDeveloper(developer);    	      
    }

    @CrossOrigin
    @RequestMapping(path = "/{id}/avatar", method = RequestMethod.GET)
    public @ResponseBody byte[] getAvatar(@PathVariable(value = "id") Long id, final HttpServletResponse response) throws IOException {
        response.addHeader("Content-Disposition", "attachment; filename=avatar.png");

    	Developer d = developerRepository.findById(id);
    	String path;
        String propertiesFilePath = "client.properties";
        File avatarPath = new File(propertiesFilePath);
     
        if (!avatarPath.exists()){
          try{
            CodeSource codeSource = SonarQuestApplication.class.getProtectionDomain().getCodeSource();
            File jarFile = new File(codeSource.getLocation().toURI().getPath());
            String jarDir = jarFile.getParentFile().getPath();
            avatarPath = new File(jarDir + System.getProperty("file.separator") + propertiesFilePath);
            avatarPath = new File(avatarPath.getParentFile().getParentFile().getParentFile() + "/avatar");
          }
          catch (Exception ignored){ }
        }
        
        File folder = new File(avatarPath.getAbsolutePath());
        path = folder + "\\" + d.getPicture();
        
        if (new File(path).isFile()) {
            return Files.toByteArray(new File(path));
        } else {
        	return null;
        }
        
    }

}
