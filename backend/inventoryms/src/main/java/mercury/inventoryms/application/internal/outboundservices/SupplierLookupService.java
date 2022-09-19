package mercury.inventoryms.application.internal.outboundservices;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class SupplierLookupService {
    
    public URI fetchSupplierURI(String supplier) {

        String param;
        RestTemplate restTemplate = new RestTemplate();
        try {
            param = URLEncoder.encode(supplier, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError("UTF-8 is unknown");
        }

        try {
            return restTemplate.getForObject("http://localhost:8787/supplierProcurement/lookup/?name={param}",
        URI.class, param);
        } catch(HttpStatusCodeException e) {
            return URI.create("error:supplierNotExist");
        }

    }

}
