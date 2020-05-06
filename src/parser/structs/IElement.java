package parser.structs;

public interface IElement {
    public String getType();

    public boolean hasChildren();

    public String getContent();

    public String output();
}
