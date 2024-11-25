package com.ogl.soundregister.service;

import com.ogl.soundregister.model.artista.Artista;
import com.ogl.soundregister.model.musica.Musica;
import com.ogl.soundregister.repository.MusicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicaService {
    @Autowired
    private MusicaRepository musicaRepository;

    public Musica salvarMusica(Musica musica) {
        return musicaRepository.save(musica);
    }

    public List<Musica> listarMusicas(){
        return musicaRepository.findAll();
    }

    public List<Musica> buscarPorArtista(Artista artista) {
        return musicaRepository.findByArtista(artista);
    }

}

