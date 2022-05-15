package com.laurynas.workorder.validationrest.business;

import com.laurynas.workorder.validationrest.presentation.model.RepairWorkOrderView;
import com.laurynas.workorder.validationrest.presentation.model.WorkOrderView;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;

import static com.laurynas.workorder.validationrest.business.ValidationResultCode.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ValidationRulesTest {

    private final WorkOrderView workOrderView = mock(WorkOrderView.class);
    private final RepairWorkOrderView repairWorkOrderView = mock(RepairWorkOrderView.class);

    static Stream<Arguments> isDepartmentValidArgs() {
        return Stream.of(
                Arguments.of("test department", null),
                Arguments.of("  ", INVALID_DEPARTMENT),
                Arguments.of(null, INVALID_DEPARTMENT)
        );
    }

    @ParameterizedTest
    @MethodSource("isDepartmentValidArgs")
    void isDepartmentValid(String department, ValidationResultCode expectedCode) {
        when(workOrderView.getDepartment()).thenReturn(department);
        ValidationResultCode result = ValidationRules.isDepartmentValid().apply(workOrderView);
        assertEquals(expectedCode, result);
    }

    static Stream<Arguments> isStartDateValidArgs() {
        return Stream.of(
                Arguments.of(LocalDate.now().minusDays(1), null),
                Arguments.of(LocalDate.now(), INVALID_START_DATE),
                Arguments.of(LocalDate.now().plusDays(1), INVALID_START_DATE),
                Arguments.of(null, INVALID_START_DATE)
        );
    }

    @ParameterizedTest
    @MethodSource("isStartDateValidArgs")
    void isStartDateValid(LocalDate startDate, ValidationResultCode expectedCode) {
        when(workOrderView.getStartDate()).thenReturn(startDate);
        ValidationResultCode result = ValidationRules.isStartDateValid().apply(workOrderView);
        assertEquals(expectedCode, result);
    }

    static Stream<Arguments> isEndDateValidArgs() {
        return Stream.of(
                Arguments.of(LocalDate.now(), LocalDate.now().plusDays(1), null),
                Arguments.of(null, date("2022-04-20"), null),
                Arguments.of(null, null, INVALID_END_DATE),
                Arguments.of(date("2022-04-21"), date("2022-04-21"), INVALID_END_DATE),
                Arguments.of(date("2022-04-21"), date("2022-04-20"), INVALID_END_DATE)
        );
    }

    @ParameterizedTest
    @MethodSource("isEndDateValidArgs")
    void isEndDateValid(LocalDate startDate, LocalDate endDate, ValidationResultCode expectedCode) {
        when(workOrderView.getStartDate()).thenReturn(startDate);
        when(workOrderView.getEndDate()).thenReturn(endDate);
        ValidationResultCode result = ValidationRules.isEndDateValid().apply(workOrderView);
        assertEquals(expectedCode, result);
    }

    static Stream<Arguments> isCurrencyValidArgs() {
        return Stream.of(
                Arguments.of("USD", null),
                Arguments.of("TEST", INVALID_CURRENCY),
                Arguments.of(null, INVALID_CURRENCY)
        );
    }

    @ParameterizedTest
    @MethodSource("isCurrencyValidArgs")
    void isCurrencyValid(String currency, ValidationResultCode expectedCode) {
        when(workOrderView.getCurrency()).thenReturn(currency);
        ValidationResultCode result = ValidationRules.isCurrencyValid().apply(workOrderView);
        assertEquals(expectedCode, result);
    }

    static Stream<Arguments> hasPositiveCostArgs() {
        return Stream.of(
                Arguments.of(BigDecimal.ONE, null),
                Arguments.of(BigDecimal.valueOf(-0.01), INVALID_COST),
                Arguments.of(null, INVALID_COST)
        );
    }

    @ParameterizedTest
    @MethodSource("hasPositiveCostArgs")
    void hasPositiveCost(BigDecimal cost, ValidationResultCode expectedCode) {
        when(workOrderView.getCost()).thenReturn(cost);
        ValidationResultCode result = ValidationRules.hasPositiveCost().apply(workOrderView);
        assertEquals(expectedCode, result);
    }

    static Stream<Arguments> isAnalysisDateValidArgs() {
        return Stream.of(
                Arguments.of(null, null, null, INVALID_ANALYSIS_DATE),
                Arguments.of(null, date("2022-05-14"), date("2022-05-13"), null),
                Arguments.of(date("2022-05-12"), null, date("2022-05-13"), null),
                Arguments.of(date("2022-05-12"), date("2022-05-14"), date("2022-05-14"), INVALID_ANALYSIS_DATE),
                Arguments.of(date("2022-05-12"), date("2022-05-14"), date("2022-05-12"), INVALID_ANALYSIS_DATE),
                Arguments.of(date("2022-05-12"), date("2022-05-14"), date("2022-05-13"), null)
        );
    }

    @ParameterizedTest
    @MethodSource("isAnalysisDateValidArgs")
    void isAnalysisDateValid(LocalDate startDate, LocalDate endDate, LocalDate analysisDate,
                             ValidationResultCode expectedCode) {
        when(repairWorkOrderView.getStartDate()).thenReturn(startDate);
        when(repairWorkOrderView.getEndDate()).thenReturn(endDate);
        when(repairWorkOrderView.getAnalysisDate()).thenReturn(analysisDate);
        ValidationResultCode result = ValidationRules.isAnalysisDateValid().apply(repairWorkOrderView);
        assertEquals(expectedCode, result);
    }

    static Stream<Arguments> hasResponsiblePersonArgs() {
        return Stream.of(
                Arguments.of(null, MISSING_RESPONSIBLE_PERSON),
                Arguments.of("  ", MISSING_RESPONSIBLE_PERSON),
                Arguments.of("test person", null)
        );
    }

    @ParameterizedTest
    @MethodSource("hasResponsiblePersonArgs")
    void hasResponsiblePerson(String responsiblePerson, ValidationResultCode expectedCode) {
        when(repairWorkOrderView.getResponsiblePerson()).thenReturn(responsiblePerson);
        ValidationResultCode result = ValidationRules.hasResponsiblePerson().apply(repairWorkOrderView);
        assertEquals(expectedCode, result);
    }

    static Stream<Arguments> isTestDateValidArgs() {
        return Stream.of(
                Arguments.of(null, null, null, INVALID_TEST_DATE),
                Arguments.of(date("2022-05-12"), date("2022-05-14"), null, INVALID_TEST_DATE),
                Arguments.of(date("2022-05-12"), null, date("2022-05-14"), null),
                Arguments.of(null, date("2022-05-14"), date("2022-05-13"), null),
                Arguments.of(date("2022-05-14"), date("2022-05-16"), date("2022-05-15"), null),
                Arguments.of(date("2022-05-14"), date("2022-05-16"), date("2022-05-14"), INVALID_TEST_DATE),
                Arguments.of(date("2022-05-14"), date("2022-05-16"), date("2022-05-16"), INVALID_TEST_DATE)
        );
    }

    @ParameterizedTest
    @MethodSource("isTestDateValidArgs")
    void isTestDateValid(LocalDate analysisDate, LocalDate endDate, LocalDate testDate, ValidationResultCode expectedCode) {
        when(repairWorkOrderView.getAnalysisDate()).thenReturn(analysisDate);
        when(repairWorkOrderView.getEndDate()).thenReturn(endDate);
        when(repairWorkOrderView.getTestDate()).thenReturn(testDate);
        ValidationResultCode result = ValidationRules.isTestDateValid().apply(repairWorkOrderView);
        assertEquals(expectedCode, result);
    }

    private static LocalDate date(String date) {
        return LocalDate.parse(date);
    }
}
