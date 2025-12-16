package com.javarush.alimov;

import java.util.HashMap;
import java.util.Map;
/**
 * Класс {@code Alphabet} хранит набор символов русского алфавита
 * (включая букву Ё) в нижнем и верхнем регистре и предоставляет
 * удобные структуры для работы с ними.
 * <p>
 * Основные элементы:
 * <ul>
 *     <li>{@link #rusAlphabet} — строка, содержащая все символы алфавита.</li>
 *     <li>{@link #chars} — массив символов для доступа по индексу.</li>
 *     <li>{@link #size} — размер алфавита (количество символов).</li>
 *     <li>{@link #index} — карта для быстрого поиска индекса символа.</li>
 * </ul>
 * </p>
 *
 * Пример использования:
 * <pre>{@code
 * int idx = Alphabet.index.get('Ж');         // Получить индекс символа 'Ж'
 * char c = Alphabet.chars[(idx + 3) % Alphabet.size]; // Сдвинуть символ на 3 позиции
 * }</pre>
 */
public class Alphabet { // Объявляем публичный класс Alphabet

    // Строка, содержащая все буквы русского алфавита (включая Ё) в нижнем и верхнем регистре
    public static final String rusAlphabet = "абвгдеёжзийклмнопрстуфцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФЦЧШЩЪЫЬЭЮЯ";

    // Преобразуем строку rusAlphabet в массив символов для удобного доступа по индексу
    public static final char[] chars = rusAlphabet.toCharArray();

    // Размер алфавита (количество символов в массиве chars)
    public static final int size = chars.length;

    // Карта для быстрого поиска индекса символа: ключ — символ, значение — его индекс в массиве chars
    public static final Map<Character, Integer> index = new HashMap<>();

    // Статический блок инициализации: выполняется один раз при загрузке класса
    static {
        // Проходим по всем символам массива chars
        for (int i = 0; i < chars.length; i++) {
            // Добавляем в карту: символ → его индекс
            index.put(chars[i], i);
        }
    }
}

