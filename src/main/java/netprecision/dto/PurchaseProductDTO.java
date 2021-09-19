package netprecision.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PurchaseProductDTO {

	@NotNull(message = "The PURCHASE_ID is required.")
	private Integer purchase_id;

	@NotNull(message = "The PRODUCT_ID is required.")
	private Integer product_id;

	@NotNull(message = "The QUANTITY is required.")
	private Integer quantity;
}
