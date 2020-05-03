package parser;

import parser.structs.IElement;

public abstract class ElementMaker {

    private String sign;

    public ElementMaker(String sign) {
        this.sign = sign;
    }

    public String getSign() {
        return sign;
    }

    public abstract IElement create(String type, String prefix, String content, String postfix);
}
