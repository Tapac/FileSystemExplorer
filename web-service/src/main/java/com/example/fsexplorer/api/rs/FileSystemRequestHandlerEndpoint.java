package com.example.fsexplorer.api.rs;

import com.example.fsexplorer.api.FileSystemRequestHandler;
import com.example.fsexplorer.api.Node;
import com.example.fsexplorer.api.NodeList;
import org.springframework.stereotype.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Arrays;

/**
 * @author Andrey Tarashevsky
 *         Date: 02.02.13
 */
@Service("fsExplorerWS")
public class FileSystemRequestHandlerEndpoint implements FileSystemRequestHandler {

    @GET
    @Path("/children")
    @Produces("application/json")
    @Override
    public NodeList getAllChildrenPaths(final String parentRoot) {
        return new NodeList(Arrays.asList(new Node("path1"), new Node("path2")));
    }

}
