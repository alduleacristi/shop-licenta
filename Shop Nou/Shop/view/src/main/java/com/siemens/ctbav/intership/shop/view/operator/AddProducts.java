package com.siemens.ctbav.intership.shop.view.operator;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.dto.operator.ClientProductDTO;
import com.siemens.ctbav.intership.shop.dto.operator.ProductColorSizeDTO;
import com.siemens.ctbav.intership.shop.exception.superadmin.ProductException;
import com.siemens.ctbav.intership.shop.service.operator.ProductService;

@ManagedBean(name = "addProducts")
@RequestScoped
public class AddProducts {

	private List<MissingProduct> productList;
	
	@EJB
	private ProductService prodService;
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void postConstruct() {
		productList = new ArrayList<MissingProduct>();

		List<ClientProductDTO> list = (List<ClientProductDTO>) (FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("productList"));
		if (list != null)
			addList(list);

		list = (List<ClientProductDTO>) (FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("stoppedOrders"));
		if (list != null)
			addList(list);
	}

	public void addList(List<ClientProductDTO> list) {
		for (ClientProductDTO pcs : list)
			if (!productList.contains(new MissingProduct(pcs.getProduct(),
					(long) 0))) {
				System.out.println(pcs.getNrPieces() + "  "
						+ pcs.getProduct().getNrPieces());
				productList.add(new MissingProduct(pcs.getProduct(), pcs
						.getNrPieces()));
			} else {
				System.out.println(pcs.getNrPieces() + "  "
						+ pcs.getProduct().getNrPieces());
				int index = productList.indexOf(new MissingProduct(pcs
						.getProduct(), (long) 0));
				if (index != -1)
					productList.get(index).setNrPieces(
							productList.get(index).getNrPieces()
									+ pcs.getNrPieces());
			}

		for (MissingProduct mp : productList) {
			Long nrPieces = mp.getProduct().getNrPieces();
			mp.setNrPieces(mp.getNrPieces() - nrPieces);
		}
	}

	public List<MissingProduct> getProductList() {
		return productList;
	}

	public void add(MissingProduct product) {
		if (product.getPiecesAdded() <= 0) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"The number of pieces must be greater than 0", ""));
			return;
		}
		
	//	product.getProduct().setNrPieces(product.getNrPieces() + product.getPiecesAdded());
		try {
			prodService.addProducts(product.getProduct());
		} catch (ProductException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							e.getMessage(), ""));
		}
	}

}
