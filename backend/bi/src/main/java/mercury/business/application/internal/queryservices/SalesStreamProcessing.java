package mercury.business.application.internal.queryservices;

import mercury.shareDomain.events.Backlog;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.function.Function;

@Configuration
public class SalesStreamProcessing {

    public final static String BACKLOG_STATE_STORE = "Backlog-store";
    private DecimalFormat df = new DecimalFormat("0.00");
    
    @Bean
    public Function<KStream<?, Backlog>, KStream<String, Double>> process() {
        df.setRoundingMode(RoundingMode.DOWN);
        return inputStream -> {
            KTable<String, Double> brandKTable = inputStream.map((k, v) -> {
                String new_key = v.getProductName();
                return KeyValue.pair(new_key, Double.valueOf(df.format(v.getTotal())));
            }).groupByKey(Grouped.with(Serdes.String(), Serdes.Double())).reduce(Double::sum,
                    Materialized.<String, Double, KeyValueStore<Bytes, byte[]>>as(BACKLOG_STATE_STORE)
                            .withKeySerde(Serdes.String()).withValueSerde(Serdes.Double()));

            KStream<String, Double> brandQuantityStream = brandKTable.toStream()
                    .map((k, v) -> KeyValue.pair(k, Double.valueOf(df.format(v))));
            brandQuantityStream.print(Printed.<String, Double>toSysOut().withLabel("Console Output"));

            return brandQuantityStream;
        };
    }

}
