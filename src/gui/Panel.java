package gui;

import app.ClausesSet;
import app.Solution;
import app.blindSearch.BlindSearch;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
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
        selectAlgoBox.setBounds(600, 25, 150, 30);
        add(selectAlgoBox);

        //Start button
        JButton startButton = new JButton("Start resolution");
        startButton.setBounds(600, 80, 150, 40);
        startButton.setEnabled(false);
        add(startButton);

        //Stop button
        JButton stopButton = new JButton("Stop resolution");
        stopButton.setBounds(600, 120, 150, 40);
        stopButton.setEnabled(false);
        add(stopButton);

        //Information label
        JLabel informationLabel = new JLabel();
        informationLabel.setBounds(300, 380, 500, 40);
        add(informationLabel);

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
                Solution solution = null;
                //check the selected algorithm index
                switch (algoNumber){
                    case 1:
                        solution = BlindSearch.DepthFirstSearch(clset);
                        break;
                    default:
                        //show error message if no algorithm is selected
                        JOptionPane.showMessageDialog(new JFrame(), "Please select a search method", "Dialog",
                                JOptionPane.ERROR_MESSAGE);
                }

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
