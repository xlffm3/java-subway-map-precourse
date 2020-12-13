package subway.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.domain.entity.Sections;
import subway.domain.entity.Station;
import subway.domain.repository.LineRepository;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class LineServiceTest {

    private final LineRepository lineRepository = new LineRepository(new ArrayList<>());
    private final Sections sections = Sections.of(new Station("의정부역"), new Station("시청역"));
    private final LineService lineService = new LineService(lineRepository);

    @BeforeEach
    void setUp() {
        lineService.addLine("1호선", sections);
    }

    @DisplayName("Line 등록 성공")
    @Test
    void addLine_성공한다() {
        String name = lineService.getLineNames()
                .get(0);

        assertThat(name).isEqualTo("1호선");
    }

    @DisplayName("Line 등록 실패 : 중복된 노선")
    @Test
    void addLine_중복된_노선_예외가_발생한다() {
        assertThatCode(() -> {
            lineService.addLine("1호선", sections);
        }).isInstanceOf(LineDuplicationException.class)
                .hasMessage("이미 등록된 노선 이름입니다.");
    }

    @DisplayName("Line 삭제 실패 : 등록되지 않은 노선 이름")
    @Test
    void deleteLineByName_없는_노선_예외가_발생한다() {
        assertThatCode(() -> {
            lineService.deleteLineByName("없는노선");
        }).isInstanceOf(CannotFindLineException.class)
                .hasMessage("등록되지 않은 지하철 노선 이름을 입력하셨습니다.");
    }

    @DisplayName("Line 삭제 성공")
    @Test
    void deleteLineByName_성공한다() {
        Sections sections = Sections.of(new Station("강화도역"), new Station("제주도역"));
        lineService.addLine("9호선", sections);
        int beforeLineCounts = lineRepository.findAll().size();

        lineService.deleteLineByName("9호선");
        int afterLineCounts = lineRepository.findAll().size();

        assertThat(beforeLineCounts).isGreaterThan(afterLineCounts);
    }
}
