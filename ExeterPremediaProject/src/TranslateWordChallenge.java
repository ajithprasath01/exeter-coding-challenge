import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class TranslateWordChallenge {

	public static void main(String[] args) {
		
		long startMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
		
		Instant startTime =Instant.now();													
		
		//calling the method
		translate();
		
		Instant endTime=Instant.now();
	
		long milliseconds=Duration.between(startTime, endTime).toMillis();	
		
		long endMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
		long memory=(startMem-endMem);
		
		timeNMemCal(milliseconds,memory);
	}
	
	
	public static void translate() {
	
		
	 FileReader fr=null;
	 BufferedReader br=null;
	 
	 FileReader frc=null;
	 BufferedReader brc=null;
	 
	 FileReader frp=null;
	 BufferedReader brp=null;
	
	 
	 FileWriter freqFw=null;
	 BufferedWriter freqBw=null;
	 
	 FileWriter translatedFw=null;
	 BufferedWriter translatedBw=null;
		
		
	 
	 ArrayList<String> afw=new ArrayList<>();
	 LinkedHashMap<String,String> hm=new LinkedHashMap<>();
	 LinkedHashMap<String,Integer> cm=new LinkedHashMap<>();
	 
	 
	 try {
		 fr=new FileReader("C:\\Users\\Downloads\\find_words.txt");
		 br=new BufferedReader(fr);
		 String str=br.readLine();
		 
		 frc=new FileReader("C:\\Users\\Downloads\\french_dictionary.csv");
		 brc=new BufferedReader(frc);
		 String sc=brc.readLine();
		 String[] dic= {};	
		 
		 frp=new FileReader("C:\\Users\\Downloads\\t8.shakespeare.txt");
		 brp=new BufferedReader(frp);
		 String stp=brp.readLine();
		 String wp="";
		 String wpE="";
		
		 
 //Reading the input from the file(find_words.txt) using FileReader & BufferedReader		 
		 while(str!=null) {
			 afw.add(str);
			 str=br.readLine();
			 }

  //Reading the input from the file(french_dictionary.csv) using FileReader & BufferedReader. And checking words from find_words are in french dictionary and storing them in Map.	 
		 int i=0;
		 while(sc!=null) {
			 dic=sc.split(",");
			 sc=brc.readLine();
			 if(afw.get(i).equals(dic[0])) {
				 hm.put(dic[0], dic[1]);
			 }
			 i++;	 
		 }
		 
  //Reading the input from the file(t8.shakespeare.txt) using FileReader & BufferedReader
		 while(stp!=null) {
			 wp=wp+stp+System.lineSeparator();
			 
			 if(stp.lines()!=null ){
				 wpE=wpE+" "+stp;
			}
			 else if(stp!=" " ){
			 wpE=wpE+stp+System.lineSeparator();
			 }
			
			 stp=brp.readLine();
	    }
 		
  //Replacing the matched text using replaceAll() & collection Map	 
 		for(Map.Entry<String,String>m : hm.entrySet()) {
 			
 				wp=wp.replaceAll(m.getKey(), m.getValue());
			 }
 		
 		
        String reg="[\"*!-&+$?.@\\,\'\\s ]";
 		String pa[]= wpE.split(reg);
 		
  //Counting the number of times the words repeated(Frequency) & Storing it in a collection Map
 	 	for(Map.Entry<String,String>m2 : hm.entrySet()) {
 	 		int ct=0;
 			String map= m2.getKey();
  		   for(int q=0;q<pa.length;q++) {
 		if(map.equalsIgnoreCase(pa[q])){
 				ct++;
 			}
 		}
  		   	cm.put(map, ct);
	 }
 
 
 	 	
  //Printing the Output(English words,French words,Frequency) to the frequency.csv file  and in console using FileWriter & BufferedWriter
 	 	
 		freqFw=new FileWriter("C:\\Users\\Documents\\frequency.csv");
 		freqBw=new BufferedWriter(freqFw);
 		
 		System.out.println("English word, French word, Frequency");
 		
 		freqBw.write("English word,French word,Frequency\n");
 		for(Entry<String,String>freq : hm.entrySet()) {
 			for(Entry<String, Integer> freq2 : cm.entrySet()) {
 				
 		 		if(freq.getKey().equalsIgnoreCase(freq2.getKey())) {
 			
 		 			freqBw.write(freq.getKey()+","+freq.getValue()+","+freq2.getValue()+"\n");
 			
 		 			System.out.println(freq.getKey()+", "+freq.getValue()+", "+freq2.getValue());
 		     }
 		 }
 	}	
 	
 		
 		
  //Printing the output(Translated paragraph) to t8.shakespeare.translated.txt file and in Console using FileWriter & BufferedWriter		
 		
 		translatedFw=new FileWriter("C:\\Users\\Documents\\t8.shakespeare.translated.txt");
 		translatedBw=new BufferedWriter(translatedFw);
 		
 		translatedBw.write(wp);
 		
 		System.out.println(wp+"\n");
 		
	 }
 	 
	 catch(FileNotFoundException e) {
		 e.printStackTrace();
	 } 
	 catch (IOException e) {
	
		e.printStackTrace();
	}
	 
	 finally {
			try {
				
				translatedBw.close();
				translatedFw.close();
				
				freqBw.close();
				freqFw.close();
				
				brp.close();
				brc.close();
				br.close();
				
				frp.close();
				frc.close();
				fr.close();
		    }
			catch (IOException e) {
				e.printStackTrace();
			}
		 }
	  }
	
	
  //Calculating time & memory
	
	public static void timeNMemCal(long milliseconds,long mem) {
	long seconds =(milliseconds/1000)%60;
	long minutes=(milliseconds/1000)/60;
	
	long memory=mem/(1024*1024);
	
  //Printing output(Time to process & Memory used) into the perfomance.txt file and in Console using FileWriter & BufferedWriter	
	
	FileWriter preFw=null;
	BufferedWriter preBw=null;

	try {
		preFw=new FileWriter("C:\\Users\\Documents\\perfomance.txt");
		preBw=new BufferedWriter(preFw);
		
		preBw.write("Time to process: "+minutes+" minutes "+seconds+" seconds\n");
		preBw.write("Memory used: "+memory+" MB");								
		
		
		System.out.println("\nTime to process: "+minutes+" minutes "+seconds+" seconds");
		System.out.println("Memory used: "+memory+" MB");								
		
	}
	catch(FileNotFoundException e) {
		 e.printStackTrace();
	}
	catch (IOException e) {
		e.printStackTrace();
	}
		
	finally {
		try {
			preBw.close();
			preFw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }
		
  }
	
	
}








