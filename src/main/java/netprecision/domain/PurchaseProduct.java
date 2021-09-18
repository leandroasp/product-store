package netprecision.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class PurchaseProduct {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "purchase_id")
	@NotNull(message = "The PURCHASE is required.")
	private Purchase purchase;

	@ManyToOne
	@JoinColumn(name = "product_id")
	@NotNull(message = "The PRODUCTS are required.")
	private Product product;

	@NotNull(message = "The QUANTITY OF PRODUCTS is required.")
	private Integer quantity;
}
