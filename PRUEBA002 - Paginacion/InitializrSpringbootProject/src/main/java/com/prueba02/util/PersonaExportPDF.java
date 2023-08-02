/*package com.prueba02.util;


import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.prueba02.dto.PersonaDto;
import java.awt.Color;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonaExportPDF {
    
    // LISTA DE OBJETOS QUE SE VAN A UTILIZAR
    private List<PersonaDto> listaPersonas;

    
    // DEFINIR LA CABECERA DE LA TABLA
    private void escribirCabeceraDeLaTabla(PdfPTable tabla) {
        
        // UNA CELDA
        PdfPCell celda = new PdfPCell();

        // COLOR DE FONDO
        celda.setBackgroundColor(Color.BLUE);
        
        // COLOR DE FONDO PERSONALIZADO RGB
        // celda.setBackgroundColor(new Color(255, 0, 0));
        
        // ESPACIADO
        celda.setPadding(5);

        // TIPO DE FUENTE
        Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
        
        // COLOR DE TEXTO
        fuente.setColor(Color.WHITE);

        // DEFINIR LAS CELDAS QUE VA A CONTENER LA CABECERA
        // TEXTO QUE SE VA A ESCRIBIR, ESTILO DEL TEXTO
        celda.setPhrase(new Phrase("ID", fuente));
        tabla.addCell(celda);
        celda.setPhrase(new Phrase("imagen", fuente));
        tabla.addCell(celda);
        celda.setPhrase(new Phrase("nombres", fuente));
        tabla.addCell(celda);
        celda.setPhrase(new Phrase("apellidos", fuente));
        tabla.addCell(celda);
        celda.setPhrase(new Phrase("dni", fuente));
        tabla.addCell(celda);
        celda.setPhrase(new Phrase("estado", fuente));
        tabla.addCell(celda);
        
    }

    // COMPRIMIR LA IMAGEN CON LA FINALIDAD DE REDUCIR EL TAMAÑO DEL PDF GENERADO
    private void agregarImagenComprimida(PdfPTable tabla, String rutaImagen) throws DocumentException, IOException {
        Image image = Image.getInstance(rutaImagen);

        // Reducir la calidad de la imagen (cambia el valor de 0.8 según tus necesidades)

        PdfPCell imageCell = new PdfPCell(image, true);
        tabla.addCell(imageCell);
    }
    
    // ESCRIBIR LOS DATOS QUE VA CONTENER LA TABLA (DEBAJO DE LA CABECERA)
    private void escribirDatosDeLaTabla(PdfPTable tabla) {
        
        // ITERAR SOBRE LA LISTA DE PERSONAS
        for (PersonaDto personaDto : listaPersonas) {
            tabla.addCell(String.valueOf(personaDto.getId()));

            // RUTA PARA DEFINIR LA IMAGEN
            try {
                agregarImagenComprimida(tabla, "assets/" + personaDto.getRutaImagen());

                image.scaleToFit(50, 1);
                PdfPCell imageCell = new PdfPCell(image, true);
                tabla.addCell(imageCell);


            } catch (IOException e) {
                // Celda vacía en caso de error de lectura de la imagen
                tabla.addCell("");
            }
            tabla.addCell(personaDto.getNombres());
            tabla.addCell(personaDto.getApellidos());
            tabla.addCell(String.valueOf(personaDto.getDni()));
            tabla.addCell(String.valueOf(personaDto.getEstado()));

        }

    }

    // GENERAR EL PDF...
    // DISEÑO DEL DOCUMENTO (DE ARRIBA HACIA ABAJO) QUE SE VA A EXPORTAR A PDF
    public void export(HttpServletResponse response) throws DocumentException, IOException {
        // TAMAÑO DE HOJA (PageSize) A4
        try (Document documento = new Document(PageSize.A4)) {
            PdfWriter.getInstance(documento, response.getOutputStream());
            // INICIO DEL DOCUMENTO
            documento.open();
            // TIPO DE LETRA, TAMAÑO Y COLOR
            Font fuente = FontFactory.getFont(FontFactory.TIMES_ROMAN);
            fuente.setSize(18);
            fuente.setColor(Color.RED);
            // TITULO (PARRAFO ALINEADO AL CENTRO)
            Paragraph titulo = new Paragraph("Lista", fuente);
            titulo.setAlignment(Paragraph.ALIGN_CENTER);
            documento.add(titulo);
            PdfPTable tabla = new PdfPTable(6);
            tabla.setWidthPercentage(100f);
            tabla.setWidths(new float[]{1.0f, 3.0f, 2.0f, 2.0f, 2.0f, 1.0f});
            tabla.setSpacingBefore(10);
            escribirCabeceraDeLaTabla(tabla);
            escribirDatosDeLaTabla(tabla);
            documento.add(tabla);
            // FIN DEL DOCUMENTO
        }

    }

}
*/
