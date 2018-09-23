import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MyScanner {

	private Scanner in = new Scanner(System.in);
	
	private static String sourceCode;
	private static int temp = 0;
	private static char ch;
	private static Token token;
	public static int currentLine = 1;
	private String value = "";
	
	
	public MyScanner(String fileName) throws IOException {
		token = new Token();
		readFile(fileName);
		ch = getChar();
	}

	private String readFile(String fileName) throws IOException {
	    BufferedReader br = new BufferedReader(new FileReader(fileName));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        sourceCode = sb.toString();
	        return sourceCode;
	    } finally {
	        br.close();
	    }
	}
	
	private boolean findKeyword(String keyWord){
		for(KeyWord kw : KeyWord.values()){
			if(kw.getDesc().equals(keyWord)){
				return true;
			}
		}
		
		return false;
	}
	
	private char getChar(){

		char temporary = Character.MIN_VALUE;
		if(temp < sourceCode.length()){
			temporary = sourceCode.charAt(temp);
			temp++;
			return temporary;
		}
		
		return Character.MIN_VALUE;
	}
	
	
	
	public Token getToken(){
		value = "";
		
		if(ch == '\n'){
			currentLine++;
			ch = getChar();
			return getToken();
		}
		
		switch(ch){
		
		case ' ':
			ch = getChar();
			return getToken();
			
		case '\t':
			ch = getChar();
			return getToken();
		
			
		case '/':
				
			ch = getChar();				
			if(ch == '/'){
				while(ch != '\n' && ch != '$'){
					ch = getChar();
				}
				return getToken();
			}
			else if( ch == '*'){
				do{
					ch = getChar();
					
					while(ch!= '*' && ch != '$')
						ch = getChar();
						
					if(ch == '$'){
						System.out.println("ERROR COMMENT SECTION 1 EOF !");
						return null;
					}
			
						
					}while(ch != '/');
				
				//fek konam niaz nist check beshe...
				if(ch == '/'){
					ch = getChar();
					return getToken();
				}
				else{
					System.out.println("ERROR COMMENT SECTION 2 !");
					return null;
				}
			}
			else{
				ch = getChar();
				token.tokenType = TokenType.specialToken;
				token.value = "/";
				return token;
			}
		
		
		case '(':
			ch = getChar();
			token.tokenType = TokenType.specialToken;
			token.value = "(";
			return token;
			
		case ')':
			ch = getChar();
			token.tokenType = TokenType.specialToken;
			token.value = ")";
			return token;
			
		case '=':
			
			ch = getChar();	
			if(ch == '='){
				ch = getChar();
				token.tokenType = TokenType.specialToken;
				token.value = "==";
				return token;
			}
			else{
				token.tokenType = TokenType.specialToken;
				token.value = "=";
				return token;
			}
			
			
		case '+':
			ch =getChar();
			if(ch == '+'){
				ch = getChar();
				token.tokenType = TokenType.specialToken;
				token.value = "++";
				return token;
			}else{
				token.tokenType = TokenType.specialToken;
				token.value = "+";
				return token;
			}
			
		case '-':
			ch = getChar();
			if(ch == '-'){
				ch = getChar();
				token.tokenType = TokenType.specialToken;
				token.value = "--";
				return token;
			}
			else{
				token.tokenType = TokenType.specialToken;
				token.value = "-";
				return token;
			}
			
		case '*':
			ch = getChar();
			token.tokenType = TokenType.specialToken;
			token.value = "*";
			return token;
			
		case '%':
			ch = getChar();
			token.tokenType = TokenType.specialToken;
			token.value = "%";
			return token;
			
		case '!':
			ch = getChar();
			token.tokenType = TokenType.specialToken;
			token.value = "!";
			return token;
			
		case '&':
			ch = getChar();
			if(ch == '&'){
				ch = getChar();
				token.tokenType = TokenType.specialToken;
				token.value = "&&";
				return token;
			}
			else{
				System.out.println("ERROR & SECTION!");
				return null;
			}
			
		case '|':
			ch = getChar();
			if(ch == '|'){
				ch = getChar();
				token.tokenType = TokenType.specialToken;
				token.value = "||";
				return token;
			}
			else{
				System.out.println("ERROR | SECTION!");
				return null;
			}
			
		case ';':
			ch = getChar();
			token.tokenType = TokenType.specialToken;
			token.value = ";";
			return token;
			
		case ',':
			ch = getChar();
			token.tokenType = TokenType.specialToken;
			token.value=",";
			return token;
			
		case '>':
			ch = getChar();
			if(ch == '='){
				ch =getChar();
				token.tokenType = TokenType.specialToken;
				token.value = ">=";
				return token;
			}
			else{
				token.tokenType = TokenType.specialToken;
				token.value = ">";
				return token;
			}
			
		case '<':
			ch = getChar();
			if(ch == '='){
				ch = getChar();
				token.tokenType = TokenType.specialToken;
				token.value = "<=";
				return token;
			}
			else{
				token.tokenType = TokenType.specialToken;
				token.value = "<";
				return token;
			}
			
		case '{':
			token.tokenType = TokenType.specialToken;
			token.value = "{";
			ch = getChar();
			return token;
			
		case '}':
			token.tokenType = TokenType.specialToken;
			token.value = "}";
			ch =getChar();
			return token;
			
		case '$':
			token.tokenType = TokenType.endOfFile;
			token.value = "$";
			ch = getChar();
			return token;
			
		case '.':
			System.out.println("Error in Dot Section... nothing can start with dot");
			return new Token("$" , TokenType.endOfFile);
			
		//STRING	
		case '\"':
			value = "";
			ch =getChar();
			while(ch != '\"'){
					value += ch;
					ch = getChar();
			}
			token.tokenType = TokenType.string;
			token.value = value;
			ch = getChar();
			return token;
			
		
			
		default :
			int dot = 0;
			boolean flag = true;

			
			while(Character.isDigit(ch) || Character.isLetter(ch) || ch == '_' ){
				value += ch;
				ch = getChar();
			}
			
			if(findKeyword(value)){
				token.tokenType = TokenType.keyWord;
				token.value = value;
				return token;
			}

			//NUMBER and ID and KeyWord
			for(int i = 0 ; i < value.length() ; i++){
				
				if(value.charAt(i) == '.'){
					dot++;
					if(dot>1){
						System.out.println("ERROR IN DEFAULT SECTION 1! more than 1 dot...");
						break;
					}
				}
				
				//number
				if(Character.isDigit(value.charAt(0))){
					if( (Character.isDigit(value.charAt(i)) || (value.charAt(i) == '.' )) && i == value.length() - 1 ){
						token.tokenType = TokenType.num;
						token.value = value;
						flag = false;
						return token;
					}
					else if (i == value.length() - 1 ){
						System.out.println("ERROR IN DEFAULT SECTION 2!not id and not number and not keyword...");
						flag = false;
						break;
					}
				}

				//ID 
				if( (Character.isLetter(value.charAt(i)) || Character.isDigit(value.charAt(i)))  && i == value.length() - 1  && flag && dot == 0){
					
					token.tokenType = TokenType.id;
					token.value = value;
					return token;
				}
				else if (i == value.length() - 1){
					System.out.println("ERROR IN DEFAULT SECTION 3!");
					break;
				}
				
			}
			
			

		}
		//check
		token.tokenType = TokenType.endOfFile;
		token.value = "Debug!";
		return token;

	}
	
}
