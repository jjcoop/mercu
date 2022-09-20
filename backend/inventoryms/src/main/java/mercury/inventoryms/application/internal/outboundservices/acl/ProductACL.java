package mercury.inventoryms.application.internal.outboundservices.acl;

import java.util.ArrayList;
import java.util.List;
import mercury.inventoryms.application.internal.queryservices.PartNotFoundException;
import mercury.inventoryms.domain.aggregate.*;
import mercury.inventoryms.infrastructure.repository.PartRepository;
import mercury.shareDomain.PartSchema;
import mercury.shareDomain.ProductSchema;

public class ProductACL {
  private PartRepository partRepository;

  public ProductACL() {}

  public ProductACL(PartRepository partRepository) {
    this.partRepository = partRepository;
  }

  public ProductSchema toProductSchema(Product product) {
    // find parts from part repo and convert to part schema
    List<PartSchema> psParts = new ArrayList<PartSchema>();

    List<Long> partIds = new ArrayList<Long>();

    for (Part part : product.getParts()) {
      partIds.add(part.getId());
    }

    List<Part> parts = partRepository.findAll();

    for (Part p : parts) {
      for (Long par : partIds) {
        if (p.getId() == par) {
          Part foundPart = partRepository
            .findById(p.getId())
            .orElseThrow(() -> new PartNotFoundException(p.getId()));
          psParts.add(toPartSchema(foundPart));
        }
      }
    }

    return new ProductSchema(
      product.getId(),
      product.getName(),
      product.getPrice(),
      product.getDescription(),
      product.getQuantity(),
      psParts
    );
  }

  public PartSchema toPartSchema(Part part) {
    return new PartSchema(
      part.getId(),
      part.getPartName(),
      part.getPartDescription(),
      (int) part.getQuantity(),
      part.getManufacturer(),
      part.getManufacturerURI()
    );
  }
}
