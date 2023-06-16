import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/*
[09:30-10:45 MoWe//13:00-15:50 Tu CS 112 L01, 11:00-11:50 Su//11:00-12:15 MoWe//11:00-11:50 Tu MATH 102 L02, 08:00-09:50 Su//09:00-09:50 Tu//09:00-09:50 Th ENGL 102 L04],
[09:30-10:45 MoWe//13:00-15:50 Tu CS 112 L01, 11:00-11:50 Su//11:00-12:15 MoWe//11:00-11:50 Tu MATH 102 L02, 08:00-09:15 MoWe//10:00-10:50 Th ENGL 102 L05],
[11:00-12:15 MoWe//08:00-10:50 We CS 112 L02, 08:00-09:50 TuSu//08:00-08:50 Th MATH 102 L01, 13:00-13:50 TuThSu//12:00-12:50 Th ENGL 102 L03],
[08:00-09:15 MoWe//14:00-16:50 Th CS 112 L03, 08:00-09:50 TuSu//08:00-08:50 Th MATH 102 L01, 13:00-13:50 TuThSu//12:00-12:50 Th ENGL 102 L03],
[08:00-09:15 MoWe//14:00-16:50 Th CS 112 L03, 11:00-11:50 Su//11:00-12:15 MoWe//11:00-11:50 Tu MATH 102 L02, 08:00-09:50 Su//09:00-09:50 Tu//09:00-09:50 Th ENGL 102 L02],
[08:00-09:15 MoWe//14:00-16:50 Th CS 112 L03, 11:00-11:50 Su//11:00-12:15 MoWe//11:00-11:50 Tu MATH 102 L02, 13:00-13:50 TuThSu//12:00-12:50 Th ENGL 102 L03],
[08:00-09:15 MoWe//14:00-16:50 Th CS 112 L03, 11:00-11:50 Su//11:00-12:15 MoWe//11:00-11:50 Tu MATH 102 L02, 08:00-09:50 Su//09:00-09:50 Tu//09:00-09:50 Th ENGL 102 L04]
 */
public class Output {
    public static void ScheduleArrangerOut(String[][] net) {
        String[][] arr = net; //getting the schedules to be available after processing
//        {{"11:00-12:15 MoWe//08:00-10:50 Su CS 112 L02", "08:00-09:50 TuSu//08:00-08:50 Th MATH 102 L01", "13:00-13:50 TuThSu//12:00-12:50 Th ENGL 102 L03"},
//        {"08:00-09:15 MoWe//14:00-16:50 Mo CS 112 L03", "08:00-09:50 TuSu//08:00-08:50 Th MATH 102 L01", "13:00-13:50 TuThSu//12:00-12:50 Th ENGL 102 L03"},
//        {"08:00-09:15 MoWe//14:00-16:50 Tu CS 112 L03", "11:00-11:50 Su//11:00-12:15 MoWe//11:00-11:50 Tu MATH 102 L02", "08:00-09:50 Su//09:00-09:50 Tu//09:00-09:50 Th ENGL 102 L02"},
//        {"08:00-09:15 MoWe//14:00-16:50 We CS 112 L03", "11:00-11:50 Su//11:00-12:15 MoWe//11:00-11:50 Tu MATH 102 L02", "13:00-13:50 TuThSu//12:00-12:50 Th ENGL 102 L03"},
//        {"08:00-09:15 MoWe//14:00-16:50 Th CS 112 L03", "11:00-11:50 Su//11:00-12:15 MoWe//11:00-11:50 Tu MATH 102 L02", "08:00-09:50 Su//09:00-09:50 Tu//09:00-09:50 Th ENGL 102 L04"}};
//        {
//        {"11:00-12:15 SuTu//13:00-14:50 Th AI 407 L01", "20:00-20:50 SuTu AI 372 L01", "18:15-19:30 MoWe AI 312 L01", "09:30-10:45 MoWe AI 306 L01"},
//        {"11:00-12:15 SuTu//13:00-14:50 Th AI 407 L01", "20:00-20:50 SuTu AI 372 L01", "08:00-08:50 MoWe AI 312 L02", "09:30-10:45 MoWe AI 306 L01"},
//        {"11:00-12:15 SuTu//13:00-14:50 Th AI 407 L01", "20:00-20:50 SuTu AI 372 L01", "09:00-09:50 SuTuTh AI 312 L03", "09:30-10:45 MoWe AI 306 L01"},
//        {"08:00-09:50 SuTu//10:00-11:50 MoWe AI 407 L02", "20:00-20:50 SuTu AI 372 L01", "18:15-19:30 MoWe AI 312 L01", "11:00-12:15 SuTu AI 306 L02"},
//        {"08:00-09:50 SuTu//10:00-11:50 MoWe AI 407 L02", "20:00-20:50 SuTu AI 372 L01", "08:00-08:50 MoWe AI 312 L02", "11:00-12:15 SuTu AI 306 L02"}
//        };
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src\\objects_file.txt"));

            String line;
            while ((line = reader.readLine()) != null) {

                int spacesIndex = line.indexOf("   "),checker;
                String courseName = line.substring(0, spacesIndex);
                System.out.println(courseName+":");
                line=line.substring(spacesIndex+3)+"|";
                char n='|';
                String fakeline=line;
                int num,sum=0;
                do {
                    num =fakeline.indexOf(n);
                    fakeline = fakeline.substring(num + 1);
                    if (num != -1) {
                        sum++;
                    } else {
                        break;
                    }
                }while (true);
                for (int i=0,x=1;i<sum;i++,x++){
                    checker=line.indexOf('|');
                    System.out.println("L0"+x+": "+line.substring(0,checker));
                    line=line.substring(checker+1);
                }
                System.out.println();
            }

            reader.close();
            // System.out.println("Number of newlines: " + newlines);

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        try {
            if (Arrays.deepEquals(arr, new String[0][])){
                throw new Exception();
            }
            String str,temp="";
            int checker;
            int possible;
            String[] days ={"Su ","Mo ","Tu ","We ","Th "};
            int TableRows=arr.length;
            int TableCol=arr[0].length;
            System.out.println("All the possible Schedules:\n");
            for (int row=0;row<TableRows;row++){
                possible=row+1;
                System.out.println("Possible Schedule: "+possible); //number of the possible schedules
                for (int col=0;col<TableCol;col++){
                    str=arr[row][col];//taking each time on its own
                    for (int day=0;day<days.length;day++) { //processing to divide the section numbers
                        checker = str.indexOf(days[day]);
                        temp = str.substring(checker+3);
                        if (checker!=-1){
                            break;
                        }
                    }
                    /*
                    String string="11:00-12:15 MoWe//08:00-10:50 We CS 112 L02";
                    String temp;
                    int checker = string.indexOf("We ");
                    temp = string.substring(checker + 3);
                    System.out.println(temp);
                     */
                    System.out.print("| "+temp+" ");
                    if (col==TableCol-1)
                        System.out.print("|"); // to put a | in the end
                }
                System.out.println(); //to space out each schedule
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("No possible schedules found, please try again.");
        } finally{
            System.out.println("\n\nDeveloped By: Team WAKINS");
        }


    }
    public void CommonBreakOut(String [] commonBreaks) {
        // String[] arr={"|8:00-11:00|12:15|  Su", "|8:00-18:15|  Mo", "|8:00-11:00|12:15-13:00|13:15-14:00|16:00|  Tu",
        //         "|8:00-18:15|  We", "|8:00-13:00|14:50-15:00|17:00|  Th"};

        String [] arr = commonBreaks;

        try {
            if (Arrays.equals(arr, new String[0])){
                throw new Exception();
            }
            int TableRows=arr.length;
            String str,time;
            String[] days ={"Sunday","Monday","Tuesday","Wednesday","Thursday"};
            int checker;
            System.out.println("Common Breaks On Each Day:\n");
            for (int row=0;row<TableRows;row++){
                str=arr[row];
                int remove =str.lastIndexOf("|");
                str=str.substring(1,remove+1);
                System.out.println("The Common Breaks on "+days[row]+" are:");
                char n='|';
                String fakestr=str;
                int num,sum=0;
                do {
                    num =fakestr.indexOf(n);
                    fakestr = fakestr.substring(num + 1);
                    if (num != -1) {
                        sum++;
                    } else {
                        break;
                    }
                }while (true);
                int i;
                for (i=0;i<sum;i++){
                    checker=str.indexOf("|");
                    time=str.substring(0,checker);
                    str=str.substring(checker+1);
                    if (sum==1){
                        System.out.print(time);
                    }
                    else if (i==sum-1){
                        if (time.contains("-")){
                            System.out.print(time+" |");
                        }
                        else{
                            System.out.print(time+"-19:00 |");
                        }
                    }
                    else System.out.print(time+" | ");
                }
                System.out.println("\n");

            }
        } catch (Exception e) {
            System.out.println("No Available Common Breaks, Sorry!");
        } finally{
            System.out.println("\n\nDeveloped By: Team WAKINS");
        }

    }
    // public static void main(String[] args) {

    //     Output output = new Output();
    //     String[][] net ={{"11:00-12:15 MoWe//08:00-10:50 Su CS 112 L02", "08:00-09:50 TuSu//08:00-08:50 Th MATH 102 L01", "13:00-13:50 TuThSu//12:00-12:50 Th ENGL 102 L03"},
    //             {"08:00-09:15 MoWe//14:00-16:50 Mo CS 112 L03", "08:00-09:50 TuSu//08:00-08:50 Th MATH 102 L01", "13:00-13:50 TuThSu//12:00-12:50 Th ENGL 102 L03"},
    //             {"08:00-09:15 MoWe//14:00-16:50 Tu CS 112 L03", "11:00-11:50 Su//11:00-12:15 MoWe//11:00-11:50 Tu MATH 102 L02", "08:00-09:50 Su//09:00-09:50 Tu//09:00-09:50 Th ENGL 102 L02"},
    //             {"08:00-09:15 MoWe//14:00-16:50 We CS 112 L03", "11:00-11:50 Su//11:00-12:15 MoWe//11:00-11:50 Tu MATH 102 L02", "13:00-13:50 TuThSu//12:00-12:50 Th ENGL 102 L03"},
    //             {"08:00-09:15 MoWe//14:00-16:50 Th CS 112 L03", "11:00-11:50 Su//11:00-12:15 MoWe//11:00-11:50 Tu MATH 102 L02", "08:00-09:50 Su//09:00-09:50 Tu//09:00-09:50 Th ENGL 102 L04"}};
    //     ScheduleArrangerOut(net);


    //     String[] arr={"|8:00-11:00|12:15|  Su", "|8:00-18:15|  Mo", "|8:00-11:00|12:15-13:00|13:15-14:00|16:00|  Tu",
    //             "|8:00-18:15|  We", "|8:00-13:00|14:50-15:00|17:00-17:15|18:30-19:00|  Th"};
        //output.CommonBreakOut(arr);
        //    String string="11:00-12:15 MoWe//08:00-10:50 We CS 112 L02";
        //    String temp;
        //    int checker = string.indexOf("We ");
        //    temp = string.substring(checker + 3);
        //    System.out.println(temp);
    // }
}