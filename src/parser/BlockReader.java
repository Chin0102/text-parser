package parser;

import parser.structs.Block;
import parser.structs.IBlock;
import parser.structs.IElement;

public abstract class BlockReader {

    public static class Result {

        final public IElement element;
        final public IBlock parent;
        final public int index;
        final public int level;
        final public boolean isBlock;

        public Result(IElement element, IBlock parent, int index, int level, boolean isBlock) {
            this.element = element;
            this.parent = parent;
            this.index = index;
            this.level = level;
            this.isBlock = isBlock;
        }
    }

    public void read(IBlock block) {
        recurRead(block, 0, 0);
    }

    private void recurRead(IBlock block, int index, int level) {
        IBlock parent = block.getParent();
        handleResult(new Result(block, parent, index, level, true));
        level++;
        int len = block.getNumChildren();
        for (int i = 0; i < len; i++) {
            IElement element = block.getChildAt(i);
            if (element.hasChildren()) recurRead((Block) element, i, level);
            else handleResult(new Result(element, block, i, level, false));
        }
    }

    abstract protected void handleResult(Result result);
}
