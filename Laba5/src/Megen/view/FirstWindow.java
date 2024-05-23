package Megen.view;

import Megen.data.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FirstWindow extends JFrame {

    TheTableModel myTableModel;

    JButton button_area;
    JButton button_city;
    JButton button_capital;
    JButton button_search;

    public FirstWindow() {
        super("Главное окно");
        setSize(400, 400); // Размер
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // Закрытие окна
        this.setLocationRelativeTo(null); // Окно посередине

        // Создаем панель для картинки
        JPanel imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon("C:\\Users\\mankh\\OneDrive\\Рабочий стол\\4 cеместр\\ООП\\Laba5\\1.jpg");
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };

        // Создаем панель с кнопками
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(new Color(0, 0, 0, 0)); // Прозрачный фон
        buttonPanel.setPreferredSize(new Dimension(200, 400));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;

        // Создаем первую кнопку Область
        this.button_area = new JButton("Область");
        this.button_area.setPreferredSize(new Dimension(200, 30));
        buttonPanel.add(button_area, gbc);
        gbc.gridy = 1;
        button_area.setVisible(true);

        // Создаем вторую кнопку Город
        this.button_city = new JButton("Город");
        this.button_city.setPreferredSize(new Dimension(200, 30));// Устанавливаем размер кнопки
        buttonPanel.add(button_city, gbc);
        gbc.gridy = 2; // Переходим на следующую строку
        button_city.setVisible(true);

        // Создаем третью кнопку Столица
        this.button_capital = new JButton("Столица");
        this.button_capital.setPreferredSize(new Dimension(200, 30));// Устанавливаем размер кнопки
        buttonPanel.add(button_capital, gbc);
        gbc.gridy = 4; // Переходим на следующую строку
        button_capital.setVisible(true);

        // Создаем четвертую кнопку Поиск
        this.button_search = new JButton("Поиск по номеру ID");
        this.button_search.setPreferredSize(new Dimension(200, 30));// Устанавливаем размер кнопки
        buttonPanel.add(button_search, gbc);
        button_search.setVisible(true);

        // Устанавливаем менеджер компоновки OverlayLayout для размещения панелей поверх друг друга
        setLayout(new OverlayLayout(getContentPane()));
        add(imagePanel);
        add(buttonPanel);

        // Инициализируем myTableModel
        this.myTableModel = new TheTableModel(new Group());

        // Добавляем слушателей
        addListeners();

        // Устанавливаем видимость окна
        setVisible(true);
    }

    public void addListeners() {
        //Добавим слушателей на кнопки
        button_area.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                area();
            }
        });
        button_city.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                city();
            }
        });
        button_capital.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                capital();
            }
        });

        button_search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search_method();
            }
        });
    }

    public void area() {
        myTableModel.setorganization(1);
        Group.sortList(1);
        new WindowArea(this, myTableModel);
    }

    public void city() {
        myTableModel.setorganization(3);
        Group.sortList(3);
        new WindowCity(this, myTableModel);
    }

    public void capital() {
        myTableModel.setorganization(2);
        Group.sortList(2);
        new WindowCapital(this, myTableModel);
    }

    public void search_method() {
        new WindowZSearch();    //Z затем чтобы внизу списка было
    }
}
