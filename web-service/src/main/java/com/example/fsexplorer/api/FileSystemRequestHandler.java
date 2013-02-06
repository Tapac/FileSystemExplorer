package com.example.fsexplorer.api;

import javax.ws.rs.core.Response;

/**
 * @author Andrey Tarashevsky
 *         Date: 02.02.13
 */
public interface FileSystemRequestHandler {

    NodeList getAllChildrenPaths(String parentRoot);
    Response getAllChildrenRendered(final String parentRoot);

}
