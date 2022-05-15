package com.laurynas.workorder.validationrest.business.validator;

import com.laurynas.workorder.validationrest.WorkOrderType;
import com.laurynas.workorder.validationrest.business.ValidationResultCode;
import com.laurynas.workorder.validationrest.business.ValidationRules;
import com.laurynas.workorder.validationrest.presentation.model.ReplacementWorkOrderView;
import com.laurynas.workorder.validationrest.presentation.model.WorkOrderView;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class ReplacementWorkOrderValidator extends WorkOrderValidator<ReplacementWorkOrderView> {

    private final List<Function<ReplacementWorkOrderView, ValidationResultCode>> rules = List.of(
            ValidationRules.hasFactoryName(),
            ValidationRules.isFactoryOrderNumberValid(),
            ValidationRules.allPartsWithInventoryNumbers()
    );

    @Override
    public boolean accept(WorkOrderView workOrderView) {
        return workOrderView.getType() == WorkOrderType.REPLACEMENT;
    }

    @Override
    public List<ValidationResultCode> validateInternal(ReplacementWorkOrderView workOrderView) {
        return validate(workOrderView, rules);
    }
}
