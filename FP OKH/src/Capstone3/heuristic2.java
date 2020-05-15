/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Capstone3;

/**
 *
 * @author MSI-PC
 */
public class heuristic2 {
	
        public static double Penalty(int jadwal[][], int matrix[][], int student){
            double nilaiPenalty = 0;
            for(int i=0; i<matrix.length-1; i++){
                for(int j=i+1; j<matrix.length; j++){
                    if(matrix[i][j] != 0){
                        if(Math.abs(jadwal[j][1]-jadwal[i][1]) >= 1 && Math.abs(jadwal[j][1]-jadwal[i][1]) <= 5){                   //math.abs memberikan nilai absolut dari argumen
                            nilaiPenalty = nilaiPenalty + (matrix[i][j]*(Math.pow(2,5-(Math.abs(jadwal[j][1]-jadwal[i][1])))));     //math.pow = 2^5 
                        }
                    }
                } 
            }
            return (nilaiPenalty/student);
        }
        
	public static boolean isSafe(int index, int m, int dat[][], int[][]srt, int[][]jadwal){
            for(int i=0; i<srt.length; i++)
                if(dat[srt[index][0]-1][i] != 0 && jadwal[i][1] == m)
                    return false;
            return true;
	}
        
        public static boolean isSafeRand(int exr, int ttr, int matriks[][], int jdwl[][]){
            for(int i=0; i<matriks.length; i++)
                if(matriks[exr][i] != 0 && jdwl[i][1] == ttr)
                    return false;
            return true;              
        }
	
        public static void hillClimbing(int jadwal[][], int matrix[][], int student, int tt, String kode, int crs){ 
            int waktu[][] = new int [jadwal.length][2];
            int waktu2[][]= new int [jadwal.length][2];
            
            for(int i=0; i<jadwal.length; i++)
            for(int j=0; j<jadwal[i].length; j++){
                waktu[i][j] = jadwal[i][j];
                waktu2[i][j] = jadwal[i][j]; 
            }
            
            double s = Penalty(waktu, matrix, student);         //s=solusi
            double d = s;
            
            //print iterasi 1-1000 dan penalty
            for(int i=0; i<1000; i++){
                int exr = (int) (Math.random()*(crs-1));
                int ttr = (int) (Math.random()*(tt-1));
                
                if(isSafeRand(exr, ttr, matrix, waktu2)){
                    waktu2[exr][1] = ttr;
                    double c = Penalty(waktu2, matrix, student);
                    if(c<s){
                        s = Penalty(waktu2, matrix, student);
                        waktu[exr][1] = waktu2[exr][1];
                    }
                    else{
                        waktu2[exr][1] = waktu[exr][1];
                    }
                }
                System.out.println("Iterasi ke " + (i+1) + " penaltinya: " + Penalty(waktu2, matrix, student));
                
                for(int j=0; j<waktu.length; j++){
                    waktu2[j][1] = (int)(Math.random()*tt);
                }
                System.out.println("Iterasi ke " + (i+1) + " penaltinya: " + Penalty(waktu2, matrix, student));
                if(Penalty(waktu2, matrix, student) < s){
                    s = Penalty(waktu2, matrix, student);
                        for(int k=0; k<jadwal.length; k++)
                            for(int l=0; l<jadwal[k].length; l++){
                                waktu[k][1] = waktu2[k][1];
                            }
                }           
            }
            
            System.out.print("-----------------------------------------------\n");
            System.out.println("Dataset : " + kode + "\nSolusi terbaik: " + s + "\nTimeslot : " + tt);
            
        }
        
}
