
public enum KeyWord {

	
	keyWord1("if"),
	keyWord2("else"),
	keyWord3("while"),
	keyWord4("for"),
	keyWord5("switch"),
	keyWord6("case"),
	keyWord7("int"),
	keyWord8("float"),
	keyWord9("boolean"),
	keyWord10("return"),
	KeyWord11("main"),
	KeyWord12("do"),
	KeyWord13("double"),
	KeyWord14("char"),
	KeyWord15("endif");
	
	private final String desc;
	
	KeyWord(String description) {
		desc = description;
	}
	
	public String getDesc(){
		return desc;
	}
	
	
}
