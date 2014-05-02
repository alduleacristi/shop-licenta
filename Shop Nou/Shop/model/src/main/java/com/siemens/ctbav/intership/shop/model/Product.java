package com.siemens.ctbav.intership.shop.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.apache.solr.analysis.LowerCaseFilterFactory;
import org.apache.solr.analysis.NGramFilterFactory;
import org.apache.solr.analysis.StandardTokenizerFactory;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.AnalyzerDefs;
import org.hibernate.search.annotations.ClassBridge;
import org.hibernate.search.annotations.ClassBridges;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

import com.siemens.ctbav.intership.shop.util.client.ProductCategoryIndex;

@NamedQueries({
		@NamedQuery(name = Product.GET_PRODUCTS_BY_CATEGORY, query = "SELECT p FROM Product p where p.category.id = :id"),
		@NamedQuery(name = Product.GET_REDUCE_PRODUCTS, query = "SELECT p FROM Product p WHERE p.reduction > 0"),
		@NamedQuery(name = Product.GET_PRODUCT_BY_ID, query = "SELECT p FROM Product p WHERE p.id = :id"),
		@NamedQuery(name = Product.GET_PRODUCTS_BY_RAND, query = "SELECT p from Product p order by rand()"),
		@NamedQuery(name = Product.GET_PRODUCT_BY_FIELDS, query = "SELECT p FROM Product p where p.name = :name and p.description = :description and p.price = :price and p.category.id = :idCategory and p.reduction = :reduction") })
@NamedNativeQueries({ @NamedNativeQuery(name = Product.GET_GENERIC_PRODUCTS_FROM_CATEGORY, query = "CALL generic_products_child_categories(:param)", resultClass = Product.class) })
@Indexed()
@ClassBridges({
// @ClassBridge(name = "category", impl = CategoryIndex.class, params =
// @Parameter(name = "sepChar", value = "/"), analyzer = @Analyzer(definition =
// "categorySearchAnalyzer")),
@ClassBridge(name = "productCategory", impl = ProductCategoryIndex.class, params = @Parameter(name = "sepChar", value = "/"), analyzer = @Analyzer(definition = "productSearchAnalyzer")) })
@AnalyzerDefs({
		@AnalyzerDef(name = "productSearchAnalyzer", filters = {
				@TokenFilterDef(factory = LowerCaseFilterFactory.class),
				@TokenFilterDef(factory = NGramFilterFactory.class, params = {
						@Parameter(name = "maxGramSize", value = "10"),
						@Parameter(name = "minGramSize", value = "3") }), }, tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class)),
		@AnalyzerDef(name = "categorySearchAnalyzer", tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class), filters = {
				@TokenFilterDef(factory = LowerCaseFilterFactory.class),
				@TokenFilterDef(factory = NGramFilterFactory.class, params = {
						@Parameter(name = "maxGramSize", value = "10"),
						@Parameter(name = "minGramSize", value = "3") }), })

})
@Entity
public class Product implements Serializable {

	private static final long serialVersionUID = 7012681729962896743L;

	public static final String GET_PRODUCTS_BY_CATEGORY = "getProductsByCategory";
	public final static String GET_GENERIC_PRODUCTS_FROM_CATEGORY = "getGenericProductsFromCategory";
	public final static String GET_REDUCE_PRODUCTS = "get_reduce_products";
	public final static String GET_PRODUCT_BY_ID = "get_product_by_id";
	public final static String GET_PRODUCTS_BY_RAND = "getProductsRandom";
	public final static String GET_PRODUCT_BY_FIELDS = "getProductByFields";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "price")
	private Double price;

	@ManyToOne
	@JoinColumn(name = "id_category")
	private Category category;

	@OneToMany(mappedBy = "color")
	List<ProductColor> productColor;
	@Column(name = "perc_reduction")
	private Double reduction;
	
	@Column(name = "rating")
	private Integer rating;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "product")
	private List<Comment> comments;

	public Product() {
	}

	public Product(String name, String description, Double price,
			Category category) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.category = category;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		DecimalFormat df = new DecimalFormat("#.#");
		this.price = Double.parseDouble(df.format(price));
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
		DecimalFormat df = new DecimalFormat("#.#");
		this.price = Double.parseDouble(df.format(price));
	}

	public void multiplyPrice(Double multiply) {
		this.price *= multiply;
		DecimalFormat df = new DecimalFormat("#.#");
		this.price = Double.parseDouble(df.format(price));
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<ProductColor> getProductColor() {
		return productColor;
	}

	public void setProductColor(List<ProductColor> productColor) {
		this.productColor = productColor;
	}

	public Double getReduction() {
		return reduction;
	}

	public void setReduction(Double reduction) {
		this.reduction = reduction;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		return result;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description="
				+ description + ", price=" + price + ", category=" + category
				+ "]";
	}

}
