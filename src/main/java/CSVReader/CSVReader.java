package CSVReader;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    public static ArrayList<ArrayList<String>> readRows(File file) throws IOException {
        if(!file.getName().endsWith(".csv")){
            throw new IOException();
        }
        BufferedReader buffer = new BufferedReader(new FileReader(file, StandardCharsets.ISO_8859_1));
        ArrayList<ArrayList<String>> rows = new ArrayList<>();
        buffer.readLine();
        String row = buffer.readLine();
        while(row != null){

            if (row.contains("\"")){
                if(row.split("\"").length < 3){
                    row = buffer.readLine();
                    continue;
                }
                else{
                    row = parseQuotes(row);
                }
            }

            ArrayList<String> parsedRow = parseRow(row);

            if(parsedRow.size() == 40) {
                rows.add(parsedRow);
            }
            row = buffer.readLine();
        }
        return rows;
    }

    private static ArrayList<String> parseRow(String row) throws IndexOutOfBoundsException{
        return new ArrayList<String>(List.of(row.split(";")));
    }

    private static String parseQuotes(String row){
        ArrayList<String> splitQuotes = new ArrayList<String>(List.of(row.split("\"")));

        return splitQuotes.get(0) + splitQuotes.get(1).replace(';', ',') + splitQuotes.get(2);
    }


}
