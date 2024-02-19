Описание программы "fileparsing.java" - "Утилита фильтрации строк".

Задание выполнил Федченко Дмитрий.
java 21.0.1 2023-10-17 LTS
Java(TM) SE Runtime Environment (build 21.0.1+12-LTS-29)
Java HotSpot(TM) 64-Bit Server VM (build 21.0.1+12-LTS-29, mixed mode, sharing)

Пример запуска программы:
java fileparsing.java in.txt in1.txt  -s -f -o  c:\1 -p RES_ -a   
Report String count=8
Report Integer count=8      
Report double count=2       
Report Integer Min=1        
Report Integer Max=5        
Report Integer Sum=21       
Report Integer Average=2.625
Report double Min=1.2       
Report double Max=4.3       
Report double Sum=5.5       
Report double Average=2.75  
Report string Min=0
Report string Max=20  

Особенности реализации:
1. При запуске программы fileparsing.java без параметров выдается информационное сообщение с форматом запуска утилиты "fileparsing":
java fileparsing.java
Комманда запуска: java FileParsing <inputfile1>...<inputfileN> -o </some/path> -p <prefixNameFile_> -s -f -a

2. При запуске программы fileparsing.java с некорректным параметром выдается информационное сообщение об игнорировании параметра, при этом утилита продолжает работать, например:
java fileparsing.java in.txt in1.txt  -s -f -G -o fff c:\1  -p RES_ -a
Incorrect parameter (ignore): -G
Incorrect parameter (ignore) Not Directory: fff
Incorrect parameter (ignore): c:\\1
Report String count=8
Report Integer count=8
Report double count=2
Report Integer Min=1
Report Integer Max=5
Report Integer Sum=21
Report Integer Average=2.625
Report double Min=1.2
Report double Max=4.3
Report double Sum=5.5
Report double Average=2.75
Report string Min=0
Report string Max=20
