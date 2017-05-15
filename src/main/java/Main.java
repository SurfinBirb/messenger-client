import console.CommandLine;

/**
 * Created by SurfinBirb on 07.05.2017.
 */
public class Main {
    public static void main(String[] args) throws Exception{
    try {
        if (args[0].equals("console")) {
            new CommandLine().launch();
        }
    }catch (Exception e){
        e.printStackTrace();
    }
    }
}
