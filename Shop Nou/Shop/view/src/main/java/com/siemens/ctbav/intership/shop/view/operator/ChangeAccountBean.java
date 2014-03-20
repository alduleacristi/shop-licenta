package com.siemens.ctbav.intership.shop.view.operator;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import com.siemens.ctbav.intership.shop.convert.operator.ConvertUser;
import com.siemens.ctbav.intership.shop.dto.operator.UserDTO;
import com.siemens.ctbav.intership.shop.exception.operator.DuplicateFieldException;
import com.siemens.ctbav.intership.shop.model.User;
import com.siemens.ctbav.intership.shop.service.operator.UserService;

@ManagedBean(name = "changeAccount")
@RequestScoped
public class ChangeAccountBean {

	@PostConstruct
	public void init() {
		User u = (User) FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get("user");
		user = ConvertUser.convertUser(u);
	

	}

	@EJB
	UserService userService;
	//private OperatorDTO operator;

	private UserDTO user;
	

	public UserDTO getUser() {
		return user;
	}


	public void setUser(UserDTO user) {
		this.user = user;
	}


	public void update() {
		User u = (User) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("user");
		
		System.out.println("user initial : " + ConvertUser.convertUser(u));
		
		System.out.println("user dupa modofcari : " + user);
		//UserDTO newUser = new UserDTO(user.getUsername(), user.getEmail(), user.getPassword(), user.getRolename(),user.getPasswordStatus());
		if (user.equals(ConvertUser.convertUser(u))) {
			System.out.println("nicio modificare");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "No changes in your account dates",
					"No changes in your account dates"));
			return;
		}

		try {
			userService.updateUser(user, u.getUsername());
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Your account has been changed!",
					"Your account has been changed!"));
		}

		catch (DuplicateFieldException e) {
			System.out.println("exceptie " + e.getMessage());
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR,"Your account cannot be updated ","Your account cannot be updated. "+ e.getMessage()));
		}

	}

	

	
}
