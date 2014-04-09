package com.siemens.ctbav.intership.shop.dto.superadmin;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Cubes")
@XmlAccessorType(XmlAccessType.FIELD)
public class CubesDTO {

	@XmlElement(name = "Cube")
	private ArrayList<CubeDTO> cubes;

	public ArrayList<CubeDTO> getCubes() {
		return cubes;
	}

	public void setCubes(ArrayList<CubeDTO> cubes) {
		this.cubes = cubes;
	}
}
