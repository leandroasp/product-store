package netprecision.controller;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import netprecision.domain.Purchase;
import netprecision.dto.PurchaseProductDTO;
import netprecision.service.PurchaseService;
import netprecision.util.ProductDTOCreator;
import netprecision.util.PurchaseCreator;
import netprecision.util.PurchaseProductCreator;
import netprecision.util.PurchaseProductDTOCreator;

@SpringBootTest
class PurchaseControllerTest {

	@InjectMocks
	private PurchaseController purchaseController;

	@Mock
	private PurchaseService purchaseServiceMock;

	@BeforeEach
	void setUp() {
		BDDMockito.when(purchaseServiceMock.findById(ArgumentMatchers.anyInt()))
				.thenReturn(PurchaseCreator.createValidPurchase());

		BDDMockito.when(purchaseServiceMock.calculateTotalPrice(ArgumentMatchers.anyInt())).thenReturn(1.0);

		BDDMockito.when(purchaseServiceMock.closeOrder(ArgumentMatchers.anyInt(), ArgumentMatchers.anyDouble()))
				.thenReturn(1.0);

		BDDMockito.when(purchaseServiceMock.calcPrice(ArgumentMatchers.anyList())).thenReturn(1.0);

		BDDMockito.when(purchaseServiceMock.create(ArgumentMatchers.any(Purchase.class)))
				.thenReturn(PurchaseCreator.createValidPurchase());

		BDDMockito.when(purchaseServiceMock.addProduct(ArgumentMatchers.any(PurchaseProductDTO.class)))
				.thenReturn(PurchaseProductCreator.createValidPurchaseProduct());

		BDDMockito.doNothing().when(purchaseServiceMock).delProduct(ArgumentMatchers.any(PurchaseProductDTO.class));
	}

	@Test
	@DisplayName("findById return a purchase when successful")
	void findById_ReturnPurchase_WhenSuccessful() {
		Purchase expectedPurchase = PurchaseCreator.createValidPurchase();

		Purchase purchase = purchaseController.findById(1).getBody();

		Assertions.assertThat(purchase).isNotNull();

		Assertions.assertThat(purchase.getId()).isEqualTo(expectedPurchase.getId());

		Assertions.assertThat(purchase.getDescription()).isEqualTo(expectedPurchase.getDescription());
	}

	@Test
	@DisplayName("getPrice return a json with price when successful")
	void getPrice_ReturnPrice_WhenSuccessful() {
		String price = purchaseController.getPrice(1).getBody();

		Assertions.assertThat(price).isNotNull().isNotEmpty();

		Assertions.assertThat(price).isEqualTo("{\"price\":1.0}");
	}

	@Test
	@DisplayName("closeOrder return a json with change when successful")
	void closeOrder_ReturnChange_WhenSuccessful() {
		String change = purchaseController.closeOrder(1, 2.0).getBody();

		Assertions.assertThat(change).isNotNull().isNotEmpty();

		Assertions.assertThat(change).isEqualTo("{\"change\":1.0}");
	}

	@Test
	@DisplayName("calcPrice return a json with price when successful")
	void calcPrice_ReturnPrice_WhenSuccessful() {
		String price = purchaseController.calcPrice(List.of(ProductDTOCreator.createValidProductDTO())).getBody();

		Assertions.assertThat(price).isNotNull().isNotEmpty();

		Assertions.assertThat(price).isEqualTo("{\"price\":1.0}");
	}

	@Test
	@DisplayName("create return a http status CREATED when successful")
	void create_ReturnCreated_WhenSuccessful() {
		ResponseEntity<Void> request = purchaseController.create(PurchaseCreator.createPurchaseToBeSaved());
		String location = request.getHeaders().getLocation().getPath();

		Assertions.assertThat(request).isNotNull();

		Assertions.assertThat(request.getBody()).isNull();

		Assertions.assertThat(location).isEqualTo("/" + PurchaseCreator.createValidPurchase().getId());

		Assertions.assertThat(request.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}

	@Test
	@DisplayName("addProduct return a http status CREATED when successful")
	void addProduct_ReturnCreated_WhenSuccessful() {
		ResponseEntity<Void> request = purchaseController
				.addProduct(PurchaseProductDTOCreator.createValidPurchaseProductDTO());
		String location = request.getHeaders().getLocation().getPath();

		Assertions.assertThat(request).isNotNull();

		Assertions.assertThat(request.getBody()).isNull();

		Assertions.assertThat(location)
				.isEqualTo("/purchase/" + PurchaseProductCreator.createValidPurchaseProduct().getId());

		Assertions.assertThat(request.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}

	@Test
	@DisplayName("delProduct return a http status NO_CONTENT when successful")
	void delProduct_ReturnCreated_WhenSuccessful() {
		ResponseEntity<Void> request = purchaseController
				.delProduct(PurchaseProductDTOCreator.createValidPurchaseProductDTO());

		Assertions.assertThat(request).isNotNull();

		Assertions.assertThat(request.getBody()).isNull();

		Assertions.assertThat(request.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}
}
