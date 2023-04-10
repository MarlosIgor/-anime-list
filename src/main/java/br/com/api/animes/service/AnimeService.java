package br.com.api.animes.service;

import br.com.api.animes.exception.BadRequestException;
import br.com.api.animes.mapper.AnimeMapper;
import br.com.api.animes.model.Anime;
import br.com.api.animes.repository.AnimeRepository;
import br.com.api.animes.model.requests.AnimePostRequestBody;
import br.com.api.animes.model.requests.AnimePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeRepository animeRepository;

    public Page<Anime> listAll(Pageable pageable) {
        return animeRepository.findAll(pageable);
    }

    public List<Anime> listAllNonPageable() {
        return animeRepository.findAll();
    }

    public Anime findById(Long id) {
        return animeRepository.findById(id)
                .orElseThrow(()
                        -> new BadRequestException("Anime not found"));
    }

    @Transactional
    public Anime save(AnimePostRequestBody animePostRequestBody) {
        return animeRepository.save(AnimeMapper.INSTANCE.toAnime(animePostRequestBody));
    }

    public List<Anime> findByName(String name) {
        if (name.isBlank()) {
            return Collections.emptyList();
        }
        return animeRepository.findByNameStartingWithIgnoreCase(name);
    }

    public Anime replace(AnimePutRequestBody animePutRequestBody) {
        Anime byId = findById(animePutRequestBody.getId());

        Anime anime = AnimeMapper.INSTANCE.toAnime(animePutRequestBody);
        anime.setId(byId.getId());

        return animeRepository.save(anime);
    }

    public void delete(Long id) {
        animeRepository.delete(findById(id));
    }

}
