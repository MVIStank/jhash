package jhash;

import java.util.ArrayList;
import java.util.Iterator;

public class WorkIpv6 {
/*
 ArrayList<String> ipv6 = new ArrayList<>();
         ipv6.add("fe80");
     ipv6.add("0000");
         ipv6.add("0000");
         ipv6.add("0000");
      ipv6.add("0000");
         ipv6.add("0000");
         ipv6.add("0000");
       ipv6.add("000c");
        Gerbil dd = new Gerbil(ipv6,127);
       // dd.buildSubnetIpv6();
        System.out.println(dd.getSubnet());
       // System.out.println(dd.getPrefix());
        System.out.println(dd.getFirstAddrIpv6());
        System.out.println(dd.getLastAddrIpv6());
 */

        private static final int BIT_OCTET_COUNT = 16;

        private ArrayList<String> ipv6Pre; // received subnet of ipv6 addr from Controller
        private ArrayList<Integer> ipv6Bin; // bin of ipv6;
        private ArrayList<String> ipv6String;
        private int prefix; //received prefix   from Controller
        private String subnet; // calculated network

         WorkIpv6(ArrayList<String> ipv6Pre, int prefix) {
                this.ipv6Pre = ipv6Pre;
                this.prefix = prefix;
                start();
        }

        //   public function returned subnet of ipv6 addr
        public String getSubnet() {
                ipv6String = arrayToStringBuilderPrepare(ipv6Bin);
                subnet = viewOfIv6(ipv6String);
                return subnet;
        }
        // return currently prefix
        public String getPrefix (){
                return "" + prefix;
        }
        // return first address of ipv6 subnet
        public String getFirstAddrIpv6() {
                ArrayList<Integer> tmp = new ArrayList<>();
                StringBuilder str= new StringBuilder();
                tmp = buildFirstAdrIpv6();
                for (Integer integer : tmp) {
                        str.append(Integer.toHexString(integer));
                        str.append(":");
                }
                return str.toString();
        }
        //
        public String getLastAddrIpv6() {
                ArrayList<Integer> tmp;
                tmp = buildLastAddrIpv6();
                return viewOfIv6(arrayToStringBuilderPrepare(tmp));
        }
        //
       private ArrayList<Integer> buildSubnetIpv6 () {
                ArrayList<Integer> tmp;
                tmp = buildBinaryIpv6();
                for (int i = 0; i < tmp.size(); i++) {
                        if (i >= prefix) {
                                tmp.set(i, 0);
                        }
                }
                return tmp;
        }
        // initialized function
        private void start(){
                ipv6Bin = buildSubnetIpv6();
        }
        /*
        Input:
        Output: Integer array with  ipv6 representative of dex  [5200,0,200]
         */
      private  ArrayList<Integer> buildFirstAdrIpv6 () {
                ArrayList<String> sr = arrayToStringBuilderPrepare(ipv6Bin);
                // sr = [f,e,8,0]
                ArrayList<Integer> tmp1 = new ArrayList<>();
                StringBuilder str = new StringBuilder();
                ArrayList<String> buffer = new ArrayList<>();
                int count = 0;
                int k = 0;
                boolean tie = true;
                while (tie) {
                        while (k < sr.size()) {
                                if (count == 4) {
                                        break;
                                } else {
                                        str.append(sr.get(k));
                                        buffer.add(sr.get(k));
                                        count++;
                                        k++;
                                }
                        }
                        int m = Integer.parseInt(str.toString(), 16);
                        tmp1.add(m);
                        count = 0;
                        str = new StringBuilder();
                        if ( k == sr.size()) {
                                tie = false;
                        }
                }
                for (int i = tmp1.size(); i >= 0; i--) {
                        int h = tmp1.get((i) - 1);
                        if (h + 1 > 65536) {
                                tmp1.set(i - 1, 0);
                        } else {
                                tmp1.set(i - 1, h + 1);
                                break;
                        }
                }
                return tmp1;
        }
        ////
        private  ArrayList<Integer> buildLastAddrIpv6 (){
                ArrayList <Integer> tmp;
                tmp = buildBinaryIpv6();
                for (int i = 0; i < tmp.size(); i++) {
                        if ( i >= prefix){
                                tmp.set(i, 1);
                        }
                }
                return tmp;
        }
        /*
         Preparation function
        Building array of fex ipv6 addresses. Converting binary to hex
         Input: array binary representative  Ipv6 [1,1,1,1 ... 0,0,0,0]
         Return: string array with hex of Ipv6    [   'f', ...  '0'   ]
         */
        private ArrayList<String> arrayToStringBuilderPrepare(ArrayList <Integer> arr){
                ArrayList <String>  ipv6Prepare = new ArrayList<>();
                StringBuilder str = new StringBuilder();
                int count = 0;
                for ( int i = 0; i < arr.size()-3; i ++) {
                        while (count < 4) {
                                str.append(arr.get(i));
                                count ++;
                                i++;
                        }
                        int dec = Integer.parseInt(str.toString(), 2);
                        String hexString = Integer.toHexString(dec);
                        ipv6Prepare.add(hexString);
                        str = new StringBuilder();
                        count = 0;
                        i--;
                }
                return  ipv6Prepare;
        }
        /*
        Input: array of hex Ipv6
        Return: representative of Ipv6 as String
         */
        private String viewOfIv6 ( ArrayList<String> arr){
                StringBuilder ipv6Representive = new StringBuilder();
                ArrayList<String> buffer = new ArrayList<>();
                ArrayList<String> fin = new ArrayList<>();
                int count = 0;
                int k = 0;
                boolean tie = true;
                while (tie) {
                        while (k < arr.size()){
                                if (count == 4) {
                                        break;
                                }
                                else {
                                        buffer.add(arr.get(k));
                                        count++;
                                        k++;
                                }
                        }
                        buffer.add(":");
                        Iterator<String> itr = buffer.iterator();
                        while (itr.hasNext()){
                                String rtu = itr.next();
                                if ( rtu.equals("0")){
                                        itr.remove();
                                }
                                else break;
                        }
                        if (buffer.size() == 1){
                                buffer.add(0, "0");
                        }
                        fin.addAll(buffer);
                        buffer = new ArrayList<>();
                        count = 0;
                        if ( k == arr.size()) {
                                tie = false;
                        }
                }
                fin.remove(fin.size() - 1);
                for (String s : fin) {
                        ipv6Representive.append(s);
                }
                return ipv6Representive.toString();
        }

        //convert hex to binary for array
        // Input:  String 'String'
        // Return: [1,0,1,1]
        private  ArrayList <Integer>  hexToBinaryOctet(String ipv6Oct) {
                // int count = 0;
                ArrayList <Integer> tmp = new ArrayList();
                int t = (Integer.parseInt(ipv6Oct, 16)); // convert hex to int. For example, fe80 -> 65152
                while ( t > 0 ){
                        int of  =  t % 2;
                        tmp.add(0,of);
                        t = t /2;
                }
                tmp = addZeroPadding(tmp);
                return tmp;
        }
        //add zero padding
        private ArrayList<Integer> addZeroPadding(ArrayList<Integer> sr) {
                while (sr.size() != BIT_OCTET_COUNT) {
                        sr.add(0,0);
                }
                return sr;
        }
        //
        private ArrayList <Integer> buildBinaryIpv6 ( ){
                ArrayList <Integer> tmp;

                ArrayList <Integer> tmp2 = new ArrayList ();
                for (String s : ipv6Pre) {
                        tmp = hexToBinaryOctet(s);
                        tmp2.addAll(tmp);
                }
                return tmp2;
        }
}

