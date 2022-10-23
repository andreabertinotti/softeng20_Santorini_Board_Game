package it.polimi.ingsw.Parser;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ParserCSV {
        String csvFile = "connections.csv";
        List<String[]> data;

        CSVReader reader = null;

        public ParserCSV(){
            try {
                reader = new CSVReader(new FileReader(csvFile));
                data = reader.readAll();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public int readPort(){
            String myString = data.get(0)[0];
            int portNumber = Integer.parseInt(myString);
            return portNumber;
        }

        public String readHostName(){
            return data.get(1)[0];
        }

 }

