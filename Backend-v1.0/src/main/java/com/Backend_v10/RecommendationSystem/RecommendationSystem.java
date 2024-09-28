package com.Backend_v10.RecommendationSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.stereotype.Component;

import java.util.Enumeration;

import com.Backend_v10.Articles.Article;
import com.Backend_v10.JobApplication.JobApplication;
import com.Backend_v10.Jobs.Job;
import com.Backend_v10.User.User;
import com.Backend_v10.User.UserRepository;

import lombok.Data;


@Component
@Data
public class RecommendationSystem {
    private int bigK;
    private int total_epochs;
    private double lr;
    private double reg;
    private Long users; 
    private Long jobs; 
    private Long articles;
    private Dictionary<Long,Integer> UserToIndexDict;
    private Dictionary<Integer,Long> IndexToUserDict;
    private Dictionary<Long,Integer> ArticleToIndexDict;
    private Dictionary<Integer,Long> IndexToArticleDict;
    private Dictionary<Long,Integer> JobToIndexDict;
    private Dictionary<Integer,Long> IndexToJobDict;

    private double PredictedArticles[][];
    private double PredictedJobs[][];

    public RecommendationSystem() {
        this.bigK = 10;
        this.total_epochs = 100;
    }



//---------------------------Methods Updating the Prediction Matrix----------------------------------------//

    public void UpdateArticleRecommendationMatrix(List<User> AllUsers, List<Article> AllArticles){

        
        this.UserToIndexDict = new Hashtable<>();
        this.IndexToUserDict = new Hashtable<>();
        this.ArticleToIndexDict = new Hashtable<>();
        this.IndexToArticleDict = new Hashtable<>();

        int  i = 0;
        //Create Dictionaries
        
        //User Dictionaries
        for(User user: AllUsers){
            this.IndexToUserDict.put(i, user.getUserID());
            this.UserToIndexDict.put(user.getUserID(), i);
            i += 1;
        }
        this.users = Long.valueOf(i);
        i = 0;
        //Article Dictionaries
        for(Article article: AllArticles){
            this.IndexToArticleDict.put(i, article.getArticleID());
            this.ArticleToIndexDict.put(article.getArticleID(), i);
            i += 1;
        }
        this.articles = Long.valueOf(i);
        
        // System.out.println("Index to User Dictionary:\n" + this.IndexToUserDict.toString());
        // System.out.println("User to Index Dictionary:\n" + this.UserToIndexDict.toString());
        // System.out.println("Index to Article Dictionary:\n" + this.IndexToArticleDict.toString());
        // System.out.println("Article to Index Dictionary:\n" + this.ArticleToIndexDict.toString());

        double P[][] = new double[Math.toIntExact(users)][bigK]; 
        double Q[][] = new double[Math.toIntExact(articles)][bigK]; 
        Integer ActualArray[][] = new Integer [Math.toIntExact(users)][Math.toIntExact(articles)];
        this.PredictedArticles = new double[Math.toIntExact(users)][Math.toIntExact(articles)];


        //Create Actual Array
        //Set all cells to 0
        for(int s = 0; s < users; s++)
            for(int j = 0; j < articles; j++)
                ActualArray[s][j] = 0;
        //For each like of a user to an article,
        //set 1 in the respective cell
        for(User user: AllUsers){
            List<Article> liked_articles = new ArrayList<>();
            liked_articles = user.getLikedArticles();
           // System.out.println(liked_articles.size());

            //Found Liked Articles for a certain user
            //UserID ---> UserIndex
            int userIndex = this.UserToIndexDict.get(user.getUserID());
            for(Article article: liked_articles){
                Long article_id = article.getArticleID();
                //ArticleID ---> ArticleIndex
                int articleIndex = this.ArticleToIndexDict.get(article_id);
                
                //Fill the correct cell with 1
                ActualArray[userIndex][articleIndex] = 1;
                
            }

        }
        //Here we have the Intial array with all User-Article Interactions

		this.lr = 0.05;
		this.reg = 0.001;
		for(int u = 0; u < users; u++){
			for(int j = 0; j < this.bigK; j++){
				P[u][j] = Math.random();
			}
		}
		for(int u = 0; u < articles; u++){
			for(int j = 0; j < this.bigK; j++){
				Q[u][j] = Math.random();
			}
		}

		int total_epochs = 150;
		for(int epoch = 0; epoch < total_epochs; epoch++){
			for(int u = 0; u < users; u++){
				for(int j = 0; j < articles; j++){
					if( ActualArray[u][j] > 0){
						//find and store error
						double error_ij = 0;
						double dot = 0;
 						for(int k = 0; k < bigK; k++){
							dot += P[u][k] * Q[j][k]; 
						}
						error_ij = ActualArray[u][j] - dot;

						//Update Latent Factors
						for(int k = 0; k < bigK; k++){
							P[u][k] += lr * (error_ij * Q[j][k] - reg * P[u][k]);
							Q[j][k] += lr * (error_ij * P[u][k] - reg * Q[j][k]);
						}

					}
				}
			}
			double loss = 0.0;
			for (int u = 0; u < users; u++) {
				for (int j = 0; j < articles; j++) {
					if (ActualArray[u][j] > 0) { // Only consider non-zero interactions

						double error_ij = 0;
						double dot = 0;
 						for(int k = 0; k < bigK; k++){
							dot += P[u][k] * Q[j][k]; 
						}
						error_ij = ActualArray[u][j] - dot;
						double error_sq = error_ij * error_ij;
						loss += error_sq;						
						// Regularization penalty
						for (int k = 0; k < bigK; k++) {
							loss +=  reg * (P[u][k] * P[u][k] + Q[j][k] * Q[j][k]);
						}
					}
				}
			}
			//System.out.println("loss= "+loss);


		}

		for(int u = 0; u < users; u++){
			for(int j = 0; j < articles; j++){
				double dot = 0;
				for (int k = 0; k < bigK; k++) {
						dot += P[u][k] * Q[j][k]; 
				}
				PredictedArticles[u][j] = dot;
			}
			
		}

	}	



    




    public void UpdateJobsRecommendationMatrix(List<User> AllUsers, List<Job> AllJobs){

        
        this.UserToIndexDict = new Hashtable<>();
        this.IndexToUserDict = new Hashtable<>();
        this.JobToIndexDict = new Hashtable<>();
        this.IndexToJobDict = new Hashtable<>();
 
        int  i = 0;
        //Create Dictionaries
        //User Dictionaries
        for(User user: AllUsers){
            this.IndexToUserDict.put(i, user.getUserID());
            this.UserToIndexDict.put(user.getUserID(), i);
            i += 1;
        }
        this.users = Long.valueOf(i);
        i = 0;
        //Article Dictionaries
        for(Job job: AllJobs){
            this.IndexToJobDict.put(i, job.getJobID());
            this.JobToIndexDict.put(job.getJobID(), i);
            i += 1;
        }
        this.jobs = Long.valueOf(i);
        
        // System.out.println("Index to User Dictionary:\n" + this.IndexToUserDict.toString());
        // System.out.println("User to Index Dictionary:\n" + this.UserToIndexDict.toString());
        // System.out.println("Index to Job Dictionary:\n" + this.IndexToJobDict.toString());
        // System.out.println("Job to Index Dictionary:\n" + this.JobToIndexDict.toString());

        double P[][] = new double[Math.toIntExact(users)][bigK]; 
        double Q[][] = new double[Math.toIntExact(jobs)][bigK]; 
        Integer ActualArray[][] = new Integer [Math.toIntExact(users)][Math.toIntExact(jobs)];
        this.PredictedJobs = new double[Math.toIntExact(users)][Math.toIntExact(jobs)];


        //Create Actual Array
        //Set all cells to 0
        for(int s = 0; s < users; s++)
            for(int j = 0; j < jobs; j++)
                ActualArray[s][j] = 0;
        //For each like of a user to an article,
        //set 1 in the respective cell
        for(User user: AllUsers){

            List<JobApplication> jobapplications = new ArrayList<>();
            List<Long> JobsIAppliedIDs = new ArrayList<>();
            jobapplications = user.getMyJobApplications();
            for(JobApplication application: jobapplications)
                JobsIAppliedIDs.add(application.getJob().getJobID());


            //Found Liked Articles for a certain user
            //UserID ---> UserIndex
            int userIndex = this.UserToIndexDict.get(user.getUserID());
            for(Long jobID: JobsIAppliedIDs){
                //JobID ---> JobIndex
                int JobIndex = this.JobToIndexDict.get(jobID);
                //Fill the correct cell with 1
                ActualArray[userIndex][JobIndex] = 1;
                
            }

        }
        //Here we have the Intial array with all User-Article Interactions        
		this.lr = 0.1;
		this.reg = 0.01;
		for(int u = 0; u < users; u++){
			for(int j = 0; j < this.bigK; j++){
				P[u][j] = Math.random();
			}
		}
		for(int u = 0; u < jobs; u++){
			for(int j = 0; j < this.bigK; j++){
				Q[u][j] = Math.random();
			}
		}

		int total_epochs = 100;
		for(int epoch = 0; epoch < total_epochs; epoch++){
			for(int u = 0; u < users; u++){
				for(int j = 0; j < jobs; j++){
					if( ActualArray[u][j] > 0){
						//find and store error
						double error_ij = 0;
						double dot = 0;
 						for(int k = 0; k < bigK; k++){
							dot += P[u][k] * Q[j][k]; 
						}
						error_ij = ActualArray[u][j] - dot;

						//Update Latent Factors
						for(int k = 0; k < bigK; k++){
							P[u][k] += lr * (error_ij * Q[j][k] - reg * P[u][k]);
							Q[j][k] += lr * (error_ij * P[u][k] - reg * Q[j][k]);
						}
					}
				}
			}

			double loss = 0.0;
			for (int u = 0; u < users; u++) {
				for (int j = 0; j < jobs; j++) {
					if (ActualArray[u][j] > 0) { // Only consider non-zero interactions

						double error_ij = 0;
						double dot = 0;
 						for(int k = 0; k < bigK; k++){
							dot += P[u][k] * Q[j][k]; 
						}
						error_ij = ActualArray[u][j] - dot;
						double error_sq = error_ij * error_ij;
						loss += error_sq;						
						// Regularization penalty
						for (int k = 0; k < bigK; k++) {
							loss +=  reg * (P[u][k] * P[u][k] + Q[j][k] * Q[j][k]);
						}
					}
				}
			}
			//System.out.println("loss= "+loss);


		}


		for(int u = 0; u < users; u++){
			for(int j = 0; j < jobs; j++){
				double dot = 0;
				for (int k = 0; k < bigK; k++) {
						dot += P[u][k] * Q[j][k]; 
				}
				PredictedJobs[u][j] = dot;
			}
			
		}

	}	
    //------------------------------------------------------------------------------------------//


//-----------------Methods Recommending Article/Job to a Certain User---------------------------//


    public List<Long> RecommendArticles(User user){
        
        int UserIndex = this.UserToIndexDict.get(user.getUserID());
        double ArticleRatingsForUser[] = this.PredictedArticles[UserIndex];
        List<Long> BestArticles = new ArrayList<>();

        Dictionary<Double,Integer> RatingToIndex = new Hashtable<>();
        int i = 0;
        for(double rating: ArticleRatingsForUser){
            RatingToIndex.put(rating,i);
            i += 1;
        }

        //We sort our Articles
        Arrays.sort(ArticleRatingsForUser);        
        //And now we get the indexes back
        int max;
        if(ArticleRatingsForUser.length < 20)
            max = ArticleRatingsForUser.length;
        else   
            max = 20;
        for(int j = 0; j < max; j++){
            //Find actual index
            int Index = RatingToIndex.get(ArticleRatingsForUser[ArticleRatingsForUser.length - 1 - j]);
            Long best_id = this.IndexToArticleDict.get(Index);
            BestArticles.add(best_id);
        }
        return BestArticles;
        
    }

    public List<Long> RecommendJobs(User user){
        int UserIndex = this.UserToIndexDict.get(user.getUserID());


        double JobRatingsForUser[] = this.PredictedJobs[UserIndex];
        List<Long> BestJobs = new ArrayList<>();

        Dictionary<Double,Integer> RatingToIndex = new Hashtable<>();
        int i = 0;
        for(double rating: JobRatingsForUser){
            RatingToIndex.put(rating,i);
            i += 1;
        }

        //We sort our Articles
        Arrays.sort(JobRatingsForUser);
    
        //And now we get the indexes back
        int max;
        if(JobRatingsForUser.length < 8)
            max = JobRatingsForUser.length;
        else   
            max = 8;
        for(int j = 0; j < max; j++){
            //Find actual index
            int Index = RatingToIndex.get(JobRatingsForUser[JobRatingsForUser.length - 1 - j]);
            Long best_id = this.IndexToJobDict.get(Index);
            BestJobs.add(best_id);
        }
        return BestJobs;
    }
//-------------------------------------------------------------------//

}
