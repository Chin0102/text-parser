package parser.elements;

import parser.structs.Block;
import parser.structs.Element;

public class KeyValue extends Block {
    private Element key;
    private Element value;

    public KeyValue(String prefix, String content, String postfix) {
        super("key-value", false, "text");
        String keyContent = content.split("\\s+")[0];
        if (keyContent.equals(content)) {
            key = new Element("key", prefix, keyContent, "");
            addChild(key);
        } else {
            String valueContent = content.substring(keyContent.length());
            String keyPostfix = valueContent.split("\\S+")[0];
            valueContent = valueContent.substring(keyPostfix.length());
            key = new Element("key", prefix, keyContent, keyPostfix);
            value = new Element("value", "", valueContent, postfix);
            addChild(key);
            addChild(value);
        }
    }

    public Element getKey() {
        return key;
    }

    public Element getValue() {
        return value;
    }


}
