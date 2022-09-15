package mercury.inventoryms.antiCorruptionLayer;

import java.util.Set;
import java.util.Collections;

import mercury.inventoryms.antiCorruptionLayer.sharedModel.ProductSimple;
import mercury.inventoryms.antiCorruptionLayer.sharedModel.PartSimple;

import mercury.inventoryms.domain.entity.Part;

public class ProductACL {

  public ProductSimple toSimpleProduct(
    Long id,
    String name,
    double price,
    String description,
    Set<Part> parts
  ) {
    Set<PartSimple> simpleParts = Collections.emptySet();
    for (Part part : parts) {
        simpleParts.add(toSimplePart(part));
    }
    return new ProductSimple(id, name, price, description, simpleParts);
  }

  public PartSimple toSimplePart(Part part) {
    return new PartSimple(part.getId(), part.getPartName(), part.getPartDescription());
  }
}
