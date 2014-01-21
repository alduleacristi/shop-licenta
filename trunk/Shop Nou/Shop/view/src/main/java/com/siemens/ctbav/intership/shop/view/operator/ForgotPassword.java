package com.siemens.ctbav.intership.shop.view.operator;

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
	
	public void generateNewPassword()
	{
		try {
			UserDTO user = ConvertUser.convertUser(userService.getUserByEmail(email));
			//User user = userService.getUserByEmail(email);
			String password;
			do
			{
			password = GenerateString.randomPassword(); //generez
			System.out.println(password);
			}
			while(userService.passwordAlreadyExists(password));
			user.setPassword(password); //setez parola random utilizatorului
			user.setPasswordStatus(1); //adica e nou generata,utilizatorul poate
										//sa acceseze pagina daca timpul nu a trecut inca
			userService.setTemporaryPassword(user);
			String link = "http://localhost:8080/Shop4j/rest/passwordRecovery/"+password;
			MailService.sendLink(email, "Password recovery", link);
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Info",
					"Please check your email"));
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Info",
					e.getMessage()));
			return;
		}
	}
}
