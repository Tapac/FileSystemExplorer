package com.example.fsexplorer.api;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Andrey Tarashevsky
 *         Date: 02.02.13
 */
@XmlRootElement(name = "node")
public class Node {

    public final String path;
    public String type = "folder";

    public Node(String path) {
        this.path = path;
    }

    public Node(String path, String type) {
        this.path = path;
        this.type = type;
    }
}
