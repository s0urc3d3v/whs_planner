package WHS_planner.Calendar;

import javafx.animation.TranslateTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Code is written by Stack Overflow users: Chui Tey and corrected/fixed by jewelsea
 * THIS CLASS IS FROM ->
 * https://gist.github.com/jewelsea/5683558
 * http://stackoverflow.com/questions/16828234/animation-upon-layout-changes
 *
 * This class has been modified by adding a "durationInMilli" variable to easily modify
 * the duration of the animations.
 */

/**
 * Animates an object when its position is changed. For instance, when
 * additional items are added to a Region, and the layout has changed, then the
 * layout animator makes the transition by sliding each item into its final
 * place.
 */
public class LayoutAnimator implements ChangeListener<Number>, ListChangeListener<Node> {

    private int durationInMilli = 250;
    private Map<Node, TranslateTransition> nodeXTransitions = new HashMap<>();
    private Map<Node, TranslateTransition> nodeYTransitions = new HashMap<>();

    /**
     * Animates all the children of a Region.
     * <code>
     *   VBox myVbox = new VBox();
     *   LayoutAnimator animator = new LayoutAnimator();
     *   animator.observe(myVbox.getChildren());
     * </code>
     *
     * @param nodes
     */
    public void observe(ObservableList<Node> nodes) {
        for (Node node : nodes) {
            this.observe(node);
        }
        nodes.addListener(this);
    }

    public void unobserve(ObservableList<Node> nodes) {
        nodes.removeListener(this);
    }

    public void observe(Node n) {
        n.layoutXProperty().addListener(this);
        n.layoutYProperty().addListener(this);
    }

    public void unobserve(Node n) {
        n.layoutXProperty().removeListener(this);
        n.layoutYProperty().removeListener(this);
    }

    @Override
    public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
        final double delta = newValue.doubleValue() - oldValue.doubleValue();
        final DoubleProperty doubleProperty = (DoubleProperty) ov;
        final Node node = (Node) doubleProperty.getBean();

        TranslateTransition t;
        switch (doubleProperty.getName()) {
            case  "layoutX":
                t = nodeXTransitions.get(node);
                if (t == null) {
                    t = new TranslateTransition(Duration.millis(durationInMilli), node);
                    t.setToX(0);
                    nodeXTransitions.put(node, t);
                }
                t.setFromX(node.getTranslateX() - delta);
                node.setTranslateX(node.getTranslateX() - delta);
                break;

            default: // "layoutY"
                t = nodeYTransitions.get(node);
                if (t == null) {
                    t = new TranslateTransition(Duration.millis(durationInMilli), node);
                    t.setToY(0);
                    nodeYTransitions.put(node, t);
                }
                t.setFromY(node.getTranslateY() - delta);
                node.setTranslateY(node.getTranslateY() - delta);
        }

        t.playFromStart();
    }

    @Override
    public void onChanged(Change change) {
        while (change.next()) {
            if (change.wasAdded()) {
                for (Node node : (List<Node>) change.getAddedSubList()) {
                    this.observe(node);
                }
            } else if (change.wasRemoved()) {
                for (Node node : (List<Node>) change.getRemoved()) {
                    this.unobserve(node);
                }
            }
        }
    }
}