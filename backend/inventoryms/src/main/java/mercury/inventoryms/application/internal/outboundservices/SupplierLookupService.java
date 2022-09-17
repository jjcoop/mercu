package mercury.inventoryms.application.internal.outboundservices;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class SupplierLookupService {
    
    public URI fetchSupplierURI(String supplier) {

        RestTemplate restTemplate = new RestTemplate();
        String param = URLEncoder.encode(supplier, StandardCharsets.UTF_8);

        try {
            return restTemplate.getForObject("http://localhost:8787/supplierProcurement/lookup/?name={supplier}",
        URI.class, param);
        } catch(HttpStatusCodeException e) {
            return URI.create("error:" + e.getRawStatusCode());
        }

    }

}
