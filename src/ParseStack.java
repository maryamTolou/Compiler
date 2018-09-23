
public class ParseStack {
	private int maxSize;
    private Token[] stackArray;
    private int top;
    
    public ParseStack(int max) {
       maxSize = max;
       stackArray = new Token[maxSize];
       top = -1;
    }
    public void push(Token j) {
       stackArray[++top] = j;
    }
    public Token pop() {
       return stackArray[top--];
    }
    public Token peek() {
       return stackArray[top];
    }
    public boolean isEmpty() {
       return (top == -1);
    }
}

