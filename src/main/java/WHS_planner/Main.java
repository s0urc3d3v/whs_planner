package WHS_planner;


//import WHS_planner.Core.MeetingFileHandler;

import WHS_planner.Calendar.CalendarBox;
import WHS_planner.UI.MainPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Random;

public class Main extends Application {

    public static final String SAVE_FOLDER = System.getenv("HOME") + File.separator + "Library" + File.separator + "Application Support" + File.separator + "WHS Planner";

    public static final String VERSION_NUMBER = "1.4.2";

    public static final String UPDATE_NOTES =
            "===== CHANGES =====\n " +
                    "- New calendar saving method. NOTE: 1.4 will automatically port your saves from older versions. However, older versions will not be able to read 1.4 saves. \n " +
                    "- Added icons to make the app look better\n " +
                    "- Added a button to copy Err.txt\n " +
                    "===== Implemented Suggestions =====\n " +
                    "- Implemented dropdown menu for applying any of your classes to a homework task (iPass login required). (Michael German) \n " +
                    "- News links now open in the default browser (Steven Russo)\n " +
                    "- Added a refresh button to News if the user started the application offline (Ella Johnson)\n " +
                    "- Escape key now closes the Schedule drawer (Steven Russo).\n " +
                    "- Added task editing support (Vincent Pak)\n " +
                    "- Saves deleted tasks (Uma Paithankar)\n " +
                    "- Show letter days on the calendar (Vincent Pak/Kevin Wang/Talia Leong)\n " +
                    "===== BUG FIXES =====\n " +
                    "- Minor bell 2 error fixed (Found by Thomas Daley) \n " +
                    "- Fixed iPass login for some users \n " +
                    "- Fixed error log \n " +
                    "- Fixed tasks not saving upon shutdown (Found by Kevin Wang)";

    public static boolean isFirstStartup = false;
    public static boolean isFirstTimeOnVersion = false;

    //ON first run move jfoenix to a place it can be referenced on a remote system
    private static String readKey = null;
    private static MainPane mainPane;

    public static String getXorKey() {
        if (readKey != null) {
            return readKey;
        }
        return null;
    }

    /**
     * The main method of the program.
     * It initializes and runs the application!
     */

    public static void main(String[] args) {
        try {

            File saveFolder = new File(SAVE_FOLDER);
            if (!saveFolder.exists()) {
                saveFolder.mkdir();
            }

            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                public void run() {
                    mainPane.saveCalendar();
                    File file = new File(Main.SAVE_FOLDER + File.separator + "Keys/ipass.key");
                    if (!file.exists()) {
                        file.delete();
                    }

                }
            }, "Shutdown-thread"));

            PrintStream console = System.err;

            File file = new File(Main.SAVE_FOLDER + File.separator + "err.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            PrintStream ps = new PrintStream(fos);
            System.setErr(ps);

            System.setProperty("http.agent", "Chrome");
//        PropertyConfigurator.configure("log4j.properties");


            File keys = new File(Main.SAVE_FOLDER + File.separator + "Keys");
            if (!keys.exists()) {
                keys.mkdir();
            }

            File CalendarFolder = new File(Main.SAVE_FOLDER + File.separator + "Calendar");
            if (!CalendarFolder.exists()) {
                CalendarFolder.mkdir();
            }

            File runVersionFolder = new File(Main.SAVE_FOLDER + File.separator + "version");
            if (!runVersionFolder.exists()) {
                runVersionFolder.mkdir();
            }

            File versionFile = new File(Main.SAVE_FOLDER + File.separator + "version" + File.separator + VERSION_NUMBER);
            if (!versionFile.exists()) {
                isFirstTimeOnVersion = true;
                versionFile.createNewFile();
            }

            boolean needToPasteDayArray = false;
            //Stupid way to do Day Array
            File dayArrayFile = new File(Main.SAVE_FOLDER + File.separator + "DayArray.json");
            if(dayArrayFile.exists()){
                BufferedReader br = new BufferedReader(new FileReader(dayArrayFile));
                if (br.readLine() == null) {
                    needToPasteDayArray = true;
                    dayArrayFile.delete();
                }
            }else{
                needToPasteDayArray = true;
            }
            if (needToPasteDayArray) {
                isFirstStartup = true;
                Files.write(dayArrayFile.toPath(), "{\"@dates\":[\"dates0: 2\\/13\",\"dates1: 2\\/10\",\"dates2: 4\\/27\",\"dates3: 4\\/3\",\"dates4: 4\\/28\",\"dates5: 4\\/4\",\"dates6: 4\\/5\",\"dates7: 4\\/6\",\"dates8: 4\\/7\",\"dates9: 5\\/10\",\"dates10: 5\\/11\",\"dates11: 5\\/12\",\"dates12: 5\\/15\",\"dates13: 5\\/16\",\"dates14: 2\\/17\",\"dates15: 2\\/16\",\"dates16: 2\\/15\",\"dates17: 2\\/14\",\"dates18: 5\\/22\",\"dates19: 5\\/23\",\"dates20: 5\\/24\",\"dates21: 5\\/25\",\"dates22: 5\\/26\",\"dates23: 2\\/28\",\"dates24: 2\\/27\",\"dates25: 3\\/1\",\"dates26: 3\\/2\",\"dates27: 3\\/3\",\"dates28: 5\\/17\",\"dates29: 5\\/18\",\"dates30: 3\\/6\",\"dates31: 5\\/19\",\"dates32: 3\\/7\",\"dates33: 3\\/8\",\"dates34: 3\\/9\",\"dates35: 5\\/31\",\"dates36: 3\\/14\",\"dates37: 3\\/13\",\"dates38: 3\\/10\",\"dates39: 5\\/30\",\"dates40: 6\\/13\",\"dates41: 6\\/12\",\"dates42: 6\\/15\",\"dates43: 6\\/14\",\"dates44: 6\\/16\",\"dates45: 3\\/24\",\"dates46: 3\\/23\",\"dates47: 3\\/22\",\"dates48: 3\\/21\",\"dates49: 3\\/20\",\"dates50: 2\\/1\",\"dates51: 2\\/2\",\"dates52: 3\\/17\",\"dates53: 2\\/3\",\"dates54: 3\\/16\",\"dates55: 3\\/15\",\"dates56: 6\\/1\",\"dates57: 2\\/6\",\"dates58: 6\\/2\",\"dates59: 2\\/7\",\"dates60: 2\\/8\",\"dates61: 2\\/9\",\"dates62: 6\\/5\",\"dates63: 6\\/6\",\"dates64: 6\\/7\",\"dates65: 6\\/8\",\"dates66: 6\\/22\",\"dates67: 6\\/9\",\"dates68: 6\\/21\",\"dates69: 6\\/23\",\"dates70: 6\\/26\",\"dates71: 6\\/28\",\"dates72: 6\\/27\",\"dates73: 1\\/11\",\"dates74: 1\\/12\",\"dates75: 1\\/10\",\"dates76: 3\\/31\",\"dates77: 6\\/20\",\"dates78: 3\\/30\",\"dates79: 3\\/29\",\"dates80: 3\\/28\",\"dates81: 3\\/27\",\"dates82: 6\\/19\",\"dates83: 4\\/10\",\"dates84: 4\\/11\",\"dates85: 4\\/12\",\"dates86: 4\\/13\",\"dates87: 1\\/23\",\"dates88: 1\\/20\",\"dates89: 6\\/30\",\"dates90: 1\\/19\",\"dates91: 1\\/17\",\"dates92: 1\\/18\",\"dates93: 1\\/3\",\"dates94: 1\\/13\",\"dates95: 1\\/4\",\"dates96: 1\\/5\",\"dates97: 5\\/1\",\"dates98: 1\\/6\",\"dates99: 5\\/2\",\"dates100: 6\\/29\",\"dates101: 5\\/3\",\"dates102: 5\\/4\",\"dates103: 1\\/9\",\"dates104: 5\\/5\",\"dates105: 5\\/8\",\"dates106: 5\\/9\",\"dates107: 4\\/24\",\"dates108: 4\\/25\",\"dates109: 4\\/26\",\"dates110: 1\\/31\",\"dates111: 1\\/30\",\"dates112: 1\\/26\",\"dates113: 1\\/27\",\"dates114: 1\\/24\",\"dates115: 1\\/25\"],\"@days\":[\"days0: 103\",\"days1: 102\",\"days2: 145\",\"days3: 133\",\"days4: 146\",\"days5: 134\",\"days6: 135\",\"days7: 136\",\"days8: 137\",\"days9: 154\",\"days10: 155\",\"days11: 156\",\"days12: 157\",\"days13: 158\",\"days14: 107\",\"days15: 106\",\"days16: 105\",\"days17: 104\",\"days18: 162\",\"days19: 163\",\"days20: 164\",\"days21: 165\",\"days22: 166\",\"days23: 109\",\"days24: 108\",\"days25: 110\",\"days26: 111\",\"days27: 112\",\"days28: 159\",\"days29: 160\",\"days30: 113\",\"days31: 161\",\"days32: 114\",\"days33: 115\",\"days34: 116\",\"days35: 168\",\"days36: 119\",\"days37: 118\",\"days38: 117\",\"days39: 167\",\"days40: 177\",\"days41: 176\",\"days42: 179\",\"days43: 178\",\"days44: 180\",\"days45: 127\",\"days46: 126\",\"days47: 125\",\"days48: 124\",\"days49: 123\",\"days50: 95\",\"days51: 96\",\"days52: 122\",\"days53: 97\",\"days54: 121\",\"days55: 120\",\"days56: 169\",\"days57: 98\",\"days58: 170\",\"days59: 99\",\"days60: 100\",\"days61: 101\",\"days62: 171\",\"days63: 172\",\"days64: 173\",\"days65: 174\",\"days66: 184\",\"days67: 175\",\"days68: 183\",\"days69: 185\",\"days70: 186\",\"days71: 188\",\"days72: 187\",\"days73: 81\",\"days74: 82\",\"days75: 80\",\"days76: 132\",\"days77: 182\",\"days78: 131\",\"days79: 130\",\"days80: 129\",\"days81: 128\",\"days82: 181\",\"days83: 138\",\"days84: 139\",\"days85: 140\",\"days86: 141\",\"days87: 88\",\"days88: 87\",\"days89: 190\",\"days90: 86\",\"days91: 84\",\"days92: 85\",\"days93: 75\",\"days94: 83\",\"days95: 76\",\"days96: 77\",\"days97: 147\",\"days98: 78\",\"days99: 148\",\"days100: 189\",\"days101: 149\",\"days102: 150\",\"days103: 79\",\"days104: 151\",\"days105: 152\",\"days106: 153\",\"days107: 142\",\"days108: 143\",\"days109: 144\",\"days110: 94\",\"days111: 93\",\"days112: 91\",\"days113: 92\",\"days114: 89\",\"days115: 90\"],\"@calendarData\":[\"calendarData0: There is No School Today\",\"calendarData1: There is No School Today\",\"calendarData2: There is No School Today\",\"calendarData3: There is No School Today\",\"calendarData4: There is No School Today\",\"calendarData5: There is No School Today\",\"calendarData6: There is No School Today\",\"calendarData7: There is No School Today\",\"calendarData8: There is No School Today\",\"calendarData9: There is No School Today\",\"calendarData10: There is No School Today\",\"calendarData11: There is No School Today\",\"calendarData12: There is No School Today\",\"calendarData13: There is No School Today\",\"calendarData14: There is No School Today\",\"calendarData15: There is No School Today\",\"calendarData16: There is No School Today\",\"calendarData17: There is No School Today\",\"calendarData18: There is No School Today\",\"calendarData19: There is No School Today\",\"calendarData20: There is No School Today\",\"calendarData21: There is No School Today\",\"calendarData22: There is No School Today\",\"calendarData23: There is No School Today\",\"calendarData24: There is No School Today\",\"calendarData25: There is No School Today\",\"calendarData26: There is No School Today\",\"calendarData27: There is No School Today\",\"calendarData28: There is No School Today\",\"calendarData29: There is No School Today\",\"calendarData30: There is No School Today\",\"calendarData31: There is No School Today\",\"calendarData32: There is No School Today\",\"calendarData33: There is No School Today\",\"calendarData34: There is No School Today\",\"calendarData35: There is No School Today\",\"calendarData36: There is No School Today\",\"calendarData37: There is No School Today\",\"calendarData38: There is No School Today\",\"calendarData39: There is No School Today\",\"calendarData40: There is No School Today\",\"calendarData41: There is No School Today\",\"calendarData42: There is No School Today\",\"calendarData43: There is No School Today\",\"calendarData44: There is No School Today\",\"calendarData45: There is No School Today\",\"calendarData46: There is No School Today\",\"calendarData47: There is No School Today\",\"calendarData48: There is No School Today\",\"calendarData49: There is No School Today\",\"calendarData50: There is No School Today\",\"calendarData51: There is No School Today\",\"calendarData52: There is No School Today\",\"calendarData53: There is No School Today\",\"calendarData54: There is No School Today\",\"calendarData55: There is No School Today\",\"calendarData56: There is No School Today\",\"calendarData57: There is No School Today\",\"calendarData58: There is No School Today\",\"calendarData59: There is No School Today\",\"calendarData60: There is No School Today\",\"calendarData61: There is No School Today\",\"calendarData62: There is No School Today\",\"calendarData63: There is No School Today\",\"calendarData64: There is No School Today\",\"calendarData65: There is No School Today\",\"calendarData66: There is No School Today\",\"calendarData67: There is No School Today\",\"calendarData68: There is No School Today\",\"calendarData69: There is No School Today\",\"calendarData70: There is No School Today\",\"calendarData71: There is No School Today\",\"calendarData72: There is No School Today\",\"calendarData73: There is No School Today\",\"calendarData74: A\",\"calendarData75: B\",\"calendarData76: C\",\"calendarData77: D\",\"calendarData78: E\",\"calendarData79: F\",\"calendarData80: G\",\"calendarData81: H\",\"calendarData82: A\",\"calendarData83: B\",\"calendarData84: C\",\"calendarData85: D\",\"calendarData86: E\",\"calendarData87: F\",\"calendarData88: G\",\"calendarData89: H\",\"calendarData90: A\",\"calendarData91: B\",\"calendarData92: H\",\"calendarData93: A\",\"calendarData94: B\",\"calendarData95: C\",\"calendarData96: D\",\"calendarData97: E\",\"calendarData98: F\",\"calendarData99: G\",\"calendarData100: H\",\"calendarData101: A\",\"calendarData102: B\",\"calendarData103: C\",\"calendarData104: D\",\"calendarData105: E\",\"calendarData106: F\",\"calendarData107: G\",\"calendarData108: H\",\"calendarData109: A\",\"calendarData110: B\",\"calendarData111: C\",\"calendarData112: D\",\"calendarData113: E\",\"calendarData114: F\",\"calendarData115: G\",\"calendarData116: H\",\"calendarData117: A\",\"calendarData118: B\",\"calendarData119: C\",\"calendarData120: D\",\"calendarData121: E\",\"calendarData122: F\",\"calendarData123: G\",\"calendarData124: H\",\"calendarData125: A\",\"calendarData126: B\",\"calendarData127: C\",\"calendarData128: D\",\"calendarData129: E\",\"calendarData130: F\",\"calendarData131: G\",\"calendarData132: H\",\"calendarData133: A\",\"calendarData134: B\",\"calendarData135: C\",\"calendarData136: D\",\"calendarData137: E\",\"calendarData138: F\",\"calendarData139: G\",\"calendarData140: H\",\"calendarData141: A\",\"calendarData142: B\",\"calendarData143: C\",\"calendarData144: D\",\"calendarData145: E\",\"calendarData146: F\",\"calendarData147: G\",\"calendarData148: H\",\"calendarData149: A\",\"calendarData150: B\",\"calendarData151: C\",\"calendarData152: D\",\"calendarData153: E\",\"calendarData154: F\",\"calendarData155: G\",\"calendarData156: H\",\"calendarData157: A\",\"calendarData158: B\",\"calendarData159: C\",\"calendarData160: D\",\"calendarData161: E\",\"calendarData162: F\",\"calendarData163: G\",\"calendarData164: H\",\"calendarData165: A\",\"calendarData166: B\",\"calendarData167: C\",\"calendarData168: D\",\"calendarData169: E\",\"calendarData170: F\",\"calendarData171: G\",\"calendarData172: H\",\"calendarData173: A\",\"calendarData174: B\",\"calendarData175: C\",\"calendarData176: D\",\"calendarData177: E\",\"calendarData178: F\",\"calendarData179: G\",\"calendarData180: H\",\"calendarData181: A\",\"calendarData182: B\",\"calendarData183: C\",\"calendarData184: D\",\"calendarData185: E\",\"calendarData186: F\",\"calendarData187: G\",\"calendarData188: H\",\"calendarData189: A\",\"calendarData190: There is No School Today\",\"calendarData191: There is No School Today\",\"calendarData192: There is No School Today\",\"calendarData193: There is No School Today\",\"calendarData194: There is No School Today\",\"calendarData195: There is No School Today\",\"calendarData196: There is No School Today\",\"calendarData197: There is No School Today\",\"calendarData198: There is No School Today\",\"calendarData199: There is No School Today\",\"calendarData200: There is No School Today\",\"calendarData201: There is No School Today\",\"calendarData202: There is No School Today\",\"calendarData203: There is No School Today\",\"calendarData204: There is No School Today\",\"calendarData205: There is No School Today\",\"calendarData206: There is No School Today\",\"calendarData207: There is No School Today\",\"calendarData208: There is No School Today\",\"calendarData209: There is No School Today\",\"calendarData210: There is No School Today\",\"calendarData211: There is No School Today\",\"calendarData212: There is No School Today\",\"calendarData213: There is No School Today\",\"calendarData214: There is No School Today\",\"calendarData215: There is No School Today\",\"calendarData216: There is No School Today\",\"calendarData217: There is No School Today\",\"calendarData218: There is No School Today\",\"calendarData219: There is No School Today\",\"calendarData220: There is No School Today\",\"calendarData221: There is No School Today\",\"calendarData222: There is No School Today\",\"calendarData223: There is No School Today\",\"calendarData224: There is No School Today\",\"calendarData225: There is No School Today\",\"calendarData226: There is No School Today\",\"calendarData227: There is No School Today\",\"calendarData228: There is No School Today\",\"calendarData229: There is No School Today\",\"calendarData230: There is No School Today\",\"calendarData231: There is No School Today\",\"calendarData232: There is No School Today\",\"calendarData233: There is No School Today\",\"calendarData234: There is No School Today\",\"calendarData235: There is No School Today\",\"calendarData236: There is No School Today\",\"calendarData237: There is No School Today\",\"calendarData238: There is No School Today\",\"calendarData239: There is No School Today\",\"calendarData240: There is No School Today\",\"calendarData241: There is No School Today\",\"calendarData242: There is No School Today\",\"calendarData243: There is No School Today\",\"calendarData244: There is No School Today\",\"calendarData245: There is No School Today\",\"calendarData246: There is No School Today\",\"calendarData247: There is No School Today\",\"calendarData248: There is No School Today\",\"calendarData249: There is No School Today\",\"calendarData250: There is No School Today\",\"calendarData251: There is No School Today\",\"calendarData252: There is No School Today\",\"calendarData253: There is No School Today\",\"calendarData254: There is No School Today\",\"calendarData255: There is No School Today\",\"calendarData256: There is No School Today\",\"calendarData257: There is No School Today\",\"calendarData258: There is No School Today\",\"calendarData259: There is No School Today\",\"calendarData260: There is No School Today\",\"calendarData261: There is No School Today\",\"calendarData262: There is No School Today\",\"calendarData263: There is No School Today\",\"calendarData264: There is No School Today\",\"calendarData265: There is No School Today\",\"calendarData266: There is No School Today\",\"calendarData267: There is No School Today\",\"calendarData268: There is No School Today\",\"calendarData269: There is No School Today\",\"calendarData270: There is No School Today\",\"calendarData271: There is No School Today\",\"calendarData272: There is No School Today\",\"calendarData273: There is No School Today\",\"calendarData274: There is No School Today\",\"calendarData275: There is No School Today\",\"calendarData276: There is No School Today\",\"calendarData277: There is No School Today\",\"calendarData278: There is No School Today\",\"calendarData279: There is No School Today\",\"calendarData280: There is No School Today\",\"calendarData281: There is No School Today\",\"calendarData282: There is No School Today\",\"calendarData283: There is No School Today\",\"calendarData284: There is No School Today\",\"calendarData285: There is No School Today\",\"calendarData286: There is No School Today\",\"calendarData287: There is No School Today\",\"calendarData288: There is No School Today\",\"calendarData289: There is No School Today\",\"calendarData290: There is No School Today\",\"calendarData291: There is No School Today\",\"calendarData292: There is No School Today\",\"calendarData293: There is No School Today\",\"calendarData294: There is No School Today\",\"calendarData295: There is No School Today\",\"calendarData296: There is No School Today\",\"calendarData297: There is No School Today\",\"calendarData298: There is No School Today\",\"calendarData299: There is No School Today\",\"calendarData300: There is No School Today\",\"calendarData301: There is No School Today\",\"calendarData302: There is No School Today\",\"calendarData303: There is No School Today\",\"calendarData304: There is No School Today\",\"calendarData305: There is No School Today\",\"calendarData306: There is No School Today\",\"calendarData307: There is No School Today\",\"calendarData308: There is No School Today\",\"calendarData309: There is No School Today\",\"calendarData310: There is No School Today\",\"calendarData311: There is No School Today\",\"calendarData312: There is No School Today\",\"calendarData313: There is No School Today\",\"calendarData314: There is No School Today\",\"calendarData315: There is No School Today\",\"calendarData316: There is No School Today\",\"calendarData317: There is No School Today\",\"calendarData318: There is No School Today\",\"calendarData319: There is No School Today\",\"calendarData320: There is No School Today\",\"calendarData321: There is No School Today\",\"calendarData322: There is No School Today\",\"calendarData323: There is No School Today\",\"calendarData324: There is No School Today\",\"calendarData325: There is No School Today\",\"calendarData326: There is No School Today\",\"calendarData327: There is No School Today\",\"calendarData328: There is No School Today\",\"calendarData329: There is No School Today\",\"calendarData330: There is No School Today\",\"calendarData331: There is No School Today\",\"calendarData332: There is No School Today\",\"calendarData333: There is No School Today\",\"calendarData334: There is No School Today\",\"calendarData335: There is No School Today\",\"calendarData336: There is No School Today\",\"calendarData337: There is No School Today\",\"calendarData338: There is No School Today\",\"calendarData339: There is No School Today\",\"calendarData340: There is No School Today\",\"calendarData341: There is No School Today\",\"calendarData342: There is No School Today\",\"calendarData343: There is No School Today\",\"calendarData344: There is No School Today\",\"calendarData345: There is No School Today\",\"calendarData346: There is No School Today\",\"calendarData347: There is No School Today\",\"calendarData348: There is No School Today\",\"calendarData349: There is No School Today\",\"calendarData350: There is No School Today\",\"calendarData351: There is No School Today\",\"calendarData352: There is No School Today\",\"calendarData353: There is No School Today\",\"calendarData354: There is No School Today\",\"calendarData355: There is No School Today\",\"calendarData356: There is No School Today\",\"calendarData357: There is No School Today\",\"calendarData358: There is No School Today\",\"calendarData359: There is No School Today\",\"calendarData360: There is No School Today\",\"calendarData361: There is No School Today\",\"calendarData362: There is No School Today\",\"calendarData363: There is No School Today\",\"calendarData364: There is No School Today\"]}".getBytes(StandardCharsets.UTF_8));
            }

            File encKey = new File(Main.SAVE_FOLDER + File.separator + "Keys" + File.separator + "xor.key");
            if (!encKey.exists()) {
                Random r = new Random();
                int key = r.nextInt();
                readKey = String.valueOf(key);
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(Main.SAVE_FOLDER + File.separator + "Keys" + File.separator + "xor.key"));
                    writer.write(String.valueOf(key));
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(Main.SAVE_FOLDER + File.separator + "Keys" + File.separator + "xor.key"));
                    readKey = reader.readLine();
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            File saveFile = new File(Main.SAVE_FOLDER + File.separator + "BellTimes.txt");
            try {
                saveFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                URL website = new URL("https://dl.dropboxusercontent.com/s/a8e3qfwgbfbi0qc/BellTimes.txt?dl=0");
                InputStream in = website.openStream();
                if (!(in == null)) {
                    Files.copy(in, Paths.get(Main.SAVE_FOLDER + File.separator + "BellTimes.txt"), StandardCopyOption.REPLACE_EXISTING);
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                launch(args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {

        }
    }

    public static Object getMainPane() {
        return mainPane;
    }

    /**
     * This method is where JavaFX creates the UI and displays the window.
     */
    @Override
    public void start(Stage stage) throws Exception {

        mainPane = new MainPane(); //Create the mainPane (pane with all the content)

        Scene scene = new Scene(mainPane); //Put the mainPane into a scene
//        mainPane.setCache(true);
//        mainPane.setCacheShape(true);
//        mainPane.setCacheHint(CacheHint.SPEED);


        //Binds the size of the mainPane to be equal to the scene
        mainPane.prefWidthProperty().bind(scene.widthProperty());
        mainPane.prefHeightProperty().bind(scene.heightProperty());

        //Original (without HOME)
//        stage.setMinHeight(CalendarBox.CALENDAR_BOX_MIN_HEIGHT*5+198); //Set the minimum height of the window
//        stage.setMinWidth(CalendarBox.CALENDAR_BOX_MIN_WIDTH*7+90); //Set the minimum width of the window

        //WITH HOME
        stage.setMinHeight(CalendarBox.CALENDAR_BOX_MIN_HEIGHT * 5 + 198 + 110); //Set the minimum height of the window
        stage.setMinWidth(CalendarBox.CALENDAR_BOX_MIN_WIDTH * 7 + 90 + 280); //Set the minimum width of the window
        //Width: 1140
        //Height: 708


        stage.setTitle("WHS Planner"); //Set the title of the window
        stage.setScene(scene); //Set the window (stage) to display things in the scene

        scene.getStylesheets().add("/Calendar/MainUI.css");
        scene.getStylesheets().add("/UI/Main.css");

        stage.show(); //Display the window

    }

    /**
     * Exits the program fully when it is closed. Put save functions here!
     */
    @Override
    public void stop() {
//        mainPane.saveCalendar();
//        new File(Main.SAVE_FOLDER+ File.separator +"Keys/ipass.key").delete();

        System.exit(0);
    }
}