package mercury.inventoryms.application.internal.queryservices;

import mercury.inventoryms.interfaces.rest.ProductController;
import mercury.inventoryms.interfaces.rest.transform.ProductModelAssembler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import mercury.inventoryms.domain.aggregate.Product;
import mercury.inventoryms.infrastructure.repository.ProductRepository;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ProductInventoryQueryService {
    private final ProductRepository productRepository;
    private ProductModelAssembler assembler;

    public ProductInventoryQueryService(ProductRepository productRepository, ProductModelAssembler assembler) {
        this.productRepository = productRepository;
        this.assembler = assembler;
    }

    public CollectionModel<EntityModel<Product>> all() {

        List<EntityModel<Product>> products = (List<EntityModel<Product>>) productRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(products, linkTo(methodOn(ProductController.class).all()).withSelfRel());
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product checkStock(Product product) {
        Product p = productRepository.findByName(product.getName());
        return p != null ? p : null;
    }

    public String findByProductName() {
        // String rootUrl = "http://localhost:8788/sales";

        // Product product = new Product();
        // product.setName(newSale.getProductName());
        // product.setDescription("description");
        // product.setPrice(12.34);
        // product = restTemplate.postForObject(rootUrl, product, Product.class);
        return "test";
    }
}
