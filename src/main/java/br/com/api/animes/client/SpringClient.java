package br.com.api.animes.client;

import br.com.api.animes.model.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

// Fazer uma requisição para outros serviços ou URL externa
@Log4j2
public class SpringClient {
    public static void main(String[] args) {

        ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8080/animes/{id}",
                Anime.class, 2);
        log.info(entity);

        Anime object = new RestTemplate().getForObject("http://localhost:8080/animes/{id}",
                Anime.class, 1);
        log.info(object);

        Anime[] animes = new RestTemplate().getForObject("http://localhost:8080/animes/all",
                Anime[].class);
        log.info(Arrays.toString(animes));

        ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange("http://localhost:8080/animes/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        log.info(exchange.getBody());

//        Anime dgz = Anime.builder().name("Dragon Ball Z").build();
//        Anime dgzSaved = new RestTemplate().postForObject("http://localhost:8080/animes/",
//                dgz, Anime.class);
//        log.info("saved anime {}", dgzSaved);

        Anime dgs = Anime.builder().name("Dragon Ball Super").build();
        ResponseEntity<Anime> dgsSaved = new RestTemplate().exchange("http://localhost:8080/animes/",
                HttpMethod.POST,
                new HttpEntity<>(dgs),
                Anime.class);
        log.info("saved anime {}, {}", dgsSaved, createJsonHeader());

        Anime animeToBeUpdated = dgsSaved.getBody();
        animeToBeUpdated.setName("Dragon Ball Super 2");
        ResponseEntity<Anime> dgsUpdated = new RestTemplate().exchange("http://localhost:8080/animes/",
                HttpMethod.PUT,
                new HttpEntity<>(animeToBeUpdated, createJsonHeader()),
                Anime.class);
        log.info(animeToBeUpdated);

        ResponseEntity<Void> dgsDeleted = new RestTemplate().exchange("http://localhost:8080/animes/{id}",
                HttpMethod.DELETE,
                null,
                Void.class,
                animeToBeUpdated.getId());
        log.info(dgsDeleted);


    }

    private static HttpHeaders createJsonHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }

}
