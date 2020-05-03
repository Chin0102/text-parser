import parser.BlockMaker;
import parser.ElementMaker;
import parser.ParseSetting;
import parser.Parser;
import parser.elements.BraceBlock;
import parser.elements.KeyValue;
import parser.structs.Block;
import parser.structs.IBlock;
import parser.structs.IElement;

public class main {

    public static void main(String[] args) throws Exception {
        //Demo LB ParseSetting
        ParseSetting setting = new ParseSetting();
        setting.setRootMaker(new BlockMaker() {
            @Override
            public IBlock create() {
                return new Block("root", true, "key-value");
            }
        });
        setting.addBlockMaker(new BlockMaker("#", Parser.LineBreak) {
            @Override
            public IBlock create() {
                return new Block("inline-comment", "#", Parser.LineBreak, false);
            }
        });
        setting.addBlockMaker(new BlockMaker("{", "}") {
            @Override
            public IBlock create() {
                return new BraceBlock();
            }
        });
        setting.addElementMaker(new ElementMaker("key-value") {
            @Override
            public IElement create(String type, String prefix, String content, String postfix) {
                return new KeyValue(prefix, content, postfix);
            }
        });
        setting.addLineBreak(";");
        //test
        String str = "#test\n abc {\naaa bbb;\n ccc{\nddd\t eee;}}\n";
        Parser parser = new Parser(setting);
        IBlock root = parser.parse(str);
        printBlock(root, "", 0);
    }

    private static void printBlock(IBlock block, String prefix, int index) throws Exception {
        System.out.println(prefix + index + ". [" + block.getType() + "]");
        int len = block.getNumChildren();
        for (int i = 0; i < len; i++) {
            IElement element = block.getChildAt(i);
            if (element.hasChildren()) {
                IBlock subBlock = (Block) element;
                printBlock(subBlock, prefix + "\t", i);
            } else {
                String output = element.output();
                if (output.equals(Parser.LineBreak)) output = "\\n";
                System.out.println(prefix + "\t" + i + ". [" + element.getType() + "] " + output);
            }
        }
    }
}