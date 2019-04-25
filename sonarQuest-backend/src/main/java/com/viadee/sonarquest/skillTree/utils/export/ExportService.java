package com.viadee.sonarquest.skillTree.utils.export;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viadee.sonarquest.skillTree.entities.SonarRule;
import com.viadee.sonarquest.skillTree.entities.UserSkill;
import com.viadee.sonarquest.skillTree.repositories.SonarRuleRepository;
import com.viadee.sonarquest.skillTree.repositories.UserSkillRepository;
import com.viadee.sonarquest.skillTree.services.SonarRuleService;
import com.viadee.sonarquest.skillTree.services.UserSkillService;

@Service
public class ExportService {

	@Autowired
	private UserSkillRepository userSkillRepository;

	@Autowired
	private SonarRuleService sonarRuleService;

	@Autowired
	private SonarRuleRepository sonarRuleRepository;

	private XSSFWorkbook workbookUserSkill = new XSSFWorkbook();
	private XSSFWorkbook workbookSonarRule = new XSSFWorkbook();

	private XSSFSheet sheet;

	public void exportUserSkills() {
		XSSFSheet sheet = workbookUserSkill.createSheet("UserSkills");// creating a blank sheet
		List<UserSkill> userSkills = userSkillRepository.findAll();
		int rownum = 0;
		Row header = sheet.createRow(rownum++);
		Cell cell = header.createCell(0);
		cell.setCellValue("ID");

		cell = header.createCell(1);
		cell.setCellValue("Name");

		cell = header.createCell(2);
		cell.setCellValue("Group");
		for (UserSkill userSkill : userSkills) {
			Row row = sheet.createRow(rownum++);
			createListUserSkill(userSkill, row);

		}

		FileOutputStream out = null;
		try {
			File file = new File("src/main/resources/export/UserSkills.xlsx");
			boolean result = Files.deleteIfExists(file.toPath());
			out = new FileOutputStream(file);
			workbookUserSkill.write(out);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void exportSonarRules() {
		XSSFSheet sheet = workbookSonarRule.createSheet("SonarRules");// creating a blank sheet
		List<SonarRule> sonarRules = sonarRuleService.update("java");
		sonarRules = sonarRuleService.findAll();
		int rownum = 0;
		Row header = sheet.createRow(rownum++);
		Cell cell = header.createCell(0);
		cell.setCellValue("ID");

		cell = header.createCell(1);
		cell.setCellValue("Name");

		cell = header.createCell(2);
		cell.setCellValue("Key");
		for (SonarRule sonarRule : sonarRules) {
			Row row = sheet.createRow(rownum++);

			createListSonarRule(sonarRule, row);

		}

		FileOutputStream out = null;
		try {
			File file = new File("src/main/resources/export/SonarRules.xlsx");
			boolean result = Files.deleteIfExists(file.toPath());
			out = new FileOutputStream(file);
			workbookSonarRule.write(out);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void createSonarRuleSQLScript() {
		try {
			 PrintStream out = new PrintStream(new FileOutputStream(
			          "src/main/resources/export/SonarRulesSQL.txt"));
			String stmt = "INSERT INTO Sonar_Rule(rule_name,rule_key,user_skill_id, added_at) VALUES ";
			out.print(stmt);
			String values = "";
			sonarRuleService.update("java");
			List<SonarRule> sonarRules = sonarRuleService.findAll();
			for (SonarRule rule : sonarRules) {
				if (values.length() == 0) {
					values =  "\n('" + rule.getName() + "','" + rule.getKey() + "',1,'"
							+ new Timestamp(System.currentTimeMillis()) + "')";

				} else {
					values = ",\n('" + rule.getName() + "','" + rule.getKey() + "',1,'"
							+ new Timestamp(System.currentTimeMillis()) + "')";
				}
				out.print(values);
			}
			out.print(';');
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void createListUserSkill(UserSkill userSkill, Row row) // creating cells for each row
	{
		Cell cell = row.createCell(0);
		cell.setCellValue(userSkill.getId());

		cell = row.createCell(1);
		cell.setCellValue(userSkill.getName());

		cell = row.createCell(2);
		cell.setCellValue(userSkill.getUserSkillGroup().getName());

	}

	private void createListSonarRule(SonarRule sonarRule, Row row) // creating cells for each row
	{
		Cell cell = row.createCell(0);
		cell.setCellValue(sonarRule.getId());

		cell = row.createCell(1);
		cell.setCellValue(sonarRule.getName());

		cell = row.createCell(2);
		cell.setCellValue(sonarRule.getKey());

	}
}
