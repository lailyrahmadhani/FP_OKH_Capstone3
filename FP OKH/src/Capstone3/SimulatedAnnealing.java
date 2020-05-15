/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Capstone3;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
/**
 *
 * @author MSI-PC
 */
public class SimulatedAnnealing {
    
                int[][] timeslotSimulatedAnnealing, initialTimeslot, conflict_matrix, course_sorted;
                int[] timeslot;
                String file;
                int jumlahexam, jumlahmurid, randomCourse, randomTimeslot, iterasi;
                double initialPenalty, bestPenalty, deltaPenalty;

                Jadwal schedule;
                
                SimulatedAnnealing(String file, int[][] conflict_matrix, int[][] course_sorted, int jumlahexam, int jumlahmurid, int iterasi) { 
		this.file = file; 
		this.conflict_matrix = conflict_matrix;
		this.course_sorted = course_sorted;
		this.jumlahexam = jumlahexam;
		this.jumlahmurid = jumlahmurid;
		this.iterasi = iterasi;
                }
                
                public static int[][] getTimeslot(int[][] timeslot) {
		int[][] copySolution = new int[timeslot.length][2];
		
		for(int i = 0; i < timeslot.length; i++) {
			copySolution[i][0] = timeslot[i][0];
			copySolution[i][1] = timeslot[i][1];
		}
		
		return copySolution;
                }
                
                private static int randomNumber(int min, int max) {
		Random random = new Random();
		return random.nextInt(max - min) + min;
                }
                
                private static double acceptanceProbability(double penaltySementara, double penaltyLLH, double temperature) {
		// If the new solution is better, accept it
//		if (penaltySementara < penaltyLLH)
//			return 1.0;
		
		// If the new solution is worse, calculate an acceptance probability
		return Math.exp((penaltySementara - penaltyLLH) / temperature);
                }

                public void getTimeslotBySimulatedAnnealing(double temperature) {
		schedule = new Jadwal(file, conflict_matrix, jumlahexam);
		timeslot = schedule.schedulingByDegree(course_sorted);
		LLH lowLevelHeuristics = new LLH(conflict_matrix);
		
		// initial solution
		timeslotSimulatedAnnealing = schedule.getSchedule();
		initialPenalty = heuristic2.Penalty(conflict_matrix, timeslotSimulatedAnnealing, jumlahmurid);
		
		int[][] timeslotSimulatedAnnealingSementara = getTimeslot(timeslotSimulatedAnnealing);
		
		for	(int i=0; i < iterasi; i++) {
			int llh = randomNumber(1, 5);
			int[][] timeslotLLH;
			switch (llh) {
				case 1:
					timeslotLLH = lowLevelHeuristics.move1(timeslotSimulatedAnnealingSementara);
					break;
				case 2:
					timeslotLLH = lowLevelHeuristics.swap2(timeslotSimulatedAnnealingSementara);
					break;
				case 3:
					timeslotLLH = lowLevelHeuristics.move2(timeslotSimulatedAnnealingSementara);
					break;
				case 4:
					timeslotLLH = lowLevelHeuristics.swap3(timeslotSimulatedAnnealingSementara);
					break;
				case 5:
					timeslotLLH = lowLevelHeuristics.move3(timeslotSimulatedAnnealingSementara);
					break;
				default:
					timeslotLLH = lowLevelHeuristics.move1(timeslotSimulatedAnnealingSementara);
					break;
			}
			
                        
                        double coolingrate = 0.1;
//			temperature = temperature * (1 - coolingrate);
//			temperature = temperature - coolingrate;
			System.out.println("Suhu : " + temperature);
			if (heuristic2.Penalty(conflict_matrix, timeslotLLH, jumlahmurid) <= heuristic2.Penalty(conflict_matrix, timeslotSimulatedAnnealingSementara, jumlahmurid)) {
				timeslotSimulatedAnnealingSementara = getTimeslot(timeslotLLH);
				if (heuristic2.Penalty(conflict_matrix, timeslotLLH, jumlahmurid) <= heuristic2.Penalty(conflict_matrix, timeslotSimulatedAnnealing, jumlahmurid)) {
					timeslotSimulatedAnnealing = getTimeslot(timeslotLLH);
					bestPenalty = heuristic2.Penalty(conflict_matrix, timeslotSimulatedAnnealing, jumlahmurid);
				}
			}
				else if (acceptanceProbability(heuristic2.Penalty(conflict_matrix, timeslotSimulatedAnnealingSementara, jumlahmurid), heuristic2.Penalty(conflict_matrix, timeslotLLH, jumlahmurid), temperature) > Math.random())
					timeslotSimulatedAnnealingSementara = getTimeslot(timeslotLLH);
//			System.out.println("acceptance P : " + acceptanceProbability(Evaluator.getPenalty(conflict_matrix, timeslotSimulatedAnnealingSementara, jumlahmurid), Evaluator.getPenalty(conflict_matrix, timeslotLLH, jumlahmurid), temperature));
//			System.out.println("temperature : " + currentTemperature);
			// print current penalty of each iteration
			System.out.println("Iterasi: " + (i+1) + " memiliki penalty " + heuristic2.Penalty(conflict_matrix, timeslotSimulatedAnnealingSementara, jumlahmurid));
//			temperature *= 1 - coolingrate;
			temperature = temperature - coolingrate;
		}
//		bestPenalty = Evaluator.getPenalty(conflict_matrix, timeslotSimulatedAnnealing, jumlahmurid);
		deltaPenalty = ((initialPenalty-bestPenalty)/initialPenalty)*100;
		System.out.println("=============================================================");
		System.out.println("		Metode SIMULATED ANNEALING				 			 "); // print best penalty
		System.out.println("\nPenalty Initial : "+ initialPenalty); // print initial penalty
		System.out.println("Penalty Terbaik : " + bestPenalty); // print best penalty
		System.out.println("Terjadi Peningkatan Penalti : " + deltaPenalty + " % dari inisial solusi");
		System.out.println("Timeslot yang dibutuhkan : " + schedule.getJumlahTimeSlot(timeslotSimulatedAnnealing) + "\n");
		System.out.println("=============================================================");
	}
                
                public int[][] getTimeslotSimulatedAnnealing() { return timeslotSimulatedAnnealing; }
                public int getJumlahTimeslotSimulatedAnnealing() { return schedule.getJumlahTimeSlot(timeslotSimulatedAnnealing); }
}
