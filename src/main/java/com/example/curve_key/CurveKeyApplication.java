package com.example.curve_key;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@SpringBootApplication
public class CurveKeyApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurveKeyApplication.class, args);
    }

    @Component
    private static class PropertyConfig extends PropertySourcesPlaceholderConfigurer {
        public PropertyConfig() throws IOException {
            String separator = File.separator;
            File dir = new File("conf");
            dir.mkdir();
            String URL = separator.equals("\\") ? dir.getAbsolutePath().replaceAll("\\\\", "/") : dir.getAbsolutePath();
            System.out.println();
            System.out.println("---------------" + URL + "----------------");
            System.out.println();
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            List<Resource> resources = new ArrayList<>();
            System.out.println("URL=" + URL);
            resources.addAll(Arrays.asList(resolver.getResources("classpath:/*.properties")));
            resources.addAll(Arrays.asList(resolver.getResources("file:" + URL + "/*.properties")));

            Resource[] resourcesArray = new Resource[resources.size()];
            setLocations(resources.toArray(resourcesArray));
            for(Resource r : resourcesArray){
                System.out.println(r.getFilename());
            }
            Arrays.sort(resourcesArray, Comparator.comparing(Resource::getFilename));
            setIgnoreUnresolvablePlaceholders(true);
        }
    }

}
