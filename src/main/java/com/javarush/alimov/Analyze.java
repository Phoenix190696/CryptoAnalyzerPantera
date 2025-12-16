package com.javarush.alimov;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс {@code Analyze} реализует метод статистического анализа для взлома шифра.
 * <p>
 * Основная идея: сравниваются частоты появления символов в зашифрованном тексте
 * с частотами символов в эталонном (репрезентативном) тексте. Ключ дешифрования
 * выбирается таким образом, чтобы минимизировать разницу между распределениями.
 * </p>
 *
 * Основные методы:
 * <ul>
 *     <li>{@link #letterFrequence(String)} — вычисляет частоты символов в заданном файле.</li>
 *     <li>{@link #findBestKey(Map, Map)} — определяет ключ, минимизирующий ошибку между частотами.</li>
 *     <li>{@link #decrypt(String, String, String)} — выполняет дешифрование текста с найденным ключом.</li>
 * </ul>
 *
 * Обработка ошибок:
 * <ul>
 *     <li>Файл-источник отсутствует → {@link FileNotFoundException}</li>
 *     <li>Любые другие ошибки → {@link Exception}</li>
 * </ul>
 *
 * Пример использования:
 * <pre>{@code
 * Analyze.decrypt("encrypted.txt", "sample.txt", "decrypted.txt");
 * }</pre>
 */
public class Analyze { // Объявляем публичный класс Analyze
    /**
     * Вычисляет частоты появления символов в файле.
     *
     * @param source путь к файлу-источнику
     * @return карта: символ → частота появления
     * @throws FileNotFoundException если файл отсутствует
     */
    private static Map<Character, Double> letterFrequence(String source){
        Map<Character, Double> freq = new HashMap<>();   // Карта: символ → частота
        Map<Character, Integer> counter = new HashMap<>(); // Карта: символ → количество вхождений
        int total = 0; // Общее количество символов

        try(BufferedReader reader = new BufferedReader(new FileReader(source))){ // Открываем файл для чтения
            if(!Files.exists(Path.of(source))){ // Проверяем, существует ли файл
                throw new FileNotFoundException(); // Если нет — выбрасываем исключение
            }

            int read; // Переменная для хранения считанного символа
            while((read = reader.read()) != -1){ // Читаем файл посимвольно до конца
                char symbol = (char) read; // Преобразуем в символ
                if(Alphabet.index.containsKey(symbol)){ // Если символ есть в алфавите
                    counter.put(symbol, counter.getOrDefault(symbol, 0) + 1); // Увеличиваем счётчик для символа
                    total++; // Увеличиваем общее количество символов
                }
            }

            // Вычисляем частоты для всех символов алфавита
            for (char ch : Alphabet.chars) {
                double p = (total == 0) ? 0.0 : counter.getOrDefault(ch, 0) / (double) total; // Частота = количество / общее
                freq.put(ch, p); // Записываем частоту в карту
            }
        }
        catch (FileNotFoundException e){ // Обработка ошибки: файл не найден
            System.out.println("Ошибка: файл не найден");
        }
        catch (Exception e){ // Обработка любых других ошибок
            System.out.println("Ошибка");
        }

        // Если файл пустой, заполняем частоты нулями
        if(total == 0){
            for (char ch : Alphabet.chars) {
                freq.put(ch, 0.0);
            }
        }

        return freq; // Возвращаем карту частот
    }


    /**
     * Определяет ключ дешифрования, минимизирующий разницу между частотами
     * эталонного текста и зашифрованного текста.
     *
     * @param representativeFreq карта частот символов эталонного текста
     * @param encryptedFreq      карта частот символов зашифрованного текста
     * @return лучший ключ дешифрования
     */
    private static int findBestKey(Map<Character, Double> representativeFreq, Map<Character, Double> encryptedFreq) {
        int bestKey = 0; // Лучший ключ
        double minError = Double.MAX_VALUE; // Минимальная ошибка (начальное значение — максимум)

        // Перебираем все возможные ключи
        for (int key = 0; key < Alphabet.size; key++) {
            double error = 0.0; // Ошибка для текущего ключа

            // Сравниваем частоты для каждого символа
            for (char c : Alphabet.chars) {
                int shiftedIndex = (Alphabet.index.get(c) + key) % Alphabet.size; // Смещаем индекс символа на ключ
                char shifted = Alphabet.chars[shiftedIndex]; // Получаем символ после смещения

                double repVal = representativeFreq.get(c); // Частота символа в эталонном тексте
                double encVal = encryptedFreq.get(shifted); // Частота символа в зашифрованном тексте
                error += Math.pow(repVal - encVal, 2); // Добавляем квадрат разницы частот к ошибке
            }

            // Если ошибка меньше минимальной — обновляем лучший ключ
            if (error < minError) {
                minError = error;
                bestKey = key;
            }
        }

        return bestKey; // Возвращаем найденный ключ
    }

    /**
     * Выполняет дешифрование текста методом статистического анализа.
     *
     * @param source   путь к зашифрованному файлу
     * @param repText  путь к эталонному тексту для сравнения
     * @param dest     путь к файлу назначения (куда сохраняется результат)
     */
    public static void decrypt(String source, String repText, String dest){
        Map<Character, Double> src = letterFrequence(source); // Частоты символов в зашифрованном тексте
        Map<Character, Double> rep = letterFrequence(repText); // Частоты символов в эталонном тексте

        int key = findBestKey(rep, src); // Находим лучший ключ на основе сравнения частот
        Decode.decode(source, dest, key); // Дешифруем исходный файл с найденным ключом
    }
}

