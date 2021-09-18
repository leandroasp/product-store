package netprecision.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Product {
	@Id
	private Integer id;

	@NotEmpty(message = "The NAME is required.")
	@Length(min = 3, max = 50, message = "The NAME must be between 3 and 50 characters.")
	private String name;

	@NotNull(message = "The PRICE is required.")
	private Double price;
}
