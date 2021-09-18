package netprecision.util;

import netprecision.dto.PurchaseProductDTO;

public class PurchaseProductDTOCreator {

	public static PurchaseProductDTO createValidPurchaseProductDTO() {
		return new PurchaseProductDTO(1, 1, 1);
	}
}
