package com.siemens.ctbav.intership.shop.view.operator;

import java.io.IOException;
import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.conf.ConfigurationService;
import com.siemens.ctbav.intership.shop.convert.operator.ConvertUser;
import com.siemens.ctbav.intership.shop.dto.operator.UserDTO;
import com.siemens.ctbav.intership.shop.enums.operator.PasswordStatus;
import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;
import com.siemens.ctbav.intership.shop.service.operator.UserService;
import com.siemens.ctbav.intership.shop.util.operator.AES;
import com.siemens.ctbav.intership.shop.util.operator.GenerateString;
import com.siemens.ctbav.intership.shop.util.operator.MailService;
import com.siemens.ctbav.intership.shop.view.internationalization.enums.EForgot;

@ManagedBean(name = "forgotPassword")
@RequestScoped
public class ForgotPassword {

	@EJB
	UserService userService;

	@EJB
	private ConfigurationService confService;

	@EJB
	private InternationalizationService internationalizationService;

	private String email;
	private String host;

	@PostConstruct
	private void init() {
		host = confService.getHost();
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void generateNewPassword() {
		try {
			UserDTO user = ConvertUser.convertUser(userService
					.getUserByEmail(email));
			String password, cryptedPassword;
			do {
				password = GenerateString.randomPassword(); // generez o parola
															// random de 10
															// caractere

				System.out.println(password);
			} while (userService.passwordAlreadyExists(password));

			user.setPassword(password);
			user.setPasswordStatus(PasswordStatus.NEW_GENERATED);
			// setez parola random utilizatorului
			// adica e nou generata,utilizatorul
			// poate
			// sa acceseze pagina daca timpul nu a
			// trecut inca
			userService.setTemporaryPassword(user);
			cryptedPassword = AES.encrypt(password);
			Long now = Calendar.getInstance().getTimeInMillis();
			// folosesc algoritmul de criptare ca sa ascund data trimiterii
			// asa clientul nu o poate modifica
			String encryptedTime = AES.encrypt(now.toString());
			String link = confService.getHost()
					+ "Shop4j/rest/passwordRecovery/" + cryptedPassword + "/"
					+ encryptedTime;
			// aici trimite linkul dar acum nu am net
			// in orice caz linkul se trimite ok

			MailService.sendLink(email, "Password recovery", link, false);// -
																			// se
			// apeleaza metoda statica sendLink din clasa MailService
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info",
							internationalizationService
									.getMessage(EForgot.INFO_MESSAGE.getName())));
			context.getExternalContext().redirect("login.xhtml");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), e.getMessage()));
			return;
		}
	}

	public void cancel() {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("login.xhtml");
		} catch (IOException e) {
			System.out.println("Nu pot sa ma intorc pe pagina de login");
		}
	}

}
