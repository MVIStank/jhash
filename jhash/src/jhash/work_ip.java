package jhash;

import java.io.Serializable;
import java.util.TreeMap;


public final class work_ip  implements Serializable
{    
private int [] ip;
private int [] mask;
private final  TreeMap<Long, String> treemap = new TreeMap<>();
private int res; // subnet network
private int pos;
private int maskShort;
static final long serialVersionUID = 10L;

public work_ip()
    {
     this.ip = new int[4];
     this.mask = new int[4]; }

 public int [] getMask (){ return this.mask;}

 public int [] getIp (){return this.ip;}

 public void set_ip( int []  subnet_network) { this.ip = subnet_network; }

 public int getMaskShort(){ return this.maskShort; }

public TreeMap<Long, String> getMap (){ return this.treemap; }

public void set_mask(int mask_int)
 {
     maskShort = mask_int;
        switch(mask_int)
        {
            case 0: 

                     this.mask = new int[] {0, 0, 0, 0};
                     break;
            case 1:
                     this.mask = new int [] {128,0,0,0};
                     break;
            case 2:
                     this.mask = new int[] {192,0,0,0};
                     break; 
            case 3:
                     this.mask = new int[] {224,0,0,0};
                     break; 
            case 4:
                    this.mask = new int[] {240,0,0,0};
                    break;  
            case 5:
                    this.mask = new int[] {248,0,0,0};
                    break;
            case 6:
                    this.mask = new int[] {252,0,0,0};
                    break;
            case 7:
                    this.mask = new int[]  {254,0,0,0};
                    break;  
            case 8:
                    this.mask = new int[] {255,0,0,0};
                    break;
            case 9:
                    this.mask = new int[]  {255,128,0,0};
                    break;
            case 10:
                    this.mask = new int[]  {255,192,0,0};
                    break;
            case 11:
                    this.mask = new int[] {255,224,0,0};
                    break;
            case 12:
                    this.mask = new int[] {255,240,0,0};
                    break;        
            case 13:
                    this.mask = new int[] {255,248,0,0};
                    break;
            case 14:
                    this.mask = new int[]  {255,252,0,0};
                    break;
            case 15:
                    this.mask = new int[] {255,254,0,0};
                    break;
            case 16:
                    this.mask = new int[] {255,255,0,0};
                    break;
            case 17:
                    this.mask = new int[]{255,255,128,0};
                    break;
            case 18:
                    this.mask = new int[] {255,255,192,0};
                    break;
             case 19:
                    this.mask = new int[] {255,255,224,0};
                    break;
             case 20:
                    this.mask = new int[] {255,255,240,0};
                    break;
             case 21:
                    this.mask = new int[] {255,255,248,0};
                    break;
             case 22:
                    this.mask = new int[] {255,255,252,0};
                    break;
            case 23:
                    this.mask = new int[] {255,255,254,0};
                    break;
            case 24:
                    this.mask = new int[] {255,255,255,0};
                    break;
            case 25:
                    this.mask = new int[]  {255,255,255,128};
                    break;
            case 26:
                    this.mask = new int[] {255,255,255,192};
                    break;
            case 27:
                    this.mask = new int[] {255,255,255,224};
                    break;
            case 28:
                    this.mask = new int[] {255,255,255,240};
                    break;
            case 29:
                    this.mask = new int[] {255,255,255,248};
                    break;
            case 30:
                    this.mask = new int[] {255,255,255,252};
                    break;
            case 31:
                    this.mask = new int[] {255,255,255,254};
                    break;
            case 32:
                    this.mask =  new int [] {255,255,255,255};
                    break;               
        }
    }
private int found_octet () //found magic octet
        {  int pos = 3;
            for (int i = 0; i < ip.length; i++)
             {
                if (this.mask[i] != 255)
                { //int y =256 - ip[i];
                  pos = i;
                  this.pos = pos;
                 break;
                }
             }
                  return pos;
        }
     
private int magic_number() {
         int y = found_octet();
         y = 256 - mask[y];
         return y;
     }
     
private void found_network() {
         int ips = magic_number();
         int masks = found_octet ();
         int res;
         for (int i = 0; i <= ip[masks]; i = i + ips)
         {   
              if (i + ips > ip[masks])
             {
                  res = i;
                  this.res = res;
             }
         }
     }
public int[] build_network() {
         found_network();
         int res = this.res;
         int pos = this.pos;
         int []mas = new int [4];
          for(int i = 0; i < ip.length; i++) {
             mas[i] = ip[i];
             if (i == pos)
                 mas[i] = res;
           }
          for (int i = 0; i < ip.length; i++) {
              if (i > pos)
              mas[i] = 0;
           }
     return mas;
     }

public int[] build_broadcast () {
   // int magic_number;
    int[] network = build_network();
    int [] broadcast = new int [4];

    for (int i = 0; i < mask.length; i++) {
         switch (mask[i]) {
             case 255:
                 broadcast[i] = ip[i];
                 break;
             case 0:
                 broadcast[i] = 255;
                 break;
             default:
                 broadcast[i] = (256 - mask[i]) + (network[pos]) - 1;
                 break; //else
         }
         }//for
     return broadcast;
     }
 
public void print () {
       if(!treemap.isEmpty()) {
            treemap.clear();
         }
            int[] network = build_network();
            int [] broadcast = build_broadcast();
            String Str;
            long count = 0;
            long count_keys_hashtable = 0;
                if (pos == 3) {
                     for (long i = network[pos]; i <= broadcast[pos]; i++) {
                          // System.out.println(network[0]+"."+network[1]+"."+network[2]+"."+i);
                           Str = network[0] + "." + network[1] + "." + network[2] + "." + i;
                           treemap.put(i, Str);
                           count ++;
                       }
                 }//pos=3
                if (pos == 2) {
                    int beg = network[pos];
                      while(beg <= broadcast[2]) {
                            for(int j = 0; j <= 255; j++) {
                                 //System.out.println(network[0]+"."+network[1]+"."+beg+"."+j);
                                   Str= network[0] + "." + network[1] + "." + beg + "."+j;
                                   treemap.put(count_keys_hashtable, Str);
                                 count_keys_hashtable++;
                                 count ++;
                              }
                                 beg++;
                         }
                 }//pos=2
                if(pos == 1) {
                    System.out.println("pos=1");
                     int beg1 = network[pos];
                     int beg2 = 0;
                       while(beg1 <= broadcast[pos])
                         {  //  beg1;
                            while(beg2 <= 255)
                              {     //beg2
                                   for(int i = 1;i <= 255;i++)
                                      {      
                                       //System.out.println(network[0]+"."+beg1+"."+beg2+"."+i);
                                         Str=network[0] + "." + beg1 + "." + beg2 + "." +i;
                                         treemap.put(count_keys_hashtable,Str);
                                         count_keys_hashtable++;
                                         count ++;
                                      }
                                beg2++;
                               }    
                      beg1++;
                      beg2 = 0;
                     }
                 }//pos=1
                if(pos == 0)
                 {  System.out.println("pos=0");
                     int beg0 = network[pos];
                    int beg1 = 0;
                    int beg2 = 0;
                      while(beg0 <= broadcast[0])
                       {
                          while(beg1 <= 255)
                          {
                             while(beg2 <= 255)
                             {
                                 for(int i = 0; i < 255; i++)
                                 {
                                     //System.out.println(beg0+"."+beg1+"."+beg2+"."+i);
                                     Str = beg0 +"." + beg1 + "." + beg2 +"." +i;
                                     treemap.put(count_keys_hashtable, Str);
                                     count_keys_hashtable++;
                                     count ++;
                                 }
                               beg2++;  
                             }
                            beg1++;
                            beg2 = 0;
                          }
                        beg0++;
                        beg1 = 0;
                        beg2 = 0;
                       }
                 }//pos=1

               treemap.put(count,"  " );
                treemap.put(count,"Summary address: " + count );
                   }// end print()
}//class
