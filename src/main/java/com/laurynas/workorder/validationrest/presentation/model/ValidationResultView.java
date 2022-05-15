package com.laurynas.workorder.validationrest.presentation.model;

import com.laurynas.workorder.validationrest.business.ValidationResultCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ValidationResultView {

    private List<ValidationResultCode> resultCodes;
}
