package com.ogl.soundregister.controller;

import com.ogl.soundregister.model.artista.Artista;
import com.ogl.soundregister.model.artista.Genero;
import com.ogl.soundregister.model.artista.TipoArtista;
import com.ogl.soundregister.model.musica.Musica;
import com.ogl.soundregister.service.ArtistaService;
import com.ogl.soundregister.service.MusicaService;
import org.hibernate.sql.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class ArtistaController {
    @Autowired
    private ArtistaService artistaService;

    @Autowired
    private MusicaService musicaService;

    // leva a página de registro de artistas
    @GetMapping("/registrar")
    public String registrar() {
        return "registro/registro_artistas";
    }

    // salva o artistas e suas informações na tabela ARTISTA do banco de dados
    @PostMapping("/salvar_artista")
    public ModelAndView salvarArtista(
            @RequestParam("nomeArtista") String nomeArtista,
            @RequestParam("genero") Genero genero,
            @RequestParam("tipoArtista") TipoArtista tipoArtista) {

        Artista artista = new Artista();
        artista.setNome(nomeArtista);
        artista.setGenero(genero);
        artista.setTipo(tipoArtista);

        artistaService.salvarArtista(artista);

        ModelAndView mv = new ModelAndView("redirect:/");
        mv.addObject("mensagem", "Artista cadastrado com sucesso!");
        return mv;
    }

    // requisição para listar os artistas na página /listarArtistas
    @GetMapping("/listarArtistas")
    public String listarArtistas(Model model) {
        List<Artista> artistas = artistaService.listarTodosArtistas(); // lista todos os artistas
        Map<Artista, String> musicasPorArtista = new HashMap<>(); // cria um map para poder exibir as músicas de cada artistas (relacionados na tabela musicas a partir de seus respectivos IDs)

        // for que a cada artista da lista ARTISTAS criada previamente ele busca suas músicas na tabela MUSICAS e as adiciona na lista musicas. Depois as concatena para separar por vírgula para o usuário final
        for (Artista artista : artistas) {
            List<Musica> musicas = musicaService.buscarPorArtista(artista);
            String musicasConcatenadas = musicas.isEmpty()
                    ? "----" // caso o artista nao tenha musicas registradas, aparece "----"
                    : musicas.stream()
                    .map(Musica::getTitulo)// pega o titulo de cada musica
                    .collect(Collectors.joining(", ")); // separa as músicas por virgula em caso de mais de uma registrada
            musicasPorArtista.put(artista, musicasConcatenadas); // adiciona no map que vai para o model/thymeleaf
        }
        // adiciona o model para ser usado no thymeleaf
        model.addAttribute("musicasPorArtista", musicasPorArtista);

        return "listagem/listar_artistas";
    }

    @PostMapping("/artistas/editar")
    public ModelAndView getArtistaById(@RequestParam("artistaId") Long artistaId,
                                                  @RequestParam("nomeArtista") String nomeArtista,
                                                  @RequestParam("genero") String genero,
                                                  @RequestParam("tipoArtista") String tipoArtista) {
        Artista artista = artistaService.buscaPorId(artistaId);
        artista.setNome(nomeArtista);
        artista.setGenero(Genero.valueOf(genero));
        artista.setTipo(TipoArtista.valueOf(tipoArtista));
        artistaService.salvarArtista(artista);
        ModelAndView mv = new ModelAndView("redirect:/");
        mv.addObject("mensagem", "Artista editado com sucesso!");
        return mv;
    }

    @PostMapping("/artistas/deletar")
    public ModelAndView deletarArtista(@RequestParam("artistaId") String artistaId) {
        artistaService.deletarArtista(Long.valueOf(artistaId));
        ModelAndView mv = new ModelAndView("redirect:/");
        mv.addObject("mensagem", "Artista deletado com sucesso!");
        return mv;
    }
}
