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

    // salva as alterações (salvamentos ou edições)
    public Musica salvarMusica(Musica musica) {
        return musicaRepository.save(musica);
    }

    // lista as músicas salvas no banco de dados
    public List<Musica> listarMusicas(){
        return musicaRepository.findAll();
    }

    // busca as músicas por um artista específico
    public List<Musica> buscarPorArtista(Artista artista) {
        return musicaRepository.findByArtista(artista);
    }

    // busca sem optional, utilizado para as edições que é certeza que terá um artista.
    public Musica buscarPorId(Long id) {
        return musicaRepository.findById(id).orElse(null);
    }


    // deletar informações de um artista já salvo
    public void deletarMusica(Long musicaIdDelete) {
        musicaRepository.deleteById(musicaIdDelete);
    }
}

