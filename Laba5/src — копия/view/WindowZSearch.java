package Megen.view;

import Megen.data.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//Z затем чтобы внизу списка было
public class WindowZSearch extends JFrame {
    private JTextField idField;
    private JTextField categoryField;
    private JTextField nameField;
    private JTextField parameterField;
    private JLabel jLabel_1;
    private JButton searchButton;
    private JButton exitButton;
    public WindowZSearch() {
        super("Поиск");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Центрирование окна
        initComponents();
        addComponents();
        addListeners();
        setVisible(true);
    }

    private void initComponents() {
        this.idField = new JTextField(10);
        this.categoryField = new JTextField(10);
        this.nameField = new JTextField(10);
        this.parameterField = new JTextField(10);

        this.searchButton = new JButton("Поиск");
        this.searchButton.setBackground(Color.GRAY);
        this.searchButton.setForeground(Color.WHITE);
        this.exitButton = new JButton("Назад");
        this.exitButton.setBackground(Color.GRAY);
        this.exitButton.setForeground(Color.WHITE);
    }

    private void addComponents() {
        JPanel panel = new JPanel(new BorderLayout());

        // Создаем панель для полей вывода с GridLayout
        JPanel outputPanel = new JPanel(new GridLayout(3, 2));
        outputPanel.add(new JLabel("Тип:"));
        categoryField.setEditable(false);
        outputPanel.add(categoryField);
        outputPanel.add(new JLabel("Название:"));
        nameField.setEditable(false);
        outputPanel.add(nameField);
        this.jLabel_1 = new JLabel("Доп.инфа:");
        outputPanel.add(jLabel_1);
        parameterField.setEditable(false);
        outputPanel.add(parameterField);

        // Создаем панель для поля ввода ID и кнопок с FlowLayout
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idField);
        inputPanel.add(searchButton);
        inputPanel.add(exitButton);

        // Добавляем панели в основную панель
        panel.add(outputPanel, BorderLayout.NORTH);
        panel.add(inputPanel, BorderLayout.SOUTH);

        // Добавляем основную панель в окно
        getContentPane().add(panel, BorderLayout.CENTER);
    }

    private void addListeners() {
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String temp_index = idField.getText();
                int ID;
                try {
                    ID = Integer.parseInt(temp_index);
                } catch (NumberFormatException ex) {
                    show_error("Некорректный ввод ID");
                    return;
                }
                if (ID < 0) {
                    show_error("ID не может быть отрицательным");
                }
                else {

                    if (!Group.searchMethod(ID)) {
                        show_error("Организация с таким ID не найдена");
                    }
                    else {

                        Organization b = Group.getorganizationByID(ID);

                        nameField.setText(b.getName());
                        if (b instanceof Area) {
                            categoryField.setText("Область");
                            parameterField.setText((((Area) b).getBenefit()));
                        }
                        else if (b instanceof City) {
                            categoryField.setText("Город");
                            parameterField.setText((((City) b).getBenefit()));
                        }
                        else if (b instanceof Capital) {
                            categoryField.setText("Столица");
                            parameterField.setText((((Capital) b).getBenefit()));
                        }
                    }
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Закрываем окно при нажатии кнопки
            }
        });
    }
    public void show_error(String message) {
        new ErrorDialog(message, this);
    }
}
