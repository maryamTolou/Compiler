
public enum Terminal {

	
	Terminal1("+"),
	Terminal2("++"),
	Terminal3("-"),
	Terminal4("--"),
	Terminal5("/"),
	Terminal6("("),
	Terminal7(")"),
	Terminal8("=="),
	Terminal9("="),
	Terminal10("*"),
	Terminal11("%"),
	Terminal12("!"),
	Terminal13("&&"),
	Terminal14("||"),
	Terminal15("<"),
	Terminal16(">"),
	Terminal17(";"),
	Terminal18(","),
	Terminal19(">="),
	Terminal20("<="),
	Terminal21("{"),
	Terminal22("}"),
	Terminal23("if"),
	Terminal24("else"),
	Terminal25("while"),
	Terminal26("for"),
	Terminal27("switch"),
	Terminal28("case"),
	Terminal29("int"),
	Terminal30("float"),
	Terminal31("boolean"),
	Terminal32("return"),
	Terminal33("string"),
	Terminal34("id"),
	Terminal35("num"),
	Terminal36("$"),
	Terminal37("main"),
	Terminal38("do"),
	Terminal39("double"),
	Terminal40("char"),
	Terminal41("endif");
	
	private final String desc;
	
	Terminal(String description) {
		desc = description;
	}
	
	public String getDesc(){
		return desc;
	}
}
