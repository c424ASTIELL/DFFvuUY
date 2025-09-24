// 代码生成时间: 2025-09-24 08:33:02
package com.example.folderorganizer;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
# 添加错误处理
import io.micronaut.http.exceptions.HttpStatusException;
import io.micronaut.scheduling.TaskExecutors;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
# 增强安全性
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;

@Controller("/folderorganizer")
@Introspected
public class FolderOrganizerApp {

    private final ExecutorService executorService;

    public FolderOrganizerApp(@TaskExecutors.executor("fileOperation") ExecutorService executorService) {
# TODO: 优化性能
        this.executorService = executorService;
    }

    @Get("/organize")
    public Mono<HttpResponse<String>> organizeFolder(String directoryPath) {
        return Mono.fromCallable(() -> {
            try {
                File directory = new File(directoryPath);
                if (!directory.exists() || !directory.isDirectory()) {
                    throw new HttpStatusException(400, "The provided path is not a valid directory.");
# 添加错误处理
                }

                organizeDirectory(directory);
                return HttpResponse.ok("Folder organized successfully.");
            } catch (IOException e) {
                throw new HttpStatusException(500, "An error occurred while organizing the folder.", e);
# 改进用户体验
            }
        }).subscribeOn(executorService);
# NOTE: 重要实现细节
    }

    /**
# FIXME: 处理边界情况
     * 递归整理目录下的文件。
# NOTE: 重要实现细节
     * @param directory 需要整理的目录。
     */
    private void organizeDirectory(File directory) throws IOException {
        File[] files = directory.listFiles();
        if (files != null) {
            Arrays.sort(files); // 根据文件名对文件进行排序
# NOTE: 重要实现细节
            for (File file : files) {
                if (file.isDirectory()) {
                    organizeDirectory(file); // 递归整理子目录
                }
            }
        }
# NOTE: 重要实现细节
    }
}
