package WHS_planner.Core;


import com.apple.eawt.AppEvent;
import com.apple.eawt.Application;
import com.apple.eawt.OpenFilesHandler;

import java.io.File;
import java.io.IOException;

/**
 * Created by matthewelbing on 12.10.16.
 */
public class MeetingFileHandler {
    public MeetingFileHandler() {

    }

    public static void configFileHandler(){
        if (System.getProperty("os.name").toLowerCase().contains("os x") || System.getProperty("os.name").toLowerCase().contains("mac")){
            Application application = Application.getApplication();
            application.setOpenFileHandler(new OpenFilesHandler() {
                @Override
                public void openFiles(AppEvent.OpenFilesEvent openFilesEvent) {
                    for (File f : openFilesEvent.getFiles()){
                        if (f.getName().contains("whsplannermeeting")){
                            //f will be meeting.json.whsplannermeeting
                            new File("src" + File.separator + "main" + File.separator + "resources" + File.separator + "Core");
                            f.renameTo(new File("src" + File.separator + "main" + File.separator + "resources" + File.separator + "Core" + f.getName()));


                        }
                        else {
                            ErrorHandler.handleGenericError("file was not a meeting", null);
                        }
                    }
                }
            });
        }
    }

}
