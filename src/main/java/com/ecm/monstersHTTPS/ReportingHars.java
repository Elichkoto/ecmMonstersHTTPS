package com.ecm.monstersHTTPS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReportingHars {
	public static void main(String [] args) throws IOException {
    	String outputFile = new SimpleDateFormat("yyyyMMddHHmmss'.txt'").format(new Date());
        String outputFileExtended = "C:\\tmp\\reports\\report" + outputFile;
        String fileName = "C:\\tmp\\tests\\export";        
        String titleAttribute = "\"title\":\"";
        String urlAttribute ="\"url\":\"http://";
        ArrayList<String> wordArrayList = new ArrayList<String>();
        String[] separatedJSON;
        
        String txtFile = renameFile(fileName);  
              
        separatedJSON = readFile(txtFile).replaceAll("\\s+","").split(",");
        
		wordArrayList.addAll(resultForElement(titleAttribute, separatedJSON));
		wordArrayList.addAll(resultForElement(urlAttribute, separatedJSON));
		writeFile(wordArrayList,outputFileExtended);
		tearDown(fileName + ".txt");
    }

    public static String renameFile(String inputFileName) {
    	String harFile = inputFileName + ".har";
        String txtFile = inputFileName + ".txt";
        new File(harFile).renameTo(new File(txtFile));
        return txtFile;
    }
    
	public static String readFile(String inputName) throws IOException {
		StringBuilder myLine = new StringBuilder();
		String line = null;
	        try {
	            FileReader fileReader =  new FileReader(inputName);
	            BufferedReader bufferedReader = new BufferedReader(fileReader);
	            while((line = bufferedReader.readLine()) != null) {
	            	myLine.append(line);            	
	            }
	            bufferedReader.close();                     
	        }        
	        catch(FileNotFoundException ex) {
	            System.out.println("Unable to open file '" + inputName + "'");                
	        }
	        catch(IOException ex) {
	            System.out.println("Error reading file '" + inputName + "'");                     
	        }
	        return myLine.toString();
	}

	public static ArrayList<String> resultForElement(String attribute, String[] list) {
		ArrayList<String> wordArrayList = new ArrayList<String>();
		for (String item : list) {
			if(item.contains(attribute)) {
				wordArrayList.add(item);
			}

		}
		return wordArrayList;
	}

	public static void writeFile(ArrayList<String> list, String outputFileName) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new FileOutputStream(outputFileName));
			for (String item : list) {
				 writer.println(item);    
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Cannot create a new file!");
		}
		finally {
			writer.close();
		}		 
	}
	
	public static void tearDown(String path) {
		try
		{
    		File file = new File(path);

    		if(file.delete()){
    			System.out.println(file.getName() + " is deleted!");
    		}else{
    			System.out.println("Delete operation is failed.");
    		}

    	}
		catch(Exception e){
    		e.printStackTrace();
    	}
	}
}
