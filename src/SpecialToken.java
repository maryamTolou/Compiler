
public enum SpecialToken {

	SpecialToken1("+"),
	SpecialToken2("++"),
	SpecialToken3("-"),
	SpecialToken4("--"),
	SpecialToken5("/"),
	SpecialToken6("("),
	SpecialToken7(")"),
	SpecialToken8("=="),
	SpecialToken9("="),
	SpecialToken10("*"),
	SpecialToken11("%"),
	SpecialToken12("!"),
	SpecialToken13("&&"),
	SpecialToken14("||"),
	SpecialToken15("<"),
	SpecialToken16(">"),
	SpecialToken17(";"),
	SpecialToken18(","),
	SpecialToken19(">="),
	SpecialToken20("<="),
	SpecialToken21("{"),
	SpecialToken22("}");
	
	
	private final String desc;
	
	SpecialToken(String description) {
		desc = description;
	}
	
	public String getDesc(){
		return desc;
	}
	
}
