package com.example.fsexplorer.api.rs;

import com.example.fsexplorer.api.FileSystemRequestHandler;
import com.example.fsexplorer.api.NodeList;
import com.example.fsexplorer.fs.FileSystemResolver;
import org.springframework.stereotype.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * @author Andrey Tarashevsky
 *         Date: 02.02.13
 */
@Service("fsExplorerWS")
public class FileSystemRequestHandlerEndpoint implements FileSystemRequestHandler {

    private final FileSystemResolver fileSystemResolver;

    public FileSystemRequestHandlerEndpoint(FileSystemResolver fileSystemResolver) {
        this.fileSystemResolver = fileSystemResolver;
    }

    @GET
    @Path("/children/{path:.*}")
    @Produces("application/json")
    @Override
    public NodeList getAllChildrenPaths(@PathParam("path") final String parentRoot) {
        return fileSystemResolver.getAllChildren(parentRoot);
    }

}
