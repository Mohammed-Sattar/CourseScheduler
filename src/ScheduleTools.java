
public class ScheduleTools {
    public static final String noTime = "00:00-00:00    //";     // a variable that represents the null time


    public static String[] filter(String[] arr) {
        return filter(arr, "");
    } //overloaded

    public static String[] filter(String[] arr, String cd) {
        //Since arrays are not resizable, this method filters the array if any null time was found
        /* the local variable cd represents the course code because sometimes this method is
        called from the getter method and the course code is passed

         */
        byte len = (byte) arr.length;
        byte sec_num = 0;               // "sec_num" is the number next to 0 in the section name (e.g L02)
        String net[];
        for (byte i = 0; i < arr.length; i++)
            if (arr[i].equals(noTime))
                len--;                  //decrementing the "len" when a null time is found

        net = new String[len];         // creating another Array based on the value of "len"


        for (String i : arr) {
            if (!(i.equals(noTime))) {
                if (!(cd.equals("")))  //Checks if any code was passed
                    net[sec_num] = i + " " + cd + " L0" + (sec_num + 1); //adding the code and the section to the time
                else {
                    net[sec_num] = i; // if there wasn't a code then it just fills the new array

                }
                sec_num++;
            }
        }

        return net;

    }




    public static byte[][][] reserve(String[] times) {
        /*
        This method basically converts the periods of time from a String into a 3-dimensional zeros array representing the whole week
        The 3 dimensions are [5][24][12] where 5 indicate weekdays and 24 is whole day (24 hours) while 12 comes from
         12*5=60 min since all classes start and end in a time that is a multiple of 5
         */

        times = filter(times);                           //"times" is an Array containing all the periods need to be reserved
        byte len = (byte) times.length;                 //(e.g 11:00-12:15 SuTu//13:00-14:50 Th|08:00-09:50 SuTu//10:00-11:50 MoWe)
        byte[][] arr = new byte[24][12];               // the Array "arr" holds the converted time for one day
        byte[][] su, mo, tue, we, th;
        su = mo = tue = we = th = new byte[24][12];
        byte len_lec_LbT=0;
        String[] lec_LbT_time=new String[]{};        // an Array that splits any tutorials or labs from lectures Using "//"
        byte[][] lec_LbT;                           // the same previos Array but with the hours and mins as bytes not strings
        byte slash = 0,start_hour=0,start_min=0,end_hour=0,end_min=0;
        boolean same_hour;                          // Indicating a special case when the starting hour is the same as the ending one

        for (byte j = 0; j < len; j++) {
            slash = (byte) times[j].indexOf("/");
            if (!(slash==-1)) {                     // The existence of "//" means that there is a lab or a tutorial for this course
                lec_LbT_time=times[j].split("//"); //splitting the labs and the tutorials according to "//"
                len_lec_LbT= (byte) lec_LbT_time.length; //This length now represebts the number of lectures with labs or tutorials
            }
            else{
                lec_LbT_time= new String[]{times[j]}; // if no "//" was found, this means that there is no labs or tutorials for this course
                len_lec_LbT=1;
            }
            lec_LbT=new byte[len_lec_LbT][4];
            for (byte i = 0; i < len_lec_LbT; i++) {
                for (byte k = 0, m = 0, n = 2; k < 4; k++, m += 3, n += 3){
                    /*
                    This loops slices the String time to convert each number
                    into byte to store them in the byte Array
                    (e.g 9:00-10:15//15:00-16:30 ---> [[9,0,10,15],[15,0,16,30]])
                     */
                    lec_LbT[i][k] = Byte.valueOf(lec_LbT_time[i].substring(m, n));
                }
            }

            for (byte cls=0;cls<len_lec_LbT;cls++) {
                start_hour = lec_LbT[cls][0];             //Assigning each element of "lec_LbT" to a variable
                start_min = (byte) (lec_LbT[cls][1] / 5); //Dividing the mins by 5 to convert them into groups of 5 mins
                end_hour = lec_LbT[cls][2];
                end_min = (byte) (lec_LbT[cls][3] / 5);
                same_hour=false;
                if (start_hour == end_hour) {
                    /*
                    When the starting hour equals the ending, we can ignore the hours and focus on the 5 mins
                     */
                    same_hour=true;
                    for (byte _5min = start_min; _5min < end_min; _5min++) {
                        arr[start_hour][_5min]++;
                    }

                }
                if (!(same_hour)) {
                    for (byte hour = start_hour; hour <= end_hour; hour++) {
                        if (hour == start_hour) {
                            for (byte _5min = start_min; _5min < 12; _5min++) { //Starting hour should be filled from the starting 5 mins
                                arr[hour][_5min]++;                             // (e.g 8:15 ---> [0,0,0,1,1,1,1,1,1,1,1,1]
                            }

                        } else if (hour == end_hour) {
                                                                                // The ending hour should be treated differently
                            for (byte _5min = 0; _5min < end_min; _5min++)      //(e.g 10:30 ---> [1,1,1,1,1,1,0,0,0,0,0,0]
                                arr[hour][_5min]++;


                        } else {                                                // Any hour in between should be filled compeletly
                                                                                // (e.g 8:15-10:30 so hour 9 should be [1,1,..,1]
                            for (byte _5min = 0; _5min < 12; _5min++)

                                arr[hour][_5min]++;


                        }

                    }
                }
                /*
                After reserving the periods in a random array "arr" we return to the splitted array "lec_LbT_time" and
                checks for the days on which these periods should be reserved
                 */
                if (lec_LbT_time[cls].contains("Su")) {
                    su = addArr(su, arr);
                }
                if (lec_LbT_time[cls].contains("Mo")) {
                    mo = addArr(mo, arr);
                }
                if (lec_LbT_time[cls].contains("Tu")) {
                    tue = addArr(tue, arr);
                }
                if (lec_LbT_time[cls].contains("We")){
                    we = addArr(we, arr);
                }
                if(lec_LbT_time[cls].contains("Th"))
                    th=addArr(th,arr);


                arr = new byte[24][12]; // we clear the random array after copying it to the needed days and continue the loop


        }
    }
        byte[][][] week_days = {su, mo, tue, we, th};
        return week_days;
    }




    public static byte[][] addArr(byte[][] arr, byte[][] arr1) {
        /*
        There is no method in Java for summing Arrays, so here it is
         */
        byte rows = (byte) arr.length, columns = (byte) arr[0].length;

        byte[][] sum = new byte[arr.length][arr[0].length];

        for (byte i = 0; i < rows; i++) {

            for (byte j = 0; j < columns; j++)

                sum[i][j] = (byte) (arr[i][j] + arr1[i][j]);
        }
        return sum;
    }

    public static boolean checkConflict(byte[][][] dys) {
        /*
        This method looks for conflicts
        returns True if a conflict was found
         */
        for (byte[][] day : dys) {
            for (byte[] hour : day) {
                for (byte _5min : hour) {
                    if (_5min > 1) {     // when the periods overlap this causes the elements of "arr" to be incremented more than once
                        return true;    // So any element in "arr" > 1 means a conflict
                    }
                }
            }
        }
        return false;
    }

    public static String[][] combine(String[][] sec) {
        /*
        This method gathers all the results from other methods and finalize it
        It takes the sections for all courses and pass to "reserve" method all the possible combinatons
         */
        // "all_pos" contains the number of combination by the product rule
        int all_pos = sec[0].length * sec[1].length * sec[2].length * sec[3].length * sec[4].length * sec[5].length * sec[6].length;
        String[][]validSchedules=new String[all_pos][],net; //An array containing the valid schedules
        int len=0;
        byte[][][] Schedule;
        for (String s1 : sec[0]) {
            for (String s2 : sec[1]) {            //We need 7 loops nested to get all the possible combination
                for (String s3 : sec[2]) {
                    for (String s4 : sec[3]) {
                        for (String s5 : sec[4]) {
                            for (String s6 : sec[5]) {
                                for (String s7 : sec[6]) {
                                    String[] combination = new String[]{s1, s2, s3, s4, s5, s6, s7};
                                    Schedule = reserve(combination);
                                    if (!(checkConflict(Schedule))) {
                                        validSchedules[len]=filter(combination); //add any valid schedule to the array
                                        len++;
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
        net=new String[len][];
    for (byte i=0;i<len;i++){
        if (validSchedules[i]!= null){   //some elements of "valid schedules" would be null so we filter them
            net[i]=validSchedules[i];
        }
    }
        return net;
    }
    public static String[] commonBreak(byte[][][] arr) { 
        /*
         This method, after receiving a 3-dimensional array representing the week will return an array
         containing the breaks (non-reserverd) periods for each day
         */
        String [][][] timetable = new String [5][19][12]; // creating a corresponding time table (5 days , 19 hours, 12 units)
        String [] week = {"Su", "Mo", "Tu", "We", "Th"};
        for(byte day=0;day<week.length;day++) {
            for (byte hour = 7; hour < 19; hour++) {
                for (byte min = 0; min < 12; min++) { // Assigning values to the time table 
                    if (min < 2)
                        timetable[day][hour][min] = "" + hour + ":0" + (min * 5); // for untits less than 2 it should add 0 to make it 05


                    else
                        timetable[day][hour][min] = "" + hour + ":" + (min * 5) ; // no need to add 0 starting from 2 which is 10
                }
            }
        }
        String []arrBreak=new String[5];           // An array of 5 elements (5 days) containing the breaks in each day separated by "|"
        boolean bool;  // A boolean to manipulate the blocks below
        String Break = "";
        for(byte day=0;day< timetable.length;day++) {
            arrBreak[day]=Break;
            Break="";
            bool = false;
            for (byte hour = 8; hour < timetable[day].length; hour++) {
                for (byte min = 0; min < timetable[day][hour].length; min++) {
                    if (arr[day][hour][min] == 0 && !(bool)) {  // if a 0 was found in "arr" it will look for the corresponding element in the timetable
                        Break +="|"+timetable[day][hour][min]; // Adding the corresponding element to Break
                        bool = true;                           // Changing the boolean to stop this block from execution for next iterations

                    } else if (arr[day][hour][min] != 0 && bool) { // if any number (not 0) was found, this means the end of the break
                        Break += "-" + timetable[day][hour][min]; // The same process will be done
                        bool = false;                           // changing the boolean to lock this block and open the other

                    }
                }
                arrBreak[day]=Break+"|  "+week[day]; // After finishing the day the "Break" variable is added to arrBreak 
            }
        }
        return arrBreak;  // After finishing the whole loop the arrBreak will be returned
    }
}

