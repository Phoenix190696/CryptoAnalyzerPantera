package com.javarush.alimov;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
/**
 * Класс {@code Brutforce} реализует перебор ключей для дешифрования текста.
 * <p>
 * Алгоритм:
 * <ul>
 *     <li>Читает первую строку файла-источника.</li>
 *     <li>Перебирает все возможные ключи от 1 до размера алфавита {@link Alphabet#size}.</li>
 *     <li>Для каждого ключа выполняет дешифрование первой строки и выводит результат в консоль.</li>
 *     <li>Пользователь вручную выбирает наиболее правильный ключ.</li>
 *     <li>После выбора запускается полное дешифрование файла методом {@link Decode#decode(String, String, int)}.</li>
 * </ul>
 * </p>
 *
 * Обработка ошибок:
 * <ul>
 *     <li>Файл-источник отсутствует → {@link FileNotFoundException}</li>
 *     <li>Любые другие ошибки → {@link Exception}</li>
 * </ul>
 *
 * Пример использования:
 * <pre>{@code
 * Brutforce.searchKey("encrypted.txt", "decrypted.txt");
 * }</pre>
 */
public class Brutforce { // Объявляем публичный класс Brutforce

    private static int key = 0; // Статическая переменная для хранения текущего ключа
    /**
     * Метод выполняет перебор ключей для дешифрования текста.
     *
     * @param source путь к файлу-источнику (зашифрованный текст)
     * @param dest   путь к файлу назначения (куда сохраняется результат дешифрования)
     *
     * @throws FileNotFoundException если файл-источник отсутствует
     */
    public static void searchKey(String source, String dest){ // Метод для перебора ключей и попытки взлома
        try(BufferedReader reader = new BufferedReader(new FileReader(source))){ // Открываем исходный файл для чтения

            if(!Files.exists(Path.of(source))){ // Проверяем, существует ли файл-источник
                new FileNotFoundException(); // Создаём исключение
            }

            String stringfile = reader.readLine(); // Читаем первую строку файла
            String[] strarray = stringfile.split(" "); // Разбиваем строку на массив слов по пробелам
            String str = strarray[0]; // Берём первое слово для анализа

            for(int i = 1; i < Alphabet.size; i++){ // Перебираем все возможные ключи от 1 до размера алфавита
                key = i; // Устанавливаем текущий ключ
                StringBuilder stringBuilder = new StringBuilder(); // Создаём StringBuilder для построения расшифрованной строки

                for(int j = 0; j < str.length(); j++){ // Проходим по каждому символу слова
                    char symbol = str.charAt(j); // Берём текущий символ
                    if(Alphabet.index.containsKey(symbol)){ // Проверяем, есть ли символ в алфавите
                        int index = Alphabet.index.get(symbol); // Получаем индекс символа
                        int newIndex = (index - key) % Alphabet.size; // Смещаем индекс назад на величину ключа
                        if(newIndex < 0){ // Если индекс стал отрицательным
                            newIndex += Alphabet.size; // Корректируем, чтобы остаться в пределах алфавита
                        }
                        symbol = Alphabet.chars[newIndex]; // Получаем новый символ из алфавита
                    }
                    stringBuilder.append(symbol); // Добавляем символ в строку результата
                }

                System.out.println("Строка: " + stringBuilder + " Ключ: " + key); // Выводим результат для текущего ключа
            }

            Scanner sc = new Scanner(System.in); // Создаём Scanner для ввода пользователя
            System.out.println("Выберите наиболее правильный вариант ключа:"); // Просим выбрать правильный ключ
            int tmp = sc.nextInt(); // Считываем выбранный ключ
            sc.close(); // Закрываем Scanner

            Decode.decode(source, dest, tmp); // Запускаем дешифрование с выбранным ключом

        }
        catch (FileNotFoundException e){ // Обработка ошибки: файл не найден
            System.out.println("Ошибка: файл не найден");
        }
        catch (Exception e){ // Обработка любых других ошибок
            System.out.println("Ошибка");
        }
    }
}
