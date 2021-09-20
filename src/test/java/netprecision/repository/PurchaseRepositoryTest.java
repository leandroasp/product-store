package netprecision.repository;

import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import netprecision.domain.Purchase;
import netprecision.util.PurchaseCreator;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Tests for Purchase Repository")
class PurchaseRepositoryTest {

	@Autowired
	private PurchaseRepository purchaseRepository;

	@Test
	@DisplayName("Save persists purchases when successful")
	void save_PersistPurchase_WhenSuccessful() {
		Purchase purchaseToBeSaved = PurchaseCreator.createPurchaseToBeSaved();

		Purchase purchaseSaved = this.purchaseRepository.save(purchaseToBeSaved);

		Assertions.assertThat(purchaseSaved).isNotNull();

		Assertions.assertThat(purchaseSaved.getId()).isNotNull();

		Assertions.assertThat(purchaseSaved.getDescription()).isEqualTo(purchaseToBeSaved.getDescription());
	}

	@Test
	@DisplayName("Find By Id returns purchase when successful")
	void findById_ReturnsPurchase_WhenSuccessful() {
		Purchase purchaseToBeSaved = PurchaseCreator.createPurchaseToBeSaved();

		Purchase purchaseSaved = this.purchaseRepository.save(purchaseToBeSaved);

		Optional<Purchase> purchaseOptional = this.purchaseRepository.findById(purchaseSaved.getId());

		Assertions.assertThat(purchaseOptional).isNotEmpty();

		Assertions.assertThat(purchaseOptional.get().getId()).isEqualTo(purchaseSaved.getId());

		Assertions.assertThat(purchaseOptional.get().getDescription()).isEqualTo(purchaseSaved.getDescription());
	}

	@Test
	@DisplayName("Find By Id returns empty when id not exists")
	void findById_ReturnsEmpty_WhenIdNotExists() {
		Purchase purchaseToBeSaved = PurchaseCreator.createPurchaseToBeSaved();

		Purchase purchaseSaved = this.purchaseRepository.save(purchaseToBeSaved);

		Optional<Purchase> purchaseOptional = this.purchaseRepository.findById(purchaseSaved.getId() + 10);

		Assertions.assertThat(purchaseOptional).isEmpty();
	}

	@Test
	@DisplayName("Delete removes purchases when successful")
	void delete_RemovesPurchase_WhenSuccessful() {
		Purchase purchaseToBeSaved = PurchaseCreator.createPurchaseToBeSaved();

		Purchase purchaseSaved = this.purchaseRepository.save(purchaseToBeSaved);

		Optional<Purchase> purchaseOptionalBeforeDelete = this.purchaseRepository.findById(purchaseSaved.getId());

		this.purchaseRepository.delete(purchaseSaved);

		Optional<Purchase> purchaseOptionalAfterDelete = this.purchaseRepository.findById(purchaseSaved.getId());

		Assertions.assertThat(purchaseOptionalBeforeDelete).isNotEmpty();

		Assertions.assertThat(purchaseOptionalAfterDelete).isEmpty();
	}

	@Test
	@DisplayName("Save throws ConstraintViolationException when description is empty")
	void save_ThrowsConstraintViolationException_WhenNameIsEmpty() {
		Purchase purchaseToBeSaved = new Purchase();

		Assertions.assertThatThrownBy(() -> this.purchaseRepository.save(purchaseToBeSaved))
				.isInstanceOf(ConstraintViolationException.class).hasMessageContaining("The DESCRIPTION is required");
	}
}
