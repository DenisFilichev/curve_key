package com.example.curve_key.views.components;

import com.example.curve_key.entities.TasksEntity;
import com.example.curve_key.entities.VpnServerEntity;
import com.example.curve_key.servicies.TaskService;
import com.example.curve_key.servicies.VpnServerService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.time.Instant;

public class CompletedDialog extends Dialog {

    private final TaskService taskService;
    private TasksEntity task;

    public CompletedDialog(TaskService taskService) {
        this.taskService = taskService;
        setModal(true);
        setHeaderTitle("Закрытие задачи");
        VerticalLayout dialogLayout = createDialogLayout();
        add(dialogLayout);
        Button saveButton = createSaveButton();
        Button cancelButton = new Button("Отмена", e -> close());
        getFooter().add(cancelButton);
        getFooter().add(saveButton);
    }

    public void setTask(TasksEntity task) {
        this.task = task;
    }

    private VerticalLayout createDialogLayout() {
        H3 title = new H3("Перевести задачу в статус \"выполнено\"");
        VerticalLayout dialogLayout = new VerticalLayout(title);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "18rem").set("max-width", "100%");
        return dialogLayout;
    }

    private Button createSaveButton() {
        Button saveButton = new Button("Сохранить", e -> {
            if(task!=null && task.isActive()) {
                task.setActive(false);
                task.setDateExecution(Instant.now());
                taskService.save(task);
            }
            close();
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return saveButton;
    }
}
