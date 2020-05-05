import parser.*;
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
        readBlock(root);
        printBlock(root);
    }

    private static void readBlock(IBlock block) {
        BlockReader reader = new BlockReader() {
            @Override
            protected void handleResult(BlockReader.Result result) {
                int i = result.index;
                int l = result.level;
                IElement e = result.element;
                if (e.getType().equals("key") && i == 0) {
                    System.out.println(e.output());
                }
            }
        };
        reader.read(block);
    }

    private static void printBlock(IBlock block) {
        System.out.println("--- structure ---");
        BlockReader reader = new BlockReader() {
            @Override
            protected void handleResult(BlockReader.Result result) {
                int i = result.index;
                int l = result.level;
                IElement e = result.element;
                StringBuilder tab = new StringBuilder();
                while (l > 0) {
                    l--;
                    tab.append("\t");
                }
                if (result.isBlock) {
                    System.out.println(tab.toString() + i + ". [" + e.getType() + "]");
                } else {
                    String output = e.output();
                    if (output.equals(Parser.LineBreak)) output = "\\n";
                    System.out.println(tab.toString() + i + ". [" + e.getType() + "] " + output);
                }
            }
        };
        reader.read(block);
    }
}