package de.whiletrue.openrobertamusicconverter;

import java.util.HashMap;
import java.util.Map;

public class Dictonary {

	private static Dictonary instance;
	private Map<Integer,Map<Character,Double>> map;
	
	private String programm = "<export xmlns=\"http://de.fhg.iais.roberta.blockly\"><program><block_set robottype=\"ev3\" xmlversion=\"2.0\" description=\"\" tags=\"\"><instance x=\"0\" y=\"0\"><block type=\"robControls_start\" id=\"z\" intask=\"true\" deletable=\"false\"><mutation declare=\"false\" /><field name=\"DEBUG\">FALSE</field></block><block type=\"mbedActions_play_note\" id=\"y\" intask=\"true\"><field name=\"DURATION\">100</field><field name=\"FREQUENCE\">587.33</field></block>%programm%</instance></block_set></program><config><block_set robottype=\"ev3\" xmlversion=\"2.0\" description=\"\" tags=\"\"><instance x=\"213\" y=\"213\"><block type=\"robBrick_EV3-Brick\" id=\"1\" intask=\"true\" deletable=\"false\"><field name=\"WHEEL_DIAMETER\">5.6</field><field name=\"TRACK_WIDTH\">18</field><value name=\"S1\"><block type=\"robBrick_touch\" id=\"2\" intask=\"true\" /></value><value name=\"S2\"><block type=\"robBrick_gyro\" id=\"3\" intask=\"true\" /></value><value name=\"S3\"><block type=\"robBrick_colour\" id=\"4\" intask=\"true\" /></value><value name=\"S4\"><block type=\"robBrick_ultrasonic\" id=\"5\" intask=\"true\" /></value><value name=\"MB\"><block type=\"robBrick_motor_big\" id=\"6\" intask=\"true\"><field name=\"MOTOR_REGULATION\">TRUE</field><field name=\"MOTOR_REVERSE\">OFF</field><field name=\"MOTOR_DRIVE\">RIGHT</field></block></value><value name=\"MC\"><block type=\"robBrick_motor_big\" id=\"7\" intask=\"true\"><field name=\"MOTOR_REGULATION\">TRUE</field><field name=\"MOTOR_REVERSE\">OFF</field><field name=\"MOTOR_DRIVE\">LEFT</field></block></value></block></instance></block_set></config></export>";
	
	public Dictonary() {
		instance = this;
		this.map = new HashMap<>();
		
		Map<Character,Double> m2 = new HashMap<Character,Double>();
		m2.put('c',130.813);
		m2.put('C',138.591);
		m2.put('d',146.832);
		m2.put('D',155.563);
		m2.put('e',164.814);
		m2.put('f',174.614);
		m2.put('F',184.997);
		m2.put('g',195.998);
		m2.put('G',207.652);
		m2.put('a',220d);
		m2.put('A',233.082);
		m2.put('b',246.942);
		Map<Character,Double> m3 = new HashMap<Character,Double>();
		m3.put('c',261.626);
		m3.put('C',277.183);
		m3.put('d',293.665);
		m3.put('D',311.127);
		m3.put('e',329.628);
		m3.put('f',349.228);
		m3.put('F',369.994);
		m3.put('g',391.995);
		m3.put('G',415.305);
		m3.put('a',440d);
		m3.put('A',466.164);
		m3.put('b',493.883);
		Map<Character,Double> m4 = new HashMap<Character,Double>();
		m4.put('c',523.251);
		m4.put('C',554.365);
		m4.put('d',587.33);
		m4.put('D',622.254);
		m4.put('e',659.255);
		m4.put('f',698.456);
		m4.put('F',739.989);
		m4.put('g',783.991);
		m4.put('G',830.609);
		m4.put('a',880d);
		m4.put('A',932.328);
		m4.put('b',987.767);
		Map<Character,Double> m5 = new HashMap<Character,Double>(m4);
		Map<Character,Double> m6 = new HashMap<Character,Double>(m4);
		this.map.put(1, m2);
		this.map.put(2, m3);
		this.map.put(3, m4);
		this.map.put(4, m5);
		this.map.put(5, m6);
	}
	
	public static Dictonary getInstance() {
		return instance;
	}
	//856,954 <> 130.813
	
	public Double getValue(Integer clazz,Character cha) {
		if(cha=='-')
			return -1d;
		clazz--;
		if(clazz>=4)
			clazz=3;
		
		return this.map.get(clazz).get(cha);
	}
	
	public String generateProgramm(String blocks) {
		return this.programm.replace("%programm%", blocks);
	}
}
