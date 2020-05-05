package parser;

import parser.structs.Block;
import parser.structs.Element;
import parser.structs.IBlock;
import parser.structs.IElement;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ParseSetting {

    private Set<String> signs = new HashSet<String>();
    private Set<String> lineBreaks = new HashSet<String>();
    private BlockMaker rootMaker;
    private HashMap<String, BlockMaker> blockMakerMap = new HashMap<String, BlockMaker>();
    private HashMap<String, ElementMaker> elementMakerMap = new HashMap<String, ElementMaker>();

    public ParseSetting() {
        addLineBreak(Parser.LineBreak);
        addElementMaker(new ElementMaker("text") {
            @Override
            public IElement create(String type, String prefix, String content, String postfix) {
                return new Element.Text(prefix, content, postfix);
            }
        });
    }

    //for parser--------------------------------------
    public IBlock createRoot() {
        if (rootMaker != null) return rootMaker.create();
        return new Block("root", true, "text");
    }

    public IBlock createBlock(String sign) {
        if (!containsSignStart(sign)) return null;
        return blockMakerMap.get(sign).create();
    }

    public IElement createElement(String sign, String prefix, String content, String postfix) {
        if (!elementMakerMap.containsKey(sign)) return new Element.Text(prefix, content, postfix);
        ElementMaker maker = elementMakerMap.get(sign);
        return maker.create(sign, prefix, content, postfix);
    }

    public boolean containsSignStart(String signStart) {
        return blockMakerMap.containsKey(signStart);
    }

    public String[] contentSection(String content) {
        for (String sign : signs)
            content = content.replace(sign, Parser.PartBreak + sign + Parser.PartBreak);
        return content.split(Parser.PartBreak);
    }

    public boolean isLineBreak(String part) {
        return lineBreaks.contains(part);
    }

    //for setting--------------------------------------
    public void setRootMaker(BlockMaker maker) {
        this.rootMaker = maker;
    }

    public void addBlockMaker(BlockMaker maker) {
        blockMakerMap.put(maker.getSignStart(), maker);
        signs.add(maker.getSignStart());
        signs.add(maker.getSignEnd());
    }

    public void addElementMaker(ElementMaker maker) {
        elementMakerMap.put(maker.getSign(), maker);
    }

    public void addLineBreak(String linebreak) {
        signs.add(linebreak);
        lineBreaks.add(linebreak);
    }
}
