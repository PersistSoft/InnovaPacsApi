package innova.pacs.api.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import innova.pacs.api.dto.UserDto;
import innova.pacs.api.model.User;
import innova.pacs.api.model.repository.IUserRepository;

@Service
public class UserService {
	@Autowired
	private IUserRepository userRepository;

	/**
	 * Find user with pager
	 * @param pageable
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page<User> findAll(Pageable pageable) {
		return this.userRepository.findAll(pageable);
	}
	/**
	 * Find by user id
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = true)
	public Optional<User> findById(Long id) {
		return this.userRepository.findById(id);
	}

	/**
	 * Delete user
	 * @param id
	 */
	@Transactional
	public void deleteById(Long id) {
		this.userRepository.deleteById(id);
	}

	/**
	 * Update user
	 * @param id
	 * @param user
	 * @return
	 */
	@Transactional
	public User update(Long id, User user) {
		Optional<User> optUser = this.findById(id);
		User currentUser = null;

		if (optUser.isPresent()) {
			currentUser = optUser.get();

			currentUser.setActive(user.getActive());
			currentUser.setUsername(user.getUsername());
			currentUser.setEmail(user.getEmail());

			if (user.getPassword() != null) {
				currentUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
			}

			currentUser = this.userRepository.save(currentUser);

		}

		return currentUser;
	}

	/**
	 * Save new user
	 * @param user
	 * @return
	 */
	@Transactional
	public User create(User user) {
		String pwd = new BCryptPasswordEncoder().encode(user.getPassword());
		user.setPassword(pwd);
		user = this.userRepository.save(user);

		return user;
	}

	/**
	 * Find By User Name
	 * @param username
	 * @return
	 */
	@Transactional(readOnly = true)
	public User findByUsername(String username) {
		return this.userRepository.findByUsername(username);
	}

	/**
	 * Get all users
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<UserDto> getAll() {
		return this.userRepository.getAll();
	}

	/**
	 * Get by Id
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = true)
	public UserDto getById(Long id) {
		return this.userRepository.getById(id);
	}

	/**
	 * User Report Query
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<UserDto> userReportQuery() {
		return this.userRepository.userReportQuery();
	}
}
