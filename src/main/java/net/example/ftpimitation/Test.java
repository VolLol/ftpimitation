package net.example.ftpimitation;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

    public static void main(String[] args) {


        File file = new File("C:\\Develop\\ftpimitation\\build\\");


        System.out.println(file.exists());

        file = new File("C:\\Develop\\ftpimitation\\build\\ssss");
        System.out.println(file.exists());


    }


    private void ll() {
        List<String> test = new ArrayList<>();
        test.add("tmp");
        test.add("compileJava");
        String rootDirectory = "C:/Develop/ftpimitation/build";
        File currentdir = new File(rootDirectory);
        for (String s : test) {
            currentdir = new File(currentdir.getAbsolutePath() + "/" + s);
            System.out.println(currentdir.getAbsolutePath());
        }


        test.remove(1);
        currentdir = new File(rootDirectory);
        for (String s : test) {
            currentdir = new File(currentdir.getAbsolutePath() + "/" + s);
            System.out.println(currentdir.getAbsolutePath());
        }

        String showPath = null;
        for (String s : test) {
            showPath = showPath + s + "/";
        }

        System.out.println(showPath);


    }
}
