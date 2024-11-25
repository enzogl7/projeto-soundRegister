package com.ogl.soundregister.controller;

import com.ogl.soundregister.model.artista.Artista;
import com.ogl.soundregister.model.artista.Genero;
import com.ogl.soundregister.model.artista.TipoArtista;
import com.ogl.soundregister.service.ArtistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ArtistaController {
    @Autowired
    private ArtistaService artistaService;

    @GetMapping("/registrar")
    public String registrar() {
        return "registro/registro_artistas";
    }

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
}
