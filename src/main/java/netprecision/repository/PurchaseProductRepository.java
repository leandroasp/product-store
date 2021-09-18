package netprecision.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import netprecision.domain.PurchaseProduct;

@Repository
public interface PurchaseProductRepository extends JpaRepository<PurchaseProduct, Integer> {
	public Optional<PurchaseProduct> findByProduct_IdAndPurchase_Id(Integer product_id, Integer puchase_id);
}
