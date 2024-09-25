package com.Backend_v10;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.Backend_v10.ScheduledTasks.ScheduledTasks;
import com.Backend_v10.User.User;

import java.util.Arrays;

@SpringBootApplication
//@EnableScheduling
public class Application {

	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		
		//bonus();
		
		//SpringApplication.run(ScheduledTasks.class, args);
	}

	public static void bonus(){
		System.out.println("ok here we start the calculation");

		//example array 
		// int Array[][] = new int[users][articles];
		
		// //array of 100 articles and 100 users
		// for(int i = 0; i < users; i++){
			// 	for(int j = 0; j < articles; j++){
				// 		Array[i][j] = 0;
				// 	}
				// }


				//dictionary for users-articles --> Array
				// Parameters: List of Users


				double[][] Array = {
					{0, 0, 0, 0, 0, 1},  // User 1
					{1, 1, 1, 0, 0, 1},  // User 2
					{0, 0, 0, 0, 0, 1},  // User 3
					{0, 1, 1, 0, 0, 1},  // User 4
					{0, 0, 0, 0, 0, 1},
					{0, 0, 0, 0, 0, 1}  // User 5
					
				};
		
				int users = 6;
				int articles = 6; 

		// for(int i = 0; i < users;i++)
		// 	Array[i][i] = 1;


		//Initialize arrays P and Q randomly!
		
		int bigK = 5;
		double lr = 0.01;
		double reg = 0.0001;
		double P[][] = new double[users][bigK]; //100 number of users
		double Q[][] = new double[articles][bigK]; // 100 number of articles
		for(int i = 0; i < users; i++){
			for(int j = 0; j < bigK; j++){
				P[i][j] = Math.random();
			}
		}
		for(int i = 0; i < articles; i++){
			for(int j = 0; j < bigK; j++){
				Q[i][j] = Math.random();
			}
		}

		int total_epochs = 100;
		for(int epoch = 0; epoch < total_epochs; epoch++){
			for(int i = 0; i < users; i++){
				for(int j = 0; j < articles; j++){
					if( Array[i][j] > 0){
						//find and store error
						double error_ij = 0;
						double dot = 0;
 						for(int k = 0; k < bigK; k++){
							dot += P[i][k] * Q[j][k]; 
						}
						error_ij = Array[i][j] - dot;

						//Update Latent Factors
						for(int k = 0; k < bigK; k++){
							P[i][k] += lr * (error_ij * Q[j][k] - reg * P[i][k]);
							Q[j][k] += lr * (error_ij * P[i][k] - reg * Q[j][k]);
						}


						//P[i, :] += alpha * (error_ij * Q[j, :] - beta * P[i, :])
						//Q[j, :] += alpha * (error_ij * P[i, :] - beta * Q[j, :])
						//error_ij = R[i][j] - np.dot(P[i, :], Q[j, :].T)
					}
				}
			}
			//System.out.println(P[5][10]);

			double loss = 0.0;
			for (int i = 0; i < users; i++) {
				for (int j = 0; j < articles; j++) {
					if (Array[i][j] > 0) { // Only consider non-zero interactions

						double error_ij = 0;
						double dot = 0;
 						for(int k = 0; k < bigK; k++){
							dot += P[i][k] * Q[j][k]; 
						}
						error_ij = Array[i][j] - dot;
						double error_sq = error_ij * error_ij;
						loss += error_sq;
						//loss += error_ij * error_ij;
						
						// Regularization penalty
						for (int k = 0; k < bigK; k++) {
							loss +=  reg * (P[i][k] * P[i][k] + Q[j][k] * Q[j][k]);
						}
					}
				}
			}
			System.out.println("loss= "+loss);


		}
		//System.out.println(Arrays.deepToString(Q));
		double Predicted[][] = new double[users][articles];

		for(int i = 0; i < users; i++){
			for(int j = 0; j < articles; j++){
				double dot = 0;
				for (int k = 0; k < bigK; k++) {
						dot += P[i][k] * Q[j][k]; 
				}
				Predicted[i][j] = dot;
			}
			
		}
		System.out.println("Actual Array " + Arrays.deepToString(Array));

		System.out.println("Predicted Array " + Arrays.deepToString(Predicted));

	}	

}