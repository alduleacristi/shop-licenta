package com.siemens.ctbav.intership.shop.view.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.siemens.ctbav.intership.shop.convert.client.ConvertProductColorSizeNumber;
import com.siemens.ctbav.intership.shop.dto.client.ProductColorSizeNumberDTO;
import com.siemens.ctbav.intership.shop.exception.client.ProductColorSizeDoesNotExistException;
import com.siemens.ctbav.intership.shop.model.Client;
import com.siemens.ctbav.intership.shop.model.ProductColorSize;
import com.siemens.ctbav.intership.shop.service.client.ProductColorSizeService;
import com.siemens.ctbav.intership.shop.util.client.CookieEncryption;

@ManagedBean(name = "cart")
@SessionScoped
@URLMappings(mappings = { @URLMapping(id = "cartClient", pattern = "/client/user/Cart", viewId = "/client/user/cart.xhtml"), })
public class Cart {
	private Map<ProductColorSize, Integer> cart;

	private Integer nrOfProducts;

	private Double totalPrice;

	private List<ProductColorSizeNumberDTO> products;

	private ProductColorSize selectedProductForDescription;

	@EJB
	ProductColorSizeService productColorSizeService;

	@PostConstruct
	private void initialize() {
		cart = new HashMap<ProductColorSize, Integer>();
		setNrOfProducts(0);
		setTotalPrice(0.0);
		products = new ArrayList<ProductColorSizeNumberDTO>();
	}

	public Integer getNrOfProducts() {
		return nrOfProducts;
	}

	public void setNrOfProducts(Integer nrOfProducts) {
		this.nrOfProducts = nrOfProducts;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void setProducts() {
		products.clear();

		Iterator<Map.Entry<ProductColorSize, Integer>> it = cart.entrySet()
				.iterator();

		while (it.hasNext()) {
			Map.Entry<ProductColorSize, Integer> ob = it.next();

			ProductColorSizeNumberDTO p = ConvertProductColorSizeNumber
					.convertToProductColorSizeNumberDTO(ob.getKey(),
							ob.getValue());
			products.add(p);
		}
	}

	public List<ProductColorSizeNumberDTO> getProducts() {
		return products;
	}

	public ProductColorSize getSelectedProductForDescription() {
		return selectedProductForDescription;
	}

	public void setSelectedProductForDescription(
			ProductColorSize selectedProductForDescription) {
		this.selectedProductForDescription = selectedProductForDescription;
	}

	public Map<ProductColorSize, Integer> getCart() {
		return cart;
	}

	public void setCart(Map<ProductColorSize, Integer> cart) {
		this.cart = cart;
	}

	public boolean isEmpty() {
		if (cart.size() == 0)
			return false;

		return true;
	}

	public void addProduct(ProductColorSize pcs, Integer nrOfProducts) {
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
			// System.out.println("exista deja");
			cart.put(pcs, cart.get(pcs) + nrOfProducts);
			setTotalPrice(getTotalPrice()
					+ nrOfProducts
					* (pcs.getProductcolor().getProduct().getPrice() - pcs
							.getProductcolor().getProduct().getPrice()
					* pcs.getProductcolor().getProduct().getReduction() / 100));
		} else {
			cart.put(pcs, nrOfProducts);
			setNrOfProducts(getNrOfProducts() + 1);
			setTotalPrice(getTotalPrice()
					+ nrOfProducts
					* (pcs.getProductcolor().getProduct().getPrice() - pcs
							.getProductcolor().getProduct().getPrice()
					* pcs.getProductcolor().getProduct().getReduction() / 100));
		}

		setProducts();
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Succes", "Products added succesfully"));
	}

	public void removeProduct(ProductColorSizeNumberDTO productDTO) {
		// System.out.println("sa apelat remove");
		ProductColorSize product = ConvertProductColorSizeNumber
				.convertToProductColorSize(productDTO);
		if (cart.containsKey(product)) {
			int nr = cart.get(product);
			cart.remove(product);
			setProducts();
			setNrOfProducts(getNrOfProducts() - 1);
			setTotalPrice(getTotalPrice()
					- product.getProductcolor().getProduct().getPrice() * nr);
			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("/Shop4j/client/user/Cart");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void populateCartFromCookie(Map<Long, Integer> cookieProducts) {
		if (cookieProducts == null)
			return;

		clearCart();

		Iterator<Map.Entry<Long, Integer>> it = cookieProducts.entrySet()
				.iterator();

		while (it.hasNext()) {
			Map.Entry<Long, Integer> ob = it.next();
			try {
				ProductColorSize product = productColorSizeService
						.getProductColorSizeById(ob.getKey());
				addProduct(product, ob.getValue());
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
		Map<Long, Integer> mapProducts = new HashMap<Long, Integer>();

		for (int i = 0; i < elements.length; i++) {
			{
				System.out.println("in for");
				String[] pairs = elements[i].split(":");
				Long idProduct = null;
				Integer numberOfPcs = null;

				if (pairs.length == 2) {
					try {

						idProduct = Long.parseLong(pairs[0]);
						System.out.println("idProduct: " + idProduct);
						numberOfPcs = Integer.parseInt(pairs[1]);
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
		setNrOfProducts(0);
		setTotalPrice(0.0);
		products.clear();
	}

	public void saveToCookie() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) facesContext
				.getExternalContext().getResponse();

		Client client = (Client) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("client");

		StringBuilder sb = new StringBuilder();
		Iterator<Map.Entry<ProductColorSize, Integer>> it = cart.entrySet()
				.iterator();

		while (it.hasNext()) {
			Map.Entry<ProductColorSize, Integer> ob = it.next();
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
				setTotalPrice(getTotalPrice() - cart.get(product)
						* product.getProductcolor().getProduct().getPrice());
				setTotalPrice(getTotalPrice()
						+ product.getProductcolor().getProduct().getPrice()
						* productDTO.getNrOfPieces());

				cart.put(product, productDTO.getNrOfPieces());
			}
		}

	}
}
