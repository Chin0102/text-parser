package parser;

import parser.structs.Element;
import parser.structs.IBlock;

public class Parser {

    public static String LineBreak = "\n";
    public static String PartBreak = "\r";

    private static String getLineBreak(String content) {
        if (content.contains("\r\n")) return "\r\n";
        if (content.contains(PartBreak)) return PartBreak;
        return LineBreak;
    }

    private ParseSetting setting;

    public Parser(ParseSetting setting) {
        this.setting = setting;
    }

    private void flushText(String text, IBlock block) {
        if (text.equals("")) return;
        String content = text.replaceAll("(^\\s*)|(\\s*$)", "");
        int i = text.indexOf(content);
        String prefix = text.substring(0, i);
        String postfix = text.substring(i + content.length());
        String type = content.equals("") ? "text" : block.getLineType();
        block.addChild(setting.createElement(type, prefix, content, postfix));
    }

    public IBlock parse(String str) throws Exception {
        //预处理-统一换行符
        String lineBreak = getLineBreak(str);
        if (!lineBreak.equals(LineBreak)) str = str.replace(lineBreak, LineBreak);
        //预处理-内容分段
        String[] parts = setting.contentSection(str);
        //结构体root
        IBlock root = setting.createRoot();
        root.setParent(root);
        //临时存储对象
        IBlock block = root;
        StringBuilder text = new StringBuilder();
        //
        int len = parts.length;
        for (int i = 0; i < len; i++) {
            String part = parts[i];
            //不处理空字符串
            if (part.equals("")) continue;
            //判断当前block结束
            if (block.getSignEnd().equals(part)) {
                //System.out.println("block end:" + (part.equals(LineBreak) ? "\\n" : part));
                flushText(text.toString(), block);
                text = new StringBuilder();
                block.addChild(new Element.Sign(part));
                block.onClose();
                block = block.getParent();
                continue;
            }
            //判断子级block开始
            if (block.containable() && setting.containsSignStart(part)) {
                //System.out.println("block begin:" + part);
                flushText(text.toString(), block);
                text = new StringBuilder();
                IBlock subBlock = setting.createBlock(part);
                subBlock.addChild(new Element.Sign(part));
                subBlock.setParent(block);
                block.addChild(subBlock);
                block = subBlock;
                continue;
            }
            //判断语义结束(断句)
            if (setting.isLineBreak(part)) {
                flushText(text.toString(), block);
                text = new StringBuilder();
                block.addChild(new Element.Sign(part));
                continue;
            }
            text.append(part);
        }
        //last text end
        flushText(text.toString(), block);
        return root;
    }
}
