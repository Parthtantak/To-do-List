
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TodoListApp extends JFrame {

    DefaultListModel<String> taskModel;
    JList<String> taskList;
    JTextField taskField;
    JLabel countLabel;

    public TodoListApp() {

        setTitle("To-Do List");
        setSize(480, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(12, 12));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 247, 250));
        setContentPane(mainPanel);

        JLabel title = new JLabel("My To-Do List");
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setForeground(new Color(35, 55, 80));

        taskField = new JTextField();
        taskField.setFont(new Font("Arial", Font.PLAIN, 15));

        JButton addButton = new JButton("Add Task");

        JPanel inputPanel = new JPanel(new BorderLayout(8, 0));
        inputPanel.add(taskField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);

        JPanel topPanel = new JPanel(new BorderLayout(8, 12));
        topPanel.setOpaque(false);
        topPanel.add(title, BorderLayout.NORTH);
        topPanel.add(inputPanel, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        taskModel = new DefaultListModel<>();
        taskList = new JList<>(taskModel);
        taskList.setFont(new Font("Arial", Font.PLAIN, 15));
        taskList.setFixedCellHeight(36);

        JScrollPane scrollPane = new JScrollPane(taskList);
        scrollPane.setBorder(
            BorderFactory.createTitledBorder("Tasks")
        );

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        countLabel = new JLabel("Tasks: 0");

        JButton deleteButton = new JButton("Delete Selected");
        JButton clearButton = new JButton("Clear All");

        JPanel buttonsPanel = new JPanel(
            new FlowLayout(FlowLayout.RIGHT)
        );

        buttonsPanel.setOpaque(false);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(clearButton);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.add(countLabel, BorderLayout.WEST);
        bottomPanel.add(buttonsPanel, BorderLayout.EAST);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addTask());
        taskField.addActionListener(e -> addTask());

        deleteButton.addActionListener(e -> {
            int index = taskList.getSelectedIndex();

            if (index == -1) {
                JOptionPane.showMessageDialog(
                    this, "Please select a task to delete."
                );
            } else {
                taskModel.remove(index);
                updateTaskCount();
            }
        });

        clearButton.addActionListener(e -> {
            if (taskModel.isEmpty()) {
                JOptionPane.showMessageDialog(
                    this, "There are no tasks to clear."
                );
                return;
            }

            int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete all tasks?",
                "Clear All Tasks",
                JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                taskModel.clear();
                updateTaskCount();
            }
        });

        setVisible(true);
    }

    private void addTask() {

        String task = taskField.getText().trim();

        if (task.isEmpty()) {
            JOptionPane.showMessageDialog(
                this, "Please enter a task."
            );
            return;
        }

        taskModel.addElement(task);
        taskField.setText("");
        taskField.requestFocus();
        updateTaskCount();
    }

    private void updateTaskCount() {
        countLabel.setText("Tasks: " + taskModel.size());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TodoListApp::new);
    }
}
