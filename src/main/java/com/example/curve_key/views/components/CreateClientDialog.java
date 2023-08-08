package com.example.curve_key.views.components;

import com.example.curve_key.entities.VpnClientEntity;
import com.example.curve_key.entities.VpnServerEntity;
import com.example.curve_key.servicies.VpnClientService;
import com.example.curve_key.views.models.CompanyClientModel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

public class CreateClientDialog extends Dialog {

    private final VpnClientService clientService;
    private TextField clientName;
    private TextField allowedIps;
    private ComboBox<String> comboBox;
    private CompanyClientModel clientModel;
    private Map<String, VpnServerEntity> servers;
    private VerticalLayout dialogLayout;

    Binder<VpnClientEntity> binder = new BeanValidationBinder<>(VpnClientEntity.class);

    public CreateClientDialog(VpnClientService service) {
        this.clientService = service;
        binder.bindInstanceFields(this);
        servers = clientService.getServerService().getAllServer();
        VerticalLayout dialogLayout = createDialogLayout();
        add(dialogLayout);
        Button saveButton = createSaveButton();
        Button cancelButton = new Button("Отмена", e -> close());
        getFooter().add(cancelButton);
        getFooter().add(saveButton);
    }

    @Override
    public void open() {
        if(clientModel!=null) setHeaderTitle(clientModel.getCompanyName());
        else setHeaderTitle("Необходимо выбрать компанию!");
        clientName.clear();
        comboBox.clear();
        super.open();
    }

    public void setClientModel(CompanyClientModel clientModel) {
        this.clientModel = clientModel;
    }

    private VerticalLayout createDialogLayout() {
        clientName = new TextField();
        clientName.setPlaceholder("Название клиента");
        allowedIps = new TextField();
        allowedIps.setValue("192.168.0.1/32, 10.80.0.0/16, 10.125.0.0/16, 10.154.0.0/16,10.81.0.0/16,10.166.0.0/16, 10.248.51.0/24, 10.249.248.0/21");
        allowedIps.setPlaceholder("Разрешенные адреса");
        comboBox = new ComboBox<>();
        comboBox.setItems(servers.keySet());
        dialogLayout = new VerticalLayout();
        dialogLayout.add(clientName, allowedIps, comboBox);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "18rem").set("max-width", "100%");
        return dialogLayout;
    }

    private Button createSaveButton() {
        Button saveButton = new Button("Сохранить", e -> {
            VpnClientEntity client = new VpnClientEntity();
            client.setClientName(clientName.getValue());
            client.setAllowedIps(allowedIps.getValue());
            String endpoint = comboBox.getValue();
            binder.setBean(client);
            if (binder.isValid() && endpoint != null && !endpoint.isEmpty()) {
                VpnServerEntity server = servers.get(endpoint);
                clientService.save(client, clientModel.getCompanyId(), server.getId());
            }else {

            }
            close();
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return saveButton;
    }
}
