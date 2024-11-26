package com.ogl.soundregister.repository;

import com.ogl.soundregister.model.artista.Artista;
import com.ogl.soundregister.model.musica.Musica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MusicaRepository extends JpaRepository<Musica, Long> {

    List<Musica> findByArtista(Artista artista);

    Optional<Musica> findMusicaByTituloAndArtista(String titulo, Artista artista);
}
