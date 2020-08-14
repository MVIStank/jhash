package jhash;

import java.io.*;


public class RestoreObj {

    private FileInputStream filestream;
    private ObjectInputStream os;
    private File FR;

    RestoreObj (File FR){
        this.FR= FR;
    }
    public work_ip run () throws IOException, ClassNotFoundException {
        filestream = new FileInputStream(FR);
        os = new ObjectInputStream(filestream);
        Object one = os.readObject();
        return (work_ip) one;
    }
}
