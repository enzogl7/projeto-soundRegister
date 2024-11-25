 function filtrarTabela() {
    var artistaFilter = document.getElementById("artistaSelect").value.toUpperCase();
    var musicaFilter = document.getElementById("musicaInput").value.toUpperCase();
    var rows = document.getElementById("tabelaMusicas").getElementsByTagName("tr");

    if (!artistaFilter && !musicaFilter) {
    for (var i = 0; i < rows.length; i++) {
    rows[i].style.display = "";
}
    return;
}

    for (var i = 0; i < rows.length; i++) {
    var columns = rows[i].getElementsByTagName("td");
    var nomeMusica = columns[0].textContent || columns[0].innerText;
    var artista = columns[1].textContent || columns[1].innerText;

    var match = true;

    if (artistaFilter && artista.toUpperCase().indexOf(artistaFilter) === -1) {
    match = false;
}

    if (musicaFilter && nomeMusica.toUpperCase().indexOf(musicaFilter) === -1) {
    match = false;
}

    if (match) {
    rows[i].style.display = "";
} else {
    rows[i].style.display = "none";
}
}
}

    document.getElementById("artistaSelect").addEventListener("change", filtrarTabela);
    document.getElementById("musicaInput").addEventListener("input", filtrarTabela);

    window.onload = function() {
    filtrarTabela();
};

function modalEdicaoMusica(button) {
    var artistaIdEdicaoMusica = button.getAttribute('data-id');
    console.log(artistaIdEdicaoMusica)
    $('#modalEdicaoMusica').modal('show')
    document.getElementById('artistaIdEdicaoMusica').value = artistaIdEdicaoMusica;
}

 function salvarEdicaoMusica() {
     var artistaIdEdicaoMusica = document.getElementById('artistaIdEdicaoMusica').value;
     var tituloMusicaEdicao = document.getElementById('tituloMusicaEdicao').value;
     var artistaEdicao = document.getElementById('artistaEdicao').value;
     var anoLancamentoEdicao = document.getElementById('anoLancamentoEdicao').value;

     $.ajax({
         url: '/musicas/editar',
         type: 'POST',
         data: {
             artistaIdEdicaoMusica: artistaIdEdicaoMusica,
             tituloMusicaEdicao: tituloMusicaEdicao,
             artistaEdicao: artistaEdicao,
             anoLancamentoEdicao: anoLancamentoEdicao
         },
         success: function (response) {
             alert('Música atualizada com sucesso!');
             window.location.reload();
         },
         error: function (error) {
             console.error('Erro ao editar música:', error);
             alert('Não foi possível editar as informações da música. Tente novamente.');
         }
     });
 }

 function modalDeletarMusica(button) {
     var musicaIdDelete = button.getAttribute('data-id');
     $('#deletarMusicaModal').modal('show')
     document.getElementById('musicaIdDelete').value = musicaIdDelete;
 }

 function deletarMusica() {
     var musicaIdDelete = document.getElementById('musicaIdDelete').value;
     $.ajax({
         url: '/musicas/deletar',
         type: 'POST',
         data: {
             musicaIdDelete: musicaIdDelete
         },
         success: function (response) {
             alert('Música excluída com sucesso!');
             window.location.reload();
         },
         error: function (error) {
             console.error('Erro ao deletar música:', error);
             alert('Não foi possível excluir a música. Tente novamente.');
         }
     });
 }