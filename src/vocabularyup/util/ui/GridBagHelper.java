package vocabularyup.util.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

/**
 * Helper for {@link GridBagLayout}. Also contains some useful constraints.
 * 
 * @author dooman
 */
public class GridBagHelper {
    public static final Insets DEFAULT_INSETS = new Insets(5, 5, 5, 5);
    public static final GridBagConstraints LABEL_CONSTRAINT = 
            new GridBagConstraints(GridBagConstraints.RELATIVE, GridBagConstraints.RELATIVE, 
                                    1, 1, 
                                    0.0, 1.0, 
                                    GridBagConstraints.CENTER, 
                                    GridBagConstraints.BOTH, 
                                    DEFAULT_INSETS, 0, 0);

    public static final GridBagConstraints FIELD_CONSTRAINT = 
            new GridBagConstraints(GridBagConstraints.RELATIVE, GridBagConstraints.RELATIVE, 
                                    GridBagConstraints.REMAINDER, 1, 
                                    1.0, 1.0, 
                                    GridBagConstraints.CENTER, 
                                    GridBagConstraints.HORIZONTAL, 
                                    DEFAULT_INSETS, 0, 0);

    /**
     * Equals to {@code FIELD_CONSTRAINT}, but fill 3 rows.
     */
    public static final GridBagConstraints MULTIROW_FIELD_CONSTRAINT = 
            new GridBagConstraints(GridBagConstraints.RELATIVE, GridBagConstraints.RELATIVE, 
                                    GridBagConstraints.REMAINDER, 3, 
                                    1.0, 3.0, 
                                    GridBagConstraints.CENTER, 
                                    GridBagConstraints.BOTH, 
                                    DEFAULT_INSETS, 0, 0);

    private Container container;
    private GridBagLayout layout;

    /**
     * Create new helper to fill {@code container}.
     * @param container container for add component
     */
    public GridBagHelper(Container container) {
        this.container = container;
        layout = new GridBagLayout();
        container.setLayout(layout);
    }

    /**
     * Add component, that will be place in the left side and have fixed width.
     * @param component component that should be added.
     */
    public void addLabelComponent(Component component) {
        addComponent(component, LABEL_CONSTRAINT);
    }

    /**
     * Field, that will be expand.
     * @param component component that should be added.
     */
    public void addFieldComponent(Component component) {
        addComponent(component, FIELD_CONSTRAINT);
    }

    /**
     * Add component with specified constraints.
     * @param component component that should be added.
     * @param constraints constraints for {@code component}.
     */
    public void addComponent(Component component, GridBagConstraints constraints) {
        layout.setConstraints(component, constraints);
        container.add(component);
    }
}
