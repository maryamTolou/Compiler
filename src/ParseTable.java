import java.io.IOException;

public class ParseTable {

	Tables table;
	Actions action;
	int [][] pt;
	Token [][] predict;
	Token [][] grammer;
	Terminal []terminals;
	Token []variables;
	
	
	public ParseTable(String variablesFile , String grammerFile) throws IOException {
		
		table = new Tables(variablesFile , grammerFile);
		action = new Actions(variablesFile , grammerFile);
		pt = new int[table.getNoVariables()][table.getNoTerminals()];
		predict = action.getPredict();
		grammer = table.getGrammerTable();
		terminals = Terminal.values();
		variables = table.getVariablesList();
		
		makeParseTable();
	}
	
	
	private void makeParseTable(){
		for(int i = 0 ; i < table.getNoVariables() ; i++){
			for(int j = 0 ; j < table.getNoTerminals() ; j++){
				pt[i][j] = -1;
			}
		}
		int iIndex = 0 , yIndex =0;
		for(int i = 0 ; i < table.getNoGrammerLine() ; i++){
			int temp = 1;

			while(temp < predict[i].length){
				if(predict[i][temp].value.equals("null") && predict[i][temp].tokenType == TokenType.endOfFile){
					temp++;
					continue;
				}
				else if(predict[i][temp].value.equals("0") && predict[i][temp].tokenType == TokenType.endOfFile){
					break;
				}
				else{
					iIndex = getVariableIndex(grammer[i][0]);
					yIndex = getTerminalIndex(predict[i][temp]);
					pt[iIndex][yIndex] = i;
					temp++;
				}
				
			}
		}
		
		
		//Print:
		System.out.print("\t");
		for(int i = 0 ; i < table.getNoTerminals() ; i++){
			System.out.print(terminals[i].getDesc() + "\t");
		}
		System.out.println();
		for(int i = 0 ; i < table.getNoVariables() ; i++){
			for(int j = 0 ; j < table.getNoTerminals() ; j++){
				if(j == 0 ){
					System.out.print(variables[i].value+ "\t" );
				}
				System.out.print(pt[i][j] + "\t");
			}
			System.out.println();
		}

	}
	
	public int getTerminalIndex(Token token){
		
		if(token.tokenType == TokenType.keyWord || token.tokenType == TokenType.specialToken){
			for(int i = 0 ; i < table.getNoTerminals() ; i++){
				if(token.value.equals(terminals[i].getDesc())){
					return i;
				}
			}
		}
		else if(token.tokenType == TokenType.id){
			for(int i = 0 ; i < table.getNoTerminals() ; i++){
				if(terminals[i].getDesc().equals("id")){
					return i;
				}
			}
		}
		else if(token.tokenType == TokenType.num){
			for(int i = 0 ; i < table.getNoTerminals() ; i++){
				if(terminals[i].getDesc().equals("num")){
					return i;
				}
			}
		}
		else if(token.tokenType == TokenType.string){
			for(int i = 0 ; i < table.getNoTerminals() ; i++){
				if(terminals[i].getDesc().equals("string")){
					return i;
				}
			}
		}
		else if(token.tokenType == TokenType.endOfFile && token.value.equals("$")){
			for(int i = 0 ; i < table.getNoTerminals() ; i++){
				if(terminals[i].getDesc().equals("$")){
					return i;
				}
			}
		}
		
		System.out.println("Thre isnt such terminal!");
		System.out.println(token.toString());
		return -1;
	}

	public int getVariableIndex(Token token){
		for(int i = 0 ; i < table.getNoVariables() ;i++){
			if(token.value.equals(variables[i].value)){
				return i;
			}
		}
		System.out.println("Thre isnt such variable!");
		System.out.println(token.toString());
		return -1;
	}
	
	public int[][] getParseTable(){
		return pt;
	}
}
