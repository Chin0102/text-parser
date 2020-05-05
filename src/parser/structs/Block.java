package parser.structs;

import java.util.ArrayList;

public class Block implements IBlock {

    private String type;
    private String lineType;
    private boolean containable;

    private String signStart = "";
    private String signEnd = "";

    private IBlock parent;

    final private ArrayList<IElement> children = new ArrayList<IElement>();

    public Block(String type, boolean containable, String lineType) {
        this.type = type;
        this.containable = containable;
        this.lineType = lineType;
    }

    public Block(String type, String signStart, String signEnd, boolean containable, String lineType) {
        this(type, containable, lineType);
        this.signStart = signStart;
        this.signEnd = signEnd;
    }

    public Block(String type, String signStart, String signEnd, boolean containable) {
        this(type, signStart, signEnd, containable, "text");
    }

    @Override
    public boolean hasChildren() {
        return true;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getSignStart() {
        return signStart;
    }

    @Override
    public String getSignEnd() {
        return signEnd;
    }

    @Override
    public String getLineType() {
        return lineType;
    }

    @Override
    public boolean containable() {
        return containable;
    }

    @Override
    public void onClose() throws Exception {
    }

    @Override
    public IBlock getParent() {
        return parent;
    }

    @Override
    public void setParent(IBlock parent) {
        this.parent = parent;
    }

    @Override
    public void addChild(IElement element) {
        children.add(element);
    }

    @Override
    public void addChild(IElement element, int index) {
        children.add(index, element);
    }

    @Override
    public int getIndex(IElement element) {
        return children.indexOf(element);
    }

    @Override
    public int getNumChildren() {
        return children.size();
    }

    @Override
    public IElement getChildAt(int index) {
        return children.get(index);
    }

    @Override
    public IElement removeChildAt(int index) {
        return children.remove(index);
    }

    @Override
    public String output() {
        StringBuilder str = new StringBuilder();
        for (IElement ele : children) str.append(ele.output());
        return str.toString();
    }

}
