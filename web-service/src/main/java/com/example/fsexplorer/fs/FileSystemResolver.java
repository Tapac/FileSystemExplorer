package com.example.fsexplorer.fs;

import com.example.fsexplorer.api.NodeList;

import java.nio.file.ProviderNotFoundException;

/**
 * @author Andrey Tarashevsky
 *         Date: 05.02.13
 */
public interface FileSystemResolver {

    NodeList getAllChildren(String path) throws ProviderNotFoundException;

}
