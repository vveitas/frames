package com.tinkerpop.frames;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import com.tinkerpop.blueprints.impls.tg.TinkerGraphFactory;
import com.tinkerpop.frames.domain.classes.Person;
import com.tinkerpop.frames.domain.classes.Project;
import com.tinkerpop.frames.domain.incidences.Created;
import com.tinkerpop.frames.domain.incidences.CreatedBy;
import junit.framework.TestCase;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class FramedElementTest extends TestCase {

    public void testGettingProperties() {
        Graph graph = TinkerGraphFactory.createTinkerGraph();
        FramedGraph<Graph> framedGraph = new FramedGraph<Graph>(graph);

        Person marko = framedGraph.getVertex(1, Person.class);
        assertEquals(marko.getName(), "marko");
        assertEquals(marko.getAge(), new Integer(29));

        Project lop = framedGraph.getVertex(3, Project.class);
        assertEquals(lop.getName(), "lop");
        assertEquals(lop.getLanguage(), "java");

        Created markoCreatedLop = framedGraph.getEdge(9, Direction.OUT, Created.class);
        assertEquals(markoCreatedLop.getWeight(), 0.4f);

        CreatedBy lopCreatedByMarko = framedGraph.getEdge(9, Direction.IN, CreatedBy.class);
        assertEquals(lopCreatedByMarko.getWeight(), 0.4f);

        Person temp = framedGraph.frame(graph.addVertex(null), Person.class);
        assertNull(temp.getName());
        assertNull(temp.getAge());

    }

    public void testSettingProperties() {
        Graph graph = TinkerGraphFactory.createTinkerGraph();
        FramedGraph<Graph> framedGraph = new FramedGraph<Graph>(graph);

        Person marko = framedGraph.getVertex(1, Person.class);
        assertEquals(marko.getName(), "marko");
        marko.setName("pavel");
        assertEquals(marko.getName(), "pavel");
        assertEquals(marko.getAge(), new Integer(29));
        marko.setAge(31);
        assertEquals(marko.getAge(), new Integer(31));

        Created markoCreatedLop = framedGraph.getEdge(9, Direction.OUT, Created.class);
        assertEquals(markoCreatedLop.getWeight(), 0.4f);
        markoCreatedLop.setWeight(99.0f);
        assertEquals(markoCreatedLop.getWeight(), 99.0f);
    }

    public void testRemoveProperties() {
        Graph graph = TinkerGraphFactory.createTinkerGraph();
        FramedGraph<Graph> framedGraph = new FramedGraph<Graph>(graph);

        Person marko = framedGraph.getVertex(1, Person.class);
        assertEquals(marko.getAge(), new Integer(29));
        marko.removeAge();
        assertNull(marko.getAge());
    }

    public void testSetPropertiesToNull() {
        Graph graph = TinkerGraphFactory.createTinkerGraph();
        FramedGraph<Graph> framedGraph = new FramedGraph<Graph>(graph);

        Person marko = framedGraph.getVertex(1, Person.class);
        assertEquals(marko.getAge(), new Integer(29));
        marko.setAge(null);
        assertNull(marko.getAge());
    }

    public void testToString() {
        Graph graph = TinkerGraphFactory.createTinkerGraph();
        FramedGraph<Graph> framedGraph = new FramedGraph<Graph>(graph);

        Person marko = framedGraph.getVertex(1, Person.class);
        assertEquals("v[1]", marko.toString());

        Created markoCreatedLop = framedGraph.getEdge(9, Direction.OUT, Created.class);
        assertEquals("e[9][1-created->3]", markoCreatedLop.toString());
    }

    public void testEquality() {
        TinkerGraph graph = TinkerGraphFactory.createTinkerGraph();
        FramedGraph<TinkerGraph> framedGraph = new FramedGraph<TinkerGraph>(graph);

        assertEquals(framedGraph.getVertex(1, Person.class), framedGraph.frame(graph.getVertex(1), Person.class));

    }
}
