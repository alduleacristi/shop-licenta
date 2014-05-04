package com.siemens.ctbav.intership.shop.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

@Entity
@NamedQueries({
		@NamedQuery(name = CategoryName.GET_NAMES_FOR_CATEGORY, query = "SELECT c FROM CategoryName c where c.id_category = :id"),
		@NamedQuery(name = CategoryName.GET_NAME_FOR_CATEGORY_AND_LANGUAGE, query = "SELECT c FROM CategoryName c where c.id_category = :id and c.language.language = :language") })
public class CategoryName implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String GET_NAMES_FOR_CATEGORY = "getNamesForCategory";
	public static final String GET_NAME_FOR_CATEGORY_AND_LANGUAGE = "getNameForCategoryAndLanguage";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@OneToOne
	@JoinColumn(name = "language")
	private Language language;

	private Long id_category;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public Long getId_category() {
		return id_category;
	}

	public void setId_category(Long id_category) {
		this.id_category = id_category;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CategoryName other = (CategoryName) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
