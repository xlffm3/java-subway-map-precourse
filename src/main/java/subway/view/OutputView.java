package subway.view;

import subway.domain.type.ManagementType;

public class OutputView {
    private static final String MAIN_DISPLAY =
            "\n## 메인 화면\n1. 역 관리\n2. 노선 관리\n3. 구간 관리\n4. 지하철 노선도 출력\nQ. 종료";
    private static final String STATION_MANAGEMENT_DISPLAY =
            "\n## 역 관리 화면\n1. 역 등록\n2. 역 삭제\n3. 역 조회\nB. 돌아가기";
    private static final String LINE_MANAGEMENT_DISPLAY =
            "\n## 노선 관리 화면\n1. 노선 등록\n2. 노선 삭제\n3. 노선 조회\nB. 돌아가기";
    private static final String SECTION_MANAGEMENT_DISPLAY = "\n## 구간 관리 화면\n1. 구간 등록\n2. 구간 삭제\nB. 돌아가기";
    private static final String ERROR_MESSAGE_FORMAT = "\n[ERROR] %s\n";

    private OutputView() {
    }

    public static void printMainDisplay() {
        System.out.println(MAIN_DISPLAY);
    }

    public static void printErrorMessage(RuntimeException runtimeException) {
        System.out.printf(ERROR_MESSAGE_FORMAT, runtimeException.getMessage());
    }

    public static void printManagementDisplay(ManagementType managementType) {
        String managementDisplay = getManagementDisplay(managementType);
        System.out.println(managementDisplay);
    }

    private static String getManagementDisplay(ManagementType managementType) {
        if (managementType == ManagementType.STATION) {
            return STATION_MANAGEMENT_DISPLAY;
        }
        if (managementType == ManagementType.LINE) {
            return LINE_MANAGEMENT_DISPLAY;
        }
        return SECTION_MANAGEMENT_DISPLAY;
    }
}
