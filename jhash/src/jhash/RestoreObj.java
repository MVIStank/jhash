package jhash;

import java.io.*;


public class RestoreObj {

    private final File FR;

    RestoreObj (File FR){
        this.FR= FR;
    }
    public WorkIpv4 run () throws IOException, ClassNotFoundException {
        FileInputStream filestream = new FileInputStream(FR);
        ObjectInputStream os = new ObjectInputStream(filestream);
        Object one = os.readObject();
        return (WorkIpv4) one;
    }
}
