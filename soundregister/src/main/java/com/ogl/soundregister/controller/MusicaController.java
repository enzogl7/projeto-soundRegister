package com.ogl.soundregister.controller;

import com.ogl.soundregister.model.artista.Artista;
import com.ogl.soundregister.model.musica.Musica;
import com.ogl.soundregister.service.ArtistaService;
import com.ogl.soundregister.service.MusicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class MusicaController {
    @Autowired
    private ArtistaService artistaService;

    @Autowired
    private MusicaService musicaService;

    // requisicao para a página de registro de músicas
    @GetMapping("/registrarMusicas")
    public String registrarMusicas(Model model) {
        List<Artista> artistas = artistaService.listarTodosArtistas();
        model.addAttribute("artistas", artistas);
        return "registro/registro_musicas";
    }

    // requisicao para salvar as músicas da página de registro na tabela MUSICAS
    @PostMapping("/salvar_musica")
    public ModelAndView salvarMusica(
            @RequestParam("tituloMusica")String tituloMusica,
            @RequestParam("artista")String artistaId,
            @RequestParam("anoLancamento")String anoLancamento) {

        Musica musica = new Musica();
        musica.setTitulo(tituloMusica);
        Optional<Artista> artista = artistaService.buscarArtistaPorId(artistaId);
        musica.setArtista(artista.orElse(null));
        musica.setAnoLancamento(Integer.valueOf(anoLancamento));

        musicaService.salvarMusica(musica);

        ModelAndView mv = new ModelAndView("redirect:/");
        return mv;
    }

    // lista as musicas presentes na tabela MUSICAS, juntamente com o nome do artista de cada uma delas
    @GetMapping("/listarMusicas")
    public String listarMusicas(Model model) {
        List<Musica> musicas = musicaService.listarMusicas();
        model.addAttribute("musicas", musicas);
        List<Artista> artistas = artistaService.listarTodosArtistas();
        model.addAttribute("artistas", artistas);
        return "listagem/listar_musicas";
    }

}
