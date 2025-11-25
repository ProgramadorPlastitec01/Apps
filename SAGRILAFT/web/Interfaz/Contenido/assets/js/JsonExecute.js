function searchDocuments() {
    const value = document.getElementById("idtemplateshr").value;
    if (value === "") {
        document.getElementById("formatResults").innerHTML = "<h3>Mensaje</h3>";
        return;
    }

    fetch("DinamicExecute?opt=1&" + encodeURIComponent(value))
            .then(resp => resp.json())
            .then(data => {

                let html = "<h3>Resultados:</h3><ul>";

                data.forEach(item => {
                    html += "<li><b>Id: </b>" + item.id +
                            " | <b>nombre: </b>" + item.nombre +
                            " | <b>Descripcion: </b>" + item.descripcion + "</li>";
                });

                html += "</ul>";

                document.getElementById("formatResults").innerHTML = html;
            })
            .catch(err => console.error("Error: ", err));

}
