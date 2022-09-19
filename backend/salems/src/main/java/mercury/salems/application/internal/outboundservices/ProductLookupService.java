package mercury.salems.application.internal.outboundservices;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductLookupService {
    
    public URI fetchProductURI(String productName) {

        String param;
        try {
            param = URLEncoder.encode(productName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError("UTF-8 is unknown");
        }
        
        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.getForObject("http://localhost:8788/productInventory/lookup/?productName={param}",
        URI.class, param);
        } catch(HttpStatusCodeException e) {
            return URI.create("error:" + e.getRawStatusCode());
        }

    }

}