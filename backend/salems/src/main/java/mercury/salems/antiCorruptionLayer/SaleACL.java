package mercury.salems.antiCorruptionLayer;
import mercury.salems.domain.aggregate.OnlineSale;
import mercury.salems.antiCorruptionLayer.sharedModel.SaleSimple;

public class SaleACL {
    public SaleSimple toSimpleSale(OnlineSale originalSale) {
        return new SaleSimple(originalSale.getId(), originalSale.getProductName(), originalSale.getQuantity(), originalSale.getDateTime());
    }
}