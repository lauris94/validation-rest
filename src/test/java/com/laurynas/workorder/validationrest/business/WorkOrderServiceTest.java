package com.laurynas.workorder.validationrest.business;

import com.laurynas.workorder.validationrest.WorkOrderType;
import com.laurynas.workorder.validationrest.business.validator.WorkOrderValidator;
import com.laurynas.workorder.validationrest.presentation.model.ValidationHistoryView;
import com.laurynas.workorder.validationrest.presentation.model.WorkOrderView;
import com.laurynas.workorder.validationrest.repository.ValidationEntryRepository;
import com.laurynas.workorder.validationrest.repository.model.ValidationEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.time.OffsetDateTime;
import java.util.List;

import static com.laurynas.workorder.validationrest.TestObjectProvider.createValidationEntry;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class WorkOrderServiceTest {

    private WorkOrderService<WorkOrderView> workOrderService;
    @Mock
    private WorkOrderValidator<WorkOrderView> workOrderValidator;
    @Mock
    private ValidationEntryRepository validationEntryRepository;
    @Mock
    private WorkOrderView workOrderView;
    @Captor
    private ArgumentCaptor<ValidationEntry> validationEntryCaptor;

    @BeforeEach
    void setUp() {
        openMocks(this);
        workOrderService = new WorkOrderService<>(List.of(workOrderValidator), validationEntryRepository);
    }

    @Test
    void getValidationHistory() {
        OffsetDateTime timestamp1 = OffsetDateTime.parse("2022-05-14T14:49:40.887900+03:00");
        OffsetDateTime timestamp2 = OffsetDateTime.parse("2022-05-13T19:17:32.474700+03:00");
        ValidationEntry validationEntry1 = createValidationEntry(1, WorkOrderType.ANALYSIS, "test dep",
                timestamp1, true);
        ValidationEntry validationEntry2 = createValidationEntry(2, WorkOrderType.REPAIR, "another dep",
                timestamp2, false);

        when(validationEntryRepository.findAll()).thenReturn(List.of(validationEntry1, validationEntry2));

        List<ValidationHistoryView> result = workOrderService.getValidationHistory();

        assertEquals(2, result.size());
        assertEquals(timestamp1, result.get(0).getTimestamp());
        assertEquals(WorkOrderType.ANALYSIS, result.get(0).getType());
        assertEquals("test dep", result.get(0).getDepartment());
        assertTrue(result.get(0).isValid());
        assertEquals(timestamp2, result.get(1).getTimestamp());
        assertEquals(WorkOrderType.REPAIR, result.get(1).getType());
        assertEquals("another dep", result.get(1).getDepartment());
        assertFalse(result.get(1).isValid());
    }

    @Test
    void validateWorkOrder() {
        when(workOrderView.getType()).thenReturn(WorkOrderType.REPAIR);
        when(workOrderView.getDepartment()).thenReturn("test dep");
        when(workOrderValidator.accept(any())).thenReturn(true);
        doReturn(List.of(ValidationResultCode.INVALID_COST)).when(workOrderValidator).validate(any());

        List<ValidationResultCode> result = workOrderService.validate(workOrderView);

        verify(workOrderValidator).accept(workOrderView);
        verify(workOrderValidator).validate(workOrderView);
        verify(validationEntryRepository).save(validationEntryCaptor.capture());
        assertEquals(1, result.size());
        assertEquals(ValidationResultCode.INVALID_COST, result.get(0));

        ValidationEntry savedEntry = validationEntryCaptor.getValue();
        assertEquals(WorkOrderType.REPAIR, savedEntry.getType());
        assertEquals("test dep", savedEntry.getDepartment());
        assertFalse(savedEntry.isValid());
    }

    @Test
    void validateWorkOrderWhenValidatorNotExistThenThrowException() {
        when(workOrderValidator.accept(any())).thenReturn(false);
        assertThrows(IllegalStateException.class, () -> workOrderService.validate(workOrderView));
    }
}
