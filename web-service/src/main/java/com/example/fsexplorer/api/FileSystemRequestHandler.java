package com.example.fsexplorer.api;

/**
 * @author Andrey Tarashevsky
 *         Date: 02.02.13
 */
public interface FileSystemRequestHandler {

    NodeList getAllChildrenPaths(String parentRoot);
}
