package com.viadee.sonarquest.skillTree.sheduledtasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CachingTask {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CachingTask.class);
	
	@Autowired
	private CacheManager cacheManager; 
	
		@Scheduled(cron = "${cron.expression.cache.clearing.rate}")
		public void clearTaskScoringCache() {
			for(String cacheName :cacheManager.getCacheNames()) {
				LOGGER.info("The cache with the name '{}' is avaiable",cacheName);
			}
			cacheManager.getCache("taskScoringCache").clear();
			cacheManager.getCache("allQuestFromWorldCache").clear();
			for(String cacheName :cacheManager.getCacheNames()) {
				LOGGER.info("The cache with the name '{}' is has been cleared",cacheName);
			}
		}

}
