package com.javarush.alimov;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
/**
 * Класс {@code Encode} отвечает за шифрование текстовых файлов
 * с использованием заданного ключа и алфавита {@link Alphabet}.
 * <p>
 * Алгоритм: каждый символ исходного файла проверяется на наличие в алфавите.
 * Если символ найден, его индекс смещается вперед на величину ключа.
 * Результат записывается в файл назначения.
 * </p>
 *
 * Обработка ошибок:
 * <ul>
 *     <li>Файл-источник отсутствует → {@link FileNotFoundException}</li>
 *     <li>Ключ вне допустимого диапазона → {@link IllegalArgumentException}</li>
 *     <li>Любые другие ошибки → {@link Exception}</li>
 * </ul>
 *
 * Пример использования:
 * <pre>{@code
 * Encode.encode("input.txt", "output.txt", 3);
 * }</pre>
 */
public class Encode {
    /**
     * Метод выполняет шифрование содержимого файла
     * @param source путь к файлу-источнику (входной файл)
     * @param dest   путь к файлу назначения (куда сохраняется результат)
     * @param key    ключ дешифрования (целое число от 1 до размера алфавита)
     *
     * @throws FileNotFoundException    если файл-источник отсутствует
     * @throws IllegalArgumentException если ключ некорректный
     */
    public static void encode(String source, String dest, int key){
        try(BufferedReader reader = new BufferedReader(new FileReader(source)); // Создаём BufferedReader для чтения текста из исходного файла
            BufferedWriter writer = new BufferedWriter(new FileWriter(dest))){ // Создаём BufferedWriter для записи текста в файл назначения

            if(!Files.exists(Path.of(source))){ // Проверяем, существует ли исходный файл
                throw new FileNotFoundException(); // Если нет — выбрасываем исключение FileNotFoundException
            }

            if(key > Alphabet.size || key < 1){ // Проверяем, что ключ находится в допустимом диапазоне
                throw new IllegalArgumentException(); // Если ключ некорректный — выбрасываем исключение IllegalArgumentException
            }

            int read; // Переменная для хранения считанного символа (в виде int)
            while((read = reader.read()) != -1){ // Читаем файл посимвольно, пока не достигнем конца (-1)
                char symbol = (char) read; // Преобразуем считанное значение в символ
                if(Alphabet.index.containsKey(symbol)){ // Проверяем, есть ли символ в словаре Alphabet.index
                    int index = Alphabet.index.get(symbol); // Получаем индекс символа в алфавите
                    symbol = Alphabet.chars[(index + key) % Alphabet.size]; // Вычисляем новый символ: смещаем индекс вперёд на величину ключа и берём остаток от деления на размер алфавита
                }
                writer.write(symbol); // Записываем символ (изменённый или исходный) в файл назначения
            }
            System.out.println("Шифровка завершена"); // Сообщаем пользователю, что процесс завершён
        }
        catch (FileNotFoundException e){ // Обработка ошибки: файл не найден
            System.out.println("Ошибка: файл не найден");
        }
        catch (IllegalArgumentException e){ // Обработка ошибки: недопустимый ключ
            System.out.println("Ошибка: недопустимый ключ");
        }
        catch (Exception e){ // Обработка любых других ошибок
            System.out.println("Ошибка");
        }
    }
}

