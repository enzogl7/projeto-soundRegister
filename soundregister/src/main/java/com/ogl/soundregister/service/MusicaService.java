package com.ogl.soundregister.service;

import com.ogl.soundregister.model.musica.Musica;
import com.ogl.soundregister.repository.MusicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MusicaService {
    @Autowired
    private MusicaRepository musicaRepository;

    public Musica salvarMusica(Musica musica) {
        return musicaRepository.save(musica);
    }


}

