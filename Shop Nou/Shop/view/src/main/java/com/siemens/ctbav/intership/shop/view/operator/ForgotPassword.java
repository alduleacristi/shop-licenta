package com.siemens.ctbav.intership.shop.view.operator;

import java.io.IOException;
import java.util.Calendar;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.convert.operator.ConvertUser;
import com.siemens.ctbav.intership.shop.dto.operator.UserDTO;
import com.siemens.ctbav.intership.shop.service.operator.UserService;
import com.siemens.ctbav.intership.shop.util.operator.GenerateString;
import com.siemens.ctbav.intership.shop.util.operator.MailService;

@ManagedBean(name = "forgotPassword")
@RequestScoped
public class ForgotPassword {

	@EJB
	UserService userService;
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void generateNewPassword() {
		try {
			UserDTO user = ConvertUser.convertUser(userService
					.getUserByEmail(email));
			String password;
			do {
				password = GenerateString.randomPassword(); // generez o parola
															// random de 10
															// caractere
				System.out.println(password);
			} while (userService.passwordAlreadyExists(password));
			user.setPassword(password); // setez parola random utilizatorului
			user.setPasswordStatus(1); // adica e nou generata,utilizatorul
										// poate
										// sa acceseze pagina daca timpul nu a
										// trecut inca
			userService.setTemporaryPassword(user);
			String link = "http://localhost:8080/Shop4j/rest/passwordRecovery/"
					+ password + "/" + Calendar.getInstance().getTimeInMillis();
			// aici trimite linkul dar acum nu am net
			// in orice caz linkul se trimite ok
			
			 MailService.sendLink(email, "Password recovery", link, false);// - se
			// apeleaza metoda statica sendLink din clasa MailService
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Info",
					"Please check your email"));
			context.getExternalContext().redirect("login.xhtml");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
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
