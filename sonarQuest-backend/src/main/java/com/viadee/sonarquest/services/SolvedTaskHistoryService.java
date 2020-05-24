package com.viadee.sonarquest.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import org.springframework.stereotype.Service;
import com.viadee.sonarquest.dto.SolvedTaskHistoryDTO;
import com.viadee.sonarquest.entities.Task;
import com.viadee.sonarquest.rules.SonarQuestStatus;

@Service
public class SolvedTaskHistoryService {
	
	/**
	 * Maps the sum of the tasks solved by each day
	 * 
	 * @return map <date and amount of solved tasks>
	 */
	public Map<Date, Long> mapSolvedTasks(final List<Task> tasks) {
		TreeMap<Date, Long> history = new TreeMap<Date, Long>();

		tasks.stream().filter(t -> !SonarQuestStatus.OPEN.equals(t.getStatus()) && t.getEnddate() != null).forEach(t -> {
			history.merge(t.getEnddate(), 1l, (v1, v2) -> v1 + v2);
		});

		try {
			LocalDate from = history.firstKey().toLocalDate();
			LocalDate to = history.lastKey().toLocalDate();
			// Fill gaps
			from.datesUntil(to.plusDays(1)).forEach(date -> {
				history.computeIfAbsent(Date.valueOf(date), v -> 0l);
			});
		} catch (Exception e) {
			System.out.println(e);
		}

		return history;
	}

	public List<SolvedTaskHistoryDTO> getSolvedTaskHistory(List<Task> tasks) {
		Map<Date, Long> mappedSolvedTaks = mapSolvedTasks(tasks);
		List<SolvedTaskHistoryDTO> result = new ArrayList<SolvedTaskHistoryDTO>();

		long totalSolvedTasks = 0;
		for (Entry<Date, Long> entry : mappedSolvedTaks.entrySet()) {
			totalSolvedTasks += entry.getValue();
			result.add(new SolvedTaskHistoryDTO(entry.getKey(), entry.getValue(), totalSolvedTasks));
		}
		return result;
	}

}
