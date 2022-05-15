package com.laurynas.workorder.validationrest.business.validator;

import com.laurynas.workorder.validationrest.WorkOrderType;
import com.laurynas.workorder.validationrest.business.ValidationResultCode;
import com.laurynas.workorder.validationrest.business.ValidationRules;
import com.laurynas.workorder.validationrest.presentation.model.RepairWorkOrderView;
import com.laurynas.workorder.validationrest.presentation.model.WorkOrderView;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class RepairWorkOrderValidator extends WorkOrderValidator<RepairWorkOrderView> {

    private final List<Function<RepairWorkOrderView, ValidationResultCode>> rules = List.of(
            ValidationRules.isAnalysisDateValid(),
            ValidationRules.hasResponsiblePerson(),
            ValidationRules.isTestDateValid(),
            ValidationRules.hasParts()
    );

    @Override
    public boolean accept(WorkOrderView workOrderView) {
        return workOrderView.getType() == WorkOrderType.REPAIR;
    }

    @Override
    public List<ValidationResultCode> validateInternal(RepairWorkOrderView workOrderView) {
        return validate(workOrderView, rules);
    }
}
