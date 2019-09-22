package de.whiletrue.openrobertamusicconverter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main {

	/*
	 * Uses https://pianoletternotes.blogspot.com/
	 * */
	
	private static Main instance;
	private int duration,speed;
	
	public Main(String[] args) {
		//Sets the instance
		instance = this;
		
		//Creates the converter
		new Converter();
		new Dictonary();
		
		//Outputs some information
		System.out.println("OpenRoberta Sound converter by while(true);");
		
		//Checks if a file was specifyed
		if(args.length<3) {
			System.out.println("Bitte gebe eine folgendes an: Datei, Speed und dauer.");
			return;
		}
		
		//Gets the file
		File song = new File(args[0]);
		
		//Checks if that file exists
		if(!song.exists()) {
			System.out.println("Diese Datei exestiert nicht.");
			return;
		}
		//Reads the file
		Optional<String[]> optLines = this.readFile(song);
		//Checks if the file could be read
		if(!optLines.isPresent()) {
			System.out.println("Datei konnte nicht gelesen werden.");
			return;
		}
		
		//Checks if the speed is an int
		if(!this.isInt(args[1])) {
			System.out.println("Bitte gebe den Speed als Ganzzahl an.");
			return;
		}
		
		//Checks if the duration is an int
		if(!this.isInt(args[2])) {
			System.out.println("Bitte gebe die Dauer als Ganzzahl an.");
			return;
		}
		
		//Converts speed and duration
		this.speed = Integer.valueOf(args[1]);
		this.duration = Integer.valueOf(args[2]);
		
		//Converts the lines to code blocks
		Optional<String> optBlocks = Converter.getInstance().convert(optLines.get());
		
		//Checks if the parsing was done right
		if(!optBlocks.isPresent()) {
			System.out.println("Konnte den Song nicht konvertieren.");
			return;
		}
		
		//generates a full programm out of the blocks
		String programm = Dictonary.getInstance().generateProgramm(optBlocks.get());
		
		//Prints the programm to the file
		if(!this.writeFile(new File("out.xml"), programm)) {
			System.out.println("Konnte keine Datei mit der Aufgabe erstellen.");
			return;
		}
	}
	
	/*
	 * Function to start the program
	 * */
	public static void main(String[] args) {
		//Starts the program
		new Main(args);
	}
	
	
	/*
	 * Function to write content on a file
	 * */
	public boolean writeFile(File file,String content) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write(content);
			bw.flush();
			bw.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/*
	 * Function used to read files that exists
	 * */
	public Optional<String[]> readFile(File file){
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String ln;
			List<String> lines = new ArrayList<>();
			while((ln=br.readLine())!=null) {
				if(ln.startsWith("LH:")||ln.startsWith("RH:"))
					ln=ln.substring(3,ln.length());
				lines.add(ln);
			}
			br.close();
			return Optional.of((String[]) lines.toArray(new String[lines.size()]));
		} catch (Exception e) {
			return Optional.empty();
		}
	}
	
	/*
	 * Returns if the given string can be converted into a int
	 * */
	public boolean isInt(String conv) {
		try {
			Integer.valueOf(conv);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/*
	 * Getters for the diffrent values
	 * */	
	public static Main getInstance() {
		return instance;
	}
	public int getDuration() {
		return duration;
	}
	public int getSpeed() {
		return speed;
	}
	
}
