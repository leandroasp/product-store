package netprecision.util;

import java.util.List;

import netprecision.domain.Purchase;
import netprecision.domain.PurchaseProduct;

public class PurchaseCreator {

	public static Purchase createPurchaseToBeSaved() {
		return new Purchase(null, "Purchase Test", null);
	}

	public static Purchase createValidPurchase() {
		PurchaseProduct purchaseProduct = new PurchaseProduct(1, null, ProductCreator.createValidProduct(), 1);

		Purchase purchase = new Purchase(1, "Purchase Test", List.of(purchaseProduct));

		purchaseProduct.setPurchase(purchase);

		return purchase;
	}
}
