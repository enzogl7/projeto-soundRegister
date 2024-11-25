package com.ogl.soundregister.repository;

import com.ogl.soundregister.model.artista.Artista;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {
}
