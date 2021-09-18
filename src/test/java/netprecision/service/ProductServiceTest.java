package netprecision.service;

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

import netprecision.domain.Product;
import netprecision.repository.ProductRepository;
import netprecision.util.ProductCreator;

@SpringBootTest
class ProductServiceTest {

	@InjectMocks
	private ProductService productService;

	@Mock
	private ProductRepository productRepositoryMock;

	@BeforeEach
	void setUp() {
		BDDMockito.when(productRepositoryMock.findById(ArgumentMatchers.anyInt()))
				.thenReturn(Optional.of(ProductCreator.createValidProduct()));
	}

	@Test
	@DisplayName("findById return a product when successful")
	void findById_ReturnProduct_WhenSuccessful() {
		Product expectedProduct = ProductCreator.createValidProduct();

		Product product = productService.findById(1);

		Assertions.assertThat(product).isNotNull();
		
		Assertions.assertThat(product.getId()).isNotNull();

		Assertions.assertThat(product.getId()).isEqualTo(expectedProduct.getId());

		Assertions.assertThat(product.getName()).isEqualTo(expectedProduct.getName());
	}


}
