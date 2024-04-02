package net.gaox.excel.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * <p> 使用nio的重命名 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-04 00:32
 */
@Slf4j
public class RenameReadme {

    public static void main(String[] args) {
        // 指定要遍历查找README.md的根目录
        String sourceDirectory = "/Users/gaox/codeing/gitee/aliyun";

        // 指定要移动到的目标目录
        String targetDirectory = "/Users/gaox/codeing/gitee/aliyun";

        try {
            Files.walk(Paths.get(sourceDirectory))
                    // .filter(path -> path.toString().endsWith("/README.md"))
                    .forEach(path -> renameAndMoveReadme(path, Paths.get(targetDirectory)));
        } catch (IOException e) {
            System.err.println("Error occurred while renaming and moving README.md files: " + e.getMessage());
        }
    }

    private static void renameAndMoveReadme(Path readmePath, Path targetDirectory) {
        Path directoryPath = readmePath.getParent();
        if (!directoryPath.startsWith(readmePath.getRoot())) {
            throw new IllegalArgumentException("Invalid directory structure");
        }
        String folderName = directoryPath.getFileName().toString();

        // 构造目标文件路径
        Path newFilePath = targetDirectory.resolve(folderName + ".md");

        try {
            // 重命名并移动文件
            Files.move(readmePath, newFilePath);
            log.info("Successfully renamed and moved [{}] to [{}].", readmePath, newFilePath);
        } catch (IOException e) {
            log.error(String.format("Failed to rename and move path = [%s]", readmePath), e);
        }
    }

}
