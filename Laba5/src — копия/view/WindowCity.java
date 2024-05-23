package Megen.view;

import Megen.data.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowCity {
    private JPanel jPanel_buttons;
    private JButton button_add;
    private JButton button_delete;
    private JButton button_back;
    private JTable jTable;
    private JScrollPane jScrollPane;
    private TheTableModel myTableModel;
    //private JPanel bottomPanel;
    private BorderLayout borderLayout;
    //Для поиска по id
    private JDialog dialog;

    public WindowCity(JFrame jFrame, TheTableModel myTableModel) {
        this.myTableModel = myTableModel;
        this.dialog = new JDialog(jFrame, "Таблица городов", true);
        this.dialog.setSize(400, 350);
        this.dialog.setLocationRelativeTo(jFrame);
        init();
        initListeners();
    }
    public void init() {
        // Создаем компоновщик BorderLayout для главного окна
        this.borderLayout = new BorderLayout();
        this.dialog.setLayout(borderLayout);

        //Панель для кнопок
        this.jPanel_buttons = new JPanel();

        //Кнопка добавки
        this.button_add = new JButton("Добавить");
        this.button_add.setBackground(Color.GRAY);
        this.button_add.setForeground(Color.WHITE);

        //Кнопка удаления
        this.button_delete = new JButton("Удалить");
        this.button_delete.setBackground(Color.GRAY);
        this.button_delete.setForeground(Color.WHITE);

        //Кнопка назад
        this.button_back = new JButton("Назад");
        this.button_back.setBackground(Color.GRAY);
        this.button_back.setForeground(Color.WHITE);

        //Инициализация таблицы
        this.jTable = new JTable();
        //this.myTableModel = new MyTableModel_v2(new Group());
        this.jTable.setModel(myTableModel);

        //Настройка рендера таблицы
        initRenderTable();

        //Инициализация скролла
        this.jScrollPane = new JScrollPane(jTable);

        //Устанавливаем видимость на кнопки
        this.button_add.setVisible(true);
        this.button_delete.setVisible(true);
        this.button_back.setVisible(true);

        //Инициализируем слушателей на кнопках
        initListeners();

        //Устанавливаем кнопки и текстовое поле в панель для кнопок
        this.jPanel_buttons.add(button_add);
        this.jPanel_buttons.add(button_delete);
        this.jPanel_buttons.add(button_back);

        //Устанавливаем цвет для панели кнопок
        this.jPanel_buttons.setBackground(Color.WHITE);

        //Устанавливаем панели
        this.dialog.add(this.jPanel_buttons, BorderLayout.SOUTH);
        this.dialog.add(this.jScrollPane, BorderLayout.CENTER);

        //Устанавливаем видимость окна
        this.dialog.setVisible(true); //Видимость
    }

    public void initListeners() {
        button_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddCity(dialog);
                Group.sortList(3);
                myTableModel.reload_table();
            }
        });
        button_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    myTableModel.delete(jTable.getSelectedRow());
                }
                catch (IndexOutOfBoundsException ex) {
                    show_error("Пожалуйста, выделите строку, которую хотите удалить");
                }
            }
        });
        button_back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose(); // Закрываем окно при нажатии кнопки
            }
        });
    }
    public void initRenderTable() {
        // Настройка рендерера заголовков колонок без создания нового класса
        TableCellRenderer headerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setForeground(Color.WHITE);
                setBackground(Color.GRAY);
                return this;
            }
        };
        // Установка рендерера заголовков колонок для каждой колонки таблицы
        for (int i = 0; i < jTable.getColumnModel().getColumnCount(); i++) {
            this.jTable.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
    }
    public void show_error(String message) {
        new ErrorDialog(message, dialog);
    }
}
