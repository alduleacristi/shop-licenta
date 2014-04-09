package com.siemens.ctbav.intership.shop.view.internationalization;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.siemens.ctbav.intership.shop.conf.ConfigurationService;
import com.siemens.ctbav.intership.shop.dto.superadmin.CubeDTO;
import com.siemens.ctbav.intership.shop.exception.superadmin.importer.InvalidFileException;
import com.siemens.ctbav.intership.shop.internationalization.enums.superadmin.EMenuIndex;
import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;
import com.siemens.ctbav.intership.shop.service.superadmin.ReadBankXml;

@ManagedBean(name = "internationalizationBean")
@SessionScoped
public class InternationalizationBean {

	@EJB
	private InternationalizationService internationalizationService;

	@EJB
	private ConfigurationService confService;

	private String menuIndexHome;
	private String menuIndexLogout;
	private boolean isEnglishSelected;
	private String language;
	private String country;
	private String changeLanguage;
	private String flag;

	@PostConstruct
	private void init() {
		isEnglishSelected = true;
		language = new String("en");
		country = new String("US");
		internationalizationService.setCurrentLocale(language, country,
				"internationalization/superadmin/menu/menuIndex/MenuIndex");
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("isEnglishSelected", isEnglishSelected);
		changeLanguage = "Romana";
		flag = "/resources/romanian_flag.jpg";
		connectToUrlGetXml();
	}

	private void connectToUrlGetXml() {
		URL myURL;
		try {
			myURL = new URL(confService.getBankService());
			URLConnection myURLConnection = myURL.openConnection();
			myURLConnection.connect();

			BufferedReader in = new BufferedReader(new InputStreamReader(
					myURLConnection.getInputStream()));

			File stream = File.createTempFile("fileName", ".xml");

			PrintStream printer = new PrintStream(stream);
			parseXml(in, printer);
			printer.close();

			FileInputStream fis = new FileInputStream(stream);

			ReadBankXml read = new ReadBankXml();
			List<CubeDTO> cubes = new ArrayList<CubeDTO>();
			try {
				cubes = read.readCubes(fis);
				Double ron = Double.parseDouble(getByCoint("RON", cubes)
						.getRate());
				System.out.println("RON: " + ron);
				FacesContext.getCurrentInstance().getExternalContext()
						.getSessionMap().put("RON", ron);
			} catch (InvalidFileException e) {
				treatException(e);
			}
		} catch (IOException e) {
			treatException(e);
		}
	}

	private void treatException(Exception e) {
		Double ron = 4.5;
		System.out.println("RON: " + ron);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("RON", ron);
		System.out.println(e);
	}

	private void parseXml(BufferedReader in, PrintStream printer)
			throws IOException {
		String inputLine;

		while ((inputLine = in.readLine()) != null) {
			if (inputLine.startsWith("<?xml")) {
				printer.println(inputLine);
			} else {
				if (!inputLine.contains("<gesmes>")
						&& !inputLine.contains("</gesmes>")) {
					if (!inputLine.contains("<Cube>")
							&& !inputLine.contains("</Cubes>")) {
						if (inputLine.contains("<Cube time=")) {
							inputLine = "<Cubes>";
							printer.println(inputLine);
						} else {
							if (inputLine.contains("<Cube currency")) {
								printer.println(inputLine);
							}
						}
					}
				}
			}
		}
		printer.println("</Cubes>");
	}

	public CubeDTO getByCoint(String coin, List<CubeDTO> cubes) {
		for (CubeDTO c : cubes) {
			if (c.getCurrency().equals(coin))
				return c;
		}
		return null;
	}

	public String getMenuIndexHome() {
		menuIndexHome = internationalizationService.getMessage(EMenuIndex.HOME
				.getName());
		return menuIndexHome;
	}

	public String getMenuIndexLogout() {
		menuIndexLogout = internationalizationService
				.getMessage(EMenuIndex.LOGOUT.getName());
		return menuIndexLogout;
	}

	public boolean isEnglishSelected() {
		return isEnglishSelected;
	}

	public void setEnglishSelected(boolean isEnglishSelected) {
		this.isEnglishSelected = isEnglishSelected;
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("isEnglishSelected", isEnglishSelected);
		if (isEnglishSelected == true) {
			language = new String("en");
			country = new String("US");
			changeLanguage = "Romana";
			flag = "/resources/romanian_flag.jpg";
		} else {
			language = new String("ro");
			country = new String("RO");
			changeLanguage = "English";
			flag = "/resources/us-flag.png";
		}
		internationalizationService.setCurrentLocale(language, country,
				"internationalization/superadmin/menu/menuIndex/MenuIndex");
	}

	public String getChangeLanguage() {
		return changeLanguage;
	}

	public void setChangeLanguage(String changeLanguage) {
		this.changeLanguage = changeLanguage;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public void change(ActionEvent actionEvent) {
		if (changeLanguage.equals("Romana")) {
			setEnglishSelected(false);
			flag = "/resources/us-flag.png";
			changeLanguage = "English";
		} else {
			flag = "/resources/romanian_flag.jpg";
			changeLanguage = "Romana";
			setEnglishSelected(true);
		}
	}
}
