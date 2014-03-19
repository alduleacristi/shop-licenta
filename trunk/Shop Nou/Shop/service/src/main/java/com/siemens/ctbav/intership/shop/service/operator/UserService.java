package com.siemens.ctbav.intership.shop.service.operator;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import com.siemens.ctbav.intership.shop.dto.operator.OperatorDTO;
import com.siemens.ctbav.intership.shop.dto.operator.UserDTO;
import com.siemens.ctbav.intership.shop.exception.UserException;
import com.siemens.ctbav.intership.shop.exception.operator.DuplicateFieldException;
import com.siemens.ctbav.intership.shop.exception.operator.UserNotFoundException;
import com.siemens.ctbav.intership.shop.model.User;
import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;

@Stateless
@TransactionManagement(value = TransactionManagementType.BEAN)
public class UserService {

	@PersistenceContext
	private EntityManager em;

	@Resource
	private UserTransaction userTransaction;

	@EJB
	private InternationalizationService internationalizationService;

	@PostConstruct
	private void init() {
		internationalizationInit();
	}

	private void internationalizationInit() {
		boolean isEnglishSelected;
		Boolean b = (Boolean) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("isEnglishSelected");
		if (b == null)
			isEnglishSelected = true;
		else
			isEnglishSelected = b;
		if (isEnglishSelected) {
			String language = new String("en");
			String country = new String("US");
			internationalizationService.setCurrentLocale(language, country,
					"internationalization/forgotPassword/ForgotPassword");
		} else {
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService.setCurrentLocale(language, country,
					"internationalization/forgotPassword/ForgotPassword");
		}
	}

	@SuppressWarnings("unchecked")
	public List<User> getClientUsers() {
		return em.createNamedQuery(User.GET_CLIENT_USERS).getResultList();
	}

	public void deleteUser(Long id) throws UserException {
		User u = em.find(User.class, id);
		if (u == null)
			throw new UserException(internationalizationService.getMessage("userNotFound") + id);
		em.remove(u);
	}

	public void deleteUserClient(String username) {
		try {
			userTransaction.begin();
			try {
				Long id = getUserId(username);
				deleteUser(id);

			} catch (UserException e) {
				userTransaction.rollback();
			} catch (UserNotFoundException e) {
				userTransaction.rollback();
			}

			userTransaction.commit();
		} catch (Exception exc) {

		}
	}

	public User getUserByUsername(String username) throws UserException,
			UserNotFoundException {
		Long id = this.getUserId(username);
		User u = em.find(User.class, id);
		if (u == null)
			throw new UserNotFoundException();
		return u;
	}

	@SuppressWarnings("unchecked")
	public Long getUserId(String username) throws UserException,
			UserNotFoundException {
		List<Long> idList = em.createNamedQuery(User.GET_USER_ID)
				.setParameter("user", username).getResultList();
		if (idList.size() > 1)
			throw new UserException(
					internationalizationService.getMessage("moreUsername")
							+ username);

		if (idList.size() == 0)
			throw new UserNotFoundException(
					internationalizationService.getMessage("usernameNotFound")
							+ username);
		return idList.get(0);
	}

	public User getUserByEmail(String email) throws UserException,
			UserNotFoundException {
		@SuppressWarnings("unchecked")
		List<User> usersList = em.createNamedQuery(User.GET_USER_BY_EMAIL)
				.setParameter("email", email).getResultList();
		if (usersList.size() == 0)
			throw new UserNotFoundException(
					internationalizationService.getMessage("emailNotFound"));
		if (usersList.size() > 1)
			throw new UserException(
					internationalizationService.getMessage("moreEmail"));
		return usersList.get(0);
	}

	// @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public boolean usernameAlreadyExists(String user, Long id) throws Exception {
		// userTransaction.begin();
		boolean exists = false;
		System.out.println("verific daca mai exista username");
		User u = null;
		try {
			u = getUserByUsername(user);
			System.out.println(u);
			if (u.getId() == id.longValue()) { // daca userul gasit este chiar
												// el
				System.out.println("userul gasit este chiar el");
				// return false;
			} else {// altfel inseamna ca exista un alt user cu acelasi username
				System.out.println("exista un alt  user cu acelasi username");
				// return true;
				exists = true;
			}
		} catch (UserException e) {// inseamna ca a gasit mai multi
			return true;
		} catch (UserNotFoundException e) {// nu a agsit nici unul
			System.out.println("nu am gasit");
			exists = false;
		}
		if (userTransaction.getStatus() != 6)
			userTransaction.commit();
		return exists;
	}

	// @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public boolean emailAlreadyExists(String email, Long id) throws Exception {
		System.out.println("verific daca mai exista email " + email);

		User u = null;
		try {
			u = getUserByEmail(email);
			System.out.println(u);

			System.out.println(u.getId() + "   " + id.longValue());
			if (u.getId() == id.longValue()) { // daca userul gasit este chiar
												// el
				System.out.println("userul gasit este chiar el");
				return false;
			} else {// altfel inseamna ca exista un alt user cu acelasi username
				System.out.println("exista un alt  user cu acelasi email");
				return true;
			}
		} catch (UserException e) {
			System.out.println(u.getId() + "    " + id.longValue());
			return false;

		} catch (UserNotFoundException e) {
			System.out.println("nu am gasit");
			if (userTransaction.getStatus() != 6)
				userTransaction.commit();
			return false;

		}
	}

	public void updateOperator(OperatorDTO oper, String oldUsername)
			throws Exception {
		try {
			userTransaction.begin();
			System.out.println("in Tranzactie");
			try {
				Long id = getUserId(oldUsername);
				if (!usernameAlreadyExists(oper.getUsername(), id)
						&& !emailAlreadyExists(oper.getEmail(), id)) {
					System.out.println("nu exista pot sa modific");
					User user = new User(id, oper.getUsername(),
							oper.getPassword(), "operator", oper.getEmail());
					// System.out.println(user);
					em.merge(user);
					System.out.println("am modificat");
				} else {
					System.out
							.println("Username or email already exists in the database");
					throw new DuplicateFieldException(
							internationalizationService.getMessage("alreadyExists"));
				}
			} catch (UserException e) {
				userTransaction.rollback();
			} catch (UserNotFoundException e) {
				userTransaction.rollback();
			}

			if (userTransaction.getStatus() != 6)
				userTransaction.commit();
		} catch (Exception exc) {
			if (exc instanceof DuplicateFieldException)
				throw new DuplicateFieldException(exc.getMessage());
			// throw new EJBException();
		}
	}

	public void setTemporaryPassword(UserDTO user)
			throws NotSupportedException, SystemException,
			IllegalStateException, SecurityException, RollbackException,
			HeuristicMixedException, HeuristicRollbackException {
		userTransaction.begin();
		try {
			Long id = getUserId(user.getUsername());
			User u = new User(id, user.getUsername(), user.getPassword(),
					user.getRolename(), user.getEmail());
			u.setPasswordStatus(user.getPasswordStatus().ordinal());
			System.out.println(user.getPasswordStatus().ordinal());
			System.out.println("VReau sa  setez parola pentru  " + u);
			em.merge(u);
		} catch (UserException exc) {
			userTransaction.rollback();
		} catch (UserNotFoundException exc) {
			userTransaction.rollback();
		}
		userTransaction.commit();
	}

	public boolean passwordAlreadyExists(String password) {
		@SuppressWarnings("unchecked")
		List<User> users = em.createNamedQuery(User.GET_USER_BY_PASSWORD)
				.setParameter("password", password).getResultList();
		if (users.size() > 0)
			return true;
		return false;
	}

	@SuppressWarnings("unchecked")
	public User getUserByPassword(String password) throws UserNotFoundException {
		System.out.println(password);
		List<User> list = em.createNamedQuery(User.GET_USER_BY_PASSWORD)
				.setParameter("password", password).getResultList();
		if (list == null || list.size() == 0)
			throw new UserNotFoundException(
					internationalizationService.getMessage("passwordNotFound"));
		return list.get(0);

	}

	public void changePassword(String generatedPassword, String newPassword)
			throws UserNotFoundException {
		try {
			userTransaction.begin();

			System.out.println("vreau sa modific");
			System.out.println(generatedPassword + " " + newPassword);
			User user = getUserByPassword(generatedPassword);
			// if (user == null)
			// throw new UserNotFoundException(
			// "the password does not exists in the database");
			user.setPasswordStatus(2);
			user.setUserPassword(newPassword);
			em.merge(user);
			System.out.println("am modificat");
			userTransaction.commit();

		} catch (Exception exc) {
			if (exc instanceof UserNotFoundException)
				throw new UserNotFoundException(exc.getMessage());
			throw new EJBException(exc);
		}
	}
}
