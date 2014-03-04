package com.siemens.ctbav.intership.shop.view.operator;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.util.operator.Interval;

@ManagedBean(name = "mailValidity")
@RequestScoped
public class MailValidity {

	private int clickedRadioButton;

	@PostConstruct
	public void postConstruct() {
		clickedRadioButton = Interval.DEFAULT.getRank();
	}

	public int getClickedRadioButton() {
		return clickedRadioButton;
	}

	public void setClickedRadioButton(int clickedRadioButton) {
		this.clickedRadioButton = clickedRadioButton;
	}

	public void change() {

		System.out.println(clickedRadioButton);
		switch (clickedRadioButton) {
		case 1:
			Interval.DEFAULT.set(Interval.ONE_DAY);
			break;
		case 2:
			Interval.DEFAULT.set(Interval.ONE_WEEK);
			break;
		case 3:
			Interval.DEFAULT.set(Interval.ONE_MONTH);
			break;
		case 4:
			Interval.DEFAULT.set(Interval.ONE_YEAR);
			break;

		}

		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"The interval has been changed!",
				"The interval has been changed!"));
	}
}
