import java.io.IOException;

public class Parser {

	MyScanner scanner;
	ParseStack ps;
	Token token;
//	Token mainToken;
	
	boolean exit = true;
	int prod = 0;
	
	ParseTable pt;
	int parseTable[][];
	Tables table;
	Token[][] rhst;
	
	
	public Parser(String sourceCode , String variablesFile , String grammerFile) throws IOException {
		scanner = new MyScanner(sourceCode);
		ps = new ParseStack(30);
		token = scanner.getToken();
		
		pt = new ParseTable(variablesFile , grammerFile);
		parseTable = pt.getParseTable();
		table = new Tables(variablesFile , grammerFile);
		rhst = table.getRhstTable();
		
		ps.push(new Token("$" , TokenType.endOfFile));
		ps.push(new Token(Actions.startValue , TokenType.VR));
		
		System.out.println("Compile begins...\n\n");
		parser();
		
	}
	
	
	private int parser(){
		
		while(exit){
			
			switch(ps.peek().tokenType){
				
			case VR:				
				Token tok = ps.pop();
				
				prod = parseTable[pt.getVariableIndex(tok)][pt.getTerminalIndex(token)];
				if(prod == -1){
					System.out.println("Syntax Error on token : " + token.toString() + "!\nLine: "   + MyScanner.currentLine );
					System.out.print("compiler need: " + tok.toString());
//					while(!ps.isEmpty()){
//						System.out.print(ps.pop().toString());
//					}
					return 0;
				}
				pushRhst(prod);
				break;
				
			default:
				Token temp = ps.pop();
				
				if(temp.value.equals("$")){
					if(token.equals(temp))
						System.out.println("Congrats!");
					else{
						System.out.println("Syntax Error on token : " + token.toString() + "!\nLine: "   + MyScanner.currentLine );
						System.out.print("compiler need: " + temp.toString());
						//System.out.println("Compiler can't find $!");
					}
					return 0;
				}
				else{
					if(temp.tokenType == token.tokenType){
						if(temp.tokenType == TokenType.specialToken){
							if(temp.value.equals(token.value)){
								token = scanner.getToken();
								break;
							}
							else{
								System.out.println("Error in line : " + MyScanner.currentLine +  "\nToken doesn't match!\nCompiler need:");
								System.out.println(temp.toString());
								System.out.println("Your token is : " + token.toString());
								
								return 0;
							}
						}
						else{
							token = scanner.getToken();
							break;
						}
					}
					else{
						System.out.println("Error in line : " + MyScanner.currentLine +  "\nToken doesn't match!\nCompiler need:");
						System.out.println(temp.toString());
						System.out.println("Your token is : " + token.toString());
						
						return 0;
					}
				}
			
			}
		}
		return 1;
	}
	
	
	private void pushRhst(int k){
		Token []token;
		token = rhst[k];
		
		if(!token[1].value.equals("null")){
			int length = 0;
			for(int i = 0 ; i < token.length ; i++){
				if(token[i].value.equals("0"))
					break;
				length++;
			}
			for(int i = 1 ; i < length ; i++){
				ps.push(token[i]);
			}
		}
	}









}
