package io.github.prepayments.app.services;

import org.sep4j.Ssio;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class FileInputUtil {

    public static byte[] readFileToBytes(File file) throws IOException {

        InputStream inputStream = new FileInputStream(file);

        long length = file.length();

        byte[] bytes = new byte[(int) length];

        int readOut = inputStream.read(bytes);

        System.out.println(readOut);

        inputStream.close();

        return bytes;
    }

    public static <T> byte[] createExcelFile(final List<T> dtoList, Class<T> dtoClass) {

        ByteArrayOutputStream spreadsheetOutputStream = new ByteArrayOutputStream();

        Ssio.save(dtoClass, dtoList, spreadsheetOutputStream);

        return spreadsheetOutputStream.toByteArray();
    }
}
