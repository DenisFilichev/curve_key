package com.example.curve_key.views;

import com.example.curve_key.entities.TasksEntity;
import com.example.curve_key.security.SecurityService;
import com.example.curve_key.servicies.TaskService;
import com.example.curve_key.views.components.*;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;
import java.util.HashSet;

@Route("tasks")
@RolesAllowed("ADMIN")
public class TaskPage extends AppLayout {

    private final SecurityService securityService;
    private final TaskService taskService;
    private final TaskGrid grid;
    private CompletedDialog completedDialog;
    private VerticalLayout contentLayout;

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

        contentLayout = new VerticalLayout();
        contentLayout.addClassName("list-view");
        setContent(contentLayout);
        completedDialog = new CompletedDialog(taskService);
        contentLayout.add(
                completedDialog,
                grid.getGrid()
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
}
