package long_polling;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import static org.springframework.web.reactive.function.client.WebClient.*;

@SpringBootApplication
public class SpringApplicationB
{
    @Bean
    WebClient client()
    {
        return create("http://localhost:8080");
    }

    @Bean
    CommandLineRunner demo(WebClient client)
    {
        return args -> {
            //client.get().uri("/data")
            //.accept(MediaType.TEXT_EVENT_STREAM).exchange().flatMapMany(cr -> cr.bodyToFlux(DataItem.class)).subscribe(System.out::println);
//TEXT_EVENT_STREAM
        };

    }

    public static void main(String[] args) 
    {
        new SpringApplicationBuilder(SpringApplicationB.class)
        .properties(Collections.singletonMap("server.port", "8081"))
        .run(args);
    }
}