package utils;


import com.sun.corba.se.impl.io.TypeMismatchException;
import model.PositionFileModel;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class FileHandler {
    public static List<PositionFileModel> readFile(FileReader fileReader, boolean isContainsHeader, int columnCount) throws IOException {
        List<PositionFileModel> positionFileEntities = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            Scanner scanner = new Scanner(bufferedReader);
            if (isContainsHeader && scanner.hasNext()) {
                scanner.nextLine();
            }
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] columnValue = line.split(",");
                if (columnValue.length < columnCount) {
                    throw new IndexOutOfBoundsException();
                } else {
                    PositionFileModel positionFileEntity = getPositionFileEntityModelFromFile(columnValue, columnCount);
                    positionFileEntities.add(positionFileEntity);
                }
            }
        }
        return positionFileEntities;
    }

    static PositionFileModel getPositionFileEntityModelFromFile(String[] columnValue, int columnCount) {
        int index = 0;
        PositionFileModel positionFileEntity = new PositionFileModel();
        positionFileEntity.setInstrument(columnValue[index++]);
        positionFileEntity.setAccount(Integer.parseInt(columnValue[index++]));
        positionFileEntity.setAccountType(columnValue[index++]);
        positionFileEntity.setQuantity(Integer.parseInt(columnValue[index++]));
        return positionFileEntity;
    }
}
