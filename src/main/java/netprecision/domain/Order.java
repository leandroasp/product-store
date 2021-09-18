package netprecision.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotEmpty(message = "The DESCRIPTION field is mandatory.")
	@Length(min = 3, max = 200, message = "The DESCRIPTION field must be between 10 and 200 characters.")
	private String description;
	
	@OneToMany(mappedBy = "product")
	private List<Product> products = new ArrayList<>();

}
