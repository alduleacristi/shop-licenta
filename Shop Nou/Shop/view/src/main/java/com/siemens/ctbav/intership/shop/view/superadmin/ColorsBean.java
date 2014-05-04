package com.siemens.ctbav.intership.shop.view.superadmin;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.siemens.ctbav.intership.shop.exception.superadmin.ColorException;
import com.siemens.ctbav.intership.shop.internationalization.enums.superadmin.EColor;
import com.siemens.ctbav.intership.shop.model.Color;
import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;
import com.siemens.ctbav.intership.shop.service.superadmin.ColorService;
import com.siemens.ctbav.intership.shop.util.superadmin.NavigationUtils;

@ManagedBean(name = "colorsBean")
@ViewScoped
@URLMappings(mappings = { @URLMapping(id = "colors", pattern = "/superadmin/colors/", viewId = "colors.xhtml") })
public class ColorsBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	ColorService colorService;

	@EJB
	private InternationalizationService internationalizationService;

	private String color;
	private List<Color> allColors;
	private Color selectedColor;
	private String newName;
	private String newDescription;
	private String photo;

	@PostConstruct
	void initialization() {
		allColors = colorService.getAllColors();
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
			photo = "/resources/colors.jpg";
			String language = new String("en");
			String country = new String("US");
			internationalizationService.setCurrentLocale(language, country,
					"internationalization/superadmin/messages/colors/Colors");
		} else {
			photo = "/resources/colorsR.jpg";
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService.setCurrentLocale(language, country,
					"internationalization/superadmin/messages/colors/Colors");
		}
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public List<Color> getAllColors() {
		return allColors;
	}

	public void setAllColors(List<Color> allColors) {
		this.allColors = allColors;
	}

	public Color getSelectedColor() {
		return selectedColor;
	}

	public void setSelectedColor(Color selectedColor) {
		this.selectedColor = selectedColor;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public String getNewDescription() {
		return newDescription;
	}

	public void setNewDescription(String newDescription) {
		this.newDescription = newDescription;
	}

	public void handleClose(CloseEvent event) {
		setColor("");
	}

	public String getPhoto() {
		return photo;
	}

	public void update(ActionEvent actionEvent) {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean update = false;
		FacesMessage msg = null;
		try {
			String c = selectedColor.getCode();
			selectedColor.setCode("#" + color);
			List<Color> otherColors = colorService.getOtherColors(selectedColor
					.getId());
			if (otherColors.contains(selectedColor)) {
				System.out.println("deja exista");
				throw new ColorException(
						internationalizationService.getMessage(EColor.EXCEPTION
								.getName()));
			} else {
				System.out.println("nu exista");
				selectedColor.setCode(c);
				if (color.equals("")) {
					colorService.updateColorNameAndDescription(
							selectedColor.getId(), selectedColor.getName(),
							selectedColor.getDescription());
				} else {
					colorService.updateColor(selectedColor.getId(),
							selectedColor.getName(),
							selectedColor.getDescription(), "#" + color);
				}
				msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						internationalizationService.getMessage(EColor.SUCCESS
								.getName()),
						internationalizationService
								.getMessage(EColor.COLOR_EDITED.getName()));
				update = true;
				//allColors = colorService.getAllColors();
			}
		} catch (ColorException e) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					internationalizationService.getMessage(EColor.ERROR
							.getName()), e.getMessage());
			update = false;
		} finally {
			setColor("");
			NavigationUtils.addMessage(msg);
			context.addCallbackParam("update", update);
			allColors = colorService.getAllColors();
		}
	}

	public void create(ActionEvent actionEvent) {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean create = false;
		FacesMessage msg = null;
		try {
			tryToCreate();
			create = true;
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					internationalizationService.getMessage(EColor.SUCCESS
							.getName()),
					internationalizationService.getMessage(EColor.COLOR_ADDED
							.getName()));
			allColors = colorService.getAllColors();
		} catch (ColorException e) {
			create = false;
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					internationalizationService.getMessage(EColor.ERROR
							.getName()), e.getMessage());
		} finally {
			NavigationUtils.addMessage(msg);
			context.addCallbackParam("create", create);
		}
	}

	private void tryToCreate() throws ColorException {
		Color newColor = new Color(newName, newDescription, "#" + color);
		if (allColors.contains(newColor)) {
			throw new ColorException(
					internationalizationService.getMessage(EColor.EXCEPTION
							.getName()));
		}
		colorService.addColor(newName, newDescription, "#" + color);
	}

	public void delete(ActionEvent actionEvent) {
		FacesMessage msg = null;

		try {
			colorService.removeColor(selectedColor.getId());
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					internationalizationService.getMessage(EColor.SUCCESS
							.getName()),
					internationalizationService.getMessage(EColor.COLOR_DELETED
							.getName()));
			allColors = colorService.getAllColors();
		} catch (ColorException e) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					internationalizationService.getMessage(EColor.ERROR
							.getName()), e.getMessage());
		} finally {
			setColor("");
			NavigationUtils.addMessage(msg);
		}
	}

}
