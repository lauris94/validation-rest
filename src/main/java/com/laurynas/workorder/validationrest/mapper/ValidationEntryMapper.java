package com.laurynas.workorder.validationrest.mapper;

import com.laurynas.workorder.validationrest.business.ValidationResultCode;
import com.laurynas.workorder.validationrest.presentation.model.ValidationHistoryView;
import com.laurynas.workorder.validationrest.presentation.model.WorkOrderView;
import com.laurynas.workorder.validationrest.repository.model.ValidationEntry;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationEntryMapper {

    public static ValidationEntry toEntity(WorkOrderView workOrderView, List<ValidationResultCode> resultCodes) {
        ValidationEntry entity = new ValidationEntry();
        entity.setType(workOrderView.getType());
        entity.setDepartment(workOrderView.getDepartment());
        entity.setValid(resultCodes.isEmpty());
        return entity;
    }

    public static List<ValidationHistoryView> toView(List<ValidationEntry> validationEntries) {
        return validationEntries.stream()
                .map(ValidationEntryMapper::toView)
                .collect(Collectors.toList());
    }

    private static ValidationHistoryView toView(ValidationEntry entity) {
        ValidationHistoryView view = new ValidationHistoryView();
        view.setType(entity.getType());
        view.setDepartment(entity.getDepartment());
        view.setValid(entity.isValid());
        view.setTimestamp(entity.getTimestamp());
        return view;
    }
}
