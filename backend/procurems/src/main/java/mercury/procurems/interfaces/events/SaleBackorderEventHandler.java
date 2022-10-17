package mercury.procurems.interfaces.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import mercury.shareDomain.events.Backorder;
import java.util.function.Consumer;

@Component
public class SaleBackorderEventHandler {
	private static final Logger log = LoggerFactory.getLogger(SaleBackorderEventHandler.class);

	@Bean
	public Consumer<Backorder> input() {
		return i -> log.info(i.toString());
	}
}