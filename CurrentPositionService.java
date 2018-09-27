package services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import model.PositionFileModel;
import model.TransactionModel;
import utils.FileHandler;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static utils.Constant.*;

public class CurrentPositionService implements ICurrentPositionService {
    @Override
    public List<PositionFileModel> getStartingDayPositions() throws IOException {
        FileReader fileReader = new FileReader(START_OF_DAY_POSITION_FILE_PATH);
        boolean isHeaderAvailable = true;
        return FileHandler.readFile(fileReader, isHeaderAvailable, START_OF_DAY_POSITION_FILECOLUMN_COUNT);
    }

    @Override
    public List<TransactionModel> getTransactionalData() throws FileNotFoundException {
        Gson gson = new Gson();
        Type REVIEW_TYPE = new TypeToken<List<TransactionModel>>() {
        }.getType();
        JsonReader fileReader = new JsonReader(new FileReader(TRANSACTION_FILE_PATH));
        return gson.fromJson(fileReader, REVIEW_TYPE);
    }

    @Override
    public List<PositionFileModel> getEndOfDayPosition() {
        try {
            List<PositionFileModel> positionFileModelList = getStartingDayPositions();
            List<TransactionModel> transactionModelList = getTransactionalData();
            List<String> distinctInstruments = transactionModelList.stream().map(TransactionModel::getInstrument).distinct().collect(Collectors.toList());
            List<PositionFileModel> newPositionFileModelList = new ArrayList<>();
            for (String s : distinctInstruments) {
                List<TransactionModel> specificRecords = transactionModelList.stream().filter(transactionModel -> transactionModel.getInstrument().equalsIgnoreCase(s)).collect(Collectors.toList());
                int totalBuy = specificRecords.stream().filter(transactionModel -> transactionModel.getTransactionType().equalsIgnoreCase("B"))
                        .mapToInt(TransactionModel::getTransactionQuantity).sum();
                int totalSell = specificRecords.stream().filter(transactionModel -> transactionModel.getTransactionType().equalsIgnoreCase("S"))
                        .mapToInt(TransactionModel::getTransactionQuantity).sum();
                int netChange = totalBuy - totalSell;
                PositionFileModel eSpecificPositionRecord = positionFileModelList.stream().filter(endOfDayPosition -> endOfDayPosition.getAccountType().equalsIgnoreCase("E") && endOfDayPosition.getInstrument().equalsIgnoreCase(s)).collect(Collectors.toList()).get(0);
                eSpecificPositionRecord.setDelta(eSpecificPositionRecord.getQuantity() + netChange - eSpecificPositionRecord.getQuantity());
                PositionFileModel ISpecificPositionRecord = positionFileModelList.stream().filter(endOfDayPosition -> endOfDayPosition.getAccountType().equalsIgnoreCase("I") && endOfDayPosition.getInstrument().equalsIgnoreCase(s)).collect(Collectors.toList()).get(0);
                ISpecificPositionRecord.setDelta(eSpecificPositionRecord.getQuantity() - netChange - eSpecificPositionRecord.getQuantity());
                newPositionFileModelList.add(eSpecificPositionRecord);
                newPositionFileModelList.add(ISpecificPositionRecord);
            }
           
            return newPositionFileModelList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
