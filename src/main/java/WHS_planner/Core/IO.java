package WHS_planner.Core;

public class IO
{
    private JSON jsonApi;
    public IO(String fileName)
    {
        jsonApi = new JSON();
        jsonApi.loadFile(fileName);
    }
    public void writeScheduleArray(Object[] classLayout)
    {
        jsonApi.writeArray("Schedule", classLayout);

    }
    public Object[] readScheduleArray()
    {
        return (Object[]) jsonApi.readPair("@Schedule");
    }

    public void unload()
    {
        jsonApi.unloadFile();
    }
}
