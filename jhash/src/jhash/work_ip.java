package jhash;

import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public class work_ip 
{    
public int [] ip;
public int [] mask;
public int task_worker;
public boolean task_worker_status=true;
TreeMap<Integer, String> treemap = new TreeMap<Integer, String>();
private int res; // subnet network
private int pos;

    
public work_ip()
 {
   int [] ip=new int[4];
   int [] mask= new int[4];
   this.ip=ip;
   this.mask=mask;    
 }
public void set_mask(int mask_int)
 {   
        switch(mask_int)
        {
            case 0: 
                     int []mask1 = {255,255,255,0};
                     this.mask=mask1;                    
                     break;
            case 1: 
                     int []mask2 = {128,0,0,0};
                     this.mask=mask2;
                     break;
            case 2: 
                     int []mask3 = {192,0,0,0};
                     this.mask=mask3;
                     break; 
            case 3: 
                     int []mask4 = {224,0,0,0};
                     this.mask=mask4;
                     break; 
            case 4: 
                    int []mask5 = {240,0,0,0};
                    this.mask=mask5;
                    break;  
            case 5: 
                    int []mask6 = {248,0,0,0};
                    this.mask=mask6;
                    break;  
            case 6: 
                    int []mask7 = {252,0,0,0};
                    this.mask=mask7;
                    break;  
            case 7: 
                    int []mask8 = {254,0,0,0};
                    this.mask=mask8;
                    break;  
            case 8: 
                    int []mask9 = {255,0,0,0};
                    this.mask=mask9;
                    break;
            case 9: 
                    int []mask10 = {255,128,0,0};
                    this.mask=mask10;
                    break;
            case 10: 
                    int []mask11 = {255,192,0,0};
                    this.mask=mask11;
                    break;
            case 11: 
                    int []mask12 = {255,224,0,0};
                    this.mask=mask12;
                    break;
            case 12: 
                    int []mask13 = {255,240,0,0};
                    this.mask=mask13;
                    break;        
            case 13: 
                    int []mask14 = {255,248,0,0};
                    this.mask=mask14;
                    break;
            case 14: 
                    int []mask15 = {255,252,0,0};
                    this.mask=mask15;
                    break;
            case 15: 
                    int []mask16 = {255,254,0,0};
                    this.mask=mask16;
                    break;
            case 16: 
                    int []mask17 = {255,255,0,0};
                    this.mask=mask17;
                    break;
            case 17: 
                    int []mask18 = {255,255,128,0};
                    this.mask=mask18;
                    break;
            case 18: 
                    int []mask19 = {255,255,192,0};
                    this.mask=mask19;
                    break;
             case 19: 
                    int []mask20 = {255,255,224,0};
                    this.mask=mask20;
                    break;
             case 20: 
                    int []mask21 = {255,255,240,0};
                    this.mask=mask21;
                    break;
             case 21: 
                    int []mask22 = {255,255,248,0};
                    this.mask=mask22;
                    break;
             case 22: 
                    int []mask23 = {255,255,252,0};
                    this.mask=mask23;
                    break;
            case 23: 
                    int []mask24 = {255,255,254,0};
                    this.mask=mask24;
                    break;
            case 24: 
                    int []mask25 = {255,255,255,0};
                    this.mask=mask25;
                    break;
            case 25: 
                    int []mask26 = {255,255,255,128};
                    this.mask=mask26;
                    break;
            case 26: 
                    int []mask27 = {255,255,255,192};
                    this.mask=mask27;
                    break;
            case 27: 
                    int []mask28 = {255,255,255,224};
                    this.mask=mask28;
                    break;
            case 28: 
                    int []mask29 = {255,255,255,240};
                    this.mask=mask29;
                    break;
            case 29: 
                    int []mask30 = {255,255,255,248};
                    this.mask=mask30;
                    break;
            case 30: 
                    int []mask31 = {255,255,255,252};
                    this.mask=mask31;
                    break;
            case 31: 
                    int []mask32 = {255,255,255,254};
                    this.mask=mask32;
                    break;
            case 32: 
                    int []mask33 = {255,255,255,255};
                    this.mask=mask33;
                    break;               
        }
    }
    
public void set_ip( int []  subnet_network)
    {
       this.ip=subnet_network;
    }
 
private int found_octet () //found magic octet
        {  int pos=0;
            for (int i=0; i<ip.length;i++)
             {
                if (this.mask[i]!=255)
                { //int y =256 - ip[i];
                  pos=i;
                  this.pos=pos;
                 break;
                } 
             }
                  return pos;
        }//fucntion
     
private int magic_number() 
     {   
         int y=found_octet();
         y=256-mask[y];
         return y;
     }
     
private void found_network()
     {   
         int ips=magic_number();
         int masks=found_octet ();
         int res=0;
         for (int i=0;i<=ip[masks];i=i+ips)
         {   
              if (i+ips>ip[masks])
             {
                  res=i;
                  this.res=res;
             }
         }
     }
public int[] build_network()
     {   
         found_network();
         int res=this.res;
         int pos=this.pos;
         int []mas =new int [4];
          for(int i=0;i<ip.length;i++)
           {
             mas[i]=ip[i];
             if (i==pos)
             { 
                 mas[i]=res;  
             }
           }
          for (int i=0;i<ip.length;i++)
           {    
              if (i>pos)
              {mas[i]=0;}
           }
     return mas;
     }
    
public int[] build_broadcast ()
     {   int magic_number;
         int[] network=new int [4];
         int [] broadcast =new int [4];
         network=build_network();
         for (int i=0;i<mask.length;i++)
         {
           if (mask[i]==255)
            {
               broadcast[i]=ip[i];
            }
           else 
               if  (mask[i]==0)
                {
                  broadcast[i]=255; 
                }
                 else 
                 { 
                   broadcast[i]= (256-mask[i])+(network[pos])-1;
                 }//else
         }//for
     return broadcast;
     }
 
public void print ()    
     {    
         int[] network=new int [4];
         int [] broadcast =new int [4];  
         network=build_network();
         broadcast=build_broadcast();
         long startTime=0;
         long endTime=0;
         long countIP=0;
         double seconds=0;
         String Str;
         int count_keys_hashtable=0;
        // startTime=System.nanoTime();
                if (pos==3)
                 { 
                     startTime=System.nanoTime();
                     for (int i=network[pos];i<=broadcast[pos];i++)
                       {   
                          // System.out.println(network[0]+"."+network[1]+"."+network[2]+"."+i);
                           Str=network[0]+"."+network[1]+"."+network[2]+"."+i;
                           treemap.put(i, Str);
                           if (countIP==125)
                            {
                                this.task_worker=50;
                            }
                           if (countIP==250)
                            {   
                                this.task_worker=100;
                                 task_worker_status=false;
                            }
                           countIP++;
                       }
                          endTime=System.nanoTime()-startTime;
                          seconds = (double)endTime / 1000000000.0;
                          //System.out.println("Time: " +seconds +" sec");
                         // System.out.println("Count IP: " +countIP);
                 }//pos=3
                if (pos==2)
                 {   
                    int beg=network[pos];
                    startTime=System.nanoTime();
                      while(beg<=broadcast[2]) 
                         {   
                            for(int j=0;j<=255;j++)
                              {
                                 //System.out.println(network[0]+"."+network[1]+"."+beg+"."+j);
                                   Str= network[0]+"."+network[1]+"."+beg+"."+j;
                                   treemap.put(count_keys_hashtable, Str);
                                  if (countIP==125)
                                     {
                                        this.task_worker=50;
                                     }
                                 countIP++;
                                 count_keys_hashtable++;
                              }
                                 beg ++;
                                 if (beg==broadcast[2])
                                     {
                                        this.task_worker=100;
                                     }
                         }
                      endTime=System.nanoTime()-startTime;
                      seconds = (double)endTime / 1000000000.0;
                     // System.out.println("Time: " +seconds +" sec");
                    //  System.out.println("Count IP: " +countIP);
                      task_worker_status=false;
                 }//pos=2
                if(pos==1)
                 {
                     int beg1=network[pos];
                     int beg2=0;
                     startTime=System.nanoTime();
                       while(beg1<=broadcast[pos]) 
                         {  //  beg1;
                            while(beg2<=255)
                              {     //beg2
                                   for(int i=1;i<=255;i++)
                                      {      
                                       //System.out.println(network[0]+"."+beg1+"."+beg2+"."+i);
                                         Str=network[0]+"."+beg1+"."+beg2+"."+i;
                                         treemap.put(count_keys_hashtable,Str);
                                         countIP++;
                                         count_keys_hashtable++;
                                      }
                                beg2++;
                               }    
                      beg1++;
                      beg2=0;
                     }
                     endTime=System.nanoTime()-startTime;
                     seconds = (double)endTime / 1000000000.0;
                     //System.out.println("Time: " +seconds +" sec");
                    // System.out.println("Count IP: " +countIP);
                 }//pos=1
                if(pos==0)
                 {  int beg0=network[pos];
                    int beg1=0;
                    int beg2=0;
                    startTime=System.nanoTime();
                      while(beg0<=broadcast[0])
                       {
                          while(beg1<=255)
                          {
                             while(beg2<=255)
                             {
                                 for(int i=0;i<255;i++)
                                 {
                                     //System.out.println(beg0+"."+beg1+"."+beg2+"."+i);
                                     Str=beg0+"."+beg1+"."+beg2+"."+i;
                                     treemap.put(count_keys_hashtable, Str);
                                     count_keys_hashtable++;
                                     countIP++;
                                 }
                               beg2++;  
                             }
                            beg1++;
                            beg2=0;
                          }
                        beg0++;
                        beg1=0;
                        beg2=0;
                       }
                    endTime=System.nanoTime()-startTime;
                    seconds = (double)endTime / 1000000000.0;
                   // System.out.println("Time: " +seconds +" sec"); 
                    //System.out.println("Count IP: " +countIP);
                    task_worker_status=false;   
                 }//pos=1
                   }// end print()
}//class
