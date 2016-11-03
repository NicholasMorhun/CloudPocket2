package com.cloudpocket.statelisteners;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Executes after application start.
 */
@Component
public class OnStartCloudPocketApp implements ApplicationListener<ApplicationReadyEvent> {

    @Value("${cloudpocket.storage}")
    private String PATH_TO_STORAGE;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        createAppRootDirectory();
    }

    /**
     * Creates application storage directory if necessary.
     * Does nothing if directory already exists.
     */
    private void createAppRootDirectory() {
        Path appStorageRootPath = Paths.get(new File(PATH_TO_STORAGE).getAbsolutePath()).normalize();
        if (!Files.exists(appStorageRootPath)) {
            try {
                Files.createDirectories(appStorageRootPath);
            } catch (IOException e) {
                System.out.println("Failed to create application root directory: '" + PATH_TO_STORAGE +
                                   "'. Cause: " + e.getMessage() + " Application will be terminated.");
                System.exit(0);
            }
        }
    }

}
