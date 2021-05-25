package gui;

import app.ClausesSet;
import app.Solution;
import app.blindSearch.BlindSearch;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


public class Panel extends JPanel {
    private int algoNumber = 0; //Selected algorithm for resolution
    private JTable clausesTable;
    private JTable variableTable;
    private ClausesSet clset;

    public Panel() {
        setLayout(null);

        //Adding clauses table to the panel
        clausesTable = new JTable();
        DefaultTableModel clausesTableModel = new DefaultTableModel();
        clausesTableModel.addColumn("clause");
        clausesTableModel.addColumn("Literal1");
        clausesTableModel.addColumn("Literal2");
        clausesTableModel.addColumn("Literal3");
        clausesTable.setModel(clausesTableModel);

        JScrollPane tableContainer = new JScrollPane(clausesTable);
        tableContainer.setSize(300, 350);
        tableContainer.setLocation(10, 25);
        add(tableContainer);

        JLabel clsTableText = new JLabel();
        clsTableText.setText("Clauses table:");
        clsTableText.setBounds(12, 10, 300, 20);
        add(clsTableText);

        //Import cnf file button
        JButton importButton = new JButton("Import cnf file");
        importButton.setBounds(30, 380, 240, 40);
        add(importButton);

        //Information label
        JLabel informationLabel = new JLabel();
        informationLabel.setBounds(10, 420, 500, 40);
        add(informationLabel);

        //Adding variables table to the panel
        variableTable = new JTable();
        DefaultTableModel variableTableModel = new DefaultTableModel();
        variableTableModel.addColumn("Variable");
        variableTableModel.addColumn("Value");
        variableTable.setModel(variableTableModel);

        JScrollPane varTableContainer = new JScrollPane(variableTable);
        varTableContainer.setSize(200, 350);
        varTableContainer.setLocation(350, 25);
        add(varTableContainer);

        JLabel varTableText = new JLabel();
        varTableText.setText("Variables table:");
        varTableText.setBounds(352, 10, 300, 20);
        add(varTableText);

        //Adding a combobox for algorithm selection
        String[] algorithms = {"Select search method", "Depth First Search", "Breadth First Search", "A* search"};
        JComboBox selectAlgoBox = new JComboBox(algorithms);
        selectAlgoBox.setBounds(580, 25, 170, 30);
        add(selectAlgoBox);

        //Start button
        JButton startButton = new JButton("Start resolution");
        startButton.setBounds(580, 80, 170, 40);
        startButton.setEnabled(false);
        add(startButton);

        //execution time spinner
        SpinnerModel model = new SpinnerNumberModel(5, 0, 15000, 1);
        JSpinner timeSpinner = new JSpinner(model);
        timeSpinner.setBounds(680, 130, 70, 30);
        add(timeSpinner);

        JLabel timeSpinnerLabel = new JLabel();
        timeSpinnerLabel.setBounds(580, 135, 200, 50);
        timeSpinnerLabel.setText("<html>Execution time: <br/><br/>Set time to 0 to execute until the end</html>");
        add(timeSpinnerLabel);


        //results Text
        JLabel resultsLabel = new JLabel();
        resultsLabel.setBounds(600, 380, 200, 60);
        resultsLabel.setText("Results: ");
        add(resultsLabel);


        // Import button action listner
        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                fileChooser.setAcceptAllFileFilterUsed(false);
                fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Conjunctive Normal Form (.cnf)", "cnf"));
                fileChooser.showOpenDialog(null);

                try {
                    informationLabel.setText(loadClausesSet(fileChooser.getSelectedFile().getAbsolutePath()));
                    startButton.setEnabled(true);
                }catch (NullPointerException error){

                }
            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //get execution time from spinner
                int executionTime = (int) timeSpinner.getValue();
                System.out.println(executionTime);
                Solution solution = null;
                //check the selected algorithm index
                switch (algoNumber){
                    case 1:
                        solution = BlindSearch.DepthFirstSearch(clset, executionTime);
                        break;
                    default:
                        //show error message if no algorithm is selected
                        JOptionPane.showMessageDialog(new JFrame(), "Please select a search method", "Dialog",
                                JOptionPane.ERROR_MESSAGE);
                }

                resultsLabel.setText("<html>Results:<br/> Number of satisfied clauses: " + solution.satisfiedClauses(clset) + "<br/>Satisfiability rate: " + Long.valueOf(solution.satisfiedClauses(clset)*100/ clset.getNumberClause()) + "%</html>");

                //Adding solution values to the variables table
                DefaultTableModel tableModel = new DefaultTableModel();
                tableModel.addColumn("Variable");
                tableModel.addColumn("Value");

                String[] tablerow = new String[clset.numberOfVariables()];
                for (int i = 0; i < clset.numberOfVariables(); i++) {
                    tablerow[0] = "X" + (i+1);
                    tablerow[1] = String.valueOf(solution.getLiteral(i));
                    tableModel.addRow(tablerow);
                }
                variableTable.setModel(tableModel);

            }
        });

        selectAlgoBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                algoNumber = selectAlgoBox.getSelectedIndex();
            }
        });

    }

    //Instantiation of the clausesSet and adding values to clauses table
    private String loadClausesSet(String path) {
        this.clset = new ClausesSet(path);

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Clause");

        for(int i=0; i<clset.getClauseSize(i); i++)
            tableModel.addColumn("Literal "+(i+1));

        String[] tableRow = new String[clset.getClauseSize(0)+1];

        for(int i=0; i<clset.getNumberClause(); i++) {
            tableRow[0] = String.valueOf(i);

            for(int j=1; j<=clset.getClauseSize(i); j++)
                tableRow[j] = String.valueOf(clset.getClause(i).getLiteral(j-1).getVar());

            tableModel.addRow(tableRow);
        }

        clausesTable.setModel(tableModel);

        return "SAT instance loaded :  "+clset.getNumberClause()+"  clauses,  "+clset.numberOfVariables()+"  variables,  "+clset.getClauseSize(0)+"  variables/clause";
    }


}
