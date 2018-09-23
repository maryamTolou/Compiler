
public class Token {
	
	public String value;
	public TokenType tokenType;

	
	public Token(){ }
	
	public Token(String value , TokenType tokenType){
		this.value = value;
		this.tokenType = tokenType;
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[ " + tokenType.toString() + " , " + value + " ]";
	}
	
	public boolean equals(Object obj) {
	    if (obj == null) {
	        return false;
	    }
	    if (!Token.class.isAssignableFrom(obj.getClass())) {
	        return false;
	    }
	    final Token other = (Token) obj;
	    if ((this.value == null) ? (other.value != null) : !this.value.equals(other.value)) {
	        return false;
	    }
	    if (this.tokenType != other.tokenType) {
	        return false;
	    }
	    return true;
	}

	public int hashCode() {
	      return value.hashCode();
	  }
}
