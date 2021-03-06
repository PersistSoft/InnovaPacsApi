package innova.pacs.api;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import innova.pacs.api.model.service.InstitutionService;
import innova.pacs.api.model.service.PatientService;
import innova.pacs.api.model.service.StudyService;

@Component
public class ScheduledTasks {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    
    @Autowired
    private StudyService studyService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private InstitutionService institutionService; 
    
	@Scheduled(cron = "0 * * * * ?")
	public void scheduleTaskRefactorStudyDate() {
	    logger.info("Refactor Study Date :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
	    this.studyService.refactorStudyDate();
	    logger.info("Refactor Study Date :: End task - {}", dateTimeFormatter.format(LocalDateTime.now()));
	}
	
	@Scheduled(cron = "0 * * * * ?")
	public void scheduleTaskRefactorPatientBirthDate() {
	    logger.info("Refactor Patient Birth Date :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
	    this.patientService.refactorPatientBirthDate();
	    logger.info("Refactor Patient Birth Date :: End task - {}", dateTimeFormatter.format(LocalDateTime.now()));
	}
	
	@Scheduled(cron = "0 * * * * ?")
	public void scheduleTaskConfigureInstitutions() {
	    logger.info("Configure institutions :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
	    this.institutionService.configureInstitutions();
	    logger.info("Configure institutions :: End task - {}", dateTimeFormatter.format(LocalDateTime.now()));
	}
	
	@Scheduled(cron = "0 * * * * ?")
	public void scheduleTaskConfigureInstitutionsStudy() {
	    logger.info("Configure institutionsStudies :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
	    this.studyService.configureIntitutionStudies();
	    logger.info("Configure institutionsStudies :: End task - {}", dateTimeFormatter.format(LocalDateTime.now()));
	}
}
