package com.example.curve_key.views.components;

import com.example.curve_key.entities.VpnClientEntity;
import com.example.curve_key.servicies.VpnClientService;
import com.example.curve_key.utils.Dispatcher;
import com.example.curve_key.views.FileDownloader;
import com.example.curve_key.views.models.CompanyClientModel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.io.File;

public class GeneratorDialog extends Dialog {
    private final VpnClientService clientService;
    private File file;
    private CompanyClientModel clientModel;
    private H3 comment = new H3();
    private H3 interfase = new H3();

    public GeneratorDialog(VpnClientService clientService){
        this.clientService = clientService;
        setModal(true);
        setHeaderTitle("Генерация новых ключей");
        VerticalLayout dialogLayout = createDialogLayout();
        add(dialogLayout);
        Button saveButton = createSaveButton();
        Button cancelButton = new Button("Отмена", e -> close());
        Button closeButton = new Button("Закрыть", e -> close());
        getFooter().add(cancelButton, saveButton, closeButton);
    }

    @Override
    public void open() {
        comment.setText("comment=" + (clientModel!=null ? clientModel.getClientName() : ""));
        interfase.setText("interface=" + (clientModel!=null ? clientModel.getServername() : ""));
        super.open();
    }

    @Override
    public void close() {
        if(file!=null) file.delete();
        file = null;
        super.close();
    }

    private VerticalLayout createDialogLayout() {
        VerticalLayout dialogLayout = new VerticalLayout(comment, interfase);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "18rem").set("max-width", "100%");
        return dialogLayout;
    }

    private Button createSaveButton() {
        Button saveButton = new Button("Сгенерировать", e -> {
            VpnClientEntity client = clientService.findById(clientModel.getClientId());
            Dispatcher dispatcher = new Dispatcher(clientService, client);
            file = dispatcher.generateKeies();
            clientService.save(client, clientModel.getCompanyId(), clientModel.getVpnServer());
            FileDownloader downloader = new FileDownloader(file);
            add(downloader);
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return saveButton;
    }

    public void setClient(CompanyClientModel clientModel) {
        this.clientModel = clientModel;
    }
}
