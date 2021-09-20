package netprecision.service;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import netprecision.domain.Purchase;
import netprecision.domain.PurchaseProduct;
import netprecision.repository.PurchaseProductRepository;
import netprecision.repository.PurchaseRepository;
import netprecision.util.ProductCreator;
import netprecision.util.ProductDTOCreator;
import netprecision.util.PurchaseCreator;
import netprecision.util.PurchaseProductCreator;
import netprecision.util.PurchaseProductDTOCreator;

@SpringBootTest
@ActiveProfiles("test")
class PurchaseServiceTest {

	@InjectMocks
	private PurchaseService purchaseService;

	@Mock
	private PurchaseRepository purchaseRepositoryMock;
	@Mock
	private PurchaseProductRepository purchaseProductRepositoryMock;
	@Mock
	private ProductService productServiceMock;

	@BeforeEach
	void setUp() {
		BDDMockito.when(purchaseRepositoryMock.findById(ArgumentMatchers.anyInt()))
				.thenReturn(Optional.of(PurchaseCreator.createValidPurchase()));

		BDDMockito.when(purchaseRepositoryMock.save(ArgumentMatchers.any(Purchase.class)))
				.thenReturn(PurchaseCreator.createValidPurchase());

		BDDMockito.when(productServiceMock.findById(ArgumentMatchers.anyInt()))
				.thenReturn(ProductCreator.createValidProduct());

		BDDMockito.when(purchaseProductRepositoryMock.save(ArgumentMatchers.any(PurchaseProduct.class)))
				.thenReturn(PurchaseProductCreator.createValidPurchaseProduct());

		BDDMockito
				.when(purchaseProductRepositoryMock.findByProduct_IdAndPurchase_Id(ArgumentMatchers.anyInt(),
						ArgumentMatchers.anyInt()))
				.thenReturn(Optional.of(PurchaseProductCreator.createValidPurchaseProduct()));

		BDDMockito.doNothing().when(purchaseProductRepositoryMock).deleteById(ArgumentMatchers.anyInt());
	}

	@Test
	@DisplayName("findById return a purchase when successful")
	void findById_ReturnPurchase_WhenSuccessful() {
		Purchase expectedPurchase = PurchaseCreator.createValidPurchase();

		Purchase purchase = purchaseService.findById(1);

		Assertions.assertThat(purchase).isNotNull();

		Assertions.assertThat(purchase.getId()).isEqualTo(expectedPurchase.getId());

		Assertions.assertThat(purchase.getDescription()).isEqualTo(expectedPurchase.getDescription());
	}

	@Test
	@DisplayName("calculateTotalPrice return price when successful")
	void calculateTotalPrice_ReturnPrice_WhenSuccessful() {
		Double price = purchaseService.calculateTotalPrice(1);

		Assertions.assertThat(price).isNotNull();

		Assertions.assertThat(price).isEqualTo(1.0);
	}

	@Test
	@DisplayName("closeOrder return change when successful")
	void closeOrder_ReturnChange_WhenSuccessful() {
		Double change = purchaseService.closeOrder(1, 2.0);

		Assertions.assertThat(change).isNotNull();

		Assertions.assertThat(change).isEqualTo(1.0);
	}

	@Test
	@DisplayName("calcPrice return a json with price when successful")
	void calcPrice_ReturnPrice_WhenSuccessful() {
		Double price = purchaseService.calcPrice(List.of(ProductDTOCreator.createValidProductDTO()));

		Assertions.assertThat(price).isNotNull();

		Assertions.assertThat(price).isEqualTo(1.0);
	}

	@Test
	@DisplayName("create return a Purchase when successful")
	void create_ReturnPurchase_WhenSuccessful() {
		Purchase purchaseToBeSaved = PurchaseCreator.createPurchaseToBeSaved();
		Purchase purchaseSaved = purchaseService.create(purchaseToBeSaved);

		Assertions.assertThat(purchaseSaved).isNotNull();

		Assertions.assertThat(purchaseSaved.getId()).isNotNull();

		Assertions.assertThat(purchaseSaved.getDescription()).isEqualTo(purchaseToBeSaved.getDescription());
	}

	@Test
	@DisplayName("addProduct return a PurchaseProduct when successful")
	void addProduct_ReturnPurchaseProduct_WhenSuccessful() {
		BDDMockito
			.when(purchaseProductRepositoryMock.findByProduct_IdAndPurchase_Id(ArgumentMatchers.anyInt(),
					ArgumentMatchers.anyInt()))
			.thenReturn(Optional.empty());

		PurchaseProduct purchaseProduct = purchaseService
				.addProduct(PurchaseProductDTOCreator.createValidPurchaseProductDTO());

		Assertions.assertThat(purchaseProduct).isNotNull();

		Assertions.assertThat(purchaseProduct.getProduct()).isNotNull();

		Assertions.assertThat(purchaseProduct.getQuantity())
				.isEqualTo(PurchaseProductCreator.createValidPurchaseProduct().getQuantity());
	}

	@Test
	@DisplayName("delProduct does not throw any exception when successful")
	void delProduct_DoesNotThrowAnyException_WhenSuccessful() {
		purchaseService.delProduct(PurchaseProductDTOCreator.createValidPurchaseProductDTO());

		Assertions
				.assertThatCode(
						() -> purchaseService.delProduct(PurchaseProductDTOCreator.createValidPurchaseProductDTO()))
				.doesNotThrowAnyException();

	}
}
