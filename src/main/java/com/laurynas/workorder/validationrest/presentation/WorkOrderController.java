package com.laurynas.workorder.validationrest.presentation;

import com.laurynas.workorder.validationrest.business.ValidationResultCode;
import com.laurynas.workorder.validationrest.business.WorkOrderService;
import com.laurynas.workorder.validationrest.presentation.model.ValidationHistoryView;
import com.laurynas.workorder.validationrest.presentation.model.ValidationResultView;
import com.laurynas.workorder.validationrest.presentation.model.WorkOrderView;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@AllArgsConstructor
public class WorkOrderController<T extends WorkOrderView> {

    private final WorkOrderService<T> workOrderService;

    @PostMapping(value = "/orders/validation", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ValidationResultView validate(@RequestBody T workOrderView) {
        List<ValidationResultCode> validationResultCodes = workOrderService.validate(workOrderView);
        return new ValidationResultView(validationResultCodes);
    }

    @GetMapping(value = "/orders/history")
    public List<ValidationHistoryView> getValidationHistory() {
        return workOrderService.getValidationHistory();
    }

    @GetMapping("/viewOrders")
    public ModelAndView viewOrders(Model model) {
        model.addAttribute("orders", workOrderService.getValidationHistory());
        return new ModelAndView("viewOrders");
    }

    @GetMapping("validationForm")
    public ModelAndView getForm() {
        return new ModelAndView("validateOrder");
    }
}
