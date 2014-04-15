package com.siemens.ctbav.intership.shop.service.superadmin.importer;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.dto.superadmin.importer.ProductDto;
import com.siemens.ctbav.intership.shop.exception.superadmin.ColorSizeProductException;
import com.siemens.ctbav.intership.shop.exception.superadmin.ProductException;
import com.siemens.ctbav.intership.shop.exception.superadmin.importer.CategoryNotExistException;
import com.siemens.ctbav.intership.shop.exception.superadmin.importer.ColorNotExistException;
import com.siemens.ctbav.intership.shop.exception.superadmin.importer.InvalidFileException;
import com.siemens.ctbav.intership.shop.exception.superadmin.importer.InvalidNumberException;
import com.siemens.ctbav.intership.shop.exception.superadmin.importer.InvalidProductException;
import com.siemens.ctbav.intership.shop.exception.superadmin.importer.InvalidProductsException;
import com.siemens.ctbav.intership.shop.exception.superadmin.importer.NotANumberException;
import com.siemens.ctbav.intership.shop.exception.superadmin.importer.SizeNotExistException;
import com.siemens.ctbav.intership.shop.internationalization.enums.EProduct;
import com.siemens.ctbav.intership.shop.internationalization.enums.superadmin.EImport;
import com.siemens.ctbav.intership.shop.model.Category;
import com.siemens.ctbav.intership.shop.model.Color;
import com.siemens.ctbav.intership.shop.model.Product;
import com.siemens.ctbav.intership.shop.model.ProductColor;
import com.siemens.ctbav.intership.shop.model.ProductColorSize;
import com.siemens.ctbav.intership.shop.model.Size;
import com.siemens.ctbav.intership.shop.service.internationalization.InternationalizationService;
import com.siemens.ctbav.intership.shop.service.superadmin.CategoryService;
import com.siemens.ctbav.intership.shop.service.superadmin.ColorProductService;
import com.siemens.ctbav.intership.shop.service.superadmin.ColorService;
import com.siemens.ctbav.intership.shop.service.superadmin.ColorSizeProductService;
import com.siemens.ctbav.intership.shop.service.superadmin.ProductService;
import com.siemens.ctbav.intership.shop.service.superadmin.SizeService;

@Stateless
public class ImportService {

	Importer importObject;

	InvalidProductsException invalidProductsException = null;

	@EJB
	private CategoryService categoryService;

	@EJB
	private ColorService colorService;

	@EJB
	private SizeService sizeService;

	@EJB
	private ProductService productService;

	@EJB
	private ColorProductService productColorService;

	@EJB
	private ColorSizeProductService productColorSizeService;

	@EJB
	private InternationalizationService internationalizationService;

	@EJB
	private InternationalizationService internationalizationProduct;

	private boolean exceptionOccured;

	@PostConstruct
	void initialize() {
		invalidProductsException = new InvalidProductsException();
		internationalizationInit();
		internationalizationProductInit();
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
					"internationalization/superadmin/messages/import/Import");
		} else {
			String language = new String("ro");
			String country = new String("RO");
			internationalizationService.setCurrentLocale(language, country,
					"internationalization/superadmin/messages/import/Import");
		}
	}

	private void internationalizationProductInit() {
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
			internationalizationProduct.setCurrentLocale(language, country,
					"internationalization/product/Product");
		} else {
			String language = new String("ro");
			String country = new String("RO");
			internationalizationProduct.setCurrentLocale(language, country,
					"internationalization/product/Product");
		}
	}

	public void importXml(InputStream input) throws InvalidProductsException,
			InvalidFileException {
		importObject = new ImportXmlProduct();

		List<ProductColorSize> validProducts = new ArrayList<ProductColorSize>();
		try {
			List<ProductDto> products = importObject.importProduct(input);
			validateProduct(products, validProducts);
			persistProduct(validProducts);
		} catch (InvalidProductsException e) {
			throw e;
		}
	}

	public void importCsv(InputStream input) throws InvalidProductsException,
			InvalidFileException {
		importObject = new ImportCsvProduct();

		List<ProductColorSize> validProduct = new ArrayList<ProductColorSize>();
		try {
			List<ProductDto> products = importObject.importProduct(input);
			validateProduct(products, validProduct);
			persistProduct(validProduct);
		} catch (InvalidProductsException e) {
			throw e;
		}
	}

	private void validateProduct(List<ProductDto> products,
			List<ProductColorSize> finalProduct)
			throws InvalidProductsException {
		// System.out.println("validateProduct");
		invalidProductsException.clearExceptions();
		for (ProductDto p : products) {
			// System.out.println(p);
			ProductColorSize product = new ProductColorSize();
			InvalidProductException invalidProductException = defineException(p);
			exceptionOccured = false;

			validate(finalProduct, p, product, invalidProductException);
		}

		if (invalidProductsException.getExceptions().size() > 0)
			throw invalidProductsException;
	}

	private InvalidProductException defineException(ProductDto p) {
		InvalidProductException invalidProductException = new InvalidProductException(
				internationalizationService
						.getMessage(EImport.EXCEPTION_AT_PRODUCT.getName())
						+ ": \n"
						+ internationalizationProduct
								.getMessage(EProduct.PRODUCT_NAME.getName())
						+ ": "
						+ p.getName()
						+ ", "
						+ internationalizationProduct
								.getMessage(EProduct.DESCRIPTION.getName())
						+ ": "
						+ p.getDescription()
						+ ", "
						+ internationalizationProduct.getMessage(EProduct.PRICE
								.getName())
						+ ": "
						+ p.getPrice()
						+ ", "
						+ internationalizationProduct.getMessage(EProduct.SALE
								.getName())
						+ ": "
						+ p.getPercReduction()
						+ "%, "
						+ internationalizationProduct
								.getMessage(EProduct.CATEGORY.getName())
						+ ": "
						+ p.getCategory()
						+ ", "
						+ internationalizationProduct.getMessage(EProduct.COLOR
								.getName())
						+ ": "
						+ p.getColor()
						+ ", "
						+ internationalizationProduct.getMessage(EProduct.SIZE
								.getName())
						+ ": "
						+ p.getSize()
						+ ", "
						+ internationalizationProduct
								.getMessage(EProduct.NR_PIECES.getName())
						+ ": " + p.getNrPieces());
		return invalidProductException;
	}

	private void validate(List<ProductColorSize> finalProduct, ProductDto p,
			ProductColorSize product,
			InvalidProductException invalidProductException) {
		Category category = getCategory(p, invalidProductException);
		Double price = null;
		if (!exceptionOccured)
			price = getPrice(p, invalidProductException);
		Double reduction = null;
		if (!exceptionOccured)
			reduction = getReduction(p, invalidProductException, reduction);
		Product prod = null;
		if (!exceptionOccured)
			prod = getProduct(p, invalidProductException, category, price,
					reduction);
		Color color = null;
		if (!exceptionOccured)
			color = getColor(p, invalidProductException);
		ProductColor prodCol = null;
		if (!exceptionOccured)
			prodCol = getProductColor(prod, color);
		if (!exceptionOccured)
			if (prodCol != null) {
				product.setProductcolor(prodCol);
			}
		if (!exceptionOccured)
			setSize(p, product, invalidProductException);
		if (!exceptionOccured)
			setNrPcs(p, product, invalidProductException);

		if (!exceptionOccured) {
			// System.out.println("DA");
			finalProduct.add(product);
		} else {
			invalidProductsException.addException(invalidProductException);
		}
	}

	private void setNrPcs(ProductDto p, ProductColorSize product,
			InvalidProductException invalidProductException) {
		try {
			product.setNrOfPieces(verifyNrBuc(p.getNrPieces()));
		} catch (NotANumberException e1) {
			invalidProductException.addCause(e1);
			exceptionOccured = true;
		} catch (InvalidNumberException e2) {
			invalidProductException.addCause(e2);
			exceptionOccured = true;
		}
	}

	private void setSize(ProductDto p, ProductColorSize product,
			InvalidProductException invalidProductException) {
		try {
			product.setSize(verifySize(p.getSize()));
		} catch (NotANumberException e1) {
			invalidProductException.addCause(e1);
			exceptionOccured = true;
		} catch (SizeNotExistException e2) {
			invalidProductException.addCause(e2);
			exceptionOccured = true;
		}
	}

	private ProductColor getProductColor(Product prod, Color color) {
		ProductColor prodCol = null;
		if (!exceptionOccured) {
			// System.out.println("get product color");
			prodCol = productColorService.getProductColorByFields(prod, color);
			if (prodCol == null) {
				// System.out.println("add product color");
				productColorService.addProductColor(prod, color);
				prodCol = productColorService.getProductColorByFields(prod,
						color);
			}
		}
		return prodCol;
	}

	private Color getColor(ProductDto p,
			InvalidProductException invalidProductException) {
		Color color = null;
		try {
			color = verifyColor(p.getColor());
		} catch (NotANumberException e) {
			invalidProductException.addCause(e);
			exceptionOccured = true;
		} catch (ColorNotExistException e) {
			invalidProductException.addCause(e);
			exceptionOccured = true;
		}
		return color;
	}

	private Product getProduct(ProductDto p,
			InvalidProductException invalidProductException, Category category,
			Double price, Double reduction) {
		Product prod = null;
		if (!exceptionOccured) {
			prod = productService.getProductByFields(p.getName(),
					p.getDescription(), price, category.getId(), reduction);
		}
		if (prod == null) {
			try {
				// System.out.println("create product");
				productService.createProduct(p.getName(), p.getDescription(),
						price, category.getId(), reduction);
				prod = productService.getProductByFields(p.getName(),
						p.getDescription(), price, category.getId(), reduction);
			} catch (ProductException e) {
				invalidProductException.addCause(e);
				exceptionOccured = true;
			}
		}
		// System.out.println(prod);
		return prod;
	}

	private Double getReduction(ProductDto p,
			InvalidProductException invalidProductException, Double reduction) {
		try {
			reduction = verifyReduction(p.getPercReduction());
		} catch (NotANumberException e) {
			invalidProductException.addCause(e);
			exceptionOccured = true;
		} catch (InvalidNumberException e) {
			invalidProductException.addCause(e);
			exceptionOccured = true;
		}
		return reduction;
	}

	private Double getPrice(ProductDto p,
			InvalidProductException invalidProductException) {
		Double price = null;
		try {
			price = verifyPrice(p.getPrice());
		} catch (NotANumberException e) {
			invalidProductException.addCause(e);
			exceptionOccured = true;
		} catch (InvalidNumberException e) {
			invalidProductException.addCause(e);
			exceptionOccured = true;
		}
		return price;
	}

	private Category getCategory(ProductDto p,
			InvalidProductException invalidProductException) {
		Category category = null;
		try {
			category = verifyCategory(p.getCategory());
		} catch (NotANumberException e) {
			invalidProductException.addCause(e);
			exceptionOccured = true;
		} catch (CategoryNotExistException e) {
			invalidProductException.addCause(e);
			exceptionOccured = true;
		}
		return category;
	}

	private Category verifyCategory(String scategory)
			throws NotANumberException, CategoryNotExistException {
		Long idCategory;
		try {
			idCategory = Long.parseLong(scategory);
		} catch (NumberFormatException e) {
			throw new NotANumberException(
					internationalizationService
							.getMessage(EImport.CATEGORY_NOT_A_NUMBER.getName()));
		}

		Category category = categoryService.getCategory(idCategory);
		if (category != null)
			return category;
		else {
			throw new CategoryNotExistException(
					internationalizationService
							.getMessage(EImport.CATEGORY_DOES_NOT_EXISTS
									.getName()));
		}
	}

	private Size verifySize(String sSize) throws NotANumberException,
			SizeNotExistException {
		Long idSize;
		try {
			idSize = Long.parseLong(sSize);
		} catch (NumberFormatException e) {
			throw new NotANumberException(
					internationalizationService
							.getMessage(EImport.SIZE_IS_NOT_A_NUMBER.getName()));
		}

		Size size = sizeService.getSize(idSize);
		if (size != null)
			return size;
		else {
			throw new SizeNotExistException(
					internationalizationService
							.getMessage(EImport.SIZE_DOES_NOT_EXIST.getName()));
		}
	}

	private Color verifyColor(String sColor) throws NotANumberException,
			ColorNotExistException {
		Long idColor;
		try {
			idColor = Long.parseLong(sColor);
		} catch (NumberFormatException e) {
			throw new NotANumberException(
					internationalizationService
							.getMessage(EImport.COLOR_IS_NOT_A_NUMBER.getName()));
		}

		Color color = colorService.getColorById(idColor);
		if (color != null)
			return color;
		else {
			throw new ColorNotExistException(
					internationalizationService
							.getMessage(EImport.COLOR_DOES_NOT_EXISTS.getName()));
		}
	}

	private Double verifyPrice(String sPrice) throws NotANumberException,
			InvalidNumberException {
		Double finalPrice;

		try {
			finalPrice = Double.parseDouble(sPrice);
		} catch (NumberFormatException e) {
			throw new NotANumberException(
					internationalizationService
							.getMessage(EImport.PRICE_IS_NOT_A_NUMBER.getName()));
		}

		if (finalPrice < 0)
			throw new InvalidNumberException(
					internationalizationService
							.getMessage(EImport.PRICE_MUST_BE_GRATER.getName()));

		return finalPrice;
	}

	private Double verifyReduction(String sReduction)
			throws NotANumberException, InvalidNumberException {
		Double finalReduction;

		try {
			finalReduction = Double.parseDouble(sReduction);
		} catch (NumberFormatException e) {
			throw new NotANumberException(
					internationalizationService
							.getMessage(EImport.REDUCTION_NOT_A_NUMBER
									.getName()));
		}

		if (finalReduction < 0 || finalReduction > 100) {
			throw new InvalidNumberException(
					internationalizationService
							.getMessage(EImport.REDUCTION_REQUIREMENT.getName()));
		}

		return finalReduction;
	}

	private Long verifyNrBuc(String sNrBuc) throws NotANumberException,
			InvalidNumberException {
		Long finalNrBuc;

		try {
			finalNrBuc = Long.parseLong(sNrBuc);
		} catch (NumberFormatException e) {
			throw new NotANumberException(
					internationalizationService
							.getMessage(EImport.NR_PCS_IS_NOT_A_NUMBER
									.getName()));
		}

		if (finalNrBuc < 0)
			throw new InvalidNumberException(
					internationalizationService
							.getMessage(EImport.NR_PCS_MUST_BE_GRATER.getName()));

		return finalNrBuc;
	}

	private void persistProduct(List<ProductColorSize> products) {
		for (ProductColorSize product : products) {
			ProductColorSize existentProduct = productColorSizeService
					.getProductByFields(product.getProductcolor().getProduct()
							.getName(), product.getProductcolor().getProduct()
							.getDescription(), product.getProductcolor()
							.getProduct().getPrice(), product.getProductcolor()
							.getProduct().getCategory().getId(), product
							.getSize().getId(), product.getProductcolor()
							.getColor().getId());
			if (existentProduct != null) {
				// System.out.println("nu e null adaug bucati");
				try {
					productColorSizeService.addPieces(existentProduct.getId(),
							product.getNrOfPieces());
				} catch (ColorSizeProductException e) {
					System.out.println(e);
				}
			} else {
				// System.out.println("e nou si adaugam");
				productColorSizeService.addProductColorSize(product);
			}
		}
	}

}
