package jhash;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author vladimir
 */
public class work_ip {
         
private  int [] ip;
private  int [] mask;


  
 private int res; // subnet network
 private int pos;
    public work_ip()
    {
      //  int [] ip1 =this.ip;
      //  int [] mask1 =this.ip;
          int [] ip=new int[4];
          int [] mask= new int[4];
           this.ip=ip;
           this.mask=mask;
         
     //int [] ip ={192,168,20,20};
     //int [] mask={255,252,0,0};
           set_ip();     
    }
            
    public void set_ip()
    {
       int[] ip1 = {15,190,7,7};
        int []mask1 = {255,255,255,0};
        this.ip=ip1;
        this.mask=mask1;
////////////////////Temp////////////////////////////////// 
        System.out.print("Подсеть: ");
        for(int i=0;i<ip.length;i++)
        {
            System.out.print(ip[i]+".");
        }
       System.out.println( "");
       
       System.out.print("Маска: ");
        for(int i=0;i<ip.length;i++)
        {
            System.out.print(mask[i]+".");
        }
       System.out.println( "");
///////////////////////////////////////////////////////////
    }

     public void go () {
        
       found_network();
        build_network();
        build_broadcast();
     System.out.println("") ;  
        System.out.println("================================================================================");
        print();
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
         // System.out.println("октет" + pos); 
         return pos;
        }//fucntion
     
     private int magic_number() 
     {   
         int y=found_octet();
         y=256-mask[y];
         // System.out.println("магическое число" + y); 
         return y;
     }
     
     private void found_network()
     {   
         int ips=magic_number();
         int masks=found_octet ();
         int res=0;
         System.out.println("шаг "+ips);
           System.out.println("октет "+masks);
         for (int i=0;i<=ip[masks];i=i+ips)
         {   
//             if(ip[masks]==0)
//             {
//             System.out.println("Результат  " +i);
//             }
              if (i+ips>ip[masks])
             {
                 System.out.println("Результат  " +res);
                  res=i;
                  this.res=res;
             }
//             else { 
//                 System.out.println("ХНЯ  " +i);   
             }
         }
     private int[] build_network()
     {      int res=this.res;
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
     System.out.print("Network: ");
    
     for(int i=0;i<ip.length;i++)
       {
            System.out.print(mas[i]+".");
       }
      System.out.println(" ");
     return mas;
     }
    
     private int[] build_broadcast ()
     {   int magic_number;
         int[] network=new int [4];
         int [] broadcast =new int [4];
         network=build_network();
         for (int i=0;i<mask.length;i++)
         {
           if (mask[i]==255){
               broadcast[i]=ip[i];
           }else  if
                   (mask[i]==0){
             broadcast[i]=255; 
         }
         else 
          { 
             broadcast[i]= (256-mask[i])+(network[pos])-1;
         }//else
         
         }//for
         System.out.print("Broadcast: ");
    
     for(int i=0;i<broadcast.length;i++)
       {
            System.out.print(broadcast[i]+".");
       }
        
     return broadcast;
     }
 
     public void print ()    
     {    
         int[] network=new int [4];
         int [] broadcast =new int [4];  
         network=build_network();
         broadcast=build_broadcast();
          long startTime;
          long endTime;
          long countIP=0;
        // startTime=System.nanoTime();
                if (pos==3)
                 { 
                     startTime=System.nanoTime();
                     for (int i=0;i<255;i++)
                       {
                           System.out.println(network[0]+"."+network[1]+"."+network[2]+"."+i);
                           countIP++;
                       }
                          endTime=System.nanoTime()-startTime;
                          System.out.println("Время выполнения: " +(TimeUnit.SECONDS.convert(endTime, TimeUnit.NANOSECONDS))+" сек");
                          System.out.println("Количество IP адресов: " +countIP);
                 }//pos=3
                if (pos==2)
                 {   
                    int beg=network[pos];
                    startTime=System.nanoTime();
                      while(beg!=broadcast[2]) 
                         {   
                            for(int j=0;j<=255;j++)
                              {
                                 System.out.println(network[0]+"."+network[1]+"."+beg+"."+j);
                                 countIP++;
                              }
                                 beg ++;
                         }
                      endTime=System.nanoTime()-startTime;
                      System.out.println("Время выполнения: " +(TimeUnit.SECONDS.convert(endTime, TimeUnit.NANOSECONDS))+" сек");
                      System.out.println("Количество IP адресов: " +countIP);
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
                                       System.out.println(network[0]+"."+beg1+"."+beg2+"."+i);
                                       countIP++;
                                      }
                                beg2++;
                               }    
                      beg1++;
                      beg2=0;
                     }
                     endTime=System.nanoTime()-startTime;
                     System.out.println("Время выполнения: " +(TimeUnit.SECONDS.convert(endTime, TimeUnit.NANOSECONDS))+" сек");
                     System.out.println("Количество IP адресов: " +countIP);
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
                                     System.out.println(beg0+"."+beg1+"."+beg2+"."+i);
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
                    System.out.println("Время выполнения: " +(TimeUnit.SECONDS.convert(endTime, TimeUnit.NANOSECONDS))+" сек");  
                    System.out.println("Количество IP адресов: " +countIP);
                 }//pos=1
     }// end pint()
}//class
