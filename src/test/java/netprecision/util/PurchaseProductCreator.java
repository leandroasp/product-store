package netprecision.util;

import netprecision.domain.PurchaseProduct;

public class PurchaseProductCreator {

	public static PurchaseProduct createValidPurchaseProduct() {
		return PurchaseCreator.createValidPurchase().getProducts().get(0);
	}
}
