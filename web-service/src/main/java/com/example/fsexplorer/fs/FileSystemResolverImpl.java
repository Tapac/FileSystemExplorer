package com.example.fsexplorer.fs;

import com.example.fsexplorer.api.Node;
import com.example.fsexplorer.api.NodeList;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrey Tarashevsky
 *         Date: 05.02.13
 */
public class FileSystemResolverImpl implements FileSystemResolver {

    private static final Logger log = LoggerFactory.getLogger(FileSystemResolverImpl.class);

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
    public NodeList getAllChildren(final String p) {
        Path path = Paths.get(rootPath.toString(), p);

        if (!Files.exists(path)) {
            return NodeList.EMPTY_NODE_LIST;
        }

        List<Node> nodes = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {

            for (Path entry : stream) {
                String strippedPath = entry.toString().substring(rootPath.toString().length());
                Node node = Node.createNode(entry.getFileName().toString(),
                        Base64.encodeBase64URLSafeString(strippedPath.getBytes()),
                        Files.size(entry), fileTypeFactory.fromPath(entry));
                nodes.add(node);
            }
        } catch (DirectoryIteratorException ex) {
            log.error("Something wrong with directory iteration", ex);
        } catch (IOException e) {
            log.error("Something happened on retrieving list of children of " + p, e);
            return NodeList.EMPTY_NODE_LIST;
        }

        return new NodeList(nodes);
    }
}
