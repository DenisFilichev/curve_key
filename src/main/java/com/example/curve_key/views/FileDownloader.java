package com.example.curve_key.views;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.StreamResource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FileDownloader extends VerticalLayout {

    private final File uploadFolder;

    public FileDownloader(File uploadFolder) {
        this.uploadFolder = uploadFolder;
        refreshFileLinks();
        setMargin(true);
    }

    public void refreshFileLinks() {
        removeAll();
        add(new H4("Download Links:"));
        addLinkToFile(uploadFolder);
    }

    private void addLinkToFile(File file) {
        StreamResource streamResource = new StreamResource(file.getName(), () -> getStream(file));
        Anchor link = new Anchor(streamResource, String.format("%s (%d B)", file.getName(),
                (int) file.length()));
        link.getElement().setAttribute("download", true);
        link.onEnabledStateChanged(true);
        add(link);
    }

    public InputStream getStream(File file) {
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return stream;
    }
}
