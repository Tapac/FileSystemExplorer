package com.example.fsexplorer.api;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

/**
 * @author Andrey Tarashevsky
 *         Date: 02.02.13
 */
@XmlRootElement(name = "nodelist")
public class NodeList {

    public final Collection<Node> nodes;

    public NodeList(Collection<Node> nodes) {
        this.nodes = nodes;
    }


    /**
     * Accessor for mustache template
     * @return list on nodes
     */
    public Collection<Node> nodes() {
        return nodes;
    }
}
