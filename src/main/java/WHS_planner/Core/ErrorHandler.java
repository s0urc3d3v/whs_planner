package WHS_planner.Core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by matthewelbing on 17.09.16.
 */
public class ErrorHandler {
    public ErrorHandler(){

    }

    /**
     * Generates a formatted ascii output of an error writes it to the console and returns it.
     * @param e Exception to be logged
     * @return Log Text
     */
    private static String CreateErrorLog(Exception e){
        Logger genericErrorLogger = LoggerFactory.getLogger(ErrorHandler.class);
        StackTraceElement[] stackTrace = e.getStackTrace();
        for (StackTraceElement element : stackTrace){
            genericErrorLogger.warn("--- GENERIC ERROR ---");
            genericErrorLogger.debug("Stack Trace\n Line " + element.getLineNumber() + "\n class: " + element.getClassName() + "\nFile: " + element.getFileName() + "\nMethod: " + element.getMethodName());
            genericErrorLogger.info("\n");
            genericErrorLogger.debug(element.toString());
        }
        return genericErrorLogger.toString();
    }

    /**
     * Handles an error for which no other definition will conform
     * @param desc A description of the error
     * @param e The Exception object
     */
    public static void handleGenericError(String desc, Exception e){
        WHS_planner.CoreUI.ErrorPage.displayErrorWithMsg(desc); //TODO
        if (e != null) { //Passing an exception is optional
            ErrorHandler.CreateErrorLog(e);
        }
    }

    /**
     * Handles an IO error
     * @param e IOException object
     */
    public static void HandleIOError(Exception e){
        WHS_planner.CoreUI.ErrorPage.displayErrorWithMsg("A read or write error occurred");
        if (e != null) { //Passing an exception is optional
            ErrorHandler.CreateErrorLog(e);
        }
    }

    /**
     * Handles a thread error
     * @param e Thread Exception Object
     */
    public static void handleThreadError(Exception e){
        WHS_planner.CoreUI.ErrorPage.displayErrorWithMsg("Internal Error");
        if (e != null) { //Passing an exception is optional
            ErrorHandler.CreateErrorLog(e);
        }
    }
}
