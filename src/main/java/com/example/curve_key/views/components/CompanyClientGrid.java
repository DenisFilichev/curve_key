package com.example.curve_key.views.components;

import com.example.curve_key.entities.CompanyEntity;
import com.example.curve_key.views.models.CompanyClientModel;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.grid.Grid;

import java.util.Set;

public class CompanyClientGrid {

    private Grid<CompanyClientModel> grid;
    private final Set<CompanyClientModel> companies;

    public CompanyClientGrid(Set<CompanyEntity> companies){
        this.companies = CompanyClientModel.getInstancies(companies);
        init();
    }

    private void init() {
        grid = new Grid<>(CompanyClientModel.class, false);
        grid.setHeight(500, Unit.PIXELS);
        grid.addColumn(CompanyClientModel::getCompanyName).setSortable(true).setKey("company_name").setHeader("Компания").setAutoWidth(true);
        grid.addColumn(CompanyClientModel::getClientName).setSortable(true).setKey("client_name").setHeader("Клиент").setAutoWidth(true);
        grid.addColumn(CompanyClientModel::getAllowedAddress).setSortable(true).setKey("allowed_address").setHeader("IP адрес").setAutoWidth(true);
        grid.addColumn(CompanyClientModel::getServername).setSortable(true).setKey("server_name").setHeader("Сервер").setAutoWidth(true);
        grid.addColumn(CompanyClientModel::getEndpoint).setSortable(true).setKey("endpoint").setHeader("Адрес сервера").setAutoWidth(true);
        grid.setItems(companies);
    }

    public Grid<CompanyClientModel> getGrid(){
        return grid;
    }

}
