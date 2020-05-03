package parser.elements;

import parser.structs.Block;
import parser.structs.IBlock;
import parser.structs.IElement;

public class BraceBlock extends Block {
    public BraceBlock() {
        super("block", "{", "}", true, "key-value");
    }

    @Override
    public void onClose() throws Exception {
        IBlock parent = getParent();
        int index = parent.getIndex(this);
        while (true) {
            index--;
            if (index < 0) throw new Exception("parse failed: not found block header");
            IElement element = parent.removeChildAt(index);
            addChild(element, 0);
            if (element.getType().equals(getLineType())) break;
        }
    }
}
