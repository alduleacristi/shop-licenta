package com.siemens.ctbav.intership.shop.view.superadmin;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "updateStockBean")
@RequestScoped
public class UpdateStockBean {

	private String photo;

	@PostConstruct
	void init() {
		boolean isEnglishSelected;
		Boolean b = (Boolean) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("isEnglishSelected");
		if (b == null)
			isEnglishSelected = true;
		else
			isEnglishSelected = b;
		if (isEnglishSelected) {
			photo = "/resources/updateStoc.jpg";
		} else {
			photo = "/resources/updateStocR.jpg";
		}
	}

	public String getPhoto() {
		return photo;
	}
}
