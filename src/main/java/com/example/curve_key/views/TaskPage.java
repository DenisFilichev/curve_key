package com.example.curve_key.views;

import com.example.curve_key.entities.TasksEntity;
import com.example.curve_key.security.SecurityService;
import com.example.curve_key.servicies.TaskService;
import com.example.curve_key.views.components.*;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;
import java.util.HashSet;
import java.util.stream.Collectors;

@Route("tasks")
@RolesAllowed("ADMIN")
public class TaskPage extends AppLayout {

    private final SecurityService securityService;
    private final TaskService taskService;
    private final TaskGrid grid;
    private CompletedDialog completedDialog;
    private H2 countItemGrid;

    @Autowired
    public TaskPage(SecurityService securityService, TaskService taskService){
        this.securityService = securityService;
        this.taskService = taskService;
        grid = new TaskGrid(new HashSet<>(taskService.findAll()));

        createHeader();
        createContent();
        createContextMenu();
    }

    private void createContent(){
        Grid<TasksEntity> grid = this.grid.getGrid();
        filter(grid);
        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.addClassName("list-view");
        setContent(contentLayout);
        completedDialog = new CompletedDialog(taskService);
        countItemGrid = new H2("Записей: " + grid.getListDataView().getItemCount());
        contentLayout.add(
                completedDialog,
                countItemGrid,
                grid
        );
    }

    private void createContextMenu(){
        Grid<TasksEntity> grid = this.grid.getGrid();
        GridContextMenu<TasksEntity> menu = grid.addContextMenu();
        menu.addItem("Выполнено", event -> {
            completedDialog.setTask(event.getItem().orElse(null));
            completedDialog.open();
        });
    }

    private void createHeader() {
        H2 logo = new H2("Генератор ключей curve");
        Tab tab1 = createTab("ВПН клиенты", MainPage.class);
        Tab tab2 = createTab("Задачи админу", TaskPage.class);
        H2 username = new H2(securityService.getAuthenticatedUser().getUsername());
        logo.addClassNames("text-l", "m-m");
        Button logout = new Button("Выход", e -> securityService.logout());
        HorizontalLayout header = new HorizontalLayout(logo, tab1, tab2, username, logout);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");
        addToNavbar(header);
    }
    private Tab createTab(String viewName, Class<? extends Component> navigationTarget){
        RouterLink link = new RouterLink();
        link.add(viewName);
        link.setRoute(navigationTarget);
        link.setTabIndex(-1);
        return new Tab(link);
    }

    private void filter(Grid<TasksEntity> grid){
        GridListDataView<TasksEntity>dataView = grid.getListDataView();

        TextField microtikField = new TextField();
        microtikField.setWidthFull();
        {
            Object attribute = VaadinService.getCurrentRequest().getWrappedSession().getAttribute("microtikField");
            microtikField.setValue(attribute!=null ? (String)attribute : "");
        }
        microtikField.setValueChangeMode(ValueChangeMode.EAGER);
        microtikField.addValueChangeListener(e -> {
            dataView.refreshAll();
            countItemGrid.setText("Записей: " + dataView.getItemCount());
        });

        Select<String> isActiveField = new Select<>();
        isActiveField.setItems("true", "false");
        {
            Object attribute = VaadinService.getCurrentRequest().getWrappedSession().getAttribute("isActiveField");
            isActiveField.setValue(attribute!=null ? (String)attribute : "true");
        }
        isActiveField.addValueChangeListener(e -> {
            dataView.refreshAll();
            countItemGrid.setText("Записей: " + dataView.getItemCount());
        });


        dataView.addFilter(task -> {
            if (task == null) return false;
            boolean res = true;
            String microtikFieldTerm = microtikField.getValue().trim();
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("microtikField", microtikFieldTerm);
            String description = task.getTaskText().toLowerCase();
            if (!description.contains(microtikFieldTerm.toLowerCase())) res = false;

            String isActiveFieldTerm = isActiveField.getValue().trim().toLowerCase();
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("isActiveField", isActiveFieldTerm);
            String status = task.isActive() ? "true" : "false";
            if(!status.equals(isActiveFieldTerm)) res=false;

            return res;
        });

        grid.getHeaderRows().clear();
        HeaderRow headerRow = grid.appendHeaderRow();
        headerRow.getCell(grid.getColumnByKey("text")).setComponent(microtikField);
        headerRow.getCell(grid.getColumnByKey("is_active")).setComponent(isActiveField);
    }
}
