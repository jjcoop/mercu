package mercury.inventoryms.application.internal.queryservices;

import mercury.inventoryms.interfaces.rest.ProductController;
import mercury.inventoryms.interfaces.rest.transform.PartModelAssembler;
import mercury.inventoryms.interfaces.rest.transform.ProductModelAssembler;
import mercury.shareDomain.AvailabilityReply;
import mercury.shareDomain.ProductTemplate;
import mercury.shareDomain.PartTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import mercury.inventoryms.domain.aggregate.Product;
import mercury.inventoryms.domain.entity.Part;
import mercury.inventoryms.infrastructure.repository.PartRepository;
import mercury.inventoryms.infrastructure.repository.ProductRepository;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ProductInventoryQueryService {
    private final ProductRepository productRepository;
    private PartRepository partRepository;
    private ProductModelAssembler assembler;
    private PartModelAssembler partAssembler;


    public ProductInventoryQueryService(ProductRepository productRepository, PartRepository partRepository, ProductModelAssembler assembler, PartModelAssembler partAssembler) {
        this.productRepository = productRepository;
        this.partRepository = partRepository;
        this.assembler = assembler;
        this.partAssembler = partAssembler;
    }

    public CollectionModel<EntityModel<Product>> all() {

        List<EntityModel<Product>> products = (List<EntityModel<Product>>) productRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(products, linkTo(methodOn(ProductController.class).getProducts()).withSelfRel());
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    // public Product checkProductStock(Product product) {
    //     Product p = productRepository.findByName(product.getName());
    //     return p != null ? p : null;
    // }

    public String findByProductName() {
        // String rootUrl = "http://localhost:8788/sales";

        // Product product = new Product();
        // product.setName(newSale.getProductName());
        // product.setDescription("description");
        // product.setPrice(12.34);
        // product = restTemplate.postForObject(rootUrl, product, Product.class);
        return "test";
    }

    public CollectionModel<EntityModel<Part>> allParts() {
        List<EntityModel<Part>> parts = (List<EntityModel<Part>>) partRepository.findAll().stream()
        .map(partAssembler::toModel)
        .collect(Collectors.toList());

        return CollectionModel.of(parts, linkTo(methodOn(ProductController.class).getParts()).withSelfRel());
    }

    public Part findPartById(Long id) {
        return partRepository.findById(id)
        .orElseThrow(() -> new PartNotFoundException(id));
    }

    public AvailabilityReply checkProductAvailability(Long productId, int quantity) {
        System.out.println("Actually made it to availability check!");  // TODO remove later
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));;

        // check for insufficient product quantity
        if(product.getQuantity() < quantity) {
            // for each part check for insufficient parts
            for (Part part : product.getParts()) {
                // get updated part quantity
                Part foundPart = partRepository.findById(part.getId()).orElseThrow(() -> new PartNotFoundException(part.getId()));
                int partQuantitiy = foundPart.getQuantity();
                // return false if part quantity insufficient
                if(partQuantitiy < quantity)
                    return new AvailabilityReply(false);
            }
        }

        return new AvailabilityReply(true);
    }

    public ProductTemplate findTemplateById(Long id) {
        Product prod = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        //System.out.println(prod.toString());

        Set<PartTemplate> partTemps = Collections.emptySet();

        if (prod.getParts().size() > 0) {
            for (Part part : prod.getParts()) {
                Part savedPart = partRepository.findById(part.getId()).orElse(new Part());
                PartTemplate tempPart = new PartTemplate(savedPart.getId(), savedPart.getPartName(), savedPart.getPartDescription(), savedPart.getQuantity(), savedPart.getManufacturer());
                partTemps.add(tempPart);
            }
        } else {
            System.out.println("Order " + prod.getId() + " has no parts added");
        }

        ProductTemplate prodTemp = new ProductTemplate(prod.getId(), prod.getName(), prod.getPrice(), prod.getDescription(), prod.getQuantity(), partTemps);

        //System.out.println(prodTemp.toString());

        return prodTemp;
    }

    // public Part checkPartStock(Part part) {
    //     Part p = partRepository.findByPartName(part.getPartName());
    //     return p != null ? p : null;
    // }
}
