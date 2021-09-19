package netprecision.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import netprecision.domain.Product;
import netprecision.domain.Purchase;
import netprecision.domain.PurchaseProduct;
import netprecision.dto.ProductDTO;
import netprecision.dto.PurchaseProductDTO;
import netprecision.repository.PurchaseProductRepository;
import netprecision.repository.PurchaseRepository;
import netprecision.service.exception.DataIntegrityViolationException;
import netprecision.service.exception.ObjectNotFoundException;

@Service
@RequiredArgsConstructor
public class PurchaseService {

	private final PurchaseRepository purchaseRepository;
	private final PurchaseProductRepository purchaseProductRepository;

	private final ProductService productService;

	public Purchase findById(Integer id) {
		Optional<Purchase> obj = purchaseRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Order " + id + " does not exist"));
	}

	public Purchase create(Purchase obj) {
		return purchaseRepository.save(obj);
	}

	public @Valid PurchaseProduct addProduct(@Valid PurchaseProductDTO obj) {
		Optional<PurchaseProduct> purchaseProductOpt = purchaseProductRepository
				.findByProduct_IdAndPurchase_Id(obj.getProduct_id(), obj.getPurchase_id());
		
		if (!purchaseProductOpt.isEmpty()) {
			throw new DataIntegrityViolationException(
					"Product " + obj.getProduct_id() + " already exists in order " + obj.getPurchase_id());
		}

		Purchase purchase = findById(obj.getPurchase_id());

		Product product = productService.findById(obj.getProduct_id());
		
		PurchaseProduct purchaseProduct = new PurchaseProduct(null, purchase, product, obj.getQuantity());

		return purchaseProductRepository.save(purchaseProduct);
	}

	public void delProduct(@Valid PurchaseProductDTO obj) {
		this.findById(obj.getPurchase_id());

		productService.findById(obj.getProduct_id());

		Optional<PurchaseProduct> purchaseProduct = purchaseProductRepository
				.findByProduct_IdAndPurchase_Id(obj.getProduct_id(), obj.getPurchase_id());

		purchaseProduct.orElseThrow(() -> new ObjectNotFoundException(
				"Product " + obj.getProduct_id() + " does not exist in the order " + obj.getPurchase_id()));

		if (obj.getQuantity() != purchaseProduct.get().getQuantity()) {
			throw new DataIntegrityViolationException("The quantity is different from the one registered");
		}

		purchaseProductRepository.deleteById(purchaseProduct.get().getId());
	}

	public Double calculateTotalPrice(Integer id) {
		Purchase purchase = this.findById(id);

		double total = 0;
		for (int i = 0; i < purchase.getProducts().size(); i++) {
			PurchaseProduct product = purchase.getProducts().get(i);
			total += product.getProduct().getPrice() * product.getQuantity();
		}

		return total;
	}

	public Double closeOrder(Integer id, Double payment) {
		Double total = calculateTotalPrice(id);
		Double change = payment - total;

		if (change < 0) {
			throw new DataIntegrityViolationException("The payment amount is not enough");
		}

		return change;
	}

	public Double calcPrice(List<ProductDTO> products) {
		double total = 0;
		for (int i = 0; i < products.size(); i++) {
			Product product = productService.findById(products.get(i).getId());
			total += product.getPrice() * products.get(i).getQuantity();
		}

		return total;
	}
}
