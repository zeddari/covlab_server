package com.axilog.cov.web.rest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

import com.axilog.cov.domain.Outlet;
import com.axilog.cov.domain.User;
import com.axilog.cov.repository.UserRepository;
import com.axilog.cov.security.SecurityUtils;
import com.axilog.cov.service.OtpMailService;
import com.axilog.cov.service.OutletService;
import com.axilog.cov.service.UserService;
import com.axilog.cov.service.dto.PasswordChangeDTO;
import com.axilog.cov.service.dto.UserDTO;
import com.axilog.cov.service.pdf.PdfService;
import com.axilog.cov.util.UserUtil;
import com.axilog.cov.web.rest.errors.EmailAlreadyUsedException;
import com.axilog.cov.web.rest.errors.InvalidPasswordException;
import com.axilog.cov.web.rest.errors.LoginAlreadyUsedException;
import com.axilog.cov.web.rest.vm.KeyAndPasswordVM;
import com.axilog.cov.web.rest.vm.ManagedUserVM;

import io.swagger.annotations.Api;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
@Api(tags = "Profile Management", value = "ProfileManagement", description = "Controller for Profile Management")
public class AccountResource {

	private static class AccountResourceException extends RuntimeException {

		private AccountResourceException(String message) {
			super(message);
		}
	}

	private final Logger log = LoggerFactory.getLogger(AccountResource.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private OtpMailService otpMail;

	@Autowired
	private OutletService outletService;
	
	@Autowired
	private PdfService pdfService;

	private static final String ADMIN = "ROLE_ADMIN";

	
	/**
	 * {@code POST  /register} : register the user.
	 *
	 * @param managedUserVM the managed user View Model.
	 * @throws InvalidPasswordException  {@code 400 (Bad Request)} if the password
	 *                                   is incorrect.
	 * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is
	 *                                   already used.
	 * @throws LoginAlreadyUsedException {@code 400 (Bad Request)} if the login is
	 *                                   already used.
	 */
	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public void registerAccount(@Valid @RequestBody ManagedUserVM managedUserVM) {
		if (!checkPasswordLength(managedUserVM.getPassword())) {
			throw new InvalidPasswordException();
		}
		User user = userService.registerUser(managedUserVM, managedUserVM.getPassword());
		otpMail.sendActivationEmail(user);
	}

	/**
	 * {@code GET  /activate} : activate the registered user.
	 *
	 * @param key the activation key.
	 * @throws RuntimeException {@code 500 (Internal Server Error)} if the user
	 *                          couldn't be activated.
	 */
	@GetMapping("/activate")
	public void activateAccount(@RequestParam(value = "key") String key) {
		Optional<User> user = userService.activateRegistration(key);
		if (!user.isPresent()) {
			throw new AccountResourceException("No user was found for this activation key");
		}
	}

	/**
	 * {@code GET  /authenticate} : check if the user is authenticated, and return
	 * its login.
	 *
	 * @param request the HTTP request.
	 * @return the login if the user is authenticated.
	 */
	@GetMapping("/authenticate")
	public String isAuthenticated(HttpServletRequest request) {
		log.debug("REST request to check if the current user is authenticated");
		return request.getRemoteUser();
	}

	/**
	 * {@code GET  /account} : get the current user.
	 *
	 * @return the current user.
	 * @throws RuntimeException {@code 500 (Internal Server Error)} if the user
	 *                          couldn't be returned.
	 */
	@GetMapping("/account")
	public UserDTO getAccount() {
		return userService.getUserWithAuthorities().map(UserDTO::new)
				.map(userDto -> addOutletToUser(getOutlets(userDto.getAuthorities()), userDto))
				.map(userDto -> addRegionToUser(getRegions(userDto.getAuthorities()), userDto))
				.orElseThrow(() -> new AccountResourceException("User could not be found"));
	}

	/**
	 * {@code POST  /account} : update the current user information.
	 *
	 * @param userDTO the current user information.
	 * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is
	 *                                   already used.
	 * @throws RuntimeException          {@code 500 (Internal Server Error)} if the
	 *                                   user login wasn't found.
	 */
	@PostMapping("/account")
	public void saveAccount(@Valid @RequestBody UserDTO userDTO) {
		String userLogin = SecurityUtils.getCurrentUserLogin()
				.orElseThrow(() -> new AccountResourceException("Current user login not found"));
		Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
		if (existingUser.isPresent() && (!existingUser.get().getLogin().equalsIgnoreCase(userLogin))) {
			throw new EmailAlreadyUsedException();
		}
		Optional<User> user = userRepository.findOneByLogin(userLogin);
		if (!user.isPresent()) {
			throw new AccountResourceException("User could not be found");
		}
		userService.updateUser(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(), userDTO.getLangKey(),
				userDTO.getImageUrl());
	}

	/**
	 * {@code POST  /account/change-password} : changes the current user's password.
	 *
	 * @param passwordChangeDto current and new password.
	 * @throws InvalidPasswordException {@code 400 (Bad Request)} if the new
	 *                                  password is incorrect.
	 */
	@PostMapping(path = "/account/change-password")
	public void changePassword(@RequestBody PasswordChangeDTO passwordChangeDto) {
		if (!checkPasswordLength(passwordChangeDto.getNewPassword())) {
			throw new InvalidPasswordException();
		}
		userService.changePassword(passwordChangeDto.getCurrentPassword(), passwordChangeDto.getNewPassword());
	}

	/**
	 * {@code POST   /account/reset-password/init} : Send an email to reset the
	 * password of the user.
	 *
	 * @param mail the mail of the user.
	 */
	@PostMapping(path = "/account/reset-password/init")
	public void requestPasswordReset(@RequestBody String mail) {
		Optional<User> user = userService.requestPasswordReset(mail);
		if (user.isPresent()) {
			otpMail.sendPasswordResetMail(user.get());
		} else {
			// Pretend the request has been successful to prevent checking which emails
			// really exist
			// but log that an invalid attempt has been made
			log.warn("Password reset requested for non existing mail");
		}
	}

	/**
	 * {@code POST   /account/reset-password/finish} : Finish to reset the password
	 * of the user.
	 *
	 * @param keyAndPassword the generated key and the new password.
	 * @throws InvalidPasswordException {@code 400 (Bad Request)} if the password is
	 *                                  incorrect.
	 * @throws RuntimeException         {@code 500 (Internal Server Error)} if the
	 *                                  password could not be reset.
	 */
	@PostMapping(path = "/account/reset-password/finish")
	public void finishPasswordReset(@RequestBody KeyAndPasswordVM keyAndPassword) {
		if (!checkPasswordLength(keyAndPassword.getNewPassword())) {
			throw new InvalidPasswordException();
		}
		Optional<User> user = userService.completePasswordReset(keyAndPassword.getNewPassword(),
				keyAndPassword.getKey());

		if (!user.isPresent()) {
			throw new AccountResourceException("No user was found for this reset key");
		}
	}

	@GetMapping("/sendcodeverif")
	public void sendVerificationCode() {
		String username = SecurityUtils.getCurrentUserLogin()
				.orElseThrow(() -> new AccountResourceException("Username not found"));

		Optional<User> user = userRepository.findOneByLogin(username);
		if (!user.isPresent()) {
			throw new AccountResourceException("User could not be found");
		}
		String mail = user.get().getEmail();
		String verificationCode = CodeVerification.randomVerificationCode();
		user.get().setCodeVerifiaction(verificationCode);
		userRepository.save(user.get());
		Context context = pdfService.getContext(verificationCode, "verificationCode");
		String htmlContent = pdfService.loadAndFillTemplate(context, "mail/verifCodeEmail");
		otpMail.sendEmail(mail, "Verification Code", htmlContent, true, true);
	}

	/**
	 * @param code
	 * @return
	 */
	@GetMapping("/checkcodeverif/{code}")
	public ResponseEntity<String> sendVerificationCode(@PathVariable(name = "code", required = true) String code) {
		String username = SecurityUtils.getCurrentUserLogin()
				.orElseThrow(() -> new AccountResourceException("Username not found"));

		Optional<User> user = userRepository.findOneByLogin(username);
		if (!user.isPresent()) {
			throw new AccountResourceException("User could not be found");
		}
		if (user.get().getCodeVerifiaction().equals(code))
			return ResponseEntity.ok("{\"result\": \"success\"}");
		return ResponseEntity.ok("{\"result\": \"failure\"}");
	}

	/**
	 * @param password
	 * @return
	 */
	private static boolean checkPasswordLength(String password) {
		return (!StringUtils.isEmpty(password) && password.length() >= ManagedUserVM.PASSWORD_MIN_LENGTH
				&& password.length() <= ManagedUserVM.PASSWORD_MAX_LENGTH);
	}

	private Set<String> getOutlets(Set<String> authorities) {
		Set<String> allOutlets = new HashSet<>();
		authorities.forEach(auth -> {
			if (auth.equals(ADMIN)) {
				allOutlets.addAll(
						outletService.findAll().stream().map(Outlet::getOutletName).collect(Collectors.toSet()));
			} else {
				allOutlets.addAll(outletService.findByOutletRegion(UserUtil.getRegionFromAuth(auth)).stream()
						.map(Outlet::getOutletName).collect(Collectors.toSet()));
			}
		});
		return allOutlets;
	}
	
	private Set<String> getRegions(Set<String> authorities) {
//		List<Outlet> outlets = new ArrayList<Outlet>();
		Set<String> allregions = new HashSet<>();
		authorities.forEach(auth -> {
			if (auth.equals(ADMIN)) {
				List<Outlet> outlets = outletService.findAll();
				outlets.forEach(outlet -> {
					allregions.add(outlet.getOutletParentRegion());
        		});
				
			} else {
				List<Outlet> outlets = outletService.findByOutletRegion(UserUtil.getRegionFromAuth(auth));
				outlets.forEach(outlet -> {
					allregions.add(outlet.getOutletParentRegion());
        		});
			}
		});
		return allregions;
	}

	private UserDTO addOutletToUser(Set<String> outlets, UserDTO userDTO) {
		userDTO.setOutlets(outlets);
		return userDTO;
	}
	private UserDTO addRegionToUser(Set<String> regions, UserDTO userDTO) {
		userDTO.setRegions(regions);
		return userDTO;
	}
}
