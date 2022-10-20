package mercury.business.interfaces.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import mercury.shareDomain.events.Backorder;
import java.util.function.Consumer;

@Component
public class BacklogEventLogger {
	private static final Logger log = LoggerFactory.getLogger(BacklogEventLogger.class);

	@Bean
	public Consumer<Backorder> process() {
		return i -> log.info(i.toString());
	}
}