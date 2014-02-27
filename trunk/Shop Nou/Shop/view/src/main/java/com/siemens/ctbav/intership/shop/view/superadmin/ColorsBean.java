package com.siemens.ctbav.intership.shop.view.superadmin;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.siemens.ctbav.intership.shop.exception.superadmin.ColorException;
import com.siemens.ctbav.intership.shop.model.Color;
import com.siemens.ctbav.intership.shop.service.superadmin.ColorService;
import com.siemens.ctbav.intership.shop.util.superadmin.NavigationUtils;

@ManagedBean(name = "colorsBean")
@ViewScoped
@URLMappings(mappings = { @URLMapping(id = "colors", pattern = "/superadmin/colors/", viewId = "colors.xhtml") })
public class ColorsBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	ColorService colorService;

	private String color;
	private List<Color> allColors;
	private Color selectedColor;
	private String newName;
	private String newDescription;

	@PostConstruct
	void initialization() {
		allColors = colorService.getAllColors();
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

	public void update(ActionEvent actionEvent) {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean update = false;
		FacesMessage msg = null;
		try {
			if (color.equals("")) {
				colorService.updateColorNameAndDescription(
						selectedColor.getId(), selectedColor.getName(),
						selectedColor.getDescription());
			} else {
				colorService.updateColor(selectedColor.getId(),
						selectedColor.getName(),
						selectedColor.getDescription(), "#" + color);
			}
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Succes",
					"Successfully edited!");
			update = true;
			allColors = colorService.getAllColors();
		} catch (ColorException e) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					e.getMessage());
			update = false;
		} finally {
			setColor("");
			NavigationUtils.addMessage(msg);
			context.addCallbackParam("update", update);
		}
	}

}
