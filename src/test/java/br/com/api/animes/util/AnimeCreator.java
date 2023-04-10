package br.com.api.animes.util;

import br.com.api.animes.model.Anime;

public class AnimeCreator {

    public static Anime createAnimeToBeSaved() {
        return Anime.builder()
                .name("DBZ")
                .build();
    }

    public static Anime createValidAnime() {
        return Anime.builder()
                .name("DBZ")
                .id(1L)
                .build();
    }

    public static Anime createValidUpdateAnime() {
        return Anime.builder()
                .name("DBZ")
                .id(1L)
                .build();
    }

}
