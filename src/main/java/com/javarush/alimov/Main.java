package com.javarush.alimov;

import java.util.Scanner;

public class Main {
    Scanner scanner = new Scanner(System.in);
    String source;
    String destination;
    int key;

    public static void main(String[] args) {
        Main main = new Main();
        main.workMode(main.showMenu());


    }

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

    public void workMode(int code) {
        scanner.nextLine();
        switch (code) {
            case 1:
                System.out.println("Введите путь к файлу-источнику");
                source=scanner.nextLine();
                System.out.println("Введите путь к файлу сохранения");
                destination =scanner.nextLine();
                System.out.println("Введите ключ(целое число)");
                key= scanner.nextInt();
                scanner.close();
                Encode.encode(source, destination,key);
                break;
            case 2:
                System.out.println("Введите путь к файлу-источнику");
                source=scanner.nextLine();
                System.out.println("Введите путь к файлу сохранения");
                destination =scanner.nextLine();
                System.out.println("Введите ключ(целое число)");
                key= scanner.nextInt();
                scanner.close();
                Decode.decode(source, destination,key);
                break;
            case 3:
                System.out.println("Введите путь к файлу-источнику");
                source=scanner.nextLine();
                System.out.println("Введите путь к файлу сохранения");
                destination =scanner.nextLine();
                Brutforce.searchKey(source, destination);
                scanner.close();
                break;
            case 4:
                System.out.println("Введите путь к файлу-источнику");
                source=scanner.nextLine();
                System.out.println("Введите путь к файлу сохранения");
                destination =scanner.nextLine();
                System.out.println("Введите путь к образцу для сравнения");
               String rep=scanner.nextLine();
                Analyze.decrypt(source,rep ,destination);
                scanner.close();
                break;
            case 5:
                scanner.close();
                System.exit(0);
                break;
        }
    }
}
