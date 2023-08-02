package com.prueba02.util;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
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
import org.apache.poi.poifs.filesystem.DocumentOutputStream;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonaExport01PDF {

    // LISTA DE OBJETOS QUE SE VAN A UTILIZAR
    private List<PersonaDto> listaPersonas;

    // DEFINIR LA CABECERA DE LA TABLA
    private void escribirCabeceraDeLaTabla(PdfPTable tabla) {

        // UNA CELDA
        PdfPCell celda = new PdfPCell();

        // COLOR DE FONDO
        celda.setBackgroundColor(new Color(5, 114, 150));

        // ESPACIADO 
        celda.setPadding(5);

        // ALINEAR EL TEXTO CONTENIDO AL CENTRO
        celda.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

        
        // TIPO DE FUENTE
        Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

        // COLOR DE TEXTO
        fuente.setColor(new Color(255, 255, 255));
        
        // TAMAÑO DE TEXTO
        fuente.setSize(10);

        // DEFINIR LAS CELDAS QUE VA A CONTENER LA CABECERA
        // TEXTO QUE SE VA A ESCRIBIR, ESTILO DEL TEXTO
        celda.setPhrase(new Phrase("ID", fuente));
        tabla.addCell(celda);
        celda.setPhrase(new Phrase("nombres", fuente));
        tabla.addCell(celda);
        celda.setPhrase(new Phrase("apellidos", fuente));
        tabla.addCell(celda);
        celda.setPhrase(new Phrase("dni", fuente));
        tabla.addCell(celda);
        celda.setPhrase(new Phrase("dirección", fuente));
        tabla.addCell(celda);
        celda.setPhrase(new Phrase("email", fuente));
        tabla.addCell(celda);
        celda.setPhrase(new Phrase("telefono", fuente));
        tabla.addCell(celda);
        celda.setPhrase(new Phrase("estado", fuente));
        tabla.addCell(celda);

    }

    // ESCRIBIR LOS DATOS QUE VA CONTENER LA TABLA (DEBAJO DE LA CABECERA)
    private void escribirDatosDeLaTabla(PdfPTable tabla) {

        // ITERAR SOBRE LA LISTA DE PERSONAS
        for (PersonaDto personaDto : listaPersonas) {
            // CONVERTIR A TEXTO LOS CAMPOS QUE NO SON DE TIPO TEXTO
            String valor1 = String.valueOf(personaDto.getId());
            String valor2 = personaDto.getNombres();
            String valor3 = personaDto.getApellidos();
            String valor4 = String.valueOf(personaDto.getDni());
            String valor5 = personaDto.getDireccion();
            String valor6 = personaDto.getEmail();
            String valor7 = String.valueOf(personaDto.getTelefono());
            String valor8 = String.valueOf(personaDto.getEstado());

            // ESTILOS
            Font textoId = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            textoId.setSize(8);
            
            Font textoValores = FontFactory.getFont(FontFactory.HELVETICA);
            textoValores.setSize(8);
            
            PdfPCell celda1 = new PdfPCell(new Phrase(valor1, textoId));
            tabla.addCell(celda1);
            PdfPCell celda2 = new PdfPCell(new Phrase(valor2, textoValores));
            tabla.addCell(celda2);
            PdfPCell celda3 = new PdfPCell(new Phrase(valor3, textoValores));
            tabla.addCell(celda3);
            PdfPCell celda4 = new PdfPCell(new Phrase(valor4, textoValores));
            tabla.addCell(celda4);
            PdfPCell celda5 = new PdfPCell(new Phrase(valor5, textoValores));
            tabla.addCell(celda5);
            PdfPCell celda6 = new PdfPCell(new Phrase(valor6, textoValores));
            tabla.addCell(celda6);
            PdfPCell celda7 = new PdfPCell(new Phrase(valor7, textoValores));
            tabla.addCell(celda7);
            PdfPCell celda8 = new PdfPCell(new Phrase(valor8, textoValores));
            tabla.addCell(celda8);
            
            
        }

    }

    // GENERAR EL PDF...
    // DISEÑO DEL DOCUMENTO (DE ARRIBA HACIA ABAJO) QUE SE VA A EXPORTAR A PDF
    public void export(HttpServletResponse response) throws DocumentException, IOException {

        // TAMAÑO DE HOJA DEL DOCUMENTO: A4
        try ( Document documento = new Document(PageSize.A4)) {

            PdfWriter.getInstance(documento, response.getOutputStream());

            // INICIO DEL DOCUMENTO
            documento.open();

            // TITULO
            // TIPO DE FUENTE
            Font fuente = FontFactory.getFont(FontFactory.TIMES_ROMAN);
            // TAMAÑO
            fuente.setSize(20);
            // COLOR
            fuente.setColor(Color.RED);

            // DEFINIR UN PARRAFO PARA EL TITULO
            Paragraph titulo = new Paragraph("Lista de registros habilitados", fuente);
            // ALINEAR PARRAFO AL CENTRO
            titulo.setAlignment(Paragraph.ALIGN_CENTER);

            documento.add(titulo);

            // SALTO DE LINEA
            documento.add(new Paragraph(" "));
            documento.add(new Paragraph(" "));
            documento.add(new Paragraph(" "));

            // DEFINIR UNA TABLA QUE TENGA 8 COLUMNAS
            PdfPTable tabla = new PdfPTable(8);

            // ANCHO DE LA TABLA AL 100% DEL ANCHO DE LA PAGINA
            tabla.setWidthPercentage(100f);

            // ANCHO DE CADA COLUMNA DE LA TABLA
            // ID / NOMBRES / APELLIDOS / DNI / DIRECCION / EMAIL / TELEFONO / ESTADO
            tabla.setWidths(new float[]{0.5f, 2.0f, 2.0f, 1.0f, 3.0f, 2.5f, 1.0f, 1.0f});

            // ESPACIO ANTES DE LA TABLA
            tabla.setSpacingBefore(10);

            // CABECERA DE LA TABLA
            escribirCabeceraDeLaTabla(tabla);

            // DATOS DE LA TABLA
            escribirDatosDeLaTabla(tabla);

            // AGREGAR LA TABLA DEFINIDA
            documento.add(tabla);

            // FIN DEL DOCUMENTO
            documento.close();
        }

    }

}
