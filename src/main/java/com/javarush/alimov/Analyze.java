package com.javarush.alimov;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Analyze {
    private static Map<Character,Double> letterFrequence(String source){
        Map<Character,Double> freq=new HashMap<>();
        Map<Character,Integer> counter=new HashMap<>();
        int total=0;
        try(BufferedReader reader=new BufferedReader(new FileReader(source))){
            if(!Files.exists(Path.of(source))){
                throw  new FileNotFoundException();
            }
            int read;
            while((read=reader.read())!=-1){
                char symbol=(char)read;
                if(Alphabet.index.containsKey(symbol)){
                    counter.put(symbol, counter.getOrDefault(symbol,0)+1);
                    total++;
                }
            }
                for (char ch : Alphabet.chars) {
                    double p = (total == 0) ? 0.0 : counter.getOrDefault(ch, 0) / (double) total;
                    freq.put(ch, p);
                }
        }
        catch (FileNotFoundException e){
            System.out.println("Ошибка:файл не найден");
        }
        catch (Exception e){
            System.out.println("Ошибка");
        }
        if(total==0){
            for (char ch : Alphabet.chars) {
                freq.put(ch, 0.0);
            }
        }
        return freq;
    }
    private static int findBestKey(Map<Character, Double> representativeFreq, Map<Character, Double> encryptedFreq) {
        int bestKey = 0;
        double minError = Double.MAX_VALUE;

        for (int key = 0; key < Alphabet.size; key++) {
            double error = 0.0;
            for (char c : Alphabet.chars) {
                int shiftedIndex = (Alphabet.index.get(c) + key) % Alphabet.size;
                char shifted = Alphabet.chars[shiftedIndex];

                double repVal = representativeFreq.get(c);
                double encVal = encryptedFreq.get(shifted);
                error += Math.pow(repVal - encVal, 2);
            }
            if (error < minError) {
                minError = error;
                bestKey = key;
            }
        }
        return bestKey;
    }
    public static void decrypt(String source,String repText, String dest){
        Map<Character,Double> src=letterFrequence(source);
        Map<Character,Double> rep=letterFrequence(repText);
       int key= findBestKey(rep,src);
        Decode.decode(source,dest,key);

    }

}
