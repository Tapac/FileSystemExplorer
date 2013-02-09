package com.example.fsexplorer.api;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Objects;

/**
 * @author Andrey Tarashevsky
 *         Date: 02.02.13
 */
@XmlRootElement(name = "nodelist")
public class NodeList {

    public static final NodeList EMPTY_NODE_LIST = new NodeList(null);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NodeList nodeList = (NodeList) o;

        return Objects.equals(nodes, nodeList.nodes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodes);
    }
}
