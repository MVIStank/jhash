package jhash;

import java.io.*;

public class WriteObj {

    private FileOutputStream filestream;
    private ObjectOutputStream os;
    private File file;
    work_ip link;

    WriteObj(work_ip link, File file)
    {
        this.link = link;
        this.file = file;
    }

    public void run () throws  IOException {
        filestream = new FileOutputStream(file);
       os = new ObjectOutputStream(filestream);
       os.writeObject(link);
        os.close();
    }
}
