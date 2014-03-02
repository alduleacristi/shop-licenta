package com.siemens.ctbav.intership.shop.view.operator;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import com.siemens.ctbav.intership.shop.convert.operator.ConvertOperator;
import com.siemens.ctbav.intership.shop.dto.operator.OperatorDTO;
import com.siemens.ctbav.intership.shop.exception.operator.DuplicateFieldException;
import com.siemens.ctbav.intership.shop.model.User;
import com.siemens.ctbav.intership.shop.service.operator.UserService;
import com.siemens.ctbav.intership.shop.util.operator.Interval;

@ManagedBean(name = "changeAccount")
@RequestScoped
public class ChangeAccountBean {

	@PostConstruct
	public void init() {
		User u = (User) FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get("user");
		operator = ConvertOperator.convertOperator(u);
	

	}

	@EJB
	UserService userService;
	private OperatorDTO operator;

	public OperatorDTO getOperator() {
		return operator;
	}

	public void setOperator(OperatorDTO operator) {
		this.operator = operator;
	}

	public void update() {
		User user = (User) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("user");
		if (operator.equals(new OperatorDTO(user.getUsername(), user
				.getUserPassword(), user.getUserPassword(), user.getEmail()))) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "No changes in your account dates",
					"No changes in your account dates"));
			return;
		}

		try {
			userService.updateOperator(operator, user.getUsername());
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Your account has been changed!",
					"Your account has been changed!"));
		}

		catch (DuplicateFieldException e) {
		//	System.out.println("exceptie " + e.getMessage());
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR,"Your account cannot be updated","Your account cannot be updated"));
		}

	}

	

	
}
