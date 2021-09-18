package netprecision.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PurchaseProductDTO {

	private Integer purchase_id;

	private Integer product_id;

	private Integer quantity;
}
