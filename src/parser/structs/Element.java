package parser.structs;

public class Element implements IElement {

    private String type = "";
    private String prefix = "";
    private String content = "";
    private String postfix = "";

    public Element(String type, String prefix, String content, String postfix) {
        this.type = type;
        this.prefix = prefix;
        this.content = content;
        this.postfix = postfix;
    }

    public Element(String type, String content) {
        this.type = type;
        this.content = content;
    }

    @Override
    public boolean hasChildren() {
        return false;
    }

    @Override
    public String getType() {
        return type;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getContent() {
        return content;
    }

    public String getPostfix() {
        return postfix;
    }

    @Override
    public String output() {
        return prefix + content + postfix;
    }

    //Element.Text
    public static class Text extends Element {
        public Text(String prefix, String content, String postfix) {
            super("text", prefix, content, postfix);
        }
    }

    //Element.Sign
    public static class Sign extends Element {
        public Sign(String content) {
            super("sign", content);
        }
    }
}
