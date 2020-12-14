package subway.map;

import subway.controller.SubwayMapController;
import subway.domain.type.FunctionType;
import subway.domain.type.ManagementType;
import subway.dto.LineDto;
import subway.dto.SectionDto;
import subway.dto.SubwayMapDto;
import subway.view.InputView;
import subway.view.OutputView;

import java.util.List;

public class SubwayMapApplication {

    private final InputView inputView;
    private final SubwayMapController subwayMapController;

    public SubwayMapApplication(InputView inputView, SubwayMapController subwayMapController) {
        this.inputView = inputView;
        this.subwayMapController = subwayMapController;
    }

    public void run() {
        ManagementType managementType = ManagementType.initiate();
        while (managementType.isRunning()) {
            OutputView.printMainDisplay();
            managementType = inputView.inputManagementType();
            executeExceptionHandler(managementType);
        }
    }

    private void executeExceptionHandler(ManagementType managementType) {
        try {
            executeManagementFunction(managementType);
        } catch (RuntimeException runtimeException) {
            OutputView.printErrorMessage(runtimeException);
            executeExceptionHandler(managementType);
        }
    }

    private void executeManagementFunction(ManagementType managementType) {
        if (managementType == ManagementType.EXIT) {
            return;
        }
        if (managementType == ManagementType.SUBWAY_MAP_PRINT) {
            executeReadFunction(managementType);
            return;
        }
        OutputView.printManagementDisplay(managementType);
        FunctionType functionType = inputView.inputFunctionType(managementType);
        selectManagementFunction(managementType, functionType);
    }

    private void selectManagementFunction(ManagementType managementType, FunctionType functionType) {
        if (functionType == FunctionType.BACK) {
            return;
        }
        if (functionType == FunctionType.READ) {
            executeReadFunction(managementType);
            return;
        }
        if (managementType == ManagementType.STATION) {
            executeStationManagement(functionType);
            return;
        }
        if (managementType == ManagementType.LINE) {
            executeLineManagement(functionType);
            return;
        }
        if (managementType == ManagementType.SECTION) {
            executeSectionManagement(functionType);
        }
    }

    private void executeReadFunction(ManagementType managementType) {
        if (managementType == ManagementType.SUBWAY_MAP_PRINT) {
            List<SubwayMapDto> subwayMapDtos = subwayMapController.getSubwayMapDtos();
            OutputView.printSubwayMap(subwayMapDtos);
        }
        if (managementType == ManagementType.STATION) {
            List<String> stationNames = subwayMapController.getStationNames();
            OutputView.printNames(managementType, stationNames);
        }
        if (managementType == ManagementType.LINE) {
            List<String> lineNames = subwayMapController.getLineNames();
            OutputView.printNames(managementType, lineNames);
        }
    }

    private void executeStationManagement(FunctionType functionType) {
        String stationName = inputView.inputStationName(functionType);
        if (functionType == FunctionType.REGISTER) {
            subwayMapController.addStationByName(stationName);
        }
        if (functionType == FunctionType.DELETE) {
            subwayMapController.deleteStationByName(stationName);
        }
        OutputView.printStationManagementSuccessMessage(functionType);
    }

    private void executeLineManagement(FunctionType functionType) {
        LineDto lineDto = inputView.inputLineRequest(functionType);
        if (functionType == FunctionType.REGISTER) {
            subwayMapController.addLine(lineDto);
        }
        if (functionType == FunctionType.DELETE) {
            subwayMapController.deleteLine(lineDto);
        }
        OutputView.printLineManagementSuccessMessage(functionType);
    }

    private void executeSectionManagement(FunctionType functionType) {
        SectionDto sectionDto = inputView.inputSectionRequest(functionType);
        if (functionType == FunctionType.REGISTER) {
            subwayMapController.addSection(sectionDto);
        }
        if (functionType == FunctionType.DELETE) {
            subwayMapController.deleteSection(sectionDto);
        }
        OutputView.printSectionManagementSuccessMessage(functionType);
    }
}
