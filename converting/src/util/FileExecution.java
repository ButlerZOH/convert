package util;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileExecution {
    private File file;

    public FileExecution(){
    }

    public FileExecution(File file){
        this.file = file;
    }

    public void createFile(String name_String) throws IOException {
        File file = new File("D:\\convert");
        if (file!=null){
            File file1 = new File(file.getAbsolutePath()+"\\" + name_String);
            file1.mkdir();
            String t1 = file1.getAbsolutePath()+"\\"+name_String+"_brief.txt";
            //String t2 = file1.getAbsolutePath()+"\\"+name_String+"_load.txt";
            file1 = new File(t1);
            file1.createNewFile();
            //file1 = new File(t2);
            //file1.createNewFile();
        }
    }

    public void deleteFile(String filename){
        File file = new File("D:\\convert\\"+filename);
        if (file !=null){
            File file1 = new File(file.getAbsolutePath()+"\\"+filename+"_brief.txt");
            file1.delete();
            //file1 = new File(file.getAbsolutePath() + "\\" +filename +"_load.txt");
            //file1.delete();
            file.delete();
        }
    }

    public void Rename(String oldFileName,String newFileName){
        File file = new File("D:\\convert\\"+oldFileName);
        if (file!=null){
            File file1 = new File(file.getAbsolutePath()+"\\"+oldFileName+"_brief.txt");
            file1.renameTo(new File(file.getAbsolutePath()+"\\"+newFileName+"_brief.txt"));
            //file1 = new File(file.getAbsolutePath() + "\\" +oldFileName +"_load.txt");
            //file1.renameTo(new File(file.getAbsolutePath() + "\\" +newFileName +"_load.txt"));
        }
        file.renameTo(new File("D:\\convert\\"+newFileName));


    }

    public String SelectFile(){
        JFileChooser jFileChooser = new JFileChooser();
        //jFileChooser.setFileSelectionMode(JFileChooser.SAVE_DIALOG | JFileChooser.DIRECTORIES_ONLY);
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        jFileChooser.showOpenDialog(null);
        File file = jFileChooser.getSelectedFile();
        if (file.exists())
            return file.getAbsoluteFile().toString();
        else
            return "";
    }


    /*public static void main(String[] args) {
        FileExecution fileExecution = new FileExecution();
        String f = fileExecution.SelectFile();
    }*/
}
