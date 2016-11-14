//package WHS_planner.Core;
//import org.slf4j.*;
///**
// * Created by matthewelbing on 17.09.16.
// */
//public class ErrorHandler {
//    public ErrorHandler(){
//
//    }
//
//    private static String CreateErrorLog(Exception e){
//        Logger genericErrorLogger = LoggerFactory.getLogger(ErrorHandler.class);
//        StackTraceElement[] stackTrace = e.getStackTrace();
//        for (StackTraceElement element : stackTrace){
//            genericErrorLogger.warn("--- GENERIC ERROR ---");
//            genericErrorLogger.debug("Stack Trace\n Line " + element.getLineNumber() +  "\n class: " + element.getClassName() + "\nFile: " + element.getFileName() + "\nMethod: " + element.getMethodName());
//            genericErrorLogger.info("\n");
//            genericErrorLogger.debug(element.toString());
//        }
//        return genericErrorLogger.toString();
//    }
//
//    public static void handleGenericError(String desc, Exception e){
//        WHS_planner.CoreUI.Error_Handler.displayErrorWithMsg(desc); //TODO
//        if (e != null) { //Passing an exception is optional
//            ErrorHandler.CreateErrorLog(e);
//        }
//    }
//
//    public static void HandleIOError(Exception e){
//        WHS_planner.CoreUI.Error_Handler.displayErrorWithMsg("A read or write error occurred");
//        if (e != null) { //Passing an exception is optional
//            ErrorHandler.CreateErrorLog(e);
//        }
//    }
//    public static void handleThreadError(Exception e){
//        WHS_planner.CoreUI.Error_Handler.displayErrorWithMsg("Internal Error");
//        if (e != null) { //Passing an exception is optional
//            ErrorHandler.CreateErrorLog(e);
//        }
//    }
//}
