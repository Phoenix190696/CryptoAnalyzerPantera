package com.javarush.alimov;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Brutforce {
    private static int key=0;
    public static void searchKey(String source,String dest){
        try(BufferedReader reader=new BufferedReader(new FileReader(source))){
            if(!Files.exists(Path.of(source))){
                new FileNotFoundException();
            }
            String stringfile=reader.readLine();
            String[] strarray=stringfile.split(" ");
            String str=strarray[0];
            for(int i=1;i<Alphabet.size;i++){
                key=i;
                StringBuilder stringBuilder=new StringBuilder();
                for(int j=0;j<str.length();j++){
                    char symbol=str.charAt(j);
                    if(Alphabet.index.containsKey(symbol)){
                        int index=Alphabet.index.get(symbol);
                        int newIndex=(index-key)%Alphabet.size;
                        if(newIndex<0){
                            newIndex+=Alphabet.size;
                        }
                        symbol=Alphabet.chars[newIndex];
                    }
                    stringBuilder.append(symbol);
                }
                System.out.println("Строка: "+stringBuilder+" Ключ: "+key);
            }
            Scanner sc=new Scanner(System.in);
            System.out.println("Выберите наиболее правильный вариант ключа:");
            int tmp=sc.nextInt();
            sc.close();
            Decode.decode(source,dest,tmp);

        }
        catch (FileNotFoundException e){
            System.out.println("Ошибка:файл не найден");
        }
        catch (Exception e){
            System.out.println("Ошибка");
        }
    }
}
