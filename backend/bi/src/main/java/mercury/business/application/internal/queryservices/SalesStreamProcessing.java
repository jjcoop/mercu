package mercury.business.application.internal.queryservices;

import mercury.business.model.SalesIntel;
import mercury.shareDomain.events.Backlog;
import mercury.shareDomain.events.Backorder;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Configuration
public class SalesStreamProcessing {

    public final static String BACKLOG_STATE_STORE = "Backlog-store";
    public final static String SALESINTEL_STATE_STORE = "SalesIntel-store";

    // @Bean
    // public Function<KStream<?, Backlog>, KStream<String, SalesIntel>> process() {
    //     return inputStream -> {

    //         inputStream.map((k, v) -> {
    //             Double total = v.getTotal();
    //             Long id = v.getSaleID();
    //             SalesIntel saleIntel = new SalesIntel(id, total);
    //             String new_key = brand_name + equipment_name;
    //             return KeyValue.pair(new_key, equipment);
    //         }).toTable(
    //                 Materialized.<String, Equipment, KeyValueStore<Bytes, byte[]>>as(BACKLOG_STATE_STORE).
    //                         withKeySerde(Serdes.String()).
    //                         // a custom value serde for this state store
    //                         withValueSerde(equipmentSerde())
    //         );

    //         KTable<String, Long> brandKTable = inputStream.
    //                 mapValues(Appliance::getBrand).
    //                 groupBy((keyIgnored, value) -> value).
    //                 count(
    //                         Materialized.<String, Long, KeyValueStore<Bytes, byte[]>>as(SALESINTEL_STATE_STORE).
    //                                 withKeySerde(Serdes.String()).
    //                                 withValueSerde(Serdes.Long())
    //                 );

    //         KStream<String, BrandQuantity> brandQuantityStream = brandKTable.
    //                 toStream().
    //                 map((k, v) -> KeyValue.pair(k, new BrandQuantity(k, v)));
    //         // use the following code for testing
    //         brandQuantityStream.print(Printed.<String, BrandQuantity>toSysOut().withLabel("Console Output"));

    //         return brandQuantityStream;
    //     };
    // }
                    

    // Can compare the following configuration properties with those defined in application.yml
    public Serde<Backorder> backorderSerde() {
        final JsonSerde<Backorder> backorderJsonSerde = new JsonSerde<>();
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "mercury.shareDomain.events.Backorder");
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        backorderJsonSerde.configure(configProps, false);      
        return backorderJsonSerde;
    // 
    }

    public Serde<Backlog> backlogSerde() {
        final JsonSerde<Backlog> backlogJsonSerde = new JsonSerde<>();
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "mercury.shareDomain.events.Backlog");
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        backlogJsonSerde.configure(configProps, false);
        return backlogJsonSerde;
    }    
}

