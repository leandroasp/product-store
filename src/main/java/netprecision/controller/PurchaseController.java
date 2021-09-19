package netprecision.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import netprecision.domain.Purchase;
import netprecision.domain.PurchaseProduct;
import netprecision.dto.ProductDTO;
import netprecision.dto.PurchaseDTO;
import netprecision.dto.PurchaseProductDTO;
import netprecision.service.PurchaseService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/purchase")
@RequiredArgsConstructor
public class PurchaseController {

	private final PurchaseService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<Purchase> findById(@PathVariable Integer id) {
		Purchase obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@GetMapping(value = "/{id}/total", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getPrice(@PathVariable Integer id) {
		Double price = service.calculateTotalPrice(id);
		return ResponseEntity.ok().body("{\"price\":" + price + "}");
	}

	@GetMapping(value = "/{id}/close", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> closeOrder(@PathVariable Integer id,
			@RequestParam(value = "payment") Double payment) {
		Double change = service.closeOrder(id, payment);
		return ResponseEntity.ok().body("{\"change\":" + change + "}");
	}

	@GetMapping(value = "/calc", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> calcPrice(@RequestBody List<ProductDTO> products) {
		Double price = service.calcPrice(products);
		return ResponseEntity.ok().body("{\"price\":" + price + "}");
	}

	@PostMapping
	public ResponseEntity<PurchaseDTO> create(@Valid @RequestBody Purchase purchase) {
		purchase = this.service.create(purchase);

		PurchaseDTO purchaseDTO = new PurchaseDTO(purchase.getId(), purchase.getDescription());

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(purchase.getId())
				.toUri();
		return ResponseEntity.created(uri).body(purchaseDTO);
	}

	@PostMapping("/add")
	public ResponseEntity<Void> addProduct(@Valid @RequestBody PurchaseProductDTO obj) {
		PurchaseProduct purchaseProduct = this.service.addProduct(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/purchase/{id}")
				.buildAndExpand(purchaseProduct.getPurchase().getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping("/del")
	public ResponseEntity<Void> delProduct(@Valid @RequestBody PurchaseProductDTO obj) {
		this.service.delProduct(obj);
		return ResponseEntity.noContent().build();
	}
}
