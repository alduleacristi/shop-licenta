package com.siemens.ctbav.intership.shop.view.client;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.validation.constraints.Size;

import com.siemens.ctbav.intership.shop.model.Adress;
import com.siemens.ctbav.intership.shop.model.Client;
import com.siemens.ctbav.intership.shop.model.Country;
import com.siemens.ctbav.intership.shop.model.County;
import com.siemens.ctbav.intership.shop.model.Locality;
import com.siemens.ctbav.intership.shop.service.client.AdressService;
import com.siemens.ctbav.intership.shop.service.client.CountryService;
import com.siemens.ctbav.intership.shop.service.client.CountyService;
import com.siemens.ctbav.intership.shop.service.client.LocalityService;
import com.siemens.ctbav.intership.shop.service.client.ProductColorSizeService;
import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;
import com.siemens.ctbav.intership.shop.view.internationalization.enums.client.ECart;

@SessionScoped
@ManagedBean(name = "SendCommandBean")
public class SendCommandBean implements Serializable {
	private static final long serialVersionUID = -6618987358166404408L;

	@EJB
	private AdressService adressService;

	@EJB
	private CountryService countryService;

	@EJB
	private CountyService countyService;

	@EJB
	private LocalityService localityService;

	@EJB
	private ProductColorSizeService productColorSizeService;

	@EJB
	private InternationalizationService internationalizationService;

	private boolean useExistingAdress, addNewAdress, existProductInCart;
	private List<Adress> userAdresses;
	private String messageUserAdress;
	private Adress selectedAdress;
	private Long selectedCountryForNewAdress, selectedCountyForNewAdress,
			selectedLocalityForNewAdress;
	private List<Country> countrysForNewAdress;
	private List<County> countysForNewAdress;
	private List<Locality> localitysForNewAdress;

	@Size(min = 1, message = "The street name must have at least on character")
	private String street;

	private String staircase;

	private Long number, block, flat;

	@PostConstruct
	private void initialize() {
		this.setUseExistingAdress(false);
		existProductInCart = true;
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
			String language = new String("en");
			String country = new String("US");
			internationalizationService.setCurrentLocale(language, country,
					"internationalization/client/cart/Cart");
		} else {
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService.setCurrentLocale(language, country,
					"internationalization/client/cart/Cart");
		}
	}

	public boolean getUseExistingAdress() {
		return useExistingAdress;
	}

	public void setUseExistingAdress(boolean useExistingAdress) {
		this.useExistingAdress = useExistingAdress;
	}

	public List<Adress> getUserAdresses() {
		return userAdresses;
	}

	public void setUserAdresses(List<Adress> userAdresses) {
		this.userAdresses = userAdresses;
	}

	public String getMessageUserAdress() {
		return messageUserAdress;
	}

	public void setMessageUserAdress(String messageUserAdress) {
		this.messageUserAdress = messageUserAdress;
	}

	public Adress getSelectedAdress() {
		return selectedAdress;
	}

	public void setSelectedAdress(Adress selectedAdress) {
		this.selectedAdress = selectedAdress;
	}

	public boolean getAddNewAdress() {
		return addNewAdress;
	}

	public void setAddNewAdress(boolean addNewAdress) {
		this.addNewAdress = addNewAdress;
	}

	public Long getSelectedCountryForNewAdress() {
		return selectedCountryForNewAdress;
	}

	public void setSelectedCountryForNewAdress(Long selectedCountryForNewAdress) {
		this.countysForNewAdress = countyService
				.getCountyOfCountry(selectedCountryForNewAdress);

		if (selectedCountryForNewAdress == 0)
			this.selectedCountryForNewAdress = null;
		else
			this.selectedCountryForNewAdress = selectedCountryForNewAdress;
	}

	public List<Country> getCountrysForNewAdress() {
		return countrysForNewAdress;
	}

	public void setCountrysForNewAdress(List<Country> countrysForNewAdress) {
		this.countrysForNewAdress = countrysForNewAdress;
	}

	public Long getSelectedCountyForNewAdress() {
		return selectedCountyForNewAdress;
	}

	public void setSelectedCountyForNewAdress(Long selectedCountyForNewAdress) {
		this.localitysForNewAdress = localityService
				.getLocalityOfCounty(selectedCountyForNewAdress);

		if (selectedCountyForNewAdress == 0)
			this.selectedCountyForNewAdress = null;
		else
			this.selectedCountyForNewAdress = selectedCountyForNewAdress;
	}

	public List<County> getCountysForNewAdress() {
		return countysForNewAdress;
	}

	public void setCountysForNewAdress(List<County> countysForNewAdress) {
		this.countysForNewAdress = countysForNewAdress;
	}

	public List<Locality> getLocalitysForNewAdress() {
		return localitysForNewAdress;
	}

	public void setLocalitysForNewAdress(List<Locality> localitysForNewAdress) {
		this.localitysForNewAdress = localitysForNewAdress;
	}

	public Long getSelectedLocalityForNewAdress() {
		return selectedLocalityForNewAdress;
	}

	public void setSelectedLocalityForNewAdress(
			Long selectedLocalityForNewAdress) {

		if (selectedLocalityForNewAdress == 0)
			this.selectedLocalityForNewAdress = null;
		else
			this.selectedLocalityForNewAdress = selectedLocalityForNewAdress;
	}

	public boolean getExistProductInCart() {
		return existProductInCart;
	}

	public void setExistProductInCart(boolean existProductInCart) {
		this.existProductInCart = existProductInCart;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public String getStaircase() {
		return staircase;
	}

	public void setStaircase(String staircase) {
		if (staircase.equals(""))
			this.staircase = null;
		else
			this.staircase = staircase;
	}

	public Long getBlock() {
		return block;
	}

	public void setBlock(Long block) {
		if (block == 0)
			this.block = null;
		else
			this.block = block;
	}

	public Long getFlat() {
		return flat;
	}

	public void setFlat(Long flat) {
		if (flat == 0)
			this.flat = null;
		else
			this.flat = flat;
	}

	public void changeUseExistingAdress() {
		if (this.useExistingAdress) {
			this.addNewAdress = false;

			Client client = (Client) FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().get("client");

			if (client == null)
				return;

			userAdresses = adressService.getClientAdress(client.getId());
			this.setUseExistingAdress(true);

			if (userAdresses.size() <= 0) {
				internationalizationInit();
				this.setMessageUserAdress(internationalizationService
						.getMessage(ECart.NO_RECORDS.getName()));
				// System.out.println(messageUserAdress);
			} else {
				this.selectedAdress = userAdresses.get(0);
				this.messageUserAdress = selectedAdress.toString();
				System.out.println(messageUserAdress + " " + useExistingAdress);
			}
		} else {
			this.setUseExistingAdress(false);
		}
	}

	public void chooseAnotherAdress(Adress adress) {
		this.selectedAdress = adress;
		this.messageUserAdress = adress.toString();
	}

	public void finalizeCommand() {

	}

	public void createNewAdress() {
		if (this.addNewAdress) {
			this.useExistingAdress = false;

			countrysForNewAdress = countryService.getCountrys();
		} else {
			this.addNewAdress = false;
		}
	}

	private void redirect(String url) {
		FacesContext fc = FacesContext.getCurrentInstance();

		try {
			fc.getExternalContext().getFlash().setKeepMessages(true);
			fc.getExternalContext().redirect(url);
		} catch (IOException e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			ctx.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							internationalizationService.getMessage(ECart.ERROR
									.getName()), internationalizationService
									.getMessage(ECart.DESCRIPTION_UNAVAILABLE
											.getName())));
		}
	}

	public void goHome() {
		redirect("/Shop4j/client/user/index");
	}
}
