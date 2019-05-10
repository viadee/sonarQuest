package com.viadee.sonarquest.skillTree.sheduledTasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.viadee.sonarquest.skillTree.services.UserSkillService;

@Component
public class CachingTask {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserSkillService.class);

	
	@Value("${cachename.task.scoring.cache}")
	private String taskScoringCache; 
	
	@Autowired
	private CacheManager cacheManager; 
	
	//TODO as sonarquest.properties
		@Scheduled(cron = "${cron.expression.cache.clearing.rate}")
		public void clearTaskScoringCache() {
			cacheManager.getCache(taskScoringCache).clear();
			LOGGER.info("The cache with the name '{}' has been cleared",taskScoringCache);
		}

}
