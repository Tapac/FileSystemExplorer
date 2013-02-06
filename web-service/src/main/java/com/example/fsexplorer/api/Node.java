package com.example.fsexplorer.api;

import com.example.fsexplorer.fs.FileType;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Andrey Tarashevsky
 *         Date: 02.02.13
 */
@XmlRootElement(name = "node")
public class Node {

    public final String fileName;
    public final String path;
    public final long size;
    public final String className;
    public final String handlerPath;

    private Node(String fileName, String path, long size, String className, String handlerPath) {
        this.fileName = fileName;
        this.path = path;
        this.size = size;
        this.className = className;
        this.handlerPath = handlerPath;
    }


    public static Node createNode(String fileName, String path, long size, FileType type) {
        return new Node(fileName, path, size, type.getClassName(), type.getHandlerUrl());
    }
}
