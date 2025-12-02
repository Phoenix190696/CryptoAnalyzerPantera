package com.javarush.alimov;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Encode {
    public static void encode(String source, String dest, int key){
        try(BufferedReader reader=new BufferedReader(new FileReader(source));
            BufferedWriter writer=new BufferedWriter(new FileWriter(dest))){
            if(!Files.exists(Path.of(source))){
              throw   new FileNotFoundException();
            }
            if(key>Alphabet.size||key<1){
               throw new IllegalArgumentException();
            }
            int read;
            while((read=reader.read())!=-1){
                char symbol=(char)read;
                if(Alphabet.index.containsKey(symbol)){
                   int index=Alphabet.index.get(symbol);
                    symbol=Alphabet.chars[(index+key)%Alphabet.size];
                }
                writer.write(symbol);
            }
            System.out.println("Шифровка завершена");
        }
        catch (FileNotFoundException e){
            System.out.println("Ошибка:файл не найден");
        }
        catch (IllegalArgumentException e){
            System.out.println("Ошибка:недопустимый ключ");
        }
        catch (Exception e){
            System.out.println("Ошибка");
        }
    }
}
