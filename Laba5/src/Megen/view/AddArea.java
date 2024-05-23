package Megen.view;

import Megen.data.Group;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddArea {
    public AddArea(JDialog jDialog) {
        JDialog dialog = new JDialog(jDialog, "Добавление области", true);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(jDialog);

        // Создаем и добавляем текстовые поля для ввода
        JTextField Field_name = new JTextField();
        JTextField Field_id = new JTextField();
        Field_id.setText(Group.getNextAvailableId());
        Field_id.setEditable(false); // Запретить редактирование текстового поля
        Field_id.setFocusable(false); // Запретить фокусировку на текстовом поле
        JTextField Field_3 = new JTextField();

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Название облсати: "));
        panel.add(Field_name);
        panel.add(new JLabel("ID: "));
        panel.add(Field_id);
        panel.add(new JLabel("Главный город: "));
        panel.add(Field_3);

        // Создаем кнопки "Добавить" и "Отмена"
        JButton addButton = new JButton("Добавить");
        addButton.setBackground(Color.GRAY); // Устанавливаем серый цвет кнопки
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = Field_name.getText();
                String idText = Field_id.getText();
                String type_of_benefit = Field_3.getText();

                // Проверка на пустые поля
                if (Group.str_is_null(name) || Group.str_is_null(type_of_benefit)) {
                    new ErrorDialog("Пожалуйста, заполните все данные", dialog);
                    return;
                }

                int id;
                try {
                    id = Integer.parseInt(idText);
                } catch (NumberFormatException ex) {
                    new ErrorDialog("Неверно введены численные данные", dialog);
                    return;
                }

                // Проверка на уникальность id
                if (!Megen.data.Group.addArea(name, id, type_of_benefit)) {
                    new ErrorDialog("Такой ID уже есть", dialog);
                    return;
                }
                // Закрываем диалоговое окно после добавления
                dialog.dispose();
            }
        });

        JButton cancelButton = new JButton("Отмена");
        cancelButton.setBackground(Color.GRAY); // Устанавливаем серый цвет кнопки
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose(); // Закрываем диалоговое окно без добавления
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
        buttonPanel.setBackground(Color.WHITE);

        // Добавляем панель с полями ввода и панель с кнопками на диалоговое окно
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        // Устанавливаем диалоговое окно видимым
        dialog.setVisible(true);
    }
}
