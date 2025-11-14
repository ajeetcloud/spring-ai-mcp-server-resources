package com.spring.ai.mcpserver.resources;

import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class SpringAiMcpServerResourcesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAiMcpServerResourcesApplication.class, args);
    }

    @Bean
    public List<McpServerFeatures.SyncResourceSpecification> myResources() {
        String path = "F:\\Temp\\documents";
        Path folderPath = Paths.get(path);

        try(Stream<Path> paths = Files.list(folderPath)) {
            List<McpServerFeatures.SyncResourceSpecification> resources = paths
                    .map(this::getResourceSpecification)
                    .collect(Collectors.toList());

            return resources;
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    private McpServerFeatures.SyncResourceSpecification getResourceSpecification(Path filePath) {
        String fileName = filePath.getFileName().toString();
        Path absolutePath = filePath.toAbsolutePath();
        String fileUri = "file://" + absolutePath;
        String mimeType = "text/plain";

        var documentResource = new McpSchema.Resource(
                fileUri,
                fileName,
                "Document: " + fileName,
                mimeType,
                null
        );

        McpServerFeatures.SyncResourceSpecification spec = new McpServerFeatures.SyncResourceSpecification(documentResource,
                (exchange, request) -> {
                    try {
                        String textContent = Files.readString(absolutePath);
                        McpSchema.TextResourceContents textContents = new McpSchema.TextResourceContents(request.uri(), mimeType, textContent);

                        return new McpSchema.ReadResourceResult(List.of(textContents));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
        return spec;
    }


}
