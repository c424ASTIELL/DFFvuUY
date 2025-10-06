// 代码生成时间: 2025-10-07 02:06:26
package ai.model.version.manager;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class manages the versioning of AI models.
 * It provides methods to list, retrieve, add, and delete model versions.
 */
@Controller("/api/model-versions")
@Introspected
public class AIModelVersionManager {

    private final List<AIModelVersion> modelVersions = new ArrayList<>();

    /**
     * Lists all AI model versions.
     *
     * @return A list of AI model versions.
     */
    @Get("/list")
    public HttpResponse<List<AIModelVersion>> listModelVersions() {
        return HttpResponse.ok(modelVersions);
    }

    /**
     * Retrieves a specific AI model version by its version number.
     *
     * @param version The version number of the model.
     * @return The AI model version if found, otherwise an error response.
     */
    @Get("/{version}")
    public HttpResponse<AIModelVersion> getModelVersion(@PathVariable String version) {
        Optional<AIModelVersion> optionalModelVersion = modelVersions.stream()
                .filter(v -> v.getVersion().equals(version))
                .findFirst();

        return optionalModelVersion
                .map(HttpResponse::ok)
                .orElse(HttpResponse.notFound());
    }

    /**
     * Adds a new AI model version.
     *
     * @param modelVersion The AI model version to be added.
     * @return The added AI model version.
     */
    public HttpResponse<AIModelVersion> addModelVersion(AIModelVersion modelVersion) {
        if (modelVersion.getVersion() == null || modelVersion.getVersion().isEmpty()) {
            return HttpResponse.badRequest("Model version is required");
        }

        modelVersions.add(modelVersion);
        return HttpResponse.created(modelVersion);
    }

    /**
     * Deletes an AI model version by its version number.
     *
     * @param version The version number of the model to be deleted.
     * @return An OK response if deletion is successful, otherwise a not found response.
     */
    public HttpResponse<Void> deleteModelVersion(String version) {
        modelVersions.removeIf(v -> v.getVersion().equals(version));
        return HttpResponse.ok();
    }
}

/**
 * Represents an AI model version.
 */
@Introspected
class AIModelVersion {
    private String version;
    private String modelData;
    private String metadata;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getModelData() {
        return modelData;
    }

    public void setModelData(String modelData) {
        this.modelData = modelData;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}
