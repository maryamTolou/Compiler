import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Compiler {

	public static void main(String[] args) throws IOException {	

		//DEBUG TABLES
		/*Tables table = new Tables("mainVariables.txt" , "mainGrammer.txt");
		Token token[][];
		token = table.getGrammerTable();
		System.out.println("Grammer table:");
		for(int i = 0 ; i < table.getNoGrammerLine() ; i++){
			for(int j = 0 ; j < table.getNoGrammerCol() ; j++){
				System.out.print(token[i][j].toString());
			}
			System.out.println();
		}
		token = table.getRhstTable();
		System.out.println("\nRHST table:" );
		for(int i = 0 ; i < table.getNoGrammerLine() ; i++){
			for(int j = 0 ; j < table.getNoGrammerCol() ; j++){
				System.out.print(token[i][j].toString());
			}
			System.out.println();
		}*/
		
		/*Token token1 = new Token("0" , TokenType.endOfFile);
		Token token2 = new Token("0" , TokenType.endOfFile);
		Set<Token> set1 = new HashSet<Token>();
		set1.add(token2);
		set1.add(token1);
		for(Token token : set1){
			System.out.println(token.toString());
		}*/
		//DEBUG ACTIONS
//		Actions action = new Actions("mainVariables.txt" , "mainGrammer.txt");
//		Actions action = new Actions("Variables3.txt" , "Grammer3.txt");
		
		//DEBUG PARSETABLE
		//ParseTable pt = new ParseTable("mainVariables.txt" , "mainGrammer.txt");
		
		//DEBUG SCANNER
				
		/*MyScanner scanner = new MyScanner("Scanner.txt");
		Token token = scanner.getToken();
		while(true){
		System.out.println(token.toString());
		token = scanner.getToken();
							
		}*/
		
		
		
		Parser p = new Parser("mainScanner.txt", "mainVariables.txt", "mainGrammer.txt");
		
	
		
	}

}



