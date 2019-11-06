package com.martinemil.aast.sic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class SicReader {

    public SicReader(String path){
        try {

            BufferedReader sicInstruction = new BufferedReader(new FileReader(path));


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
