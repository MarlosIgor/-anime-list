package br.com.api.animes.controller;

import br.com.api.animes.model.Anime;
import br.com.api.animes.model.requests.AnimePostRequestBody;
import br.com.api.animes.model.requests.AnimePutRequestBody;
import br.com.api.animes.service.AnimeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("animes")
@Log4j2
@RequiredArgsConstructor
public class AnimeController {

    private final AnimeService animeService;

    @GetMapping
    @Operation(summary = "List all animes oaginated", description = "The default size is 20, use the parameter size to change the default value",
            tags = {"anime"})
    public ResponseEntity<Page<Anime>> list(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(animeService.listAll(pageable));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Anime>> listAll() {
        return ResponseEntity.ok(animeService.listAllNonPageable());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Anime> findById(@PathVariable Long id) {
        return ResponseEntity.ok(animeService.findById(id));
    }

    @GetMapping("/by-id/{id}")
    public ResponseEntity<Anime> findByIdAuthenticationPricipal(@PathVariable Long id,
                                                                @AuthenticationPrincipal UserDetails userDetails) {
        log.info(userDetails);
        return ResponseEntity.ok(animeService.findById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Anime>> findByName(@RequestParam("name") String name) {
        return ResponseEntity.ok(animeService.findByName(name));
    }

    @PostMapping("/admin")
    public ResponseEntity<Anime> save(@Valid @RequestBody AnimePostRequestBody animePostRequestBody) {
        return new ResponseEntity<>(animeService.save(animePostRequestBody), HttpStatus.CREATED);
    }

    @PutMapping("/admin")
    public ResponseEntity<Anime> replace(@Valid @RequestBody AnimePutRequestBody animePutRequestBody) {
        return new ResponseEntity<>(animeService.replace(animePutRequestBody), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/admin/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "When Anime Does Not Exist in The Database"),
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        animeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
