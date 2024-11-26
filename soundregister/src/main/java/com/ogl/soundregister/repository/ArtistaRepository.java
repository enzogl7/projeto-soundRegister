package com.ogl.soundregister.repository;

import com.ogl.soundregister.model.artista.Artista;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {
    Optional<Artista> findArtistaByNome(String nome);
}
