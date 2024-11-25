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
    var idArtista = button.getAttribute('data-id');
    console.log(artistaId);
    $('#editarArtistaModal').modal('show')
    document.getElementById('artistaId').value = idArtista;

}


