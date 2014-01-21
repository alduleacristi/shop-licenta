package com.siemens.ctbav.intership.shop.view.operator;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.convert.operator.ConvertOperator;
import com.siemens.ctbav.intership.shop.dto.operator.OperatorDTO;
import com.siemens.ctbav.intership.shop.model.User;
import com.siemens.ctbav.intership.shop.service.operator.UserService;

@ManagedBean(name = "changeAccount")
@RequestScoped
public class ChangeAccountBean {
	
	
	@PostConstruct
	public void init()
	{
		User u = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
		operator = ConvertOperator.convertOperator(u);
	}
	
//	@PreDestroy
//	public void incercare()
//	{
//		System.out.println("inainte de destroy");
//		System.out.println("operator nou " + operator);
//		//User user = (User)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
//		//System.out.println("operator vechi " + new OperatorDTO(user.getUsername(), user.getUserPassword(), user.getUserPassword(), user.getEmail()));
//	}
	@EJB
	UserService userService;
	
	private OperatorDTO operator;
	

	public OperatorDTO getOperator() {
		return operator;
	}

	public void setOperator(OperatorDTO operator) {
		this.operator = operator;
	}

	
	public void update()
	{
		User user = (User)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
		if(operator.equals(new OperatorDTO(user.getUsername(), user.getUserPassword(), user.getUserPassword(), user.getEmail())))
		{
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Info",
					"No changes in your account dates"));
			return;
		}
	
		try {
			userService.updateOperator(operator, user.getUsername());
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "OK",
					"Your account has been changed!"));
		} 
		
		catch(Exception e )
		{
			System.out.println("exceptie " + e.getMessage());
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "OOps",
					e.getMessage()));
		}
		
	}

}
