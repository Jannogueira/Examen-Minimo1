$(document).ready(function(){
    var urlApi = '/dsaApp/tracks';
    $.ajax({
        url: urlApi,
        method: 'GET',
        dataType: 'json',
        success: function(crearTabla){
            // Limpiar la tabla
            $('#tablaCanciones').empty();

            // Crear la tabla
            var tabla = $('<table class="table table-striped"></table>');
            var thead = $('<thead><tr><th>Index</th><th>Titulo</th><th>Cantante</th><th>Id</th></tr></thead>');
            var tbody = $('<tbody></tbody>');

            // Iterar sobre las canciones
            $.each(crearTabla, function(index, relleno){

                var fila = $('<tr></tr>');
                var filaIndex = $('<td></td>').text(index);
                var filaCantante = $('<td></td>').text(relleno.singer);
                var filaTitulo = $('<td></td>').text(relleno.title);
                var filaId = $('<td></td>').text(relleno.id);

                fila.append(filaIndex, filaTitulo, filaCantante, filaId);
                tbody.append(fila);
            });

            // Agregar la tabla completa
            tabla.append(thead, tbody);
            $('#tablaCanciones').append(tabla);
        },
        error: function(){
            $('#tablaCanciones').empty();
            $('#tablaCanciones').append('<p><b>Error en la petición</b></p>');
        }
    });
});
