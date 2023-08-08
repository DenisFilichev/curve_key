package com.example.curve_key.views.components;

import com.example.curve_key.entities.TasksEntity;
import com.example.curve_key.views.models.CompanyClientModel;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

public class TaskGrid {
    private Grid<TasksEntity> grid;
    private Set<TasksEntity> tasks;

    @Autowired
    public TaskGrid(Set<TasksEntity> tasks){
        this.tasks = tasks.stream().filter(TasksEntity::isActive).collect(Collectors.toSet());
        init();
    }

    private void init() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        grid = new Grid<>(TasksEntity.class, false);
        grid.setHeight(500, Unit.PIXELS);
        grid.addColumn(TasksEntity::getTaskText).setKey("text").setHeader("Строка микротика").setFlexGrow(0).setWidth("60%").setResizable(true);
        grid.addColumn(e->{
            Instant instant = e.getDateGenerate();
            return instant==null ? "" :
                    instant.atZone(ZoneId.of("Europe/Moscow").normalized()).format(formatter);
        }).setKey("date_generate").setHeader("Дата создания").setFlexGrow(0).setAutoWidth(true).setResizable(true);
        grid.addColumn(e->{
            Instant instant = e.getDateExecution();
            return instant==null ? "" :
                    instant.atZone(ZoneId.of("Europe/Moscow").normalized()).format(formatter);
        }).setKey("date_execution").setHeader("дата выполнения").setFlexGrow(0).setAutoWidth(true).setResizable(true);
        grid.addColumn(TasksEntity::isActive).setKey("is_active").setHeader("is active").setFlexGrow(0).setAutoWidth(true).setResizable(true);
        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        grid.setItems(tasks);
    }

    public Grid<TasksEntity> getGrid(){
        return grid;
    }

}
