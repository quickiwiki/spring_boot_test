package long_polling;

import org.apache.catalina.connector.ClientAbortException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.function.Tuple2;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.Duration;
import java.util.stream.Stream;

@Controller
public class SpringApplicationBController
{
    static final String URL_DATA = "http://localhost:8080";
    static final String CHART = "chart";
    static final String CHART_ROUTE = "/" + CHART;
    static final String CHART_PAGE = CHART + ".html";
    //public static final List<SseEmitter> emitters = Collections.synchronizedList(new ArrayList<>());
    
    @RequestMapping(CHART_ROUTE)
    public String chart()
    {
    	return CHART_PAGE;
    }

    @GetMapping(produces=MediaType.TEXT_EVENT_STREAM_VALUE, value=CHART_ROUTE + "/data")
    Flux<DataItem> data()
    {
        WebClient client = WebClient.create(URL_DATA);
        Flux<DataItem> flux = client.get().uri("/data")
            .accept(MediaType.TEXT_EVENT_STREAM).exchange().flatMapMany(cr -> cr.bodyToFlux(DataItem.class));// .subscribe(System.out::println);

        flux.subscribe(System.out::println);

        Flux<Long> durationFlux = Flux.interval(Duration.ofSeconds(1));
        return Flux.zip(flux, durationFlux).map(Tuple2::getT1);
    }

    /*@GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE, value=CHART_ROUTE + "/data")
    public SseEmitter chartData(HttpSession session)
    {
        /*ClientHttpConnector httpConnector = new ReactorClientHttpConnector();
        ClientRequest<Void> request = ClientRequest.GET("http://localhost:8080/location")
                .accept(MediaType.TEXT_PLAIN).build();
        WebClient.create(httpConnector)
                .exchange(request)
                .flatMap(cr -> cr.bodyToFlux(String.class))
                .doOnNext(System.out::println).collectList().block();
        /*ClientHttpConnector httpConnector = new ReactorClientHttpConnector();
        ClientRequest<Flux<DataItem>> request = ClientRequest.POST(URL_DATA)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromPublisher(getPayload(), LatLng.class));
        LOG.info("Sending request");
        WebClient.create(httpConnector)
                .exchange(request)
                .doOnNext(cr -> LOG.info("Received {}", cr.statusCode())).block();
        LOG.info("Received response");
		//RestTemplate restTemplate = new RestTemplate();
 
        // Send request with GET method and default Headers.
        //String result = restTemplate.getForObject(URL_DATA, String.class);
        //ResponseEntity<String> response = restTemplate.getForEntity(URL_DATA, String.class);
		SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

        Thread t1 = new Thread(() -> 
        {
            try 
            {
                while(true)
                {
					Thread.sleep(1000);
					System.out.println("Sending");
                    try
                    {
                        emitter.send(new DataItem(0.0, 0.0));
						//emitter.send(new DataItem(result.getX(), result.getY()));
                    }
                    catch(ClientAbortException cae)
                    {
						cae.printStackTrace();
					}
				}

			}
			catch (IOException | InterruptedException e)
            {
                emitter.complete();
				e.printStackTrace();
			}
        });
        
		t1.start();
		
		return emitter;
	}*/
}