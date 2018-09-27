import model.PositionFileModel;

import services.CurrentPositionService;
import services.ICurrentPositionService;

import java.util.List;

import static utils.Constant.*;

public class Main {

    public static void main(String[] args) {
        ICurrentPositionService currentPositionService = new CurrentPositionService();
        List<PositionFileModel> endOfDayPositionList = currentPositionService.getEndOfDayPosition();
    }
}
