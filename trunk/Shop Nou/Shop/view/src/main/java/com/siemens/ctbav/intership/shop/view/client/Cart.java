package com.siemens.ctbav.intership.shop.view.client;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.siemens.ctbav.intership.shop.convert.client.ConvertProductColorSizeNumber;
import com.siemens.ctbav.intership.shop.dto.client.ProductColorSizeNumberDTO;
import com.siemens.ctbav.intership.shop.exception.client.AdressDoesNotExistException;
import com.siemens.ctbav.intership.shop.exception.client.CommmandStatusDoesNotExistException;
import com.siemens.ctbav.intership.shop.exception.client.NotEnoughProductsInStockException;
import com.siemens.ctbav.intership.shop.exception.client.ProductColorSizeDoesNotExistException;
import com.siemens.ctbav.intership.shop.model.Adress;
import com.siemens.ctbav.intership.shop.model.Client;
import com.siemens.ctbav.intership.shop.model.ClientProduct;
import com.siemens.ctbav.intership.shop.model.Command;
import com.siemens.ctbav.intership.shop.model.CommandStatus;
import com.siemens.ctbav.intership.shop.model.Locality;
import com.siemens.ctbav.intership.shop.model.ProductColorSize;
import com.siemens.ctbav.intership.shop.service.client.ClientProductService;
import com.siemens.ctbav.intership.shop.service.client.CommandStatusService;
import com.siemens.ctbav.intership.shop.service.client.ProductColorSizeService;
import com.siemens.ctbav.intership.shop.util.Enum.CommandStatusEnum;
import com.siemens.ctbav.intership.shop.util.client.CookieEncryption;

@SessionScoped
@ManagedBean(name = "cart")
@URLMappings(mappings = {
		@URLMapping(id = "cartClient", pattern = "/client/user/Cart", viewId = "/client/user/cart.xhtml"),
		@URLMapping(id = "sendCommand", pattern = "/client/user/sendCommand", viewId = "/client/user/sendCommand.xhtml") })
public class Cart implements Serializable {
	private static final long serialVersionUID = -4660863521509380343L;

	@EJB
	private ProductColorSizeService productColorSizeService;

	@EJB
	private CommandStatusService commandStatusService;
	
	@EJB
	private ClientProductService clientProductService;

	@ManagedProperty(value = "#{SendCommandBean}")
	private SendCommandBean sendCommand;

	private Map<ProductColorSize, Long> cart;
	private List<ProductColorSizeNumberDTO> products;
	private ProductColorSizeNumberDTO selectedProductForDescription;
	private Adress accountAdress;

	@PostConstruct
	private void initialize() {
		cart = new HashMap<ProductColorSize, Long>();
		products = new ArrayList<ProductColorSizeNumberDTO>();
	}

	public void setProducts() {
		products.clear();

		Iterator<Map.Entry<ProductColorSize, Long>> it = cart.entrySet()
				.iterator();

		while (it.hasNext()) {
			Map.Entry<ProductColorSize, Long> ob = it.next();

			ProductColorSizeNumberDTO p = ConvertProductColorSizeNumber
					.convertToProductColorSizeNumberDTO(ob.getKey(),
							ob.getValue());
			products.add(p);
		}
	}

	public List<ProductColorSizeNumberDTO> getProducts() {
		return products;
	}

	public ProductColorSizeNumberDTO getSelectedProductForDescription() {
		return selectedProductForDescription;
	}

	public void setSelectedProductForDescription(
			ProductColorSizeNumberDTO selectedProductForDescription) {
		this.selectedProductForDescription = selectedProductForDescription;
	}

	public Map<ProductColorSize, Long> getCart() {
		return cart;
	}

	public void setCart(Map<ProductColorSize, Long> cart) {
		this.cart = cart;
	}

	public Adress getAccountAdress() {
		return accountAdress;
	}

	public void setAccountAdress(Adress accountAdress) {
		this.accountAdress = accountAdress;
	}

	public SendCommandBean getSendCommand() {
		return sendCommand;
	}

	public void setSendCommand(SendCommandBean sendCommand) {
		this.sendCommand = sendCommand;
	}

	public boolean isEmpty() {
		if (cart.size() == 0)
			return false;

		return true;
	}

	public String getNrOfProducts() {
		return String.valueOf(cart.size());
	}

	public String getTotalPrice() {
		Double price = 0.0;
		Iterator<Map.Entry<ProductColorSize, Long>> it = cart.entrySet()
				.iterator();

		while (it.hasNext()) {
			Map.Entry<ProductColorSize, Long> ob = it.next();
			ProductColorSize pcs = ob.getKey();

			price += ob.getValue()
					* (pcs.getProductcolor().getProduct().getPrice() - pcs
							.getProductcolor().getProduct().getPrice()
							* pcs.getProductcolor().getProduct().getReduction()
							/ 100);
		}

		return String.valueOf(price);
	}

	private void addProductFromCookie(ProductColorSize pcs, Long nrOfProducts) {
		addProduct(pcs, nrOfProducts);
	}

	private void addProduct(ProductColorSize pcs, Long nrOfProducts) {
		if (pcs == null) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "Warn",
					"You must choose a size"));
			return;
		}
		if (nrOfProducts == null) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "Warn",
					"You must enter the number of products"));
			return;
		}

		if (nrOfProducts > pcs.getNrOfPieces()) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "Warn",
					"We dont't have enough product in stock"));
			return;
		}

		if (cart.containsKey(pcs)) {
			cart.put(pcs, cart.get(pcs) + nrOfProducts);
		} else {
			cart.put(pcs, nrOfProducts);
		}

		setProducts();
	}

	public void addProductFromPage(ProductColorSize pcs, Long nrOfProducts) {
		addProduct(pcs, nrOfProducts);

		FacesContext
				.getCurrentInstance()
				.getExternalContext()
				.getSessionMap()
				.put("addProductMessage",
						"The product was added succesfully to cart.");

		redirect("/Shop4j/client/user/recommandation");
	}

	public void removeProduct(ProductColorSizeNumberDTO productDTO) {
		ProductColorSize product = ConvertProductColorSizeNumber
				.convertToProductColorSize(productDTO);
		if (cart.containsKey(product)) {
			cart.remove(product);
			setProducts();
			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("/Shop4j/client/user/Cart");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void populateCartFromCookie(Map<Long, Long> cookieProducts) {
		if (cookieProducts == null)
			return;

		clearCart();

		Iterator<Map.Entry<Long, Long>> it = cookieProducts.entrySet()
				.iterator();

		while (it.hasNext()) {
			Map.Entry<Long, Long> ob = it.next();
			try {
				ProductColorSize product = productColorSizeService
						.getProductColorSizeById(ob.getKey());
				addProductFromCookie(product, ob.getValue());
			} catch (ProductColorSizeDoesNotExistException e) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Info",
								"Some products was removed from stock,so it was removed from your cart."));
			}

		}
	}

	public String readCookie() {
		Client client = (Client) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("client");

		if (client == null)
			return null;

		Map<String, Object> cookies = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestCookieMap();

		Cookie cookie = null;
		if (cookies.containsKey(client.getUser().getUsername()))
			cookie = (Cookie) cookies.get(client.getUser().getUsername());
		else
			return null;

		String valCookie = CookieEncryption.decrypt(cookie.getValue());

		String[] elements = valCookie.split("/");
		Map<Long, Long> mapProducts = new HashMap<Long, Long>();

		for (int i = 0; i < elements.length; i++) {
			{
				System.out.println("in for");
				String[] pairs = elements[i].split(":");
				Long idProduct = null;
				Long numberOfPcs = null;

				if (pairs.length == 2) {
					try {

						idProduct = Long.parseLong(pairs[0]);
						// System.out.println("idProduct: " + idProduct);
						numberOfPcs = Long.parseLong(pairs[1]);
						mapProducts.put(idProduct, numberOfPcs);
					} catch (NumberFormatException exc) {
						exc.printStackTrace();
					}
				}
			}
		}

		populateCartFromCookie(mapProducts);

		return null;
	}

	private void clearCart() {
		cart.clear();
		products.clear();
	}

	public void saveToCookie() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) facesContext
				.getExternalContext().getResponse();

		Client client = (Client) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("client");

		StringBuilder sb = new StringBuilder();
		Iterator<Map.Entry<ProductColorSize, Long>> it = cart.entrySet()
				.iterator();

		while (it.hasNext()) {
			Map.Entry<ProductColorSize, Long> ob = it.next();
			sb.append(ob.getKey().getId().toString());
			sb.append(":");
			sb.append(ob.getValue());
			sb.append("/");
		}

		String aux = CookieEncryption.encrypt(sb.toString());
		Cookie cookie = new Cookie(client.getUser().getUsername(), aux);
		cookie.setMaxAge(2592000); // 1 month
		cookie.setPath("/");

		response.addCookie(cookie);

		clearCart();
	}

	public void changeQuantity(ProductColorSizeNumberDTO productDTO) {
		ProductColorSize product = ConvertProductColorSizeNumber
				.convertToProductColorSize(productDTO);

		if (cart.containsKey(product)) {
			if (product.getNrOfPieces() < productDTO.getNrOfPieces()) {
				productDTO.setNrOfPieces(cart.get(product));

				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_WARN, "Info",
						"Sorry we don't have enought products in stock."));
				return;
			} else {
				cart.put(product, productDTO.getNrOfPieces());
			}
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
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Error", "Sorry.Description is not availabel."));
		}
	}

	/**
	 * Verify if all the products from cart are in stock
	 * 
	 * @throws NotEnoughProductsInStockException
	 *             When are products in cart that are not in stock
	 */
	private void verifStock() throws NotEnoughProductsInStockException {
		NotEnoughProductsInStockException exception = new NotEnoughProductsInStockException();

		Iterator<Map.Entry<ProductColorSize, Long>> it = cart.entrySet()
				.iterator();

		while (it.hasNext()) {
			Map.Entry<ProductColorSize, Long> ob = it.next();

			ProductColorSize pcs = ob.getKey();

			try {
				ProductColorSize pcsFromService = productColorSizeService
						.getProductColorSizeById(pcs.getId());

				if (pcsFromService.getNrOfPieces() == 0)
					throw new ProductColorSizeDoesNotExistException();

				if (pcsFromService.getNrOfPieces() < pcs.getNrOfPieces()) {
					cart.put(pcs, pcsFromService.getNrOfPieces());
					exception
							.addMessage("We have only: "
									+ pcsFromService.getNrOfPieces()
									+ " pieces of "
									+ pcs.getProductcolor().getProduct()
											.getName()
									+ " so we modify the quantity of product from your cart.");
				}
			} catch (ProductColorSizeDoesNotExistException e) {
				exception
						.addMessage("Product: "
								+ pcs.getProductcolor().getProduct().getName()
								+ " is not in stock for now so it was removed from your cart.");
				cart.remove(pcs);
			}
		}

		setProducts();

		if (exception.getMessages().size() > 0)
			throw exception;
	}

	private Adress buildAdress() throws AdressDoesNotExistException {
		Adress adress = null;

		if (sendCommand.getUseExistingAdress()) {
			if (sendCommand.getSelectedAdress() == null) {
				throw new AdressDoesNotExistException(
						"You must choose an adress or create a new one.");
			}
			adress = sendCommand.getSelectedAdress();
		}

		if (sendCommand.getAddNewAdress()) {
			if (sendCommand.getSelectedCountryForNewAdress() == null)
				throw new AdressDoesNotExistException(
						"You must choose a country.");

			if (sendCommand.getSelectedCountyForNewAdress() == null)
				throw new AdressDoesNotExistException(
						"You must choose a county.");

			if (sendCommand.getSelectedLocalityForNewAdress() == null)
				throw new AdressDoesNotExistException(
						"You must choose a locality.");

			adress = new Adress();
			adress.setBlock(sendCommand.getBlock());
			adress.setFlat(sendCommand.getFlat());
			adress.setNumber(sendCommand.getNumber());
			adress.setStaircase(sendCommand.getStaircase());
			adress.setStreet(sendCommand.getStreet());
			
			Client client = (Client) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("client");
			adress.setClient(client);

			boolean ok = false;
			for (Locality l : sendCommand.getLocalitysForNewAdress()) {
				if (l.getIdLocality() == sendCommand
						.getSelectedLocalityForNewAdress()) {
					ok = true;
					adress.setLocality(l);
					break;
				}
			}

			if (!ok)
				throw new AdressDoesNotExistException(
						"Your adress is incorrect. Please try again");
		}

		if (adress == null)
			throw new AdressDoesNotExistException(
					"You must choose an adress or create a new one.");

		return adress;
	}

	private Command buildCommand(Adress adress)
			throws CommmandStatusDoesNotExistException {
		Command command = new Command();

		command.setAdress(adress);

		CommandStatus commandStatus = commandStatusService
				.getCommandStatusByName(CommandStatusEnum.IN_PROGRESS.toString());
		command.setCommand_status(commandStatus);
		
		command.setOrderDate(new Date());
		
		Client client = (Client) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("client");
		command.setClient(client);

		return command;
	}
	
	private List<ClientProduct> buildClientProducts(Command command){
		List<ClientProduct> clientProducts = new ArrayList<ClientProduct>();
		
		Iterator<Map.Entry<ProductColorSize, Long>> it = cart.entrySet()
				.iterator();

		while (it.hasNext()) {
			Map.Entry<ProductColorSize, Long> ob = it.next();

			ProductColorSize pcs = ob.getKey();

			ClientProduct clientProduct = new ClientProduct();
			clientProduct.setCommand(command);
			clientProduct.setNrPieces(ob.getValue());
			clientProduct.setPercRedution(pcs.getProductcolor().getProduct().getReduction());
			clientProduct.setPrice(pcs.getProductcolor().getProduct().getPrice());
			clientProduct.setProduct(pcs);
			
			clientProducts.add(clientProduct);
		}
		
		return clientProducts;
	}

	public void sendCommand() {
		try {
			verifStock();

			Adress adress = buildAdress();

			Command command = buildCommand(adress);
			
			List<ClientProduct> clientProducts = buildClientProducts(command);
			
			clientProductService.persistClientProducts(clientProducts);
//			for(ClientProduct cp:clientProducts)
//				System.out.println(cp);

		} catch (NotEnoughProductsInStockException e) {
			for (int i = 0; i < e.getMessages().size(); i++) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Sorry", e
								.getMessages().get(i)));
			}
			return;
		} catch (AdressDoesNotExistException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e
							.getMessage()));
			return;
		} catch (CommmandStatusDoesNotExistException e) {
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Error",
									"For the moment we have a problem. Please try again later"));
			e.printStackTrace();
			return;
		}

		cart.clear();
		setProducts();
	}
}
