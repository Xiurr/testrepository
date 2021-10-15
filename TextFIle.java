package org.example;

dsaaaaaaaaaaaaaaaaaa
import com.ibm.icu.text.Bidi;
import com.ibm.icu.text.BidiRun;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.util.PDFTextStripper;
import org.odftoolkit.odfdom.doc.OdfDocument;
import org.odftoolkit.odfdom.doc.OdfTextDocument;

import javax.print.*;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.rtf.RTFEditorKit;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TextFIle extends JFrame
{
    public JFileChooser filechooser = new JFileChooser();
    public FileDialog openDia;
    public static StyledDocument doc = new DefaultStyledDocument();
    public static JTextPane workArea = new JTextPane(doc);
    public static JTextPane testArea = new JTextPane();
    public JLabel jLabelDate = new JLabel("Date");
    public JLabel jLabelTime = new JLabel("Time");
    public JPanel jPanelNorth = new JPanel();
    public UndoManager um;
    public PDDocument initfile;
    public String readfile;

    private Object OdfTextDocument;

    //主函数,设置页面的实现
    public static void main(String[] args)
    {
        setUIFont();
        TextFIle frame = new TextFIle();
        frame.setVisible(true);
    }
//设置文本编辑器菜单栏的全局字体
    public static void setUIFont()
    {
        Font f = new Font("楷体",Font.PLAIN,20);
        String[] names ={ "Title","Label", "CheckBox", "PopupMenu","MenuItem", "CheckBoxMenuItem",
                "JRadioButtonMenuItem","ComboBox", "Button", "Tree", "ScrollPane",
                "TabbedPane", "EditorPane", "TitledBorder", "Menu", "TextArea",
                "OptionPane", "MenuBar", "ToolBar", "ToggleButton", "ToolTip",
                "ProgressBar", "TableHeader", "Panel", "List", "ColorChooser",
                "PasswordField","TextField", "Table", "Label", "Viewport",
                "RadioButtonMenuItem","RadioButton", "DesktopPane", "InternalFrame", "JTextPane"
        };
        for (String item : names) {
            UIManager.put(item+ ".font",f);
        }
    }
//主要的TextFIle,设计文本编辑器的窗口内容
    public TextFIle()
    {
        setTitle("Text Editor");
        setBounds(300,300,700,700);
        setJMenuBar(createJMenuBar());
        JScrollPane imgScrollPane = new JScrollPane(workArea);
        GridLayout gridLayout = new GridLayout(1, 2);
        jPanelNorth.setLayout(gridLayout);
        jPanelNorth.add(jLabelDate);
        jPanelNorth.add(jLabelTime);

        openDia = new FileDialog(this,"Open(O)",FileDialog.LOAD);

        um = new UndoManager();
        workArea.getDocument().addUndoableEditListener(um);
        add(jPanelNorth, BorderLayout.NORTH);
        add(imgScrollPane,BorderLayout.CENTER);
        time().start();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public JMenuBar createJMenuBar()
    {
        JMenuBar menubar=new JMenuBar();

        JMenu menuFile=new JMenu("File");
        JMenu menuEdit=new JMenu("Edit");
        JMenu menuAbout=new JMenu("Help");

        menuFile.add(newopen());
        menuFile.add(open());
        menuFile.add(openJAVA());
        menuFile.add(openPYTHON());
        menuFile.add(openCPP());
        menuFile.add(openRTF());
        menuFile.add(openPDF());
        menuFile.addSeparator();
        menuFile.add(save());
        menuFile.add(savePDF());
        menuFile.add(saveODT());
        menuFile.add(print());
        menuFile.add(exit());

        menuEdit.add(revocation());
        menuEdit.addSeparator();
        menuEdit.add(cut());
        menuEdit.add(copy());
        menuEdit.add(paste());
        menuEdit.add(search());


        menuAbout.add(help());

        menubar.add(menuFile);
        menubar.add(menuEdit);
        menubar.add(menuAbout);

        return menubar;
    }

    private Timer time()
    {
        return new Timer(1000, e -> {
            long timemillis = System.currentTimeMillis();
            SimpleDateFormat date = new SimpleDateFormat("yyyy / MM / dd ");
            jLabelDate.setText("   Date:  " + date.format(new Date(timemillis)));
            SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss ");
            jLabelTime.setText("   Time:  " + time.format(new Date(timemillis)));
        });
    }

    public JMenuItem newopen()
    {
        JMenuItem newopen = new JMenuItem("New(N)", KeyEvent.VK_N);
        newopen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
        newopen.addActionListener(e -> workArea.setDocument(new DefaultStyledDocument()));
        return newopen;

    }

    public JMenuItem open()
    {
        JMenuItem open = new JMenuItem("Open(O)",KeyEvent.VK_O);
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        open.addActionListener(arg0 -> {
            openDia.setVisible(true);
            String dirPath = openDia.getDirectory();
            String fileName = openDia.getFile();
            if (dirPath == null || fileName == null) {
                return;
            }
            workArea.setText("");
            File fileO = new File(dirPath, fileName);
            readfile = readFromFile(fileO);
            workArea.setText(readfile);
        });
        return open;
    }

    private JMenuItem openPYTHON()
    {
        JMenuItem openPYTHON = new JMenuItem("OpenPYTHON(Y)",KeyEvent.VK_Y);
        openPYTHON.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK));
        openPYTHON.addActionListener(arg0 -> {
            openDia.setVisible(true);
            String dirPath = openDia.getDirectory();
            String fileName = openDia.getFile();
            if (dirPath == null || fileName == null) {
                return;
            }
            workArea.setText("");
            File fileO = new File(dirPath, fileName);
            String finalfile = readFromFile(fileO);

            String finalfile1 = finalfile.replace("\n", "");
            char[] arr = finalfile1.toCharArray();
            String[] PkeywordsRED = {"from","for","finally","except","else","elif","del","def","continue","class","break","and","as","assert"};
            String[] PkeywordsORANGE = {"with","while","try","raise","return","print","pass","or","not","lambda","global","if","import","is","in"};
            String[] PkeywordsPURPLE = {"format","list","float","type","range","len","bool","tuple","file","input","open","all","int","str","sum","super","print"};
            workArea.setText(finalfile);
            testArea.setText(finalfile1);
            SimpleAttributeSet set = new SimpleAttributeSet();
            StyleConstants.setForeground(set, new Color(226, 87, 78));

            SimpleAttributeSet set1 = new SimpleAttributeSet();
            StyleConstants.setForeground(set1, new Color(191, 131, 30));

            SimpleAttributeSet set2 = new SimpleAttributeSet();
            StyleConstants.setForeground(set2, new Color(177, 80, 176));



            for (String item:PkeywordsRED) {
                int k;
                int l;
                testArea.setCaretPosition(finalfile1.length());

                while (true) {
                    k = finalfile1.lastIndexOf(item, testArea.getCaretPosition() - item.length() - 1);
                    l=k+item.length();
                    if (k > -1 ) {
                        testArea.setCaretPosition(k);
                        if(Word(arr,l-1,item)){
                            doc.setCharacterAttributes(k, item.length(), set, true);}
                        else{
                            continue;
                        }
                    }
                    if (k==-1){
                        break;
                    }
                }
            }
            for (String item:PkeywordsORANGE) {
                int k;
                int l;
                testArea.setCaretPosition(finalfile1.length());

                while (true) {
                    k = finalfile1.lastIndexOf(item, testArea.getCaretPosition() - item.length() - 1);
                    l=k+item.length();
                    if (k > -1 ) {
                        testArea.setCaretPosition(k);
                        if(Word(arr,l-1,item)){
                            doc.setCharacterAttributes(k, item.length(), set1, true);}
                        else{
                            continue;
                        }
                    }
                    if (k==-1){
                        break;
                    }
                }
            }
            for (String item:PkeywordsPURPLE) {
                int k;
                int l;
                testArea.setCaretPosition(finalfile1.length());

                while (true) {
                    k = finalfile1.lastIndexOf(item, testArea.getCaretPosition() - item.length() - 1);
                    l=k+item.length();
                    if (k > -1 ) {
                        testArea.setCaretPosition(k);
                        if(Word(arr,l-1,item)){
                            doc.setCharacterAttributes(k, item.length(), set2, true);}
                        else{
                            continue;
                        }
                    }
                    if (k==-1){
                        break;
                    }
                }
            }
        });
        return openPYTHON;
    }


    private JMenuItem openJAVA()
    {
        JMenuItem openJAVA = new JMenuItem("OpenJAVA(J)",KeyEvent.VK_J);
        openJAVA.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J, InputEvent.CTRL_MASK));
        openJAVA.addActionListener(arg0 -> {
            openDia.setVisible(true);
            String dirPath = openDia.getDirectory();
            String fileName = openDia.getFile();
            if (dirPath == null || fileName == null) {
                return;
            }
            workArea.setText("");
            File fileO = new File(dirPath, fileName);
            String finalfile = readFromFile(fileO);
            char[] arr = finalfile.toCharArray();

            String[] JkeywordsRED = {"package", "public", "protected", "private", "class", "interface", "abstract", "implements", "extends", "new", "try", "catch", "throw"};
            String[] JkeywordsORANGE = {"null", "true", "false", "void", "import", "package", "byte", "char", "boolean", "double", "short", "int", "long", "float"};
            String[] JkeywordsPURPLE = {"this", "super", "final", "static", "return", "continue", "if", "else", "while", "for", "switch", "case", "default", "do", "break"};
            workArea.setText(finalfile);

            SimpleAttributeSet set = new SimpleAttributeSet();
            StyleConstants.setForeground(set, new Color(226, 87, 78));

            SimpleAttributeSet set1 = new SimpleAttributeSet();
            StyleConstants.setForeground(set1, new Color(191, 131, 30));

            SimpleAttributeSet set2 = new SimpleAttributeSet();
            StyleConstants.setForeground(set2, new Color(177, 80, 176));
            workArea.setCaretPosition(workArea.getDocument().getLength());


            for (String item:JkeywordsRED) {
                int k;
                int l;
                workArea.setCaretPosition(workArea.getDocument().getLength());
                while (true) {
                    k = workArea.getText().lastIndexOf(item, workArea.getCaretPosition() - item.length() - 1);
                    l=k+item.length();
                    if (k > -1 ) {
                        workArea.setCaretPosition(k);
                        if(Word(arr,l-1,item)){
                            doc.setCharacterAttributes(k, item.length(), set, true);}
                        else{
                            continue;
                        }
                    }
                    if (k==-1){
                        break;
                    }
                }
            }

            for (String item:JkeywordsORANGE) {
                int k;
                int l;
                workArea.setCaretPosition(workArea.getDocument().getLength());
                while (true) {
                    k = workArea.getText().lastIndexOf(item, workArea.getCaretPosition() - item.length() - 1);
                    l=k+item.length();
                    if (k > -1 ) {
                        workArea.setCaretPosition(k);
                        if(Word(arr,l-1,item)){
                            doc.setCharacterAttributes(k, item.length(), set1, true);}
                        else{
                            continue;
                        }
                    }
                    if (k==-1){
                        break;
                    }
                }
            }
            for (String item:JkeywordsPURPLE) {
                int k;
                int l;
                workArea.setCaretPosition(workArea.getDocument().getLength());
                while (true) {
                    k = workArea.getText().lastIndexOf(item, workArea.getCaretPosition() - item.length() - 1);
                    l=k+item.length();
                    if (k > -1 ) {
                        workArea.setCaretPosition(k);
                        if(Word(arr,l-1,item)){
                            doc.setCharacterAttributes(k, item.length(), set2, true);}
                        else{
                            continue;
                        }
                    }
                    if (k==-1){
                        break;
                    }
                }
            }
        });
        return openJAVA;

    }


    private JMenuItem openCPP()
    {
        JMenuItem openCPP = new JMenuItem("OpenCPP(B)",KeyEvent.VK_B);
        openCPP.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_MASK));
        openCPP.addActionListener(arg0 -> {
            openDia.setVisible(true);
            String dirPath = openDia.getDirectory();
            String fileName = openDia.getFile();
            if (dirPath == null || fileName == null) {
                return;
            }
            workArea.setText("");
            File fileO = new File(dirPath, fileName);
            String finalfile = readFromFile(fileO);

            String finalfile1 = finalfile.replace("\n", "");
            char[] arr = finalfile1.toCharArray();
            String[] PkeywordsRED = {"from","for","finally","except","else","elif","del","def","continue","class","break","and","as","assert"};
            String[] PkeywordsORANGE = {"with","while","try","raise","return","print","pass","or","not","lambda","global","if","import","is","in"};
            String[] PkeywordsPURPLE = {"format","list","float","type","range","len","bool","tuple","file","input","open","all","int","str","sum","super","print"};
            workArea.setText(finalfile);
            testArea.setText(finalfile1);
            SimpleAttributeSet set = new SimpleAttributeSet();
            StyleConstants.setForeground(set, new Color(226, 87, 78));

            SimpleAttributeSet set1 = new SimpleAttributeSet();
            StyleConstants.setForeground(set1, new Color(191, 131, 30));

            SimpleAttributeSet set2 = new SimpleAttributeSet();
            StyleConstants.setForeground(set2, new Color(177, 80, 176));



            for (String item:PkeywordsRED) {
                int k;
                int l;
                testArea.setCaretPosition(finalfile1.length());

                while (true) {
                    k = finalfile1.lastIndexOf(item, testArea.getCaretPosition() - item.length() - 1);
                    l=k+item.length();
                    if (k > -1 ) {
                        testArea.setCaretPosition(k);
                        if(Word(arr,l-1,item)){
                            doc.setCharacterAttributes(k, item.length(), set, true);}
                        else{
                            continue;
                        }
                    }
                    if (k==-1){
                        break;
                    }
                }
            }
            for (String item:PkeywordsORANGE) {
                int k;
                int l;
                testArea.setCaretPosition(finalfile1.length());

                while (true) {
                    k = finalfile1.lastIndexOf(item, testArea.getCaretPosition() - item.length() - 1);
                    l=k+item.length();
                    if (k > -1 ) {
                        testArea.setCaretPosition(k);
                        if(Word(arr,l-1,item)){
                            doc.setCharacterAttributes(k, item.length(), set1, true);}
                        else{
                            continue;
                        }
                    }
                    if (k==-1){
                        break;
                    }
                }
            }
            for (String item:PkeywordsPURPLE) {
                int k;
                int l;
                testArea.setCaretPosition(finalfile1.length());

                while (true) {
                    k = finalfile1.lastIndexOf(item, testArea.getCaretPosition() - item.length() - 1);
                    l=k+item.length();
                    if (k > -1 ) {
                        testArea.setCaretPosition(k);
                        if(Word(arr,l-1,item)){
                            doc.setCharacterAttributes(k, item.length(), set2, true);}
                        else{
                            continue;
                        }
                    }
                    if (k==-1){
                        break;
                    }
                }
            }
        });
        return openCPP;
    }

    private JMenuItem openRTF()
    {
        JMenuItem openRTF = new JMenuItem("OpenRTF(D)",KeyEvent.VK_R);
        openRTF.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
        openRTF.addActionListener(arg0 -> {
            openDia.setVisible(true);
            String dirPath = openDia.getDirectory();
            String fileName = openDia.getFile();
            if(dirPath == null || fileName == null){
                return;
            }
            workArea.setText("");
            File file = new File(dirPath,fileName);
            try{
                DefaultStyledDocument styledDoc = new DefaultStyledDocument();
                InputStream is = new FileInputStream(file);
                new RTFEditorKit().read(is, styledDoc, 0);
                String line;
                line = new String(styledDoc.getText(0, styledDoc.getLength()).getBytes("ISO8859_1"), "GBK");
                workArea.setText(workArea.getText()+line+"\r\n");
                is.close();
            }catch(IOException | BadLocationException er1){
                throw new RuntimeException("Fail to read !");
            }

        });
        return openRTF;
    }

    private JMenuItem openPDF()
    {
        JMenuItem openPDF = new JMenuItem("OpenPDF(M)",KeyEvent.VK_M);
        openPDF.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_MASK));
        openPDF.addActionListener(arg0 -> {
            try {
                openDia.setVisible(true);
                String dirPath = openDia.getDirectory();
                String fileName = openDia.getFile();
                if (dirPath == null || fileName == null) {
                    return;
                }
                workArea.setText("");
                initfile = PDDocument.load(new File(dirPath, fileName));
                PDFTextStripper textStripper = new PDFTextStripper();
                String line;
                line = textStripper.getText(initfile);
                workArea.setText(workArea.getText()+line+"\r\n");
                initfile.close();
            } catch (IOException er1) {
                throw new RuntimeException("Fail to read !");
            }
        });
        return openPDF;
    }

    private JMenuItem save()
    {
        JMenuItem save = new JMenuItem("Save(S)", KeyEvent.VK_S);
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        save.addActionListener(e -> {
            int i=filechooser.showSaveDialog(TextFIle.this);
            if(i==JFileChooser.APPROVE_OPTION)
            {
                File f=filechooser.getSelectedFile();
                try
                {
                    FileOutputStream out=new FileOutputStream(f);
                    out.write(workArea.getText().getBytes());
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        return save;
    }

    private JMenuItem savePDF()
    {

        JMenuItem savePDF = new JMenuItem("Save as PDF(F)", KeyEvent.VK_F);
        savePDF.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK | InputEvent.SHIFT_DOWN_MASK, true));
        savePDF.addActionListener(e -> {
            int i=filechooser.showSaveDialog(TextFIle.this);
            if(i==JFileChooser.APPROVE_OPTION)
            {
                try
                {
                    PDDocument fir;
                    PDPage sec;
                    fir = new PDDocument();
                    sec = new PDPage();
                    fir.addPage(sec);
                    PDFont font = PDType1Font.TIMES_BOLD;
                    PDPageContentStream content = new PDPageContentStream(fir, sec);
                    content.beginText();
                    content.setFont(font, 15);
                    content.moveTextPositionByAmount(100, 700);
                    content.drawString(workArea.getText());
                    content.endText();
                    content.close();
                    fir.save(filechooser.getSelectedFile().getPath());
                    fir.close();
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        return savePDF;
    }

    private JMenuItem saveODT()
    {

        JMenuItem saveODT = new JMenuItem("Save as ODT(T)", KeyEvent.VK_T);
        saveODT.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_MASK | InputEvent.SHIFT_DOWN_MASK, true));
        saveODT.addActionListener(e -> {
            int i=filechooser.showSaveDialog(TextFIle.this);
            if(i==JFileChooser.APPROVE_OPTION)
            {
                try
                {
                    OdfTextDocument odt = org.odftoolkit.odfdom.doc.OdfTextDocument.newTextDocument();
                    odt.addText(workArea.getText());
                    odt.save(filechooser.getSelectedFile().getPath());
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        return saveODT;
    }

    private JMenuItem print()
    {
        JMenuItem print = new JMenuItem("Print(P)", KeyEvent.VK_P);
        print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
        print.addActionListener(e -> {
            PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
            DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
            PrintService[] printService = PrintServiceLookup.lookupPrintServices(flavor, pras);
            PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
            PrintService service;
            service = ServiceUI.printDialog(null, 100, 100, printService, defaultService, flavor, pras);
            if (service != null)
            {
                DocPrintJob job = service.createPrintJob();
                DocAttributeSet das = new HashDocAttributeSet();
                Doc doc = new SimpleDoc(createJMenuBar().getMenu(0).getText().getBytes(), flavor, das);
                try {
                    job.print(doc, pras);
                } catch (PrintException printException) {
                    printException.printStackTrace();
                }
            }
        });
        return print;
    }

    private JMenuItem exit()
    {
        JMenuItem exit = new JMenuItem("Exit(E)", KeyEvent.VK_E);
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
        exit.addActionListener(arg0 -> System.exit(0));
        return exit;
    }

    private JMenuItem revocation()
    {
        workArea.getDocument().addUndoableEditListener(um);
        JMenuItem revocation = new JMenuItem("Revocation(U)", KeyEvent.VK_Z);
        revocation.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
        revocation.addActionListener(e -> {
            if (um.canUndo()) {
                um.undo();
            }
        });
        return revocation;
    }

    private JMenuItem cut()
    {
        JMenuItem cut=new JMenuItem("Cut(T)", KeyEvent.VK_T);
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_MASK));
        cut.addActionListener(e -> workArea.cut());
        return cut;
    }

    private JMenuItem copy()
    {
        JMenuItem copy = new JMenuItem("Copy(C)", KeyEvent.VK_C);
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
        copy.addActionListener(e -> workArea.copy());
        return copy;
    }

    private JMenuItem paste()
    {
        JMenuItem paste = new JMenuItem("Paste(V)", KeyEvent.VK_V);
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
        paste.addActionListener(e -> workArea.paste());
        return paste;
    }

    private JMenuItem search()
    {
        JMenuItem search = new JMenuItem("Search(F)", KeyEvent.VK_F);
        search.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
        search.addActionListener(e -> Find());
        return search;
    }

    private JMenuItem help()
    {
        JMenuItem help = new JMenuItem("Help(H)", KeyEvent.VK_H);
        help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK));
        help.addActionListener(e -> JOptionPane.showMessageDialog(null,"Member A:Jay Sun 20007886 \n" +
                "Member B:Dylan Li 20007903"));
        return help;
    }





    private void Find()
    {
    final JDialog findDialog=new JDialog(this,"Find",false);
    Container con=findDialog.getContentPane();
    con.setLayout(new FlowLayout(FlowLayout.RIGHT));
    JLabel findContentLabel=new JLabel("search:");
    final JTextField findText=new JTextField(15);
    JButton findNextButton=new JButton("search next one");
    ButtonGroup bGroup=new ButtonGroup();
    final JRadioButton upButton=new JRadioButton("up");
    final JRadioButton downButton=new JRadioButton("down");
    downButton.setSelected(true);
    bGroup.add(upButton);
    bGroup.add(downButton);
    JButton cancel=new JButton("cancel");
    cancel.addActionListener(e -> findDialog.dispose());

    findNextButton.addActionListener(e -> {
        int k;
        final String str1,str2;
        str1=workArea.getText();
        str2=findText.getText();

        if(upButton.isSelected())
        {
            k=str1.lastIndexOf(str2, workArea.getCaretPosition()-findText.getText().length()-1);
            System.out.println(k);
            if(k>-1)
            {
                workArea.setCaretPosition(k);
                workArea.select(k,k+str2.length());
                workArea.setSelectedTextColor(Color.red);
            }
            else
            {   JOptionPane.showMessageDialog(null,"Cannot find anything!","Find",JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else if(downButton.isSelected())
        {
            if(workArea.getSelectedText()==null)
            k=str1.indexOf(str2,workArea.getCaretPosition()+1);
        else
            k=str1.indexOf(str2, workArea.getCaretPosition()-findText.getText().length()+1);
            if(k>-1)
            {
                workArea.setCaretPosition(k);
                workArea.select(k,k+str2.length());
                workArea.setSelectedTextColor(Color.red);
            }
            else
            {   JOptionPane.showMessageDialog(null,"Cannot find anything!","Find",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    });
    JPanel panel1=new JPanel();
    JPanel panel2=new JPanel();
    JPanel panel3=new JPanel();
    JTextPane textPane = new JTextPane();
    JPanel directionPanel=new JPanel();
    directionPanel.setBorder(BorderFactory.createTitledBorder("Direction"));
    textPane.setText(textPane.getText()+"begin from button 'up'");
    textPane.setBackground(Color.yellow);
    directionPanel.add(upButton);
    directionPanel.add(downButton);
    directionPanel.add(textPane);
    panel1.setLayout(new GridLayout(2,1,5,5));
    panel1.add(findNextButton);
    panel1.add(cancel);
    panel2.add(findContentLabel);
    panel2.add(findText);
    panel3.add(directionPanel);
    con.add(panel2);
    con.add(panel1);
    con.add(panel3);
    con.setLayout(new FlowLayout(FlowLayout.LEADING,10,10));
    findDialog.setSize(500,350);
    findDialog.setResizable(false);
    findDialog.setLocation(100,100);
    findDialog.setVisible(true);
}

    private boolean Word(char[] a, int b,String item)
    {
        char c=a[b+1];
        if (b - item.length() + 1 == 0){
            return true;
        }
        else{char d =a[b-item.length()];
        if (Character.isLetterOrDigit(d)){
            return false;
        }
            return !Character.isLetterOrDigit(c);
        }
    }


    public String readFromFile(File file)
    {
        char[] lines =null;
        try
        {
            FileReader fin=new FileReader(file);
            lines=new char[(int)file.length()];
            fin.read(lines);
            fin.close();
        }
        catch(FileNotFoundException fe)
        {
            JOptionPane.showMessageDialog(this,"not exist");
        }
        catch(IOException ioex) {
            JOptionPane.showMessageDialog(this,"fail");
        }
        finally
        {
            return new String(lines);
        }
    }
}
