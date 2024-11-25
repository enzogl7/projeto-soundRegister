package com.ogl.soundregister.controller;

import com.ogl.soundregister.service.ConsultaChatGpt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PesquisaController {

    private final ConsultaChatGpt consultaChatGpt;

    public PesquisaController(ConsultaChatGpt consultaChatGpt) {
        this.consultaChatGpt = consultaChatGpt;
    }

    // leva a página de pesquisa de artistas
    @GetMapping("/pesquisar")
    public String pesquisar() {
        return "pesquisa/pesquisar_artistas";
    }

    @PostMapping("/pesquisa")
    public String realizarPesquisa(@RequestParam("nomeArtista") String nomeArtista, Model model) {
        String resposta = consultaChatGpt.obterInformacao(nomeArtista);
        model.addAttribute("resposta", resposta);
        return "pesquisa/pesquisar_artistas";
    }


}
