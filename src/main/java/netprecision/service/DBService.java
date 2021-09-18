package netprecision.service;

import java.util.Arrays;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import netprecision.domain.Product;
import netprecision.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class DBService {

	private final ProductRepository productRepository;

	public void instanceDatabase() {
		Product p1 = new Product(1147, "Cachorro quente", 3.00);
		Product p2 = new Product(1154, "Bauru", 2.50);
		Product p3 = new Product(1164, "Misto quente", 2.00);
		Product p4 = new Product(1155, "X-Burger", 6.00);

		productRepository.saveAll(Arrays.asList(p1, p2, p3, p4));
	}
}
