import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.text.html.HTMLDocument.Iterator;

public class Actions {
	
	Tables table;
	Token [] variables;
	Token [][] gt;
	Token [][] first;
	Token [][] follow;
	Token [][] predict;
	public static String startValue;
	
	public Actions(String variablesFile , String grammerFile) throws IOException{
		
		table = new Tables(variablesFile , grammerFile);
		gt = table.getGrammerTable();
		variables = table.getVariablesList();
		
		first = new Token[table.getNoVariables()][table.getNoTerminals()];
		follow = new Token[table.getNoVariables()][table.getNoTerminals()];
		for(int i = 0 ; i < table.getNoVariables() ; i++){
			for(int j = 0 ; j < table.getNoTerminals() ; j++){
				first[i][j] = new Token("0" , TokenType.endOfFile);
				follow[i][j] = new Token("0" , TokenType.endOfFile);
			}
		}
		predict = new Token[table.getNoGrammerLine()][table.getNoTerminals()];
		for(int i = 0 ; i < table.getNoGrammerLine() ; i++){
			for(int j = 0 ; j < table.getNoTerminals() ; j++){
				predict[i][j] = new Token("0" , TokenType.endOfFile);
			}
		}
		
		startValue = "E";
		generateFirst();
		generateFollow();
		generatePredict();
//		Token tt[] = findPredict(15);
//		System.out.println(lengthF(tt));
//		for(int i = 0 ; i < lengthF(tt) ; i++)
//			System.out.print(tt[i].toString());
//		
		
	}
	
	private Token[] findFirst(Token token){
		Token temp[];
		Token ft[] = new Token[table.getNoTerminals()];
		for(int i = 0 ; i < table.getNoTerminals() ; i++)
			ft[i] = new Token("0" , TokenType.endOfFile);
		
		if(token.tokenType!=TokenType.VR)
			System.out.println("Error first --> This token is not Vr : " + token.toString());
		else{
			for(int i = 0 ; i < table.getNoGrammerLine() ; i++){
				if(gt[i][0].value.equals(token.value)){
					for(int j = 1 ; j < table.getNoGrammerCol() ; j++){
						if(gt[i][j].tokenType == TokenType.VR){
							temp = findFirst(gt[i][j]);
							concatArrays(ft, temp);
							if(!isNullable(temp))
								break;
						}
						else{
							concatArraysWithToken(ft, gt[i][j]);
							break;
						}
					}
				}
			}
		}
		return ft;
	}
	
	private void generateFirst(){
//		System.out.println("\nGenerate first:");
		for(int i = 0 ; i < table.getNoVariables() ; i++){
			concatArrays(first[i], findFirst(variables[i]));
			int temporary = lengthF(first[i]);
			first[i][temporary].value = first[i][0].value;
			first[i][temporary].tokenType = first[i][0].tokenType;
			first[i][0].value = variables[i].value;
			first[i][0].tokenType = variables[i].tokenType;
			//Print:
//			for(int j = 0 ; j < table.getNoTerminals() ; j++){
//				if(first[i][j].value.equals("0"))
//					break;
//				System.out.print(first[i][j].toString());
//			}
//			System.out.println();
		}
	}

	private Token[] findFollow(Token token){
		
		Token temp[];
		Token ft[] = new Token[table.getNoTerminals()];
		for(int i = 0 ; i < table.getNoTerminals() ; i++)
			ft[i] = new Token("0" , TokenType.endOfFile);
		
		if(token.tokenType!=TokenType.VR)
			System.out.println("Error follow --> This token is not Vr : " + token.toString());
		else{
			for(int i = 0 ; i < table.getNoGrammerLine() ; i++){
				for(int j = 1 ; j < table.getNoGrammerCol() ; j++ ){
					if(gt[i][j].value.equals(token.value)){
						int tempInd = ++j;
						if(tempInd < table.getNoGrammerCol()){
							while(tempInd < table.getNoGrammerCol()){
								if(gt[i][tempInd].tokenType == TokenType.VR){
									temp = getFirst(gt[i][tempInd]);
									concatArrays(ft, temp);
									if(!isNullable(temp))
										break;
									else{
										tempInd++;
									}
								}
								else if(gt[i][tempInd].value.equals("0")){
									if(!gt[i][0].value.equals(token.value)){
										temp = findFollow(gt[i][0]);
										concatArrays(ft , temp);
									}
									break;
								}
								else{
									concatArraysWithToken(ft, gt[i][j]);
									break;
								}
							}
							if(tempInd == table.getNoGrammerCol()){
								if(!gt[i][0].value.equals(token.value)){
									temp = findFollow(gt[i][0]);
									concatArrays(ft , temp);
								}
							}
							break;
						}
						else{
							if(!gt[i][0].value.equals(token.value)){
								temp = findFollow(gt[i][0]);
								concatArrays(ft , temp);
							}
							break;
						}
					}
				}
			}
		}
		
		if(token.value.equals(startValue)){
			int l = lengthF(ft);
			ft[l].value = "$";
			ft[l].tokenType = TokenType.endOfFile;
		}
		return ft;
	}

	private void generateFollow(){
		
//		System.out.println("\nGenerate follow:");
		for(int i = 0 ; i < table.getNoVariables() ; i++){
			
			concatArrays(follow[i], findFollow(variables[i]));
			
			int temporary = lengthF(follow[i]);
			follow[i][temporary].value = follow[i][0].value;
			follow[i][temporary].tokenType = follow[i][0].tokenType;
			follow[i][0].value = variables[i].value;
			follow[i][0].tokenType = variables[i].tokenType;
			
			int temp = 1;
			while(!follow[i][temp].value.equals("0")){
				if(follow[i][temp].tokenType == TokenType.VR){
					follow[i][temp].value = follow[i][lengthF(follow[i]) - 1].value;
					follow[i][temp].tokenType = follow[i][lengthF(follow[i]) - 1].tokenType;
					follow[i][lengthF(follow[i]) - 1].value = "0";
					follow[i][lengthF(follow[i]) - 1].tokenType = TokenType.endOfFile;
					continue;
				}
				temp++;
			}
			//Print:
			for(int j = 0 ; j < table.getNoTerminals() ; j++){
				if(follow[i][j].value.equals("0"))
					break;
				System.out.print(follow[i][j].toString());
			}
			System.out.println();
		}
	}
	
	private Token[] findPredict(int i){
		Token temp[];
		Token pt[] = new Token[table.getNoTerminals()];
		for(int j = 0 ; j < table.getNoTerminals() ; j++)
			pt[j] = new Token("0" , TokenType.endOfFile);
		
		
		int j = 1;
		for( ; j < table.getNoGrammerCol() ; j++){
			if(gt[i][j].tokenType == TokenType.VR){
				temp = getFirst(gt[i][j]);
				concatArrays(pt, temp);
				if(!isNullable(temp))
					break;
			}
			else if(gt[i][j].value.equals("0")){
				concatArrays(pt, getFollow(gt[i][0]));
				break;
			}
			else{
				concatArraysWithToken(pt, gt[i][j]);
				break;
			}
		}
		if(j == 1 && gt[i][j].tokenType == TokenType.endOfFile && gt[i][j].value.equals("null"))
			concatArrays(pt, getFollow(gt[i][0]));
		if( j == table.getNoGrammerCol())
			concatArrays(pt, getFollow(gt[i][0]));
		
		
		return pt;
	}

	private void generatePredict(){
//		System.out.println("\nGenerate predict:");
		for(int i = 0 ; i < table.getNoGrammerLine() ; i++){
			concatArrays(predict[i], findPredict(i));
			
			int temporary = lengthF(predict[i]);
			predict[i][temporary].value = predict[i][0].value;
			predict[i][temporary].tokenType = predict[i][0].tokenType;
			predict[i][0].value = Integer.toString(i+1);
			predict[i][0].tokenType =  TokenType.num;
			int temp = 1;
			while(!predict[i][temp].value.equals("0")){
				if(predict[i][temp].tokenType == TokenType.VR){
					temporary = lengthF(predict[i]);
					predict[i][temp].value = predict[i][temporary - 1].value;
					predict[i][temp].tokenType = predict[i][temporary - 1].tokenType;
					predict[i][temporary - 1].value = predict[i][temporary].value;
					predict[i][temporary - 1].tokenType = predict[i][temporary].tokenType;
					continue;
				}
				temp++;
			}
			//Print:
//			for(int j = 0 ; j < table.getNoTerminals() ; j++){
//				if(predict[i][j].value.equals("0"))
//					break;
//				System.out.print(predict[i][j].toString());
//			}
//			System.out.println();
		}
	}
	
	private Token[] concatArrays(Token[] tokens1 , Token[] tokens2){
		int length2 = 0;
		for(int i = 0 ; i < tokens2.length ; i++){
			if(tokens2[i].value.equals("0") && tokens2[i].tokenType == TokenType.endOfFile)
				break;
			else
				length2++;
		}


		int tempo = 0;
		
		for(int i = 0 ; i < tokens1.length ; i++){
			if(tokens1[i].value.equals("0") && tempo < length2){
				tokens1[i].value = tokens2[tempo].value;;
				tokens1[i].tokenType = tokens2[tempo].tokenType;
				deleteDup(tokens1);
				i--;
				tempo++;
			}
		}
		
		return tokens1;
	}
	
	private boolean isNullable(Token []tokens){
		for(int i = 0 ; i < tokens.length ; i++){
			if(tokens[i].value.equals("null"))
				return true;
			if(tokens[i].value.equals("0"))
				return false;
		}
		return false;
	}
	
	private int lengthF(Token[] tokens){
		int tempo = 0;
		for(int i = 0 ; i < tokens.length ; i++){
			if(!tokens[i].equals(new Token("0" , TokenType.endOfFile)))
				tempo++;
		}
		return tempo;
	}
	
	/*private Token[] concatArrays(Token[] tokens1 , Token[] tokens2){
		int end1 = tokens1.length;
		int end2 = tokens2.length;
	
		Set<Token> set1 = new HashSet<Token>();

		for(int i = 0; i < end1; i++){
			if(!tokens1[i].equals(new Token("0" , TokenType.endOfFile))){
				set1.add(tokens1[i]);
			}
		}
		for(int i = 0; i < end2; i++){
			if(!tokens2[i].equals(new Token("0" , TokenType.endOfFile))){
				set1.add(tokens2[i]);
			}
		}
		
		for(int i = 0 ; i < table.getNoTerminals() ; i++){
			tokens1[i].value = "0";
			tokens1[i].tokenType = TokenType.endOfFile;
		}
		
		 int i = 0;
		for (Token token : set1) {
	        tokens1[i] = token;
	        i++;
	     }
		return tokens1;
	}*/

	private void deleteDup(Token[] tokens){

		int tempo = lengthF(tokens);
		
		for(int i = 0 ; i < tempo ; i++){
			for(int j = i+1 ; j < tempo ; j++){
//				if(tokens[i].value.equals(tokens[j].value) && tokens[i].tokenType == tokens[j].tokenType){
				if(tokens[i].equals(tokens[j])){
					tempo--;
					tokens[j].value = tokens[tempo].value;
					tokens[j].tokenType = tokens[tempo].tokenType;
					tokens[tempo].value = tokens[tempo+1].value;
					tokens[tempo].tokenType = tokens[tempo+1].tokenType;
					j--;
				}
				if(tokens[i].value.equals("0"))
					break;
			}
		}
	}
	
	/*private void deleteDup(Token[] tokens){
		int end = tokens.length;
		Set<Token> set = new HashSet<Token>();

		for(int i = 0; i < end; i++){
		  set.add(tokens[i]);
		}
		
		for(int i = 0 ; i < table.getNoTerminals() ; i++){
			tokens[i].value = "0";
			tokens[i].tokenType = TokenType.endOfFile;
		}
		int i = 0;
		for (Token token : set) {
	        tokens[i].value = token.value;
	        tokens[i].tokenType = token.tokenType;
	        i++;
	     }
		
	}*/
	
	private void concatArraysWithToken(Token[] tokens1 , Token tokens2 ){

		for(int i = 0 ; i < tokens1.length ; i++){
			if(tokens1[i].value.equals("0") ){
				tokens1[i].value = tokens2.value;
				tokens1[i].tokenType = tokens2.tokenType;
				deleteDup(tokens1);
				break;
			}
		}
	}
	
	private Token[] getFirst(Token token){
		for(int i = 0 ; i < table.getNoVariables() ; i++){
			if(first[i][0].value.equals(token.value)){
				return first[i];
			}
		}
		return null;
	}
	
	private Token[] getFollow(Token token){
		for(int i = 0 ; i < table.getNoVariables() ; i++){
			if(follow[i][0].value.equals(token.value)){
				return follow[i];
			}
		}
		return null;
	}
	
	public Token[][] getFirst(){
		return first;
	}
	
	
	public Token[][] getFollow(){
		return follow;
	}

	public Token[][] getPredict(){
		return predict;
	}
}
