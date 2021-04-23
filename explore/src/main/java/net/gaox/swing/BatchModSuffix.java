package net.gaox.swing;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: <p> 文件名批量替换 </p>
 * @ClassName BatchModSuffix
 * @Author: gaox·Eric
 * @Date: 2019/3/31 18:22
 */
public class BatchModSuffix {

    private static int lineY = 50;
    private static int startY = 30;
    private static int HEIGHT = 30;
    private static JTextArea ta;
    private static JComboBox inputBox;
    private static JComboBox outBox;
    static List<String> fileList = new ArrayList<String>();

    /**
     * @param args
     */
    public static void main(String[] args) {

        final JFrame f = new JFrame("File");
        f.setSize(500, 500);
        f.setLocation(580, 240);
        f.setLayout(null);

        // 文件地址

        JLabel fileName = new JLabel("文件地址：");
        fileName.setBounds(50, startY, 70, HEIGHT);
        f.add(fileName);

        // 输入框
        final JTextField tfName = new JTextField("请输入地址");
        // tfName.setPreferredSize(new Dimension(150, 30));
        tfName.setBounds(120, startY, 180, HEIGHT);
        f.add(tfName);

        // 选择按钮
        JButton btnSelect = new JButton("选择地址");
        btnSelect.setBounds(300, startY, 100, HEIGHT);
        f.add(btnSelect);

        lineY += startY;
        // 下拉框出现的条目
        JLabel findLabel = new JLabel("查找类型：");
        findLabel.setBounds(50, lineY, 80, HEIGHT);
        f.add(findLabel);

        final String[] heroes = new String[]{".xml", ".txt", ".java", ".kt", ".c", ".sh", ".mk"};

        inputBox = new JComboBox(heroes);
        inputBox.setBounds(130, lineY, 80, HEIGHT);
        inputBox.setSelectedIndex(0);
        f.add(inputBox);

        JLabel changeLabel = new JLabel("转为类型：");
        changeLabel.setBounds(210, lineY, 80, HEIGHT);
        f.add(changeLabel);

        outBox = new JComboBox(heroes);
        outBox.setBounds(290, lineY, 80, HEIGHT);
        outBox.setSelectedIndex(0);
        f.add(outBox);
        lineY += startY;
        final JCheckBox checkBox = new JCheckBox("选择此项，转化后删除源文件");
        checkBox.setBounds(50, lineY, 200, HEIGHT);
        checkBox.setSelected(false);
        f.add(checkBox);

        lineY += (startY + HEIGHT);

        JButton btnFind = new JButton("查找");
        btnFind.setBounds(50, lineY, 80, HEIGHT);
        f.add(btnFind);

        JButton btnChange = new JButton("转换");
        btnChange.setBounds(200, lineY, 80, HEIGHT);
        f.add(btnChange);

        lineY += startY + HEIGHT;
        ta = new JTextArea();
        ta.setBounds(50, lineY, 350, 250);
        ta.setText("文件名");
        // 设置自动换行
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(ta);
        //把定义的JTextArea放到JScrollPane里面去
        scroll.setBounds(50, lineY, 350, 250);
        //分别设置水平和垂直滚动条自动出现
        //scroll.setHorizontalScrollBarPolicy(
        //JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);


        f.add(scroll);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);

        // 文件选择
        final JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        //文件夹查找事件
        btnSelect.addActionListener(e -> {
            fc.showDialog(new JLabel(), "选择");
            File file = fc.getSelectedFile();
            if (file.isDirectory()) {
                tfName.setText(file.getAbsolutePath());
                System.out.println("文件夹:" + file.getAbsolutePath());
            }
            // System.out.println(fc.getSelectedFile().getName());

        });

        //文件选择事件
        btnFind.addActionListener(e -> {
            System.out.println("文件夹:" + tfName.getText() + "," + heroes[inputBox.getSelectedIndex()]);
            if (IsFileDirExists(tfName.getText())) {
                showDialog(f, "地址为空或地址格式不正确。");
                return;
            }
            fileList.clear();
            getFileSearcHelper(tfName.getText(), heroes[inputBox.getSelectedIndex()], fileList);
            System.out.print("123456789");
        });

        //转化选择事件
        btnChange.addActionListener(e -> {

            if (outBox.getSelectedIndex() == inputBox.getSelectedIndex()) {
                showDialog(f, "格式相同，无法转化");
                return;
            }
            new Thread(() -> {
                if (fileList != null && fileList.size() > 0) {
                    for (int i = 0; i < fileList.size(); i++) {
                        String path = fileList.get(i);
                        if (IsFileExists(path)) {
                            copyFile(path, heroes[inputBox.getSelectedIndex()], heroes[outBox.getSelectedIndex()], checkBox.isSelected());
                        }
                    }
                } else {
                    showDialog(f, "查找此格式的文件不存在");
                }
            }).start();
        });
    }

    private static List<String> getFileSearcHelper(String path, final String s2, final List<String> list) {
        File f = new File(path);
        File[] temp = f.listFiles();
        if (temp.length != 0) {
            final File[] str = f.listFiles((f1, mys) -> {
                if (new File(f1, mys).isDirectory()) {
                    getFileSearcHelper(new File(f1, mys).getAbsolutePath(), s2, list);
                    return false;
                } else if (new File(f1, mys).isFile()) {
                    return mys.endsWith(s2);
                }
                return false;
            });
            if (ta != null) {
                ta.setText("");
                new Thread(() -> {
                    for (final File myFile : str) {
                        ta.append(myFile.getAbsolutePath() + "\n");
                        System.out.println(myFile.getAbsolutePath());
                        list.add(myFile.getAbsolutePath());
                    }
                }).start();
            }
        }
        return list;
    }

    /**
     * 判断文件是否存在
     *
     * @param path
     * @return
     */
    private static boolean IsFileExists(String path) {
        File file = new File(path);
        if (file.exists() && !file.isDirectory()) {
            return true;
        }

        return false;
    }

    /**
     * 判断文件是否存在
     *
     * @param path
     * @return
     */
    private static boolean IsFileDirExists(String path) {
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            return false;
        }
        return true;
    }

    private static void copyFile(String path, String str1, String str2, boolean bool) {
        FileWriter fw = null;
        FileReader fr = null;
        boolean b = true;
        try {
            String newPath = path.replace(str1, str2);
            System.out.println("newPath=" + newPath);
            File file = new File(newPath);
            file.createNewFile();
            //先创建目的地文件
            fw = new FileWriter(file);
            fr = new FileReader(path);

            char[] reader = new char[1024];
            int num = 0;
            while ((num = fr.read(reader)) != -1) {
                fw.write(reader, 0, num);
            }
        } catch (IOException e) {
            b = false;
            System.out.println(path);
            throw new RuntimeException("读写错误");
        } finally {
            if (fw != null) {
                try {
                    // 这里使用try-catch是因为防止创建失败（路径错误之类），当创建失败的时候这里就会出现NullPointerException
                    fw.close();
                } catch (Exception e2) {
                }
            }
            if (fr != null) {
                try {
                    fr.close(); //同上
                } catch (Exception e2) {
                }
            }
            if (bool && b) {
                File file = new File(path);
                file.delete();
            }
        }
    }

    private static void showDialog(JFrame f, String str) {
        JOptionPane.showMessageDialog(f, str);
    }
}
