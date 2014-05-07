package com.siemens.ctbav.intership.shop.view.visitor;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.validation.constraints.Size;

import org.primefaces.context.RequestContext;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.siemens.ctbav.intership.shop.exception.UserException;
import com.siemens.ctbav.intership.shop.exception.client.AdressDoesNotExistException;
import com.siemens.ctbav.intership.shop.internationalization.enums.client.ECart;
import com.siemens.ctbav.intership.shop.internationalization.enums.superadmin.EManageUsers;
import com.siemens.ctbav.intership.shop.model.Adress;
import com.siemens.ctbav.intership.shop.model.Client;
import com.siemens.ctbav.intership.shop.model.Country;
import com.siemens.ctbav.intership.shop.model.County;
import com.siemens.ctbav.intership.shop.model.Locality;
import com.siemens.ctbav.intership.shop.model.User;
import com.siemens.ctbav.intership.shop.service.client.AdressService;
import com.siemens.ctbav.intership.shop.service.client.ClientService;
import com.siemens.ctbav.intership.shop.service.client.CountryService;
import com.siemens.ctbav.intership.shop.service.client.CountyService;
import com.siemens.ctbav.intership.shop.service.client.LocalityService;
import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;
import com.siemens.ctbav.intership.shop.util.superadmin.NavigationUtils;

@ManagedBean(name = "createAccountStep2Bean")
@ViewScoped
@URLMappings(mappings = { @URLMapping(id = "createAccountStep2", pattern = "/client/CreateAccountStep2/", viewId = "/client/CreateAccount/createAccountStep2.xhtml") })
public class CreateAccountStep2 implements Serializable {

	private static final long serialVersionUID = 15456L;

	@EJB
	private InternationalizationService internationalizationService;

	@EJB
	private ClientService clientService;

	@EJB
	private CountryService countryService;

	@EJB
	private CountyService countyService;

	@EJB
	private LocalityService localityService;

	@EJB
	private AdressService adressService;

	private String firstName;
	private String lastName;
	private String phoneNumber;
	private boolean created;
	private User user;
	private boolean nullUser;
	private boolean addAdress;
	private Long selectedCountryForNewAdress;
	private Long selectedCountyForNewAdress;
	private Long selectedLocalityForNewAdress;
	private List<Country> countrysForNewAdress;
	private List<County> countysForNewAdress;
	private List<Locality> localitysForNewAdress;
	private String staircase;
	private Long number;
	private Long block;
	private Long flat;

	@Size(min = 1, message = "The street name must have at least on character")
	private String street;

	@PostConstruct
	private void initialize() {
		Boolean c = (Boolean) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("created");

		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.remove("created");

		System.out.println("initialize step2");
		if (c == null) {
			created = false;
			System.out.println("created false");
		} else {
			created = c;
			System.out.println("created: " + created);
		}

		internationalizationInit();

		user = (User) FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get("createAccountStep2");

		if (user != null) {
			FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().remove("createAccountStep2");
			nullUser = false;
		} else {
			nullUser = true;
		}
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
			internationalizationService
					.setCurrentLocale(language, country,
							"internationalization/superadmin/messages/manageUsers/ManageUsers");
		} else {
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService
					.setCurrentLocale(language, country,
							"internationalization/superadmin/messages/manageUsers/ManageUsers");
		}
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean isCreated() {
		return created;
	}

	public void setCreated(boolean created) {
		this.created = created;
	}

	public boolean isNullUser() {
		return nullUser;
	}

	public void setNullUser(boolean nullUser) {
		this.nullUser = nullUser;
	}

	public boolean isAddAdress() {
		return addAdress;
	}

	public void setAddAdress(boolean addAdress) {
		this.addAdress = addAdress;
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

	public List<Country> getCountrysForNewAdress() {
		return countrysForNewAdress;
	}

	public void setCountrysForNewAdress(List<Country> countrysForNewAdress) {
		this.countrysForNewAdress = countrysForNewAdress;
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

	public String getStaircase() {
		return staircase;
	}

	public void setStaircase(String staircase) {
		this.staircase = staircase;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public Long getBlock() {
		return block;
	}

	public void setBlock(Long block) {
		this.block = block;
	}

	public Long getFlat() {
		return flat;
	}

	public void setFlat(Long flat) {
		this.flat = flat;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void createAccount() {
		Client client = new Client();
		client.setUser(user);
		client.setFirstname(this.firstName);
		client.setLastname(this.lastName);
		client.setPhoneNumber(this.phoneNumber);
		System.out.println(client);
		FacesMessage msg = null, msg2 = null;
		RequestContext context = RequestContext.getCurrentInstance();

		try {
			clientService.addClient(client);

			msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					internationalizationService.getMessage(EManageUsers.SUCCESS
							.getName()),
					internationalizationService
							.getMessage(EManageUsers.USER_ADDED.getName()));
			created = true;

			if (this.addAdress) {
				Adress adress;

				adress = buildAdress(client);

				adressService.addAdress(adress);

				msg2 = new FacesMessage(FacesMessage.SEVERITY_INFO,
						internationalizationService
								.getMessage(EManageUsers.SUCCESS.getName()),
						internationalizationService
								.getMessage(EManageUsers.USER_ADDED.getName()));
			}

		} catch (UserException e) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					internationalizationService
							.getMessage(EManageUsers.ACCOUNT_ERROR.getName()));
		} catch (AdressDoesNotExistException e) {
			msg2 = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					internationalizationService
							.getMessage(EManageUsers.ADRESS_ERROR.getName()));
		} finally {
			if (msg != null)
				NavigationUtils.addMessage(msg);

			if (msg2 != null)
				NavigationUtils.addMessage(msg2);

			context.addCallbackParam("create", created);

		}
	}

	public void redirectLogin(ActionEvent actionEvent) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.remove("created");
		NavigationUtils.redirect("/Shop4j/Login");
	}

	public void createNewAdress() {
		if (this.addAdress) {
			countrysForNewAdress = countryService.getCountrys();
		} else {
			this.addAdress = false;
		}
	}

	private Adress buildAdress(Client client)
			throws AdressDoesNotExistException {
		if (this.getSelectedCountryForNewAdress() == null)
			throw new AdressDoesNotExistException(
					internationalizationService.getMessage(ECart.CHOOSE_COUNTRY
							.getName()));

		if (this.getSelectedCountyForNewAdress() == null)
			throw new AdressDoesNotExistException(
					internationalizationService.getMessage(ECart.CHOOSE_COUNTY
							.getName()));

		if (this.getSelectedLocalityForNewAdress() == null)
			throw new AdressDoesNotExistException(
					internationalizationService
							.getMessage(ECart.CHOOSE_LOCALITY.getName()));

		Adress adress = new Adress();

		adress.setBlock(this.getBlock());
		adress.setFlat(this.getFlat());
		adress.setNumber(this.getNumber());
		adress.setStaircase(this.getStaircase());
		adress.setStreet(this.getStreet());
		adress.setClient(client);

		boolean ok = false;
		for (Locality l : this.getLocalitysForNewAdress()) {
			if (l.getIdLocality() == this.getSelectedLocalityForNewAdress()) {
				ok = true;
				adress.setLocality(l);
				break;
			}
		}

		if (!ok)
			throw new AdressDoesNotExistException(
					internationalizationService
							.getMessage(ECart.INCORRECT_ADDRESS.getName()));

		return adress;
	}
}
