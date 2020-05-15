/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Capstone3;
import static Capstone3.LLH.conflict_matrix;
import static Capstone3.heuristic2.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
/**
 *
 * @author MSI-PC
 */
public class Main {
        public static void main(String[] args) throws IOException {
                
                
        
                System.out.print("-----------------------------------------------\n");
                System.out.print("Berikut adalah daftar 13 kode dataset : \n");
                
                //bikin matrix [],[] --> baris,kolom
		String kode[][] = {
                    {"car-f-92", "CAR92"},     //baris 1 kolom 1 = car-f-92  ,  baris 1 kolom 2 = CAR92
                    {"car-s-91", "CAR91"},     //baris 2 kolom 1 = car-s-91  ,  baris 2 kolom 2 = CAR91
                    {"ear-f-83", "EAR83"},     //dst
                    {"hec-s-92", "HEC92"}, 
                    {"kfu-s-93", "KFU93"}, 
                    {"lse-f-91", "LSE91"}, 
                    {"pur-s-93", "PUR93"}, 
                    {"rye-s-93", "RYE92"}, 
                    {"sta-f-83", "STA83"},
                    {"tre-s-92", "TRE92"}, 
                    {"uta-s-92", "UTA92"}, 
                    {"ute-s-92", "UTE92"}, 
                    {"yor-f-83", "YOR83"}
                };

                //iterasi buat nampilin list kode dataset
		for(int i=0; i<kode.length;i++)                         //kode.length --> jumlah karakter pada variabel kode
			System.out.println(i+1 + "  "+ kode[i][1]);     //kode[i][0] --> matrix baris ke i, kolom ke 0
		
                
                System.out.print("-----------------------------------------------\n");
                
                Scanner in = new Scanner(System.in);    //Scanner: untuk mendapat user input
		System.out.print("Pilih nomor dataset yang akan dijadwalkan : ");
                int pilihan = in.nextInt();             //in.nextInt --> membaca user input berupa integer
		
                String noexam = "";                     //exam ke 1-dst
		String alokasits = "";                  //alokasi timeslot
		for(int i=0; i<kode.length; i++){
                    if(pilihan==i+1){
                        noexam = kode[i][0];        //tampilkan matrix kode baris ke i kolom ke 0      exam ke-1...
                        alokasits = kode[i][1];     //tampilkan matrix kode baris ke i kolom ke 1      
                    }
		}
                
		String sumber = "E:\\semester 6\\OKH\\FP\\Toronto\\" + noexam;
		BufferedReader filecrs = new BufferedReader(new FileReader(sumber + ".crs"));
		
                
                
                int exam = 0;
		while (filecrs.readLine() != null)         //readLine --> membaca file bufferedreader berupa string
                    exam++;       
		filecrs.close();
                
                BufferedReader filestu = new BufferedReader(new FileReader(sumber + ".stu"));
                
                int student = 0;
		while (filestu.readLine() != null) 
                    student++;
		filestu.close();
		
                int data[][] = new int[exam][exam];     //matrix data: jumlah baris exam*jumlah kolom exam2 (timeslot)
		int sort[][] = new int[exam][2];        //matrix sort: jumlah baris exam*jumlah kolom=2  --->> array urutan degree terbesar ke terkecil
		int timeslot[][] = new int[exam][2];    //matrix timeslot: jumlah baris exam*jumlah kolom=2 --->> array timeslot per exam
		
                int ts = 1;                             
		int count = 0;
		
                int max [][] = new int [1][2];      //matrix max: jumlah baris=1 * jumlah kolom=2
		max[0][0] = -1;                     //matrix max baris ke-0 kolom ke-0 = -1
		max[0][1] = -1;                     //matrix max: baris ke-0 kolom ke-1 = -1
                
                int x = 0;                          //x untuk sorting degree terbesar, untuk mendapatkan nilai i dari looping
		for (int i=0; i<data.length; i++)               //i=nomor exam
			for(int j=0; j<data.length; j++)        //j=timeslot
				data[i][j] = 0;                 //data baris ke-i kolom ke-j =0
		
                int degree[][] = new int [exam][2];             //matrix degree: jumlah baris exam*julmah kolom=2 --->> mengisi exam x berapa degree tapi belom urutan
                
                System.out.print("-----------------------------------------------\n");
                System.out.print("Berikut adalah daftar pilihan heuristik : \n");
                
                //bikin matrix [],[] --> baris,kolom
		String heuristik [] = {
                    "Initial Solution",
                    "Hill Climbing"
                };
                
                //iterasi buat nampilin list heuristik
		for(int i=0; i<heuristik.length;i++)                         //kode.length --> jumlah karakter pada variabel kode
			System.out.println(i + "  "+ heuristik[i]);
                
   
                System.out.print("-----------------------------------------------\n");
                System.out.print("Pilih heuristik yang akan dijalankan : ");
                
                int pilihanheuristik = in.nextInt();             //in.nextInt --> membaca user input berupa integer
                
                String noheuristik = "";                     //heuristik ke 1-dst
		
		for(int i=0; i<heuristik.length; i++){
                    if(pilihan==i+1){
                        noheuristik = heuristik[i];          
                    }
		}
                
                //untuk looping pemberian nomor kolom pertama (kolom exam)
                for (int i=0; i<degree.length; i++)             //i=nomor exam
			for (int j=0; j<degree[0].length; j++)  
				degree[i][0] = i+1;             //matrix degree baris ke-i kolom ke-0
        
                long mulai = System.nanoTime();                 //get the current system time in
        
                try {
            
                    File fstu = new File(sumber + ".stu");
                    BufferedReader stu = new BufferedReader(new FileReader(fstu));
                    String readLine = "";                   

                    while ((readLine = stu.readLine()) != null) {             
                    //System.out.println(readLine);
                        String tmp [] = readLine.split(" ");
                            for(int i=0; i<tmp.length; i++)
				for(int j=0; j<tmp.length; j++)
                                    if(tmp[i] != tmp[j])
					data[Integer.parseInt(tmp[i])-1][Integer.parseInt(tmp[j])-1]++;         //parseInt --> mengubah string menjadi integer
                    }
			
                    //untuk mengisi kolom kedua (kolom jumlah degree per exam)
                    for (int i=0; i<exam; i++){
			for (int j=0; j<exam; j++)
                            if(data[i][j] > 0)
				count++;
                            else
				count = count;					
                                degree[i][1] = count;
                                    count=0;
                    }
			
                    //untuk mengurutkan degree yang sudah ada dari yang terbesar ke terkecil
                    for(int a=0; a<degree.length; a++){
			for(int i=0; i<degree.length; i++){
                            if(max[0][1]<degree[i][1]){         //jika matrix max baris ke-0 kolom ke-1 < matrix degree baris ke-i kolom ke-j
				max[0][0] = degree[i][0];       //matrix max baris ke-0 kolom ke-0 =  matrix degree baris ke-i kolom ke-0
				max[0][1] = degree[i][1];       //matrix max baris ke-0 kolom ke-1 = matrix degree baris ke-i kolom ke-1
				x = i;
                            }				
                        }
			
                        //menginisiasi degree exam yang udah kepindah
                        degree[x][0] = -2;          //matrix degree baris ke-x kolom ke-0 = -2      --->> x=i=exam
			degree[x][1] = -2;          //matrix degree baris ke-x kolom ke-1 = -2      --->> x=i=exam
			sort[a][0] = max[0][0];     //matrix sort baris ke-a kolom ke-0 = matrix max baris ke-0 kolom ke-0
			sort[a][1] = max[0][1];     //matrix sort baris ke-a kolom ke-1 = matrix max baris ke-0 kolom ke-1
			max[0][0] = -1;             //matrix max baris ke-0 kolom ke-0 = -1         --->> sama kek line 84
			max[0][1] = -1;             //matrix max baris ke-0 kolom ke-1 = -1         --->> sama kek line 85
                    }
			
                    for(int i=0; i<timeslot.length; i++){
			for(int j=0; j<timeslot[i].length; j++){
                            timeslot[i][0] = i+1;                       //mengisi baris exam ke-i kolom ke 0
                            timeslot[i][1] = -1;                        //mengisi baris exam ke-i kolom ke 1 = -1
			}
                    }
		
                    // untuk assign exam ke timeslot berdasarkan sortingan degree
                    for(int i=0; i<sort.length; i++){
			for (int j=0; j<ts; j++){
                            if(isSafe(i, j, data, sort, timeslot)){         //i=exam
                                timeslot[sort[i][0]-1][1] = j;
				break;
                            }
                            else{
                                ts++;
                            }
			}
                    }
		
                    
                
                    //TextFileWritingExample1(timeslot, alokasits);	
                } catch (IOException e) {
                    e.printStackTrace();        //method untuk handle exceptions dan error
                }
                
                int tt=0;
                
                
                
                switch (pilihanheuristik) {
		case 0 :
                        //jumlah timeslot
                        
                        for(int i=0; i<timeslot.length; i++){
                            if(timeslot[i][1]>tt){
                            tt = timeslot[i][1];
                            }
                        }
                        
                        tt = tt+1;
                        hillClimbing(timeslot, data, student, tt, alokasits, exam);
                        
                        
                        
                        
                        break;
		case 1 :
			
                        
                    
                    
                        //jumlah timeslot
                        
                        for(int i=0; i<timeslot.length; i++){
                            if(timeslot[i][1]>tt){
                            tt = timeslot[i][1];
                            }
                        }
                
                        tt = tt+1;
                        hillClimbing(timeslot, data, student, tt, alokasits, exam);

                        long selesai = System.nanoTime();
                        long runningtime = selesai - mulai;

                        System.out.print("-----------------------------------------------\n");
                        System.out.println("Running time: " + (double)runningtime/1000000000);
                        System.out.print("-----------------------------------------------\n");
			
			break;
		case 2 :
                        
                        SimulatedAnnealing optimization;
                        optimization = new SimulatedAnnealing(sumber, conflict_matrix, sort, exam, student, 1000);
      
			long starttimeSA = System.nanoTime();
                        optimization.getTimeslotBySimulatedAnnealing(100.0);
                        long endtimeSA = System.nanoTime();
                        
                        System.out.println("Timeslot dibutuhkan (menggunakan Simulated Annealing) 		: " + optimization.getJumlahTimeslotSimulatedAnnealing());
                        System.out.println("Penalti Simulated Annealing 					: " + Penalty(conflict_matrix, optimization.getTimeslotSimulatedAnnealing(), student));
                        System.out.println("Waktu eksekusi yang dibutuhkan Simmulated Annealing " + ((double) (endtimeSA - starttimeSA)/1000000000) + " detik.\n");
			break;
			
		
		}
                
                
               
        }
        
}
