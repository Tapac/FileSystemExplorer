package com.example.fsexplorer.fs;

import org.jetbrains.annotations.NotNull;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Andrey Tarashevsky
 *         Date: 05.02.13
 */
public class FileTypeFactory {

    public static final FileType FOLDER = new FileType("folder", Collections.<String>emptyList(), "/default");
    public static final FileType UNKNOWN = new FileType("unknown", Collections.<String>emptyList(), "");

    private List<FileType> fileTypes;

    public FileTypeFactory(List<FileType> fileTypes) {
        this.fileTypes = fileTypes;
    }

    /**
     * Factory method that returns concrete {@link FileType} depends on file extension,
     * or null if path doesn't exist.
     *
     * if path points to folder then {@link FileTypeFactory#FOLDER} returns,
     * or {@link FileTypeFactory#UNKNOWN} for not mapped extensions.
     *
     * @param path path to file or directory
     * @return concrete FileType or null on non existed path
     */
    public FileType fromPath(@NotNull final Path path) {

        Objects.requireNonNull(path);

        if (!Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
            return null;
        }

        if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
            return FOLDER;
        }

        String ext = com.google.common.io.Files.getFileExtension(path.toFile().getName());
        for (FileType type : fileTypes) {
            if (type.getExtensions().indexOf(ext) > -1) {
                return type;
            }
        }

        return UNKNOWN;
    }


}
