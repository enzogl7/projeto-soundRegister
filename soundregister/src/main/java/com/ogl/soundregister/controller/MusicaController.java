package com.ogl.soundregister.controller;

import com.ogl.soundregister.model.artista.Artista;
import com.ogl.soundregister.model.artista.Genero;
import com.ogl.soundregister.model.artista.TipoArtista;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String salvarMusica(
            @RequestParam("tituloMusica") String tituloMusica,
            @RequestParam("artista") String artistaId,
            @RequestParam("anoLancamento") String anoLancamento,
            RedirectAttributes redirectAttributes) {

        Artista artista = artistaService.buscaPorId(Long.valueOf(artistaId));

        // verifica se a musica ja existe
        if (musicaService.existeMusica(tituloMusica, artista)) {
            System.out.println("JÁ EXSITE MUSICA");
            redirectAttributes.addFlashAttribute("mensagem", "Essa música já existe para esse artista.");
            return "redirect:/";
        }

        // salva normalmente caso seja nova
        Musica musica = new Musica();
        musica.setTitulo(tituloMusica);
        musica.setArtista(artista);
        musica.setAnoLancamento(Integer.valueOf(anoLancamento));

        musicaService.salvarMusica(musica);

        redirectAttributes.addFlashAttribute("mensagemSucesso", "Música registrada com sucesso!");
        return "redirect:/";
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

    // edita a música selecionada no front a partir de seu ID, depois setando as novas informações e salvando (usa ajax no musica.js)
    @PostMapping("/musicas/editar")
        public String getArtistaById(@RequestParam("artistaIdEdicaoMusica") String artistaIdEdicaoMusica,
                                       @RequestParam("tituloMusicaEdicao") String tituloMusicaEdicao,
                                       @RequestParam("artistaEdicao") Artista artistaEdicao,
                                       @RequestParam("anoLancamentoEdicao") String anoLancamentoEdicao,
                                       RedirectAttributes redirectAttributes) {

        Musica musicaEditada = musicaService.buscarPorId(Long.valueOf(artistaIdEdicaoMusica));
        musicaEditada.setTitulo(tituloMusicaEdicao);
        musicaEditada.setArtista(artistaEdicao);
        musicaEditada.setAnoLancamento(Integer.valueOf(anoLancamentoEdicao));
        musicaService.salvarMusica(musicaEditada);


        redirectAttributes.addFlashAttribute("mensagemSucesso", "Música editada com sucesso!");
        return "redirect:/";
    }

    // deleta a música selecionada no front a partir do seu id (usa ajax no musica.js)
    @PostMapping("/musicas/deletar")
    public String deletarMusica(@RequestParam("musicaIdDelete") String musicaIdDelete,
                                      RedirectAttributes redirectAttributes) {
        musicaService.deletarMusica(Long.valueOf(musicaIdDelete));


        redirectAttributes.addFlashAttribute("mensagemSucesso", "Música excluída com sucesso!");
        return "redirect:/";
    }
}