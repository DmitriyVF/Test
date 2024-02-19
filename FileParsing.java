// Задание- Федченко Дмитрий
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class FileParsing {
    static String fileNameInt= "integers.txt";
    static String fileNameDouble= "floats.txt";
    static String fileNameStr= "strings.txt";
    static boolean keyPath = false;
    static  boolean keyResult = false;
    static  boolean keyStatFull = false;
    static  boolean keyStatShort = false;
    static  boolean keyAppendFile = false;
    static  boolean flagsetkey = false;
    static  String paramDir = "";
    static  String paramPrefix="";
    static int iMax, iMin, iSum, sMax, sMin;
    static double fMax, fMin, fSum;
    static List<String> InputFileList = new ArrayList<>();

    // Функция поиска ключей в параметрах
    static boolean fnCheckArg(String arg){
        // Определен ключ - путь для результатов
        if (arg.equals("-o")) {
            keyPath = flagsetkey = true;
            return true;
        }
        // Определен ключ - префикс имен выходных файлов
        if (arg.equals("-p")) {
            keyResult=  flagsetkey = true;
            return true;
        }
        // Определен ключ - краткая статистика
        if (arg.equals( "-s")){
            keyStatShort = flagsetkey = true;
            return true;
        }
        // Определен ключ - полная статистика
        if (arg.equals( "-f")){
            keyStatFull = flagsetkey= true;
            return true;
        }
        // Определен ключ - режим добавления в существующие файлы
        if (arg.equals ("-a")){
            keyAppendFile  = flagsetkey = true;
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        String CurDir;
        boolean isKey=false;
        int filecount=0;

        // 1. Разбор параметров
        if (args.length == 0) {
          System.out.println("Комманда запуска: java FileParsing <inputfile1>...<inputfileN> -o </some/path> -p <prefixNameFile_> -s -f -a");
            return;
        }
        for (int i = 0; i < args.length; i++) {
            isKey = fnCheckArg(args[i]);
            //System.out.println(args[i]+" is key:"+isKey);
            // если предыдущий параметр - путь для результатов
            if (!isKey && keyPath && paramDir.isEmpty()) {
                String directoryPath = args[i];
                File file = new File(directoryPath);
                if (file.isDirectory()) {
                    paramDir = args[i];
                    keyPath = false;
                }
                else {
                    System.out.println("Incorrect parameter (ignore) Not Directory: " + args[i]);
                    keyPath = false;
                }
            }
            // если предыдущий параметр - префикс имен выходных файлов
            else if (!isKey && keyResult && paramPrefix.isEmpty()) {
                    paramPrefix = args[i];
                    keyResult = false;
                } else {
                // если параметр - не ключ и ключи не определялись ранее (flagsetkey) - значит входной файл
                if (!isKey && !flagsetkey){
                    InputFileList.add(args[i]);
                    filecount++;
                    //System.out.println("Input file ADD: " + args[i]);
                }
                else if (!isKey && flagsetkey){
                    System.out.println("Incorrect parameter (ignore): " + args[i]);
                }
            }
        }
        CurDir = System.getProperty("user.dir");
        // Определяем путь для выходных файлов
        if (paramDir.isEmpty()){
            paramDir = CurDir;
        }
        // Определяем имена выходных файлов
        fileNameInt = paramDir+"\\"+  paramPrefix + fileNameInt;
        fileNameDouble = paramDir+"\\"+paramPrefix +fileNameDouble;
        fileNameStr= paramDir+"\\"+paramPrefix +fileNameStr;

        // отладка - что наразбирали
      /* System.out.println ("paramDir = "+ paramDir);
        System.out.println ("paramPrefix = "+paramPrefix);
        System.out.println("CurDir = " + System.getProperty("user.dir"));
        System.out.println("fileNameInt ="+  fileNameInt);
        System.out.println("fileNameDouble="+  fileNameDouble);
        System.out.println(" fileNameStr="+  fileNameStr);
        System.out.println("Input file count: " + filecount);
        for (String inputFile :InputFileList){
            System.out.print(inputFile+" ");
        }
        System.out.println();*/

        //2. Разбор входных файлов и подсчёт статистики
        List<String> textLines = new ArrayList<>();
        List<Integer> intLines = new ArrayList<>();
        List<Double> doubleLines = new ArrayList<>();

        for (String inputFile :InputFileList) {
            try (BufferedReader br = new BufferedReader(new FileReader(CurDir+"\\" + inputFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    try {
                        int intValue = Integer.parseInt(line);
                        intLines.add(intValue);

                        if (keyStatFull){
                            if (intLines.stream().count()==1){
                                iMax = iMin =intValue;
                            }
                            else {
                                if (intValue>iMax)  {
                                    iMax = intValue;
                                }
                                if (intValue<iMin)  {
                                    iMin = intValue;
                                }
                            }
                            iSum =  iSum + intValue;
                        }
                    } catch (NumberFormatException e1) {
                        try {
                            double doubleValue = Double.parseDouble(line);
                            doubleLines.add(doubleValue);
                            if (keyStatFull){
                                if (doubleLines.stream().count()==1){
                                    fMax = fMin = doubleValue;
                                }
                                else {
                                    if (doubleValue>fMax)  {
                                        fMax = doubleValue;
                                    }
                                    if (doubleValue<fMin)  {
                                        fMin = doubleValue;
                                    }
                                }
                                fSum =  fSum + doubleValue;
                            }
                        } catch (NumberFormatException e2) {
                            textLines.add(line);
                            if (textLines.stream().count()==1){
                                sMax = sMin =line.length();
                                }
                            else {
                                if (line.length()>sMax)  {
                                    sMax = line.length();
                                }
                                if (line.length()<iMin)  {
                                    sMin = line.length();
                                }
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //3. Вывод статистики
        if (keyStatShort){
            System.out.println("Report String count=" +  textLines.stream().count());
            System.out.println("Report Integer count=" + intLines.stream().count());
            System.out.println("Report double count=" + doubleLines.stream().count());
        }
        if (keyStatFull){
            if (intLines.stream().count()!=0){
                System.out.println("Report Integer Min="+iMin);
                System.out.println("Report Integer Max="+iMax);
                System.out.println("Report Integer Sum="+iSum);
                System.out.println("Report Integer Average="+  (double) iSum/intLines.stream().count());
            }
            if (doubleLines.stream().count()!=0){
                System.out.println("Report double Min="+fMin);
                System.out.println("Report double Max="+fMax);
                System.out.println("Report double Sum="+fSum);
                System.out.println("Report double Average="+fSum/doubleLines.stream().count());
            }
            if (textLines.stream().count()!=0){
                System.out.println("Report string Min="+sMin);
                System.out.println("Report string Max="+sMax);
            }
        }

        // реализация когда всегда создаем выходные файлы
       /* try (PrintWriter textWriter = new PrintWriter(new FileOutputStream(outputFileStr,keyAppendFile));
             PrintWriter intWriter = new PrintWriter(new FileOutputStream(outputFileInt,keyAppendFile));
             PrintWriter doubleWriter = new PrintWriter(new FileOutputStream(outputFiledouble,keyAppendFile))) {
            for (String line : textLines) {
                textWriter.println(line);
            }
            for (int value : intLines) {
                intWriter.println(value);
            }
            for (double value :doubleLines) {
                doubleWriter.println(value);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/

        //4. Запись в выходные файлы (с проверкой необходимости создания файла)
        try {
            PrintWriter textWriter = null;
            PrintWriter intWriter = null;
            PrintWriter doubleWriter = null;
            if (textLines.stream().count()!=0){
                textWriter = new PrintWriter(new FileOutputStream( fileNameStr,keyAppendFile));
                for (String line : textLines) {
                    textWriter.println(line);
                }
                textWriter.close();
            }
            if (intLines.stream().count()!=0){
                intWriter = new PrintWriter(new FileOutputStream( fileNameInt,keyAppendFile));
                for (int value : intLines) {
                    intWriter.println(value);
                }
                intWriter.close();
            }
            if ( doubleLines.stream().count()!=0){
                doubleWriter = new PrintWriter(new FileOutputStream( fileNameDouble,keyAppendFile));
                for (double value :doubleLines) {
                    doubleWriter.println(value);
                }
                doubleWriter.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}