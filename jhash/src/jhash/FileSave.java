
package jhash;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.TreeMap;

public class FileSave {
    
    
     public int export_file (File file, TreeMap<Integer, String> treemap) throws  IOException{
         
         FileWriter fileReader = new FileWriter(file);
         BufferedWriter bufferedWriter = new BufferedWriter(fileReader);     
        Set<Integer> keys = treemap.keySet();
             for(Integer key: keys)
              {
                 bufferedWriter.write(treemap.get(key) + "\n");
              }
             bufferedWriter.close();
         return -1;
     }
}
