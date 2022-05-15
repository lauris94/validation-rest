package com.laurynas.workorder.validationrest.business;

import com.laurynas.workorder.validationrest.business.validator.WorkOrderValidator;
import com.laurynas.workorder.validationrest.mapper.ValidationEntryMapper;
import com.laurynas.workorder.validationrest.presentation.model.ValidationHistoryView;
import com.laurynas.workorder.validationrest.presentation.model.WorkOrderView;
import com.laurynas.workorder.validationrest.repository.ValidationEntryRepository;
import com.laurynas.workorder.validationrest.repository.model.ValidationEntry;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class WorkOrderService<T extends WorkOrderView> {

    private final List<WorkOrderValidator<T>> validators;
    private final ValidationEntryRepository validationEntryRepository;

    @Transactional
    public List<ValidationResultCode> validate(T workOrderView) {
        WorkOrderValidator<T> workOrderValidator = resolveValidator(workOrderView);
        List<ValidationResultCode> resultCodes = workOrderValidator.validate(workOrderView);
        saveRequest(workOrderView, resultCodes);
        return resultCodes;
    }

    private WorkOrderValidator<T> resolveValidator(T workOrderView) {
        return validators.stream()
                .filter(workOrderValidator -> workOrderValidator.accept(workOrderView))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Type " + workOrderView.getType() + " is not supported"));
    }

    private void saveRequest(T workOrderView, List<ValidationResultCode> resultCodes) {
        ValidationEntry validationEntry = ValidationEntryMapper.toEntity(workOrderView, resultCodes);
        validationEntryRepository.save(validationEntry);
    }

    @Transactional(readOnly = true)
    public List<ValidationHistoryView> getValidationHistory() {
        List<ValidationEntry> validationEntries = validationEntryRepository.findAll();
        return ValidationEntryMapper.toView(validationEntries);
    }
}
