package com.example.fsexplorer.api;

import com.example.fsexplorer.fs.FileType;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Andrey Tarashevsky
 *         Date: 02.02.13
 */
@XmlRootElement(name = "node")
public class Node {

    public final String fullFileName;
    public final String path;
    public final String size;
    public final String className;
    public final String handlerPath;
    public final String shortFileName;

    private Node(String fileName, String path, String size, String className, String handlerPath,
                 String shortFileName) {
        this.fullFileName = fileName;
        this.path = path;
        this.size = size;
        this.className = className;
        this.handlerPath = handlerPath;
        this.shortFileName = shortFileName;
    }


    public static Node createNode(String fileName, String path, long size, FileType type) {

        return new Node(fileName, path, humanReadableByteCount(size, true), type.getClassName(),
                type.getHandlerUrl(), shortenString(fileName, 25));
    }

    public static Node createArchivedNode(String fileName, String path,
                                          String innerPath, long size, FileType type) {
        return new Node(fileName, path, humanReadableByteCount(size, true), type.getClassName(),
                innerPath, shortenString(fileName, 25));
    }

    private static String shortenString(final String string, int size) {
        return (string.length() > size) ? string.substring(0, size) + "..." : string;
    }

    /**
     * Copy-paste from StackOverflow
     */
    public static String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes == 0) return "";
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }
}
