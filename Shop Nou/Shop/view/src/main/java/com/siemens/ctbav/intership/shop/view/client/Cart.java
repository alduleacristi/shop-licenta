package com.siemens.ctbav.intership.shop.view.client;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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
import com.siemens.ctbav.intership.shop.exception.client.ProductColorSizeDoesNotExistException;
import com.siemens.ctbav.intership.shop.model.Adress;
import com.siemens.ctbav.intership.shop.model.Client;
import com.siemens.ctbav.intership.shop.model.ProductColorSize;
import com.siemens.ctbav.intership.shop.service.client.ProductColorSizeService;
import com.siemens.ctbav.intership.shop.util.client.CookieEncryption;

@SessionScoped
@ManagedBean(name = "cart")
@URLMappings(mappings = {
		@URLMapping(id = "cartClient", pattern = "/client/user/Cart", viewId = "/client/user/cart.xhtml"),
		@URLMapping(id = "sendCommand", pattern = "/client/user/sendCommand", viewId = "/client/user/sendCommand.xhtml") })
public class Cart implements Serializable {
	private static final long serialVersionUID = -4660863521509380343L;

	@EJB
	ProductColorSizeService productColorSizeService;

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

	public void sendCommand() {

		System.out.println("in send command");

		cart.clear();
		setProducts();
	}
}
