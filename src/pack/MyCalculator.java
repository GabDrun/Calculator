package pack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyCalculator {
    private JPanel numbers, operationsPanel, output, mainPanel, otherOperations;
    private JTextArea text;
    private JFrame frame;
    private String operations[];
    private Logic logic;

    public MyCalculator(String title, String[] operations, Logic logic) {
        this.operations = operations;
        this.logic = logic;

        frame = new JFrame(title);
        numbers = new JPanel(new GridLayout(3,3));
        output = new JPanel();
        mainPanel = new JPanel(new BorderLayout());

        numbers.setSize(150, 150);
        numbers.setBackground(Color.white);
        numbers.setVisible(true);

        output.setSize(300, 50);
        output.setBackground(Color.white);
        output.setVisible(true);
        text = new JTextArea("");
        output.add(text);

        addNumberButtons();

        operationsPanel = new JPanel(new GridLayout(4,5));
        addOperationButtons();

        otherOperations = new JPanel(new GridLayout(3,1));
        addOtherButtons();

        mainPanel.add(numbers, BorderLayout.WEST);
        mainPanel.add(output, BorderLayout.NORTH);
        mainPanel.add(operationsPanel, BorderLayout.EAST);
        mainPanel.add(otherOperations, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setSize(250 + 60*operations.length/4, 300);
//        frame.pack();
        frame.setVisible(true);
    }

    private void addOtherButtons() {
        JButton clear = new JButton("C");
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setText("");
            }
        });
        otherOperations.add(clear);

        JButton equals = new JButton("=");
        equals.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setText(logic.calculate(getText()));
            }
        });
        otherOperations.add(equals);

        JButton backspace = new JButton("<-");
        backspace.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(getText().length() > 0) {
                    setText(getText().substring(0, getText().length() - 1));
                }
            }
        });
        otherOperations.add(backspace);
    }

    private void addOperationButtons() {
        for (String operation : operations) {
            JButton button = new JButton(operation);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setText(getText() + operation);
                }
            });
            operationsPanel.add(button);
        }
    }


    private void addNumberButtons() {
        for (int i = 1; i < 10; i++) {
            JButton button = new JButton(Integer.toString(i));
            button.addActionListener(new ActionListener()  {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setText(getText() + button.getText());
                }
            });
            numbers.add(button);
        }
    }

    private String getText() {
        return text.getText();
    }

    private void setText(String s) {
        text.setText(s);
    }
}

