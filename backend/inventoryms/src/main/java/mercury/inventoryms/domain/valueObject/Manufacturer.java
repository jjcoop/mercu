package mercury.inventoryms.domain.valueObject;

import java.net.URI;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Manufacturer {
    @Column(name = "SUPPLIER", unique = false, nullable = false, length = 100)
    private String value;
    @Column(name = "SUPPLIER_URI", unique = false, nullable = false, length = 100)
    private URI uri = URI.create("");


    Manufacturer(){}

    public Manufacturer(String value) {
        this.value = value;
    }

    public Manufacturer(String uri, String value) {
        this.uri = URI.create(uri);
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public URI getURI(){
        return this.uri;
    }

}
