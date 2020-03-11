package com.viadee.sonarquest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.entities.WizardMessage;
import com.viadee.sonarquest.entities.World;

@Service
public class WizardService {

	@Autowired
	private WorldService worldService;

	/**
	 * The WizardState keeps solution message key and message key together. These
	 * point to keys in the translation files, e.g. to en.json or de.json.
	 */
	private enum WizardState {
		NO_MESSAGES("", ""),
		NO_WORLDS_FOUND("WIZARD.MSG_NO_WORLDS_FOUND", "WIZARD.HINT_NO_WORLDS_FOUND"),
		NO_ACTIVE_WORLD("WIZARD.MSG_NO_ACTIVE_WORLD", "WIZARD.HINT_NO_ACTIVE_WORLD"),
		NO_PLAYERS_ASSIGNED("WIZARD.MSG_NO_PLAYERS_ASSIGNED", "WIZARD.HINT_NO_PLAYERS_ASSIGNED"),
		NO_GAMEMASTER_ASSIGNED("WIZARD.MSG_NO_GAMEMASTER_ASSIGNED", "WIZARD.HINT_NO_GAMEMASTER_ASSIGNED"),
		NO_TASKS_FOUND("WIZARD.MSG_NO_TASKS_FOUND", "WIZARD.HINT_NO_TASKS_FOUND"),
		NO_QUESTS_FOUND("WIZARD.MSG_NO_QUESTS_FOUND", "WIZARD.HINT_NO_QUESTS_FOUND"),
		NO_WORLD_SELECTED("WIZARD.MSG_NO_WORLD_SELECTED", "WIZARD.HINT_NO_WORLD_SELECTED");

		private String messageKey;

		private String solutionKey;

		private WizardState(String messageKey, String solutionKey) {
			this.messageKey = messageKey;
			this.solutionKey = solutionKey;
		}

		public WizardMessage toWizardMessage() {
			WizardMessage wm = new WizardMessage();
			wm.setMessage(messageKey);
			wm.setSolution(solutionKey);
			return wm;
		}
	}

	/**
	 * Checks the world to see if the following steps are complete. If they are not,
	 * a message with the most important next step is delivered.
	 * <ol>
	 * <li>NO_WORLDS_FOUND - If the world is null and there are no worlds in the DB
	 * => "Worlds aka Projects must be retrieved from the server first"</li>
	 * <li>NO_ACTIVE_WORLD - If there is no active world => "One of the worlds must
	 * be chosen." (more steps to check if worlds aka projects can be
	 * generated)</li>
	 * <li>NO_GAMEMASTER_ASSIGNED - If there is no gm in the active world => "A
	 * Gamemaster must be chosen for the world."</li>
	 * <li>NO_PLAYERS_ASSIGNED - If there are no players => "Players should be
	 * assigned to the world"</li>
	 * <li>NO_TASKS_FOUND If there are no tasks => "The Gamemaster should retrieve
	 * tasks from the server to assign them to quests"</li>
	 * <li>NO_QUESTS_FOUND If there are no quests => "The Gamemaster should create a
	 * quest with tasks</li>
	 * <li>NO_WORLD_SELECTED If no world is selected => show a hint</li>
	 * </ol>
	 * 
	 * @param world
	 *            if the id of world is set, a current world has been selected in
	 *            the UI. Only retrieve messages for this world then.
	 */
	public WizardMessage getMostImportantMessageFor(World world) {
		WizardState state = determineGameState(world);
		return (state == WizardState.NO_MESSAGES ? null : state.toWizardMessage());
	}

	private WizardState determineGameState(World selectedWorld) {
		// no active world
		if (selectedWorld.getId() == null) {
			List<World> allWorlds = worldService.findAll();
			if (allWorlds.isEmpty()) {
				return WizardState.NO_WORLDS_FOUND;
			}
			if (allWorlds.stream().noneMatch(World::getActive)) {
				return WizardState.NO_ACTIVE_WORLD;
			}
			return WizardState.NO_WORLD_SELECTED;
		}
		// active world
		else {
			if (selectedWorld.getUsers().isEmpty()) {
				return WizardState.NO_PLAYERS_ASSIGNED;
			}
			if (selectedWorld.getUsers().stream().noneMatch(User::isGamemaster)) {
				return WizardState.NO_GAMEMASTER_ASSIGNED;
			}
			if (selectedWorld.getTasks().isEmpty()) {
				return WizardState.NO_TASKS_FOUND;
			}
			if (selectedWorld.getQuests().isEmpty()) {
				return WizardState.NO_QUESTS_FOUND;
			}
		}
		return WizardState.NO_MESSAGES;
	}
}