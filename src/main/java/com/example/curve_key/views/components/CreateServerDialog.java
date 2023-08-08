package com.example.curve_key.views.components;

import com.example.curve_key.entities.VpnServerEntity;
import com.example.curve_key.servicies.VpnServerService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class CreateServerDialog extends Dialog {

    private final VpnServerService service;
    private TextField serverNameField;
    private TextField endpointField;
    private TextField publicKeyField;

    public CreateServerDialog(VpnServerService service) {
        this.service = service;
        setModal(true);
        setHeaderTitle("Новый VPN-сервер");
        VerticalLayout dialogLayout = createDialogLayout();
        add(dialogLayout);
        Button saveButton = createSaveButton();
        Button cancelButton = new Button("Отмена", e -> close());
        getFooter().add(cancelButton);
        getFooter().add(saveButton);
    }

    @Override
    public void open() {
        serverNameField.clear();
        endpointField.clear();
        publicKeyField.clear();
        super.open();
    }

    private VerticalLayout createDialogLayout() {
        serverNameField = new TextField("Название сервера");
        endpointField = new TextField("endpoint");
        publicKeyField = new TextField("public key");
        VerticalLayout dialogLayout = new VerticalLayout(serverNameField,
                endpointField, publicKeyField);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "18rem").set("max-width", "100%");
        return dialogLayout;
    }

    private Button createSaveButton() {
        Button saveButton = new Button("Сохранить", e -> {
            VpnServerEntity server = new VpnServerEntity();
            server.setServername(serverNameField.getValue());
            server.setEndpoint(endpointField.getValue());
            server.setPublicKey(publicKeyField.getValue());
            service.save(server);
            close();
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return saveButton;
    }
}
