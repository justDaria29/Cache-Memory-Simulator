import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class View {
    private JFrame frame;
    private JTextField cacheSizeField;
    private JTextField mainMemorySizeField;
    private JComboBox<String> mappingStrategyBox;
    private JTextField associativityField;
    private JComboBox<String> replacementPolicyBox;
    private JTextField numAccessesField;
    private JButton startButton;
    private JTextArea outputTextArea;
    private JButton runTestsButton;

    /////////////
    private JTextField addressField;
    private JTextField valueField;
    private JButton addDataButton;
    private JTable cacheTable;
    private DefaultTableModel tableModel;

    ///////////////////

    public View() {
        prepareGui();
    }

//    private void prepareGui() {
//        frame = new JFrame("Cache Simulator");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(600, 400);
//
//        frame.setLocationRelativeTo(null);
//
//        JPanel inputPanel = new JPanel(new GridLayout(7, 2));
//
//        inputPanel.add(new JLabel("Cache Size (KB):"));
//        cacheSizeField = new JTextField();
//        cacheSizeField.setToolTipText("Enter the size of the cache in kilobytes.");
//        inputPanel.add(cacheSizeField);
//
//        inputPanel.add(new JLabel("Main Memory Size (KB):"));
//        mainMemorySizeField = new JTextField();
//        mainMemorySizeField.setToolTipText("Enter the size of the main memory in kilobytes.");
//        inputPanel.add(mainMemorySizeField);
//
//        inputPanel.add(new JLabel("Mapping Strategy:"));
//        mappingStrategyBox = new JComboBox<>(new String[]{"Direct Mapping", "Fully Associative", "Set Associative Mapping"});
//        mappingStrategyBox.setToolTipText("Choose the cache mapping strategy.");
//        inputPanel.add(mappingStrategyBox);
//
//        inputPanel.add(new JLabel("Associativity (only for Set Associative):"));
//        associativityField = new JTextField();
//        associativityField.setEnabled(false); // Disabled by default
//        associativityField.setToolTipText("Enter the number of lines per set (only for Set Associative Mapping).");
//        inputPanel.add(associativityField);
//
//        inputPanel.add(new JLabel("Replacement Policy:"));
//        replacementPolicyBox = new JComboBox<>(new String[]{"FIFO", "LRU", "Random"});
//        replacementPolicyBox.setToolTipText("Choose the cache replacement policy.");
//        inputPanel.add(replacementPolicyBox);
//
//        inputPanel.add(new JLabel("Number of Accesses:"));
//        numAccessesField = new JTextField();
//        numAccessesField.setToolTipText("Enter the number of memory accesses to simulate.");
//        inputPanel.add(numAccessesField);
//
//        startButton = new JButton("Start Simulation");
//        inputPanel.add(startButton);
//
//        runTestsButton = new JButton("Run Tests");
//        inputPanel.add(runTestsButton);
//        //////
////        inputPanel.add(new JLabel("Memory Address:"));
////        addressField = new JTextField();
////        inputPanel.add(addressField);
////
////        inputPanel.add(new JLabel("Value to Write:"));
////        valueField = new JTextField();
////        inputPanel.add(valueField);
////
////        addDataButton = new JButton("Add Data");
////        inputPanel.add(addDataButton);
//
//        inputPanel.add(new JLabel("Memory Address:"));
//        addressField = new JTextField();
//        addressField.setToolTipText("Enter the memory address to write to.");
//        inputPanel.add(addressField);
//
//        inputPanel.add(new JLabel("Value to Write:"));
//        valueField = new JTextField();
//        valueField.setToolTipText("Enter the value to write at the specified address.");
//        inputPanel.add(valueField);
//
//        // Add the "Add Data" button
//        addDataButton = new JButton("Add Data");
//        inputPanel.add(addDataButton);
//
//        // Adjust layout if necessary
//        // For example, add an empty label to fill the grid
//        inputPanel.add(new JLabel(""));
//        ///////////////
//
//        // Output area for results
//        outputTextArea = new JTextArea();
//        outputTextArea.setEditable(false);
//        JScrollPane scrollPane = new JScrollPane(outputTextArea);
//        scrollPane.setBorder(BorderFactory.createTitledBorder("Simulation Results"));
//
//        JTabbedPane tabbedPane = new JTabbedPane();
//        tabbedPane.addTab("Configuration", inputPanel);
//        tabbedPane.addTab("Results", scrollPane);
//
//        frame.add(tabbedPane);
//        frame.setVisible(true);
//
//        // Enable/disable associativity field dynamically
//        mappingStrategyBox.addItemListener(e -> {
//            if ("Set Associative Mapping".equals(mappingStrategyBox.getSelectedItem())) {
//                associativityField.setEnabled(true);
//            } else {
//                associativityField.setEnabled(false);
//                associativityField.setText(""); // Clear field when disabled
//            }
//        });
//    }

    private void prepareGui() {
        frame = new JFrame("Cache Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        // Main panel with GridBagLayout for precise component positioning
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5); // Padding between components
        gbc.anchor = GridBagConstraints.WEST; // Align labels to the left
        gbc.fill = GridBagConstraints.HORIZONTAL; // Stretch text fields horizontally
        gbc.weightx = 1; // Allow horizontal expansion
        gbc.gridx = 0; // Start from column 0
        gbc.gridy = 0; // Start from row 0

        // Row 1: Cache Size
        inputPanel.add(new JLabel("Cache Size (KB):"), gbc);
        gbc.gridx = 1; // Move to the next column
        cacheSizeField = new JTextField();
        inputPanel.add(cacheSizeField, gbc);

        // Row 2: Main Memory Size
        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Main Memory Size (KB):"), gbc);
        gbc.gridx = 1;
        mainMemorySizeField = new JTextField();
        inputPanel.add(mainMemorySizeField, gbc);

        // Row 3: Mapping Strategy
        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Mapping Strategy:"), gbc);
        gbc.gridx = 1;
        mappingStrategyBox = new JComboBox<>(new String[]{"Direct Mapping", "Fully Associative", "Set Associative Mapping"});
        inputPanel.add(mappingStrategyBox, gbc);

        // Row 4: Associativity
        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Associativity (Set Associative):"), gbc);
        gbc.gridx = 1;
        associativityField = new JTextField();
        associativityField.setEnabled(false);
        inputPanel.add(associativityField, gbc);

        // Row 5: Replacement Policy
        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Replacement Policy:"), gbc);
        gbc.gridx = 1;
        replacementPolicyBox = new JComboBox<>(new String[]{"FIFO", "LRU", "Random"});
        inputPanel.add(replacementPolicyBox, gbc);

        // Row 6: Number of Accesses
        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Number of Accesses:"), gbc);
        gbc.gridx = 1;
        numAccessesField = new JTextField();
        inputPanel.add(numAccessesField, gbc);

        // Row 7: Memory Address (For Add Data)
        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Memory Address:"), gbc);
        gbc.gridx = 1;
        addressField = new JTextField();
        inputPanel.add(addressField, gbc);

        // Row 8: Value to Write
        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Value to Write:"), gbc);
        gbc.gridx = 1;
        valueField = new JTextField();
        inputPanel.add(valueField, gbc);

        // Row 9: Buttons
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1; // Reset gridwidth to 1
        startButton = new JButton("Start Simulation");
        inputPanel.add(startButton, gbc);

        gbc.gridx = 1;
        runTestsButton = new JButton("Run Tests");
        inputPanel.add(runTestsButton, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2; // Make button span two columns
        addDataButton = new JButton("Add Data");
        inputPanel.add(addDataButton, gbc);

        tableModel = new DefaultTableModel(new Object[]{"Index", "Tag", "Data", "Valid", "Dirty"}, 0);
        cacheTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(cacheTable);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("Cache State"));


        // Output area for results
        outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Simulation Results"));

        // Add the inputPanel and output area to the main frame
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Configuration", inputPanel);
        tabbedPane.addTab("Cache State", tableScrollPane);
        tabbedPane.addTab("Results", scrollPane);

        frame.add(tabbedPane);
        frame.setVisible(true);

        // Enable/disable associativity field dynamically
        mappingStrategyBox.addItemListener(e -> {
            if ("Set Associative Mapping".equals(mappingStrategyBox.getSelectedItem())) {
                associativityField.setEnabled(true);
            } else {
                associativityField.setEnabled(false);
                associativityField.setText(""); // Clear field when disabled
            }
        });
    }


    public int getCacheSize() {
        return Integer.parseInt(cacheSizeField.getText());
    }

    public int getMainMemorySize() {
        return Integer.parseInt(mainMemorySizeField.getText());
    }

    public String getMappingStrategy() {
        return (String) mappingStrategyBox.getSelectedItem();
    }

    public int getAssociativity() {
        return associativityField.getText().isEmpty() ? 0 : Integer.parseInt(associativityField.getText());
    }
    public String getReplacementPolicy() {
        return (String) replacementPolicyBox.getSelectedItem();
    }
    public int getNumAccesses() {
        return Integer.parseInt(numAccessesField.getText());
    }
    public void addStartButtonListener(ActionListener listener) {
        startButton.addActionListener(listener);
    }
    public String getOutputTextArea() {
        return outputTextArea.getText();
    }
    public void setOutputTextArea(String outputTextArea) {
        this.outputTextArea.setText(outputTextArea);
    }
    public void addRunTestsButtonListener(ActionListener listener) {
        runTestsButton.addActionListener(listener);
    }
    ////////////
//    public int getMemoryAddress() {
//        return Integer.parseInt(addressField.getText());
//    }
//
//    public int getValueToWrite() {
//        return Integer.parseInt(valueField.getText());
//    }
//
//    public void addAddDataButtonListener(ActionListener listener) {
//        addDataButton.addActionListener(listener);
//    }

    public int getMemoryAddress() {
        String text = addressField.getText().trim();
        if (text.isEmpty()) throw new NumberFormatException("Memory Address is empty.");
        return Integer.parseInt(text);
    }

    public int getValueToWrite() {
        String text = valueField.getText().trim();
        if (text.isEmpty()) throw new NumberFormatException("Value to Write is empty.");
        return Integer.parseInt(text);
    }

    public void addAddDataButtonListener(ActionListener listener) {
        addDataButton.addActionListener(listener);
    }

    public void updateCacheTable(String[][] data) {
        tableModel.setRowCount(0); // Clear the existing table
        for (String[] row : data) {
            tableModel.addRow(row); // Add each row of cache state
        }
    }
    ///////////////
}
