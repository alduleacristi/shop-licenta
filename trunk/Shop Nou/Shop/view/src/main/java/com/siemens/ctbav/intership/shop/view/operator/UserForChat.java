package com.siemens.ctbav.intership.shop.view.operator;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.model.User;


@ManagedBean(name="userName")
@RequestScoped
public class UserForChat {

	
	public String getUserName(){
		
		System.out.println("get userbame ");
		User user = (User) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("user");
		if (user == null)
			return "";
		
		System.out.println(user.getUsername());
		return user.getUsername();
		
		
	}
	
	public boolean ok(){
		return true;
	}
}
