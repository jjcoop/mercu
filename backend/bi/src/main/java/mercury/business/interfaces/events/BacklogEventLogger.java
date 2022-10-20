package mercury.business.interfaces.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import mercury.shareDomain.events.Backlog;
import java.util.function.Consumer;

@Component
public class BacklogEventLogger {
	private static final Logger log = LoggerFactory.getLogger(BacklogEventLogger.class);
	public Consumer<Backlog> process() {
		return i -> log.info(i.toString());
	}
}