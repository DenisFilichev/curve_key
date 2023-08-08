package com.example.curve_key.views;

import com.example.curve_key.security.SecurityService;
import com.example.curve_key.servicies.CompanyService;
import com.example.curve_key.servicies.VpnClientService;
import com.example.curve_key.servicies.VpnServerService;
import com.example.curve_key.views.components.*;
import com.example.curve_key.views.models.CompanyClientModel;
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
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.annotation.security.RolesAllowed;

@Route("")
@RolesAllowed("ADMIN")
public class MainPage extends AppLayout {

    private final SecurityService securityService;
    private final CompanyService companyService;
    private final VpnClientService clientService;
    private final VpnServerService serverService;
    private final CompanyClientGrid grid;
    private CreateCompanyDialog createCompanyDialog;
    private CreateClientDialog createClientDialog;
    private CreateServerDialog createServerDialog;
    private GeneratorDialog generatorDialog;
    private VerticalLayout contentLayout;

    @Autowired
    public MainPage(SecurityService securityService, CompanyService companyService, VpnClientService clientService, VpnServerService serverService){
        this.securityService = securityService;
        this.companyService = companyService;
        this.clientService = clientService;
        this.serverService = serverService;
        grid = new CompanyClientGrid(companyService.findFullAll());
        createHeader();
        createContent();
        createContextMenu();
    }

//    @Lookup
//    private CreateClientDialog getClientDialog(){
//        return null;
//    }

    private void createContent(){

        contentLayout = new VerticalLayout();
        contentLayout.addClassName("list-view");
        setContent(contentLayout);
        createCompanyDialog = new CreateCompanyDialog(companyService);
        createClientDialog = new CreateClientDialog(clientService);
        createServerDialog = new CreateServerDialog(serverService);
        generatorDialog = new GeneratorDialog(clientService);
        contentLayout.add(
                createCompanyDialog,
                createClientDialog,
                createServerDialog,
                generatorDialog,
                grid.getGrid()
        );
    }

    private void createContextMenu(){
        Grid<CompanyClientModel> grid = this.grid.getGrid();
        GridContextMenu<CompanyClientModel> menu = grid.addContextMenu();
        menu.addItem("Создать клиента", event -> {
            createClientDialog.setClientModel(event.getItem().orElse(null));
            createClientDialog.open();
        });
        menu.addItem("Сгенерировать ключи", event -> {
            generatorDialog.setClient(event.getItem().orElse(null));
            generatorDialog.open();
        });
    }

    private void createHeader() {
        H2 logo = new H2("Генератор ключей curve");
        Tab tab1 = createTab("ВПН клиенты", MainPage.class);
        Tab tab2 = createTab("Задачи админу", TaskPage.class);
        Button button1 = new Button("Новая компания");
        button1.addClickListener(e -> {
            createCompanyDialog.open();
        });
        Button button2 = new Button("Новый впн-сервер");
        button2.addClickListener(e -> {
            createServerDialog.open();
        });
        H2 username = new H2(securityService.getAuthenticatedUser().getUsername());
        logo.addClassNames("text-l", "m-m");
        Button logout = new Button("Выход", e -> securityService.logout());
        HorizontalLayout header = new HorizontalLayout(logo, tab1, tab2, button1, button2, username, logout);
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
