package mercury.salems.antiCorruptionLayer;
import mercury.salems.domain.aggregate.OnlineSale;
import mercury.salems.antiCorruptionLayer.sharedModel.SaleTemplate;

public class SaleACL {
    public SaleTemplate toSaleTemplate(OnlineSale originalSale) {
        return new SaleTemplate(originalSale.getId(), originalSale.getProductName(), originalSale.getQuantity(), originalSale.getDateTime());
    }
}