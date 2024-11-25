package com.ogl.soundregister.repository;

import com.ogl.soundregister.model.musica.Musica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicaRepository extends JpaRepository<Musica, Long> {
}
