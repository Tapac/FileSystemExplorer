package com.example.fsexplorer.fs;

import com.example.fsexplorer.api.NodeList;

/**
 * @author Andrey Tarashevsky
 *         Date: 05.02.13
 */
public interface FileSystemResolver {

    NodeList getAllChildren(String path);

}
