package mercury.inventoryms.application.internal.outboundservices.acl;

import mercury.inventoryms.application.internal.queryservices.PartNotFoundException;
import mercury.inventoryms.domain.aggregate.*;
import mercury.inventoryms.infrastructure.repository.PartRepository;
import mercury.inventoryms.infrastructure.repository.ProductRepository;
import mercury.shareDomain.PartSchema;
import mercury.shareDomain.ProductSchema;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class ProductACL {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private PartRepository partRepository;

  public ProductSchema toProductSchema(Product product) {
    ProductSchema ps;

    // find parts from part repo and convert to part schema
    List<PartSchema> psParts = new ArrayList<PartSchema>();
    for (Part part : product.getParts()) {
        Part foundPart = partRepository.findById(part.getId()).orElseThrow(() -> new PartNotFoundException(part.getId()));
        psParts.add(toPartSchema(foundPart));
    }

    return new ProductSchema(product.getId(), product.getName(), product.getPrice(), product.getName(), product.getQuantity(), psParts);
  }

  public PartSchema toPartSchema(Part part) {
    return new PartSchema(part.getId(), part.getPartName(), part.getPartDescription(), (int)part.getQuantity(), part.getManufacturer(), part.getManufacturerURI());   
  }
}
