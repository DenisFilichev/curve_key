package com.example.curve_key.views.components;

import com.example.curve_key.CurveKeyApplication;
import com.example.curve_key.entities.CompanyEntity;
import com.example.curve_key.servicies.CompanyService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class CreateCompanyDialog extends Dialog{

    private final CompanyService companyService;
    private TextField companyNameField;
    private TextField networkField;

    public CreateCompanyDialog(CompanyService companyService) {
        this.companyService = companyService;
        setModal(true);
        setHeaderTitle("Новая компания");
        VerticalLayout dialogLayout = createDialogLayout();
        add(dialogLayout);
        Button saveButton = createSaveButton();
        Button cancelButton = new Button("Отмена", e -> close());
        getFooter().add(cancelButton);
        getFooter().add(saveButton);
    }

    @Override
    public void open() {
        companyNameField.clear();
        super.open();
    }

    private VerticalLayout createDialogLayout() {
        companyNameField = new TextField();
        companyNameField.setPlaceholder("Название компании");
        networkField = new TextField();
        networkField.setPlaceholder("Сеть");
        VerticalLayout dialogLayout = new VerticalLayout(companyNameField, networkField);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "18rem").set("max-width", "100%");
        return dialogLayout;
    }

    private Button createSaveButton() {
        Button saveButton = new Button("Сохранить", e -> {
            CompanyEntity company = new CompanyEntity();
            company.setCompanyName(companyNameField.getValue());
            company.setNetwork(networkField.getValue());
            companyService.save(company);
            close();
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return saveButton;
    }
}
