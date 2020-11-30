
package jhash;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.TreeMap;

public class FileSave {
    
    
     public void export_file (File file, TreeMap<Long, String> treemap) throws  IOException{
         
         FileWriter fileReader = new FileWriter(file);
         BufferedWriter bufferedWriter = new BufferedWriter(fileReader);     
        Set<Long> keys = treemap.keySet();
             for(Long key: keys)
              {
                 bufferedWriter.write(treemap.get(key) + "\n");
              }
             bufferedWriter.close();
     }
}
