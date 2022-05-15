package com.laurynas.workorder.validationrest.business.validator;

import com.laurynas.workorder.validationrest.WorkOrderType;
import com.laurynas.workorder.validationrest.business.ValidationResultCode;
import com.laurynas.workorder.validationrest.presentation.model.AnalysisWorkOrderView;
import com.laurynas.workorder.validationrest.presentation.model.WorkOrderView;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class AnalysisWorkOrderValidator extends WorkOrderValidator<AnalysisWorkOrderView> {

    private final List<Function<AnalysisWorkOrderView, ValidationResultCode>> rules = List.of();

    @Override
    public boolean accept(WorkOrderView workOrderView) {
        return workOrderView.getType() == WorkOrderType.ANALYSIS;
    }

    @Override
    public List<ValidationResultCode> validateInternal(AnalysisWorkOrderView workOrderView) {
        return validate(workOrderView, rules);
    }
}
