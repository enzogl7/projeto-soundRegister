package com.ogl.soundregister.service;

import com.ogl.soundregister.model.artista.Artista;
import com.ogl.soundregister.repository.ArtistaRepository;
import org.hibernate.sql.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistaService {
    @Autowired
    private ArtistaRepository artistaRepository;

    // guarda as informações (edição ou salvamento)
    public Artista salvarArtista(Artista artista) {
        return artistaRepository.save(artista);
    }

    // lista todos os artistas do banco de dados
    public List<Artista> listarTodosArtistas() {
        return artistaRepository.findAll(Sort.by(Sort.Order.asc("nome")));
    }


    // busca artistas especificos atraves de seus IDs
    public Optional<Artista> buscarArtistaPorId(String id) {
        return artistaRepository.findById(Long.valueOf(id));
    }


    // busca sem optional (utilizando quando é certeza que existe)
    public Artista buscaPorId(Long id) {
        return artistaRepository.findById(id).orElse(null);
    }

    // deleta as informações de um artista específico
    public void deletarArtista(Long artistaId) {
        artistaRepository.deleteById(artistaId);
    }
}
