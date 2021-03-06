package innova.pacs.api.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import innova.pacs.api.dto.InstitutionConfigurtionDto;
import innova.pacs.api.dto.InstitutionDto;
import innova.pacs.api.model.Institution;
import innova.pacs.api.model.InstitutionUser;
import innova.pacs.api.model.repository.IInstitutionUserRepository;
import innova.pacs.api.model.repository.IInstitutionRepository;
import innova.pacs.api.model.repository.ISeriesRepository;

@Service
public class InstitutionService {
	@Autowired
	private IInstitutionRepository institutionRepository;
	@Autowired
	private IInstitutionUserRepository institutionUserRepository;
	@Autowired
	private ISeriesRepository seriesRepository;
	
	
	/**
	 * Find all institutions
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Institution> findAll(){
		return (List<Institution>) this.institutionRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Institution findById(Integer id){
		Optional<Institution> institution = this.institutionRepository.findById(id);
		
		if(institution.isPresent()) {
			return institution.get();
		}
		
		return null;
	}
	
	/**
	 * Configure institution user
	 * @param institutionUser
	 * @return
	 */
	@Transactional
	public InstitutionUser configure(InstitutionUser institutionUser){
		return this.institutionUserRepository.save(institutionUser);
	}
	
	/**
	 * Get all institutions from series
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<InstitutionDto> getAllInstitutions(){
		return this.seriesRepository.getAllInstitutions();
	}
	
	/**
	 * Configure Institutions
	 */
	public void configureInstitutions() {
		List<InstitutionDto> lstIntitutions = this.getAllInstitutions();
		
		for (InstitutionDto institutionDto : lstIntitutions) {
			Institution institution = this.findByName(institutionDto.getName());
			
			if(institution == null) {
				Institution newInstitution = new Institution();
				newInstitution.setName(institutionDto.getName());
				this.save(newInstitution);
			}
		}
	}
	
	/**
	 * Find by name
	 * @param name
	 * @return
	 */
	@Transactional(readOnly = true)
	public Institution findByName(String name) {
		return this.institutionRepository.findByName(name);
	}
	
	/**
	 * Save institution
	 * @param institution
	 * @return
	 */
	@Transactional
	public Institution save(Institution institution) {
		return this.institutionRepository.save(institution);
	}
	/**
	 * Get institutions by user id
	 * @param userId
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<String> findInstitutionByUserId(Long userId){
		return this.institutionUserRepository.findInstitutionByUserId(userId);
	}
	
	/**
	 * Get all institutions configured
	 * @param userId
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<InstitutionConfigurtionDto> getConfiguration(Long userId) {
		return this.institutionUserRepository.getConfiguration(userId);
	}
	
	/**
	 * Save configuration
	 * @param userId
	 * @return
	 */
	@Transactional
	public List<InstitutionConfigurtionDto> saveConfiguration(Long userId, List<Long> ids) {
		this.institutionUserRepository.deleteConfigurationByUserId(userId);
		
		for (Long id : ids) {
			if (id != null) {
				InstitutionUser iu = new InstitutionUser();

				iu.setUserId(userId);
				iu.setInstitutionId(id);

				this.institutionUserRepository.save(iu);
			}
		}
		
		return this.getConfiguration(userId);
	}
}
