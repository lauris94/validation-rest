package com.laurynas.workorder.validationrest.business.validator;

import com.laurynas.workorder.validationrest.business.ValidationResultCode;
import com.laurynas.workorder.validationrest.presentation.model.WorkOrderView;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.laurynas.workorder.validationrest.business.ValidationRules.*;

public abstract class WorkOrderValidator<T extends WorkOrderView> {

    private final List<Function<T, ValidationResultCode>> commonRules = List.of(
            isDepartmentValid(),
            isStartDateValid(),
            isEndDateValid(),
            isCurrencyValid(),
            hasPositiveCost()
    );

    public final List<ValidationResultCode> validate(T workOrderView) {
        List<ValidationResultCode> resultCodes = validate(workOrderView, commonRules);
        List<ValidationResultCode> resultCodesInternal = validateInternal(workOrderView);
        resultCodes.addAll(resultCodesInternal);
        return resultCodes;
    }

    final List<ValidationResultCode> validate(T workOrderView, List<Function<T, ValidationResultCode>> rules) {
        return rules.stream()
                .map(function -> function.apply(workOrderView))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public abstract boolean accept(WorkOrderView workOrderView);

    abstract List<ValidationResultCode> validateInternal(T workOrderView);
}
