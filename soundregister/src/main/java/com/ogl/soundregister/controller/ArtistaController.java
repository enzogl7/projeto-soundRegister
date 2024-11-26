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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    // salva o artista e suas informações na tabela ARTISTA do banco de dados
    @PostMapping("/salvar_artista")
    public String salvarArtista(
            @RequestParam("nomeArtista") String nomeArtista,
            @RequestParam("genero") Genero genero,
            @RequestParam("tipoArtista") TipoArtista tipoArtista,
            RedirectAttributes redirectAttributes) {

        // verifica se o artista já existe
        if (artistaService.existeArtista(nomeArtista)) {
            redirectAttributes.addFlashAttribute("mensagem", "Esse artista já está registrado.");
            return "redirect:/";
        }

        // salva o artista normalmente caso seja novo
        Artista artista = new Artista();
        artista.setNome(nomeArtista);
        artista.setGenero(genero);
        artista.setTipo(tipoArtista);

        artistaService.salvarArtista(artista);

        redirectAttributes.addFlashAttribute("mensagemSucesso", "Artista registrado com sucesso!");
        return "redirect:/";
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

    // edita o artsita selecionado no front a partir de seu ID, depois setando as novas informações e salvando (usa ajax no artista.js)
    @PostMapping("/artistas/editar")
    public String getArtistaById(@RequestParam("artistaId") Long artistaId,
                                                  @RequestParam("nomeArtista") String nomeArtista,
                                                  @RequestParam("genero") String genero,
                                                  @RequestParam("tipoArtista") String tipoArtista,
                                                    RedirectAttributes redirectAttributes) {
        Artista artista = artistaService.buscaPorId(artistaId);
        artista.setNome(nomeArtista);
        artista.setGenero(Genero.valueOf(genero));
        artista.setTipo(TipoArtista.valueOf(tipoArtista));
        artistaService.salvarArtista(artista);

        redirectAttributes.addFlashAttribute("mensagemSucesso", "Artista editado com sucesso!");
        return "redirect:/";
    }

    // deleta o artista selecionado no front a partir do seu id (usa ajax no artista.js)
    @PostMapping("/artistas/deletar")
    public String deletarArtista(@RequestParam("artistaId") String artistaId, RedirectAttributes redirectAttributes) {
        artistaService.deletarArtista(Long.valueOf(artistaId));

        redirectAttributes.addFlashAttribute("mensagemSucesso", "Artista excluído com sucesso!");
        return "redirect:/";
    }
}
