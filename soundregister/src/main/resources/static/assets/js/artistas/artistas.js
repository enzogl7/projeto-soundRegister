function filtrarTabela() {
    var artistaFilter = document.getElementById("artistaInput").value.toUpperCase();
    var generoFilter = document.getElementById("generoSelect").value.toUpperCase();
    var rows = document.getElementById("tabelaArtistas").getElementsByTagName("tr");

    if (!artistaFilter && !generoFilter) {
        for (var i = 0; i < rows.length; i++) {
            rows[i].style.display = "";
        }
        return;
    }

    for (var i = 0; i < rows.length; i++) {
        var columns = rows[i].getElementsByTagName("td");
        var nomeArtista = columns[0].textContent || columns[0].innerText;
        var generoArtista = columns[2].textContent || columns[2].innerText;

        var match = true;

        if (artistaFilter && nomeArtista.toUpperCase().indexOf(artistaFilter) === -1) {
            match = false;
        }

        if (generoFilter && generoArtista.toUpperCase().indexOf(generoFilter) === -1) {
            match = false;
        }

        if (match) {
            rows[i].style.display = "";
        } else {
            rows[i].style.display = "none";
        }
    }
}

document.getElementById("artistaInput").addEventListener("input", filtrarTabela);
document.getElementById("generoSelect").addEventListener("change", filtrarTabela);

window.onload = function() {
    filtrarTabela();
};

function abrirModalEdicaoArtista(button) {
    var artistaId = button.getAttribute('data-id');
    $('#editarArtistaModal').modal('show')
    document.getElementById('artistaId').value = artistaId;
}

function salvarEdicaoArtista() {
    var artistaId = document.getElementById('artistaId').value;
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

function modalDeletarArtista(button) {
    var artistaIdDelete = button.getAttribute('data-id');
    $('#deletarArtistaModal').modal('show')
    document.getElementById('artistaIdDelete').value = artistaIdDelete;
}

function deletarArtista() {
    var artistaIdDelete = document.getElementById('artistaIdDelete').value;
    $.ajax({
        url: '/artistas/deletar',
        type: 'POST',
        data: {
            artistaId: artistaIdDelete
        },
        success: function (response) {
            alert('Artista deletado com sucesso!');
            window.location.reload();
        },
        error: function (error) {
            console.error('Erro ao deletar artista:', error);
            alert('Não foi possível deletar o artista. Tente novamente.');
        }
    });
}

