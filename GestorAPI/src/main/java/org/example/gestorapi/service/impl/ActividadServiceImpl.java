package org.example.gestorapi.service.impl;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.gestorapi.model.Actividad;
import org.example.gestorapi.repository.ActividadRepository;
import org.example.gestorapi.service.ActividadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ActividadServiceImpl implements ActividadService {
    @Autowired
    private ActividadRepository actividadRepository;
    @Override
    public List<Actividad> findAll() {
        return actividadRepository.findAll();
    }

    @Override
    public Actividad findById(Integer id) {
        return actividadRepository.findById(id).orElse(null);
    }

    @Override
    public Actividad guardar(Actividad actividad) {
        return actividadRepository.save(actividad);
    }

    @Override
    public Actividad actualizar(Actividad nuevo, Integer id) {
        if(actividadRepository.existsById(id)) {
            nuevo.setId(id);
            return actividadRepository.save(nuevo);
        }else{
            return null;
        }
    }

    @Override
    public Actividad eliminar(Integer id) {
        if(actividadRepository.existsById(id)) {
            Actividad actividad = actividadRepository.findById(id).get();
            actividadRepository.delete(actividad);
            return actividad;
        }else{
            return null;
        }
    }
    @Override
    public void excel(Actividad actividad) {
        String rutaArchivo = "plantilla autorizacion.xlsx";
        String tmpdir = System.getProperty("java.io.tmpdir");
        try (FileInputStream fis = new FileInputStream(new File(rutaArchivo))) {
            Workbook workbook = new XSSFWorkbook(fis);

            // Obtener la hoja del archivo (0 es la primera hoja)
            Sheet sheet = workbook.getSheetAt(0);

            // Obtener la fila (índice de fila 1 es la segunda fila, ya que la numeración empieza en 0)
            Row row = sheet.getRow(10); // Fila 2 (índice 1)

            // Obtener la celda en la columna 2 (índice 2)
            Cell cell = row.getCell(9); // Columna C (índice 2)
            cell.setCellValue(actividad.getImportePorAlumno().doubleValue());

            //FEHCA
            row = sheet.getRow(13);
            cell = row.getCell(9);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            cell.setCellValue(actividad.getFini().format(formatter));

            //HORA SALIDA
            row = sheet.getRow(15);
            cell = row.getCell(9);
            cell.setCellValue(actividad.getHini().getHour() + ":" + actividad.getHini().getMinute());

            //HORA LLEGADA
            row = sheet.getRow(15);
            cell = row.getCell(25);
            cell.setCellValue(actividad.getHfin().getHour() + ":" + actividad.getHfin().getMinute());

            //TITULO
            row = sheet.getRow(5);
            cell = row.getCell(4);
            cell.setCellValue(actividad.getTitulo());


            try (FileOutputStream fos = new FileOutputStream(new File(tmpdir + "/autorizacion.xlsx"))) {
                workbook.write(fos);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
