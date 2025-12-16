package com.javarush.alimov;

import java.util.Scanner;

/**
 * Главный класс {@code Main} является точкой входа в программу.
 * <p>
 * Программа реализует консольное меню для выбора режима работы:
 * <ul>
 *     <li>1 — Шифрование текста методом {@link Encode#encode(String, String, int)}</li>
 *     <li>2 — Дешифрование текста методом {@link Decode#decode(String, String, int)}</li>
 *     <li>3 — Взлом методом перебора ключей через {@link Brutforce#searchKey(String, String)}</li>
 *     <li>4 — Статистический анализ текста через {@link Analyze#decrypt(String, String, String)}</li>
 *     <li>5 — Выход из программы</li>
 * </ul>
 * </p>
 *
 * Пример использования:
 * <pre>{@code
 * java com.javarush.alimov.Main
 * }</pre>
 */
public class Main {

    /** Сканер для ввода данных с консоли. */
    Scanner scanner = new Scanner(System.in);

    /** Путь к файлу-источнику. */
    String source;

    /** Путь к файлу назначения (сохранения результата). */
    String destination;

    /** Ключ для шифрования/дешифрования. */
    int key;

    /**
     * Точка входа в программу.
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        Main main = new Main(); // Создаём объект класса Main
        main.workMode(main.showMenu()); // Отображаем меню и запускаем выбранный режим
    }

    /**
     * Отображает меню выбора режима работы и считывает выбор пользователя.
     *
     * @return выбранный режим (целое число от 1 до 5)
     */
    public int showMenu() {
        System.out.println("""
                Выберите режим работы(введите цифру):
                1.Режим шифрования
                2.Режим дешифрования
                3.Взлом методом перебора
                4.Статический анализ
                5.Выход
                """);
        return scanner.nextInt();
    }

    /**
     * Выполняет выбранный пользователем режим работы.
     *
     * @param code код режима (1–5)
     */
    public void workMode(int code) {
        scanner.nextLine(); // Считываем пустую строку после ввода числа
        switch (code) {
            case 1: // Шифрование
                System.out.println("Введите путь к файлу-источнику");
                source = scanner.nextLine();
                System.out.println("Введите путь к файлу сохранения");
                destination = scanner.nextLine();
                System.out.println("Введите ключ(целое число)");
                key = scanner.nextInt();
                scanner.close();
                Encode.encode(source, destination, key);
                break;

            case 2: // Дешифрование
                System.out.println("Введите путь к файлу-источнику");
                source = scanner.nextLine();
                System.out.println("Введите путь к файлу сохранения");
                destination = scanner.nextLine();
                System.out.println("Введите ключ(целое число)");
                key = scanner.nextInt();
                scanner.close();
                Decode.decode(source, destination, key);
                break;

            case 3: // Взлом методом перебора
                System.out.println("Введите путь к файлу-источнику");
                source = scanner.nextLine();
                System.out.println("Введите путь к файлу сохранения");
                destination = scanner.nextLine();
                Brutforce.searchKey(source, destination);
                scanner.close();
                break;

            case 4: // Статистический анализ
                System.out.println("Введите путь к файлу-источнику");
                source = scanner.nextLine();
                System.out.println("Введите путь к файлу сохранения");
                destination = scanner.nextLine();
                System.out.println("Введите путь к образцу для сравнения");
                String rep = scanner.nextLine();
                Analyze.decrypt(source, rep, destination);
                scanner.close();
                break;

            case 5: // Выход
                scanner.close();
                System.exit(0);
                break;
        }
    }
}
