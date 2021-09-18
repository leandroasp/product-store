package netprecision.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import netprecision.domain.Product;
import netprecision.repository.ProductRepository;
import netprecision.service.exception.ObjectNotFoundException;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;

	public Product findById(Integer id) {
		Optional<Product> obj = productRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Product " + id + " does not exist"));
	}
}
