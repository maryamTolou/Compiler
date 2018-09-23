import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Tables {
	
	//Variables Table
	private Token variables[];
	//RHST TABLE
	private Token rhst[][];
	//Grammer Table
	private Token grammer[][];
	
	private int noGrammerLine , noGrammerCol , noVariables , noTerminals;
	private int temp;
	
	
	public Tables(String variablesFile , String grammerFile) throws IOException{
		
		noTerminals = Terminal.values().length;
		
		noVariables = getNoLines(variablesFile);
		noGrammerCol = getNoColGrammer(grammerFile);
		noGrammerLine = getNoLines(grammerFile);

		
		variables = new Token[getNoLines(variablesFile)];
		for(int i = 0 ; i < getNoLines(variablesFile) ; i++)
			variables[i] = new Token();
		makeVariables(variablesFile);

		
		grammer = new Token[noGrammerLine][noGrammerCol];
		for(int i = 0 ; i < noGrammerLine ; i++){
			for(int j = 0 ; j < noGrammerCol ; j++){
				grammer[i][j] = new Token("0" , TokenType.endOfFile);
			}
		}
		makeGrammerTable(grammerFile);
		
		

		rhst = new Token[getNoLines(grammerFile)][getNoColGrammer(grammerFile)];
		for(int i = 0 ; i < getNoLines(grammerFile) ; i++){
			for(int j = 0 ; j < getNoColGrammer(grammerFile) ; j++){
				rhst[i][j] = new Token("0" , TokenType.endOfFile);
			}
		}
		makeRhst(grammer);
		
		

	}
	
	public Tables(){ }
	
	//tedad khat haye yek file:
	private int getNoLines(String fileName) throws IOException {
		int noLine = 0;
	    BufferedReader br = new BufferedReader(new FileReader(fileName));
	    try {
	        String line = br.readLine();
	        
	        while (line != null) {
	            noLine++;
	            line = br.readLine();
	        }
	        return noLine;
	    } finally {
	        br.close();
	    }
	}
	
	//tedad sotoon haye yek file:
	private int getNoColGrammer(String fileName) throws FileNotFoundException{
		int tempCol = 0 , col = 0;
		Scanner sc2 = new Scanner(new File(fileName));
	    
	    while (sc2.hasNextLine()) {
	        Scanner s2 = new Scanner(sc2.nextLine());
	        while (s2.hasNext()) {
	        	s2.next();
	            tempCol++;
	        }
	        if(tempCol > col)
	        	col = tempCol;
	        tempCol = 0;
	    }
	    return col;
	}
	
	//arraye i az variable ha:
	private void makeVariables(String fileName) throws IOException {
		temp = 0;
		
		Scanner sc2 = new Scanner(new File(fileName));
		
	    while (sc2.hasNextLine()) {   
	    	variables[temp].value = sc2.nextLine();
	    	variables[temp].tokenType = TokenType.VR;
	    	temp++;
	    }
	    
	       
		for(int i = 0 ; i < variables.length ; i++){
			for(int j = i+1 ; j < variables.length ; j++){
				if(variables[i].equals(variables[j])){
					//Motaqayer tekrari
					System.out.println("ERROR in your variables!");
					System.exit(0);
				}
			}
		}
	        
  
	}
	
	//jadval grammer ha:
	private void makeGrammerTable(String fileName) throws IOException{
		int tempLine = 0 , tempCol = 0;
		Scanner sc2 = new Scanner(new File(fileName));
	    
	    while (sc2.hasNextLine()) {
	        Scanner s2 = new Scanner(sc2.nextLine());
	        while (s2.hasNext()) {
	        	String temporary = s2.next();
	        	if(isUpperCase(temporary) || temporary.contains("#")){
	        		grammer[tempLine][tempCol].tokenType = TokenType.VR;
	        		grammer[tempLine][tempCol].value = temporary;
	        	}
	        	else{
	        		if(temporary.equals("id")){
	        			grammer[tempLine][tempCol].tokenType = TokenType.id;
		        		grammer[tempLine][tempCol].value = temporary;
	        		}
	        		else if(temporary.equals("num")){
	        			grammer[tempLine][tempCol].tokenType = TokenType.num;
		        		grammer[tempLine][tempCol].value = temporary;
	        		}
	        		else if(findSpecialToken(temporary)){
	        			grammer[tempLine][tempCol].tokenType = TokenType.specialToken;
		        		grammer[tempLine][tempCol].value = temporary;
	        		}
	        		else if(findKeyword(temporary)){
	        			grammer[tempLine][tempCol].tokenType = TokenType.keyWord;
		        		grammer[tempLine][tempCol].value = temporary;
	        		}
	        		else if(temporary.equals("null")){
	        			grammer[tempLine][tempCol].tokenType = TokenType.endOfFile;
	        			grammer[tempLine][tempCol].value = "null";
	        		}
	        		else{
	        			grammer[tempLine][tempCol].tokenType = TokenType.string;
		        		grammer[tempLine][tempCol].value = temporary;
	        		}
	        	}
	            tempCol++;
	        }
	        tempLine++;
	        tempCol = 0;
	    }
	    
	}
	
	//jadval rhst
	private void makeRhst(Token [][] grammer){
		
		for(int i = 0 ; i < noGrammerLine ; i++){
			int temp = 1;
			for(int j = lengthTable(grammer[i]) - 1 ; j >= 0 ; j--){
				if(j == 0){
					rhst[i][j].value = Integer.toString(i+1);
					rhst[i][j].tokenType = TokenType.endOfFile;
				}else{
					rhst[i][temp] = grammer[i][j];
					temp++;
				}
			}
		}
	}
	
	private boolean isUpperCase(String string){
		int tempo = 0;
		while(tempo < string.length()){
			if(!Character.isUpperCase(string.charAt(tempo)))
				return false;
			tempo++;
		}
		return true;
	}

	private boolean findKeyword(String keyWord){
		for(KeyWord kw : KeyWord.values()){
			if(kw.getDesc().equals(keyWord)){
				return true;
			}
		}
		return false;
	}
	
	private boolean findSpecialToken(String specialToken){
		for(SpecialToken st : SpecialToken.values()){
			if(st.getDesc().equals(specialToken)){
				return true;
			}
		}
		
		return false;
	}
	
	private int lengthTable(Token[] token){
		int length = 0;
		for(int i = 0 ; i < token.length ; i++){
			if(token[i].value.equals("0"))
				break;
			length++;
		}
		return length;
	}
	
	public int getNoVariables(){
		return noVariables;
	}

	public int getNoGrammerLine(){
		return noGrammerLine;
	}
	
	public int getNoGrammerCol(){
		return noGrammerCol;
	}

	public int getNoTerminals(){
		return noTerminals;
	}
	
	public Token[][] getRhstTable(){
		return rhst;
	}
	
	public Token[][] getGrammerTable(){
		return grammer;
	}
	
	public Token[] getVariablesList(){
		return variables;
	}


}
