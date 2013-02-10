package com.example.fsexplorer.fs;

import com.example.fsexplorer.api.Node;
import com.example.fsexplorer.api.NodeList;
import com.sun.nio.zipfs.ZipPath;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrey Tarashevsky
 *         Date: 05.02.13
 */
public class FileSystemResolverImpl implements FileSystemResolver {

    private static final Logger log = LoggerFactory.getLogger(FileSystemResolverImpl.class);

    private static final String PATH_SPLITTER = "!#";

    private final Path rootPath;

    private final FileTypeFactory fileTypeFactory;

    public FileSystemResolverImpl(String rootPath, FileTypeFactory fileTypeFactory) {
        if (rootPath == null || rootPath.isEmpty()) {
            this.rootPath = FileSystems.getDefault().getRootDirectories().iterator().next();
        } else {
            this.rootPath = Paths.get(rootPath);
        }
        this.fileTypeFactory = fileTypeFactory;
    }

    @Override
    public NodeList getAllChildren(final String p) throws ProviderNotFoundException {

        String[] paths = p.split(PATH_SPLITTER, 2);

        Path path = Paths.get(rootPath.toString(), paths[0]);

        if (!Files.exists(path)) {
            return NodeList.EMPTY_NODE_LIST;
        }

        return (paths.length == 1) ? getFromPath(path) : getFromPath(path, paths[1]);
    }

    private NodeList getFromPath(Path root, String innerPath) throws ProviderNotFoundException {

        if (innerPath != null && fileTypeFactory.fromPath(root).isHandled()) {
            try (FileSystem fs = FileSystems.newFileSystem(root, FileSystemResolverImpl.class.getClassLoader())) {
                if (innerPath.isEmpty()) {
                    innerPath = fs.getSeparator();
                }
                Path path = fs.getPath(innerPath);
                return getFromPath(path);
            } catch (ProviderNotFoundException e) {
                log.warn("Can't open {}", root , e);
                throw new ProviderNotFoundException("Provider for " +
                        FileTypeFactory.getExtension(root) + " extension not found.");
            } catch (IOException e) {
                log.warn("Can't resolve file system from " + root);
                return NodeList.EMPTY_NODE_LIST;
            }
        }

        return NodeList.EMPTY_NODE_LIST;
    }

    private NodeList getFromPath(Path root) {

        List<Node> nodes = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(root)) {

            String archivePath = "";
            if (root instanceof ZipPath) {
                archivePath = root.getFileSystem().toString().substring(rootPath.toString().length());
            }

            for (Path entry : stream) {
                String strippedPath = entry.toString().substring(rootPath.toString().length());
                Node node = archivePath.isEmpty() ?
                        Node.createNode(entry.getFileName().toString(),
                                Base64.encodeBase64URLSafeString(strippedPath.getBytes()),
                                Files.size(entry), fileTypeFactory.fromPath(entry)) :
                        Node.createArchivedNode(entry.getFileName().toString(),
                                Base64.encodeBase64URLSafeString(archivePath.getBytes()),
                                PATH_SPLITTER + entry.toString(),
                                Files.size(entry), fileTypeFactory.fromPath(entry));
                nodes.add(node);
            }
        } catch (DirectoryIteratorException ex) {
            log.error("Something wrong with directory iteration", ex);
        } catch (IOException e) {
            log.error("Something happened on retrieving list of children of " + root, e);
            return NodeList.EMPTY_NODE_LIST;
        }

        return new NodeList(nodes);
    }
}
