package long_polling.server1;

import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

@RestController
public class Controller 
{
    private final AtomicInteger counter = new AtomicInteger(0);

    //@GetMapping(produces=MediaType.TEXT_EVENT_STREAM_VALUE, value="/data")
    @GetMapping(produces=MediaType.APPLICATION_STREAM_JSON_VALUE, value="/data")
    Flux<DataItem> data()
    {
        Flux<DataItem> dataItemFlux = Flux.fromStream(Stream.generate(() -> new DataItem(counter.getAndIncrement() * 1.0, DataItem.random()) ));
        Flux<Long> durationFlux = Flux.interval(Duration.ofSeconds(1));
        return Flux.zip(dataItemFlux, durationFlux).map(Tuple2::getT1);
    }
}