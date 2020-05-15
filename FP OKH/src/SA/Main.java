package SA;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    //done
    static String folderDataset = "E:\\semester 6\\OKH\\FP\\Toronto\\";
    static String namafile[][] = {	
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
    
    static int timeslot[]; // fill with course & its timeslot
    static int[][] conflict_matrix, course_sorted, hasil_timeslot;
	
	private static Scanner scanner;
	
    public static void main(String[] args) throws IOException {
        scanner = new Scanner(System.in);
        for	(int i=0; i< namafile.length; i++)
        	System.out.println(i+1 + " "+ namafile[i][1]);
        
        System.out.print("\nSilahkan pilih file untuk dijadwalkan : ");
        int pilih = scanner.nextInt();
        
        String filePilihanInput = namafile[pilih-1][0];
        String filePilihanOutput = namafile[pilih-1][1];
        
        
        String file = folderDataset + filePilihanInput;         //folder dataset=sumber
        
        
		
        Course course = new Course(file);
        int jumlahexam = course.getJumlahCourse();
        
        conflict_matrix = course.getConflictMatrix();
        int jumlahmurid = course.getJumlahMurid();
        
		// sort exam by degree DONE
		course_sorted = course.sortingByDegree(conflict_matrix, jumlahexam);
		
		// scheduling
		/*
		 * Scheduling by largest degree
		 */
		
		long starttimeLargestDegree = System.nanoTime();
		Jadwal schedule = new Jadwal(file, conflict_matrix, jumlahexam);
		timeslot = schedule.schedulingByDegree(course_sorted);
		int[][] timeslotByLargestDegree = schedule.getSchedule();
		long endtimeLargestDegree = System.nanoTime();
		
		/*
		 * params 1: file to be scheduling
		 * params 2: conflict matrix from file
		 * params 3: sort course by degree
		 * params 4: how many course from file
		 * params 5: how many student from file
		 * params 6: how many iterations
		 */
                
		
		
                
		SimulatedAnnealing optimization = new SimulatedAnnealing(file, conflict_matrix, course_sorted, jumlahexam, jumlahmurid, 1000);
		
		/*
		 * use simmulated annealing for timesloting
		 * params : temperature
		 */
		long starttimeSA = System.nanoTime();
		optimization.getTimeslotBySimulatedAnnealing(100.0);
		long endtimeSA = System.nanoTime();
		/*
		 * use tabu search for timeslotting
		 */
		System.out.print  ("Dataset       : " + filePilihanOutput + "\n" );
		System.out.println("Running time  : " + ((double) (endtimeSA - starttimeSA)/1000000000) + " detik.\n");
		
    }
}
    