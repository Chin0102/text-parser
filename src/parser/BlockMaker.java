package parser;

import parser.structs.IBlock;

public abstract class BlockMaker {

    private String signStart;
    private String signEnd;

    public BlockMaker() {
    }

    public BlockMaker(String signStart, String signEnd) {
        this.signStart = signStart;
        this.signEnd = signEnd;
    }

    public String getSignStart() {
        return signStart;
    }

    public String getSignEnd() {
        return signEnd;
    }

    public abstract IBlock create();
}
