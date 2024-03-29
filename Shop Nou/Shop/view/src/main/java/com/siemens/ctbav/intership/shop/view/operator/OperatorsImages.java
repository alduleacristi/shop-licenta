package com.siemens.ctbav.intership.shop.view.operator;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "opImages")
@ApplicationScoped
public class OperatorsImages {

	private static int nrImages = 12;
	private List<String> images;

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	@PostConstruct
	public void postConstruct() {
		System.out.println("post construct");
		images = new ArrayList<String>();
		for(int i=0; i<=nrImages; i++)
		images.add("/resources/operator/img"+i+".jpg");

	}

}
