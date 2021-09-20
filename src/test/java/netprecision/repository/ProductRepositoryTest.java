package netprecision.repository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import netprecision.domain.Product;
import netprecision.util.ProductCreator;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Tests for Product Repository")
class ProductRepositoryTest {

	@Autowired
	private ProductRepository productRepository;

	@Test
	@DisplayName("Save persists products when successful")
	void save_PersistProduct_WhenSuccessful() {
		Product productToBeSaved = ProductCreator.createProductToBeSaved();

		Product productSaved = this.productRepository.save(productToBeSaved);

		Assertions.assertThat(productSaved).isNotNull();

		Assertions.assertThat(productSaved.getId()).isNotNull();

		Assertions.assertThat(productSaved.getName()).isEqualTo(productToBeSaved.getName());
	}

	@Test
	@DisplayName("Find By Id returns product when successful")
	void findById_ReturnsProduct_WhenSuccessful() {
		Product productToBeSaved = ProductCreator.createProductToBeSaved();

		Product productSaved = this.productRepository.save(productToBeSaved);

		Optional<Product> productOptional = this.productRepository.findById(productSaved.getId());

		Assertions.assertThat(productOptional).isNotEmpty();

		Assertions.assertThat(productOptional.get().getId()).isEqualTo(productSaved.getId());

		Assertions.assertThat(productOptional.get().getName()).isEqualTo(productSaved.getName());
	}

	@Test
	@DisplayName("Find By Id returns empty when id not exists")
	void findById_ReturnsEmpty_WhenIdNotExists() {
		Product productToBeSaved = ProductCreator.createProductToBeSaved();

		Product productSaved = this.productRepository.save(productToBeSaved);

		Optional<Product> productOptional = this.productRepository.findById(productSaved.getId() + 10);

		Assertions.assertThat(productOptional).isEmpty();
	}

	@Test
	@DisplayName("Delete removes products when successful")
	void delete_RemovesProduct_WhenSuccessful() {
		Product productToBeSaved = ProductCreator.createProductToBeSaved();

		Product productSaved = this.productRepository.save(productToBeSaved);

		Optional<Product> productOptionalBeforeDelete = this.productRepository.findById(productSaved.getId());

		this.productRepository.delete(productSaved);

		Optional<Product> productOptionalAfterDelete = this.productRepository.findById(productSaved.getId());

		Assertions.assertThat(productOptionalBeforeDelete).isNotEmpty();

		Assertions.assertThat(productOptionalAfterDelete).isEmpty();
	}
}
