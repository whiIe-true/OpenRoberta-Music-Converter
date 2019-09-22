package de.whiletrue.openrobertamusicconverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Converter {

	private static Converter instance;
	
	public Converter() {
		instance = this;
	}
	
	public static Converter getInstance() {
		return instance;
	}
	
	/*
	 * Converting method
	 * */
	public Optional<String> convert(String[] lines) {
		
		//Checks if any lines are given
		if(lines.length<=0)
			return Optional.empty();
		
		int len = lines[0].length();
		
		//Checks if all lines are valid
		if(!Arrays.stream(lines).allMatch(i->i.length()==0||(i.matches("[1-6]\\|[\\-c-gC-GABab]+\\|")&&i.length()==len)))
			return Optional.empty();
		
		//Splits the lines into groups that must be played at the same time
		Map<Integer, List<String>> parsed = this.splitLines(lines);
		//Creates a list where all code block will be stored
		List<Double> full = new ArrayList<Double>();
		
		//Streams all parsed lines
		parsed.entrySet().stream()
		//Converts them into one line
		.map(i->this.convertLines(i.getValue()))
		//Adds the lines to the full array
		.forEach(i->full.addAll(Arrays.asList(i)));
		
		//Copys the whole full list but replaces all single waits with multi waits
		List<Double> copy = new ArrayList<>();
		//Iterates over all elments of the list
		for(int i = 0; i < full.size(); i++) {
			//Gets the current element
			double curr = full.get(i);
			//Checks if its lesser or 0 and if the latest element is the same
			if(!copy.isEmpty()&&curr<=0&&copy.get(copy.size()-1)<=-1) {
				//Copys it onto the latest elements
				copy.set(copy.size()-1, copy.get(copy.size()-1)+curr);
			}else
				//just adds the element to the list
				copy.add(full.get(i));
		}
		
		//Generates the code blocks
		String blocks = "";
		for(double x : copy)
			blocks+=this.generateBlock(x);
		
		//Returns the code blocks
		return Optional.of(blocks);
	}
	
	private String generateBlock(double value) {
		//Checks if the value say that the programm should wait
		if(value<=0)
			return "<block type=\"robControls_wait_time\" id=\"a\" intask=\"true\"><value name=\"WAIT\"><block type=\"math_number\" id=\"c\" intask=\"true\"><field name=\"NUM\">"+(Main.getInstance().getSpeed()*value*-1)+"</field></block></value></block>";
		return "<block type=\"mbedActions_play_note\" id=\"b\" intask=\"true\"><field name=\"DURATION\">"+Main.getInstance().getDuration()+"</field><field name=\"FREQUENCE\">"+value+"</field></block>";
	}
	
	/*
	 * Function to convert lines into the perfect matching line and converts them into there frequences
	 * */
	private Double[] convertLines(List<String> lines) {
		//Creates the prefect line
		Double[] perfectMatch = new Double[lines.get(0).length()-3];

		//Iterates over the nodes only
		for(int i = 2; i < lines.get(0).length()-1; i++) {
			Double pref = -1d;
			
			//Iterates over the lines
			for(String s : lines) {
				Integer multi = Integer.valueOf(String.valueOf(s.charAt(0)));
				if(multi<=5)
					multi=4;
				if(pref<=0) {
					pref = Dictonary.getInstance().getValue(multi, s.charAt(i));
				}
			}
			//Adds the prefered character to the array
			perfectMatch[i-2]=pref;
		}
		
		return perfectMatch;
	}
	
	/*
	 * Function to split lines into groups that must be played at the same time
	 */
	private Map<Integer,List<String>> splitLines(String[] lines){
		//Sets a counter to split into groups
		AtomicInteger counter = new AtomicInteger(0);
		
		//Goes through every line and puts it into a group
		Map<Integer, List<String>> out = Arrays.stream(lines)
				.collect(Collectors.groupingBy(i->{
					//Casts the unknown object to a string
					String cast = (String) i;
					//Checks if the lines is empty 
					if(cast.isEmpty()) {
						//Returns trash group and increases the counter
						counter.incrementAndGet();
						return -1;
					}
					return counter.get();
				}));
		//Throws out the trash group
		out.remove(-1);
		return out;
	}
	
}
