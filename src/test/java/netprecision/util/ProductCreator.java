package netprecision.util;

import netprecision.domain.Product;

public class ProductCreator {

	public static Product createProductToBeSaved() {
		return new Product(1, "Product Test", 1.0);
	}

	public static Product createValidProduct() {
		return new Product(1, "Product Test", 1.0);
	}
}
