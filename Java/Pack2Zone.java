import java.io.*;
import java.util.ArrayList;

/**
 * Pack2Zone
 * This program is convert PackDecimal to ZoneDecimal.
 */
public class Pack2Zone {
    public static void main(String[] args) {
        // File path
        // (Class file is create into java dir).
        final String fname = "../OUT-TP.dat";
        // Layout file path
        final String layfname = "../layout.csv";
        try{
            // Open COBOL written tape.
            FileInputStream fis = new FileInputStream(fname);
            // Converted pack to zone (String).
            String Pack2Zone = "";
            // Create Layout array / Array index.
            ArrayList<ArrayList<String>> layouts = CSVtoArray(new File(layfname));
            int i = 0;
            // Read first byte / first layout.
            int b = fis.read();
            ArrayList<String> layout = layouts.get(i);
            // Read byte counter.
            int j = 0;
            // End Condition: File is end.
            while (b != -1) {
                j++;
                // Layout is out of range -> Read next layout.
                if (j > Integer.parseInt(layout.get(2))) {
                    layout = layouts.get(++i);
                }
                switch (layout.get(0)) {
                    case "Z":
                    case "X":
                    case "N":
                        // Zone/X is simple concat.
                        Pack2Zone += (char)b;
                        break;
                    case "P":
                        // Pack is concat hex str.
                        Pack2Zone = concatHexString(Pack2Zone, Integer.toHexString(b));
                        break;
                }
                // Read next byte.
                b = fis.read();
            }
            // Tape close.
            fis.close();
            // Convert ConcatHexString to int.
            System.out.println(Pack2Zone);
        } catch (Exception e) {
            // If exception, print exception infomation.
            e.printStackTrace();
        }
    }

    /**
     * Return concat hex string.
     */
    private static String concatHexString(String str, String hex) {
        // "f" or "c" only is 0.
        if (hex.equals("f") || hex.equals("c")) return str + "0";
        // "d" only is minus.
        if (hex.equals("d")) return "-" + str;
        // "f9" is unsigned, "c9" is plus for signed. ("9" is numeric)
        if (hex.contains("f") || hex.contains("c")) return str + hex.substring(1);
        // "d9" is minus for signed. ("9" is numeric)
        if (hex.contains("d")) return "-" + str + hex.substring(1);
        // other is simple concat.
        return str + hex;
    }

    private static ArrayList<ArrayList<String>> CSVtoArray(File file) {
        // Layout array.
        ArrayList<ArrayList<String>> layouts = new ArrayList<>();
        try {
            // Open layout csv.
            BufferedReader br = new BufferedReader(new FileReader(file));
            // Read first line.
            String line = br.readLine();
            while (line != null) {
                ArrayList<String> layout = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    layout.add(line.split(",")[i]);
                }
                layouts.add(layout);
                line = br.readLine();
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return layouts;
    }
}