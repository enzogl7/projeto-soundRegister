package com.ogl.soundregister.service;

import com.ogl.soundregister.model.artista.Artista;
import com.ogl.soundregister.repository.ArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistaService {
    @Autowired
    private ArtistaRepository artistaRepository;

    public Artista salvarArtista(Artista artista) {
        return artistaRepository.save(artista);
    }

    public List<Artista> listarTodosArtistas() {
        return artistaRepository.findAll();
    }

    public Optional<Artista> buscarArtistaPorId(String id) {
        return artistaRepository.findById(Long.valueOf(id));
    }
}
