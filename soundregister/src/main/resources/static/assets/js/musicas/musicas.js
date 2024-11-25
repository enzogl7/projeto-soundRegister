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
     var nomeArtista = document.getElementById('nomeArtista').value;
     var genero = document.getElementById('genero').value;
     var tipoArtista = document.getElementById('tipoArtista').value;

     $.ajax({
         url: '/artistas/editar',
         type: 'POST',
         data: {
             artistaId: artistaId,
             nomeArtista: nomeArtista,
             genero: genero,
             tipoArtista: tipoArtista
         },
         success: function (response) {
             alert('Artista atualizado com sucesso!');
             window.location.reload();
         },
         error: function (error) {
             console.error('Erro ao editar artista:', error);
             alert('Não foi possível editar o artista. Tente novamente.');
         }
     });
 }