package org.example.gestorapi.service.files;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {

    public static void guardarFichero(String directorioSubida, String nombreFichero,
    MultipartFile multipartFile) throws IOException {
        Path rutaSubida = Paths.get(directorioSubida);

        if(!Files.exists(rutaSubida)){
            try {
                Files.createDirectories(rutaSubida);
            } catch (IOException e) {
                System.out.println("Error al crear el directorio de subida " + e.getMessage());
            }
        }

        try (InputStream input = multipartFile.getInputStream()){
            Path rutaDelFichero = rutaSubida.resolve(nombreFichero);
            Files.copy(input, rutaDelFichero, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("No se puedo guardar el fichero: " + nombreFichero + " ERROR: "+e.getMessage());
        }
    }
}
