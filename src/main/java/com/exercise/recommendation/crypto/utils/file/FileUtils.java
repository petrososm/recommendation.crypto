package com.exercise.recommendation.crypto.utils.file;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

public class FileUtils {
    public static Resource[] getResourcesFromClasspath(String path, ClassLoader classLoader) throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(classLoader);
        Resource resources[] = resolver.getResources("classpath:"+path+"/**");
        return resources;
    }
}
