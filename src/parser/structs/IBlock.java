package parser.structs;

public interface IBlock extends IElement {

    public String getSignStart();

    public String getSignEnd();

    public String getLineType();

    public boolean containable();

    public void onClose() throws Exception;//结束时的处理

    public IBlock getParent();

    public void setParent(IBlock block);

    public void addChild(IElement element);

    public void addChild(IElement element, int index);

    public int getNumChildren();

    public int getIndex(IElement element);

    public IElement getChildAt(int index);

    public IElement removeChildAt(int index);

}
