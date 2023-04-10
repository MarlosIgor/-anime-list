package br.com.api.animes.repository;

import br.com.api.animes.model.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, Long> {

    List<Anime> findByNameStartingWithIgnoreCase(String name);

}
