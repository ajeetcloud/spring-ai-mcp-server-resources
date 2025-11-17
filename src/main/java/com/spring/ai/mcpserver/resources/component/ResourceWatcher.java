package com.spring.ai.mcpserver.resources.component;

import io.modelcontextprotocol.server.McpSyncServer;
import jakarta.annotation.PostConstruct;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;

@Component
public class ResourceWatcher {

    @Value("${resource.directory.path}")
    private String directoryPath;

    @Autowired
    private McpSyncServer mcpSyncServer;

    @PostConstruct
    public void start() throws Exception {

        FileAlterationObserver observer = new FileAlterationObserver(directoryPath);
        observer.addListener(new FileAlterationListenerAdaptor() {

            @Override
            public void onFileChange(final File file) {
                System.out.println("File created: " + file.getName());
                mcpSyncServer.notifyResourcesListChanged();
            }

        });
        new FileAlterationMonitor(2000, observer).start();
    }

}
