package com.Backend_v10;
import java.nio.file.OpenOption;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.Backend_v10.Articles.Article;
import com.Backend_v10.Articles.ArticleRepository;
import com.Backend_v10.Comments.Comment;
import com.Backend_v10.Comments.CommentRepository;
import com.Backend_v10.JobApplication.JobApplication;
import com.Backend_v10.JobApplication.JobApplicationRepository;
import com.Backend_v10.User.User;
import com.Backend_v10.User.UserRepository;
import com.Backend_v10.User.UserService;
import com.Backend_v10.UserConnection.UserConnection;
import com.Backend_v10.UserConnection.UserConnectionRepository;
import com.Backend_v10.Jobs.Job;
import com.Backend_v10.Jobs.JobRepository;
import com.Backend_v10.RecommendationSystem.RecommendationSystem;



//Preload Database with certain users
@Configuration
public class LoadDatabase {

  @Autowired  // Inject UserRepository
  private UserRepository userRepo;


  @Autowired  // Inject UserRepository
  private UserConnectionRepository UserConnRepo;

  @Autowired  // Inject ArticleRepository
  private ArticleRepository articleRepo;

  @Autowired  // Inject CommentRepository
  private CommentRepository commentRepo;

  @Autowired  // Inject JobRepository
  private JobRepository jobRepo;

  @Autowired
  private JobApplicationRepository jobApplicationRepo;

  @Autowired  // Inject PasswordEncoder
  private PasswordEncoder encoder;

  @Autowired
  private UserService userService;

  public LoadDatabase(UserService userService, UserConnectionRepository UserConnRepo, UserRepository userRepo, ArticleRepository articleRepo, CommentRepository commentRepo, JobRepository jobRepo, JobApplicationRepository jobApplicationRepo, PasswordEncoder encoder) {
    this.userRepo = userRepo;
    this.articleRepo = articleRepo;
    this.jobRepo = jobRepo;
    this.jobApplicationRepo = jobApplicationRepo;
    this.encoder = encoder;
    this.commentRepo = commentRepo;
    this.userService = userService;
  }

  @Bean  
  CommandLineRunner initDatabase(){
    return args -> {

        // Create and save users firsT
        User admin1 = new User("teomor", "Theodoros", this.encoder.encode("12345"), "ROLE_ADMIN", "Moraitis", "teomor@email.com", LocalDate.of(2002, 2, 8), "6910101010");
        User admin2 = new User("nickmosch", "Nikitas", this.encoder.encode("12345"), "ROLE_ADMIN", "Moschos", "nickmosch@email.com", LocalDate.of(2002, 8, 19), "6901010101");
        User user1 = new User("bobross", "Bob", this.encoder.encode("12345"), "ROLE_USER", "Ross", "bobross@email.com", LocalDate.of(1989, 9, 3), "(555) 123-4567");
        User user2 = new User("jetlee", "Jet", this.encoder.encode("12345"), "ROLE_USER", "Lee", "jetlee@email.com", LocalDate.of(1997, 8, 16), "(555) 234-5678");
        User user3 = new User("TC", "Thomas", this.encoder.encode("12345"), "ROLE_USER", "Charles", "thomasch@email.com", LocalDate.of(1992, 12, 8), "(555) 456-7890");
        User user4 = new User("TC", "Taylor", this.encoder.encode("12345"), "ROLE_USER", "Carlson", "taylorcar@email.com", LocalDate.of(1999, 3, 10), "(555) 678-9012");
        User user5 = new User("marial", "Maria", this.encoder.encode("12345"), "ROLE_USER", "Lazarou", "marial@email.com", LocalDate.of(1996, 7, 23), "(555) 890-1234");
        User user6 = new User("ketip", "Keti", this.encoder.encode("12345"), "ROLE_USER", "Perry", "ketip@email.com", LocalDate.of(1977, 6, 17), "(555) 345-6789");
        // Save users to the repository
        userRepo.save(admin1);
        userRepo.save(admin2);
        userRepo.save(user1);
        userRepo.save(user2);
        userRepo.save(user3);
        userRepo.save(user4);
        userRepo.save(user5);
        userRepo.save(user6);
    

        // Create articles and jobs
        Article article1 = new Article("Just got my First Job!!", null);
        // Add a delay of 1 second (1000 milliseconds):
        // Thread.sleep(1000);
        Article article2 = new Article("Just got my Second Job!!", null);
        Article article3 = new Article("Third Job SOOO EXCITING!!", null);

        Job job1 = new Job("Full Stack Developer", true, "Java, Spring, Angular", 0, true);
        Job job2 = new Job("In need of a Data Analyst", false, "MS Office, Python", 850, false);
        Job job3 = new Job("HR Manager", true, "Communication skills, MBA Masters", 1300, true);
        Job job4 = new Job ("Network Engineer", true, "Networks, C, Python, Protocols understanding", 1000, true);
        // Save articles and jobs to the repository
        articleRepo.save(article1);
        articleRepo.save(article2);
        articleRepo.save(article3);
        jobRepo.save(job1);
        jobRepo.save(job2);
        jobRepo.save(job3);
        jobRepo.save(job4);
        
        // Associate articles and jobs with users
        userService.addArticle(user1, article1);
        userService.addArticle(user1, article2);
        userService.addArticle(user3, article3);
        userService.addJob(user1, job1);
        userService.addJob(user1, job2);
        userService.addJob(user3, job3);
        userService.addJob(user4, job4);


        // Create comments and save them to the repository
        Comment comment1 = new Comment();
        Comment comment2 = new Comment();
        comment1.setContent("Nice Post");
        comment2.setContent("I Agree Great Post");

        commentRepo.save(comment1);
        commentRepo.save(comment2);
        //Assosiate comments with articles/users
        userService.addComment(article1, user2, comment1);
        userService.addComment(article1, user5, comment2);

        // Create jobapplications and save them to the repository
        JobApplication jobApplication1 = new JobApplication();
        JobApplication jobApplication2 = new JobApplication();
        jobApplicationRepo.save(jobApplication1);  
        jobApplicationRepo.save(jobApplication2);
        // Assosiate jobapplications with jobs/users
        userService.addJobApplication(job3, user1, jobApplication1);
        userService.addJobApplication(job2, user2, jobApplication2);

        
        // Contacts tests:
        userService.addContact(user1, user3); 
        userService.addContact(user1, user4); 

        // UserConnections tests:
        // bobross have sent request to marial
        UserConnection conn1 = new UserConnection();
        conn1.setUser1(user1.getEmail());
        conn1.setUser2(user5.getEmail());
        UserConnRepo.save(conn1);

        // bobross have received request from ketip
        UserConnection conn2 = new UserConnection();
        conn2.setUser1(user6.getEmail());
        conn2.setUser2(user1.getEmail());
        UserConnRepo.save(conn2);

        // Test likes
        user1.likeArticle(article1);
        user2.likeArticle(article1);
        user5.likeArticle(article1);

        // Save the updated users
        userRepo.save(user1);
        userRepo.save(user2);
        userRepo.save(user5);


        // Add DATASET (Mostly for the Recommendation System)
        CreateData(userService, UserConnRepo, userRepo, articleRepo, commentRepo, jobRepo, jobApplicationRepo, encoder);
        
        //Recommendation System Creation
        RecommendationSystem R = new RecommendationSystem();
        R.UpdateArticleRecommendationMatrix(userRepo.findAll(), articleRepo.findAll());
        R.UpdateJobsRecommendationMatrix(userRepo.findAll(), jobRepo.findAll());


      };
  }


  public void CreateData(UserService userService, UserConnectionRepository UserConnRepo, UserRepository userRepo, ArticleRepository articleRepo, CommentRepository commentRepo, JobRepository jobRepo, JobApplicationRepository jobApplicationRepo, PasswordEncoder encoder){

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////  USERS  /////////////////////////////////////////////////////////

    // CREATE USERS
    User Jackie = userRepo.save(new User("Jackie", "Jackie", this.encoder.encode("12345"), "ROLE_USER", "Chan", "Jchan@email.com", LocalDate.of(1982, 7, 19), "(555) 456-7890"));
    User JLo = userRepo.save(new User("JLo", "Jennifer", this.encoder.encode("12345"), "ROLE_USER", "Lopez", "JLo78@email.com", LocalDate.of(1980, 9, 5), "(555) 567-8901"));
    User WD = userRepo.save(new User("WD", "Walt", this.encoder.encode("12345"), "ROLE_USER", "Disney", "Disney@email.com", LocalDate.of(1995, 2, 22), "(555) 678-9012"));
    User WS = userRepo.save(new User("WS", "Will", this.encoder.encode("12345"), "ROLE_USER", "Smith", "WillS@email.com", LocalDate.of(1987, 6, 30), "(555) 789-0123"));
    User WSJr = userRepo.save(new User("WS", "Will", this.encoder.encode("12345"), "ROLE_USER", "Smith Jr", "WillSmithJR@email.com", LocalDate.of(1992, 4, 14), "(555) 890-1234"));
    User MorganF = userRepo.save(new User("MorganF", "Morgan", this.encoder.encode("12345"), "ROLE_USER", "Freeman", "Freeman@email.com", LocalDate.of(1978, 8, 9), "(555) 901-2345"));
    User Messi = userRepo.save(new User("Messi", "Lionel", this.encoder.encode("12345"), "ROLE_USER", "Messi", "LMessi10@email.com", LocalDate.of(1996, 12, 3), "(555) 012-3456"));
    User MuhammedALi = userRepo.save(new User("MuhammedALi", "Casius", this.encoder.encode("12345"), "ROLE_USER", "Clay", "Clay@email.com", LocalDate.of(1983, 10, 11), "(555) 234-5670"));
    User Washington = userRepo.save(new User("Washington", "George", this.encoder.encode("12345"), "ROLE_USER", "Washington", "GeorgeW@email.com", LocalDate.of(1991, 3, 8), "(555) 345-6781"));
    User Napoleon = userRepo.save(new User("Napoleon The Emperor", "Napoleon", this.encoder.encode("12345"), "ROLE_USER", "Bonaparte", "NapoleonEmperor@email.com", LocalDate.of(1976, 5, 26), "(555) 456-7892"));
    User GrahamBell = userRepo.save(new User("GrahamBell", "Alexander", this.encoder.encode("12345"), "ROLE_USER", "Bell", "AlexBell@email.com", LocalDate.of(1989, 6, 20), "(555) 567-8903"));
    User Einstein = userRepo.save(new User("Einstein", "Albert", this.encoder.encode("12345"), "ROLE_USER", "Einstein", "einstein@email.com", LocalDate.of(1993, 1, 17), "(555) 678-9014"));
    User Newton = userRepo.save(new User("Newton", "Isaac", this.encoder.encode("12345"), "ROLE_USER", "Newton", "newton@email.com", LocalDate.of(1994, 8, 30), "(555) 789-0125"));
    User Curie = userRepo.save(new User("Curie", "Marie", this.encoder.encode("12345"), "ROLE_USER", "Curie", "curie@email.com", LocalDate.of(1997, 2, 1), "(555) 890-1236"));
    User Tesla = userRepo.save(new User("Tesla", "Nikola", this.encoder.encode("12345"), "ROLE_USER", "Tesla", "tesla@email.com", LocalDate.of(1985, 9, 12), "(555) 901-2347"));
    User Galilei = userRepo.save(new User("Galilei", "Galileo", this.encoder.encode("12345"), "ROLE_USER", "Galilei", "galilei@email.com", LocalDate.of(1990, 4, 15), "(555) 012-3458"));
    User DaVinci = userRepo.save(new User("DaVinci", "Leonardo", this.encoder.encode("12345"), "ROLE_USER", "Da Vinci", "davinci@email.com", LocalDate.of(1998, 7, 28), "(555) 123-4569"));
    User Darwin = userRepo.save(new User("Darwin", "Charles", this.encoder.encode("12345"), "ROLE_USER", "Darwin", "darwin@email.com", LocalDate.of(1981, 10, 19), "(555) 234-5679"));
    User Hawking = userRepo.save(new User("Hawking", "Stephen", this.encoder.encode("12345"), "ROLE_USER", "Hawking", "hawking@email.com", LocalDate.of(1977, 11, 4), "(555) 345-6788"));
    User Turing = userRepo.save(new User("Turing", "Alan", this.encoder.encode("12345"), "ROLE_USER", "Turing", "turing@email.com", LocalDate.of(1992, 12, 25), "(555) 456-7897"));
    User Lovelace = userRepo.save(new User("Lovelace", "Ada", this.encoder.encode("12345"), "ROLE_USER", "Lovelace", "lovelace@email.com", LocalDate.of(1996, 3, 16), "(555) 567-8906"));
    User Franklin = userRepo.save(new User("Franklin", "Rosalind", this.encoder.encode("12345"), "ROLE_USER", "Franklin", "franklin@email.com", LocalDate.of(1984, 2, 14), "(555) 678-9015"));
    User Pasteur = userRepo.save(new User("Pasteur", "Louis", this.encoder.encode("12345"), "ROLE_USER", "Pasteur", "pasteur@email.com", LocalDate.of(1991, 1, 7), "(555) 789-0124"));
    User Mendel = userRepo.save(new User("Mendel", "Gregor", this.encoder.encode("12345"), "ROLE_USER", "Mendel", "mendel@email.com", LocalDate.of(1982, 8, 22), "(555) 890-1233"));
    User Fleming = userRepo.save(new User("Fleming", "Alexander", this.encoder.encode("12345"), "ROLE_USER", "Fleming", "fleming@email.com", LocalDate.of(1995, 4, 17), "(555) 901-2342"));
    User Copernicus = userRepo.save(new User("Copernicus", "Nicolaus", this.encoder.encode("12345"), "ROLE_USER", "Copernicus", "copernicus@email.com", LocalDate.of(1988, 5, 28), "(555) 012-3459"));
    User Kepler = userRepo.save(new User("Kepler", "Johannes", this.encoder.encode("12345"), "ROLE_USER", "Kepler", "kepler@email.com", LocalDate.of(1986, 6, 11), "(555) 123-4560"));
    User Babbage = userRepo.save(new User("Babbage", "Charles", this.encoder.encode("12345"), "ROLE_USER", "Babbage", "babbage@email.com", LocalDate.of(1990, 7, 4), "(555) 234-5671"));
    User Bohr = userRepo.save(new User("Bohr", "Niels", this.encoder.encode("12345"), "ROLE_USER", "Bohr", "bohr@email.com", LocalDate.of(1975, 3, 21), "(555) 345-6782"));
    User Planck = userRepo.save(new User("Planck", "Max", this.encoder.encode("12345"), "ROLE_USER", "Planck", "planck@email.com", LocalDate.of(1993, 8, 9), "(555) 456-7893"));
    User Fermi = userRepo.save(new User("Fermi", "Enrico", this.encoder.encode("12345"), "ROLE_USER", "Enrico Fermi", "fermi@email.com", LocalDate.of(1982, 12, 29), "(555) 567-8904"));
    User Oppenh = userRepo.save(new User("Oppenheimer", "Robert", this.encoder.encode("12345"), "ROLE_USER", "Oppenheimer", "oppenheimer@email.com", LocalDate.of(1980, 11, 18), "(555) 678-9015"));
    User Heisenberg = userRepo.save(new User("Heisenberg", "Werner", this.encoder.encode("12345"), "ROLE_USER", "Heisenberg", "heisenberg@email.com", LocalDate.of(1983, 1, 23), "(555) 789-0126"));
    User Euler = userRepo.save(new User("Euler", "Leonhard", this.encoder.encode("12345"), "ROLE_USER", "Euler", "euler@email.com", LocalDate.of(1992, 9, 30), "(555) 890-1237"));
    User Rutherford = userRepo.save(new User("Rutherford", "Ernest", this.encoder.encode("12345"), "ROLE_USER", "Rutherford", "rutherford@email.com", LocalDate.of(1986, 5, 1), "(555) 901-2348"));
    User Feynman = userRepo.save(new User("Feynman", "Richard", this.encoder.encode("12345"), "ROLE_USER", "Feynman", "feynman@email.com", LocalDate.of(1984, 2, 19), "(555) 012-3450"));
    User Faraday = userRepo.save(new User("Faraday", "Michael", this.encoder.encode("12345"), "ROLE_USER", "Faraday", "faraday@email.com", LocalDate.of(1981, 10, 3), "(555) 123-4561"));
    User Dirac = userRepo.save(new User("Dirac", "Paul", this.encoder.encode("12345"), "ROLE_USER", "Dirac", "dirac@email.com", LocalDate.of(1994, 4, 16), "(555) 234-5672"));
    User Hubble = userRepo.save(new User("Hubble", "Edwin", this.encoder.encode("12345"), "ROLE_USER", "Hubble", "hubble@email.com", LocalDate.of(1990, 8, 27), "(555) 345-6783"));
    User Watson = userRepo.save(new User("Watson", "James", this.encoder.encode("12345"), "ROLE_USER", "Watson", "watson@email.com", LocalDate.of(1985, 3, 13), "(555) 456-7894"));
    User Crick = userRepo.save(new User("Crick", "Francis", this.encoder.encode("12345"), "ROLE_USER", "Crick", "crick@email.com", LocalDate.of(1995, 6, 28), "(555) 567-8905"));
    User Godel = userRepo.save(new User("Godel", "Kurt", this.encoder.encode("12345"), "ROLE_USER", "Godel", "godel@email.com", LocalDate.of(1980, 2, 15), "(555) 678-9016"));
    User Nietzsche = userRepo.save(new User("FriedrichN", "Friedrich", this.encoder.encode("12345"), "ROLE_USER", "Nietzsche", "FNietzsche@email.com", LocalDate.of(1989, 1, 4), "(555) 789-0127"));
    User Farmer = userRepo.save(new User("Georger Farmer", "George", this.encoder.encode("12345"), "ROLE_USER", "Adams", "AdamsG@email.com", LocalDate.of(1997, 11, 11), "(555) 890-1238"));
    User Lincoln = userRepo.save(new User("AbrahamL", "Abraham", this.encoder.encode("12345"), "ROLE_USER", "Lincoln", "LincolnA@email.com", LocalDate.of(1965, 8, 15), "(555) 890-1578"));
    User Shapiro =  userRepo.save(new User("ShaprioR", "Robert", this.encoder.encode("12345"), "ROLE_USER", "Shapiro", "RSA@email.com", LocalDate.of(1955, 5, 22), "(555) 726-7856"));
    User Woodrow =  userRepo.save(new User("WWoodrow", "Wilson", this.encoder.encode("12345"), "ROLE_USER", "Woodrow", "WW@email.com", LocalDate.of(1979, 1, 7), "(555) 890-1578"));
    User WGary =  userRepo.save(new User("WEGarry", "Willy E.", this.encoder.encode("12345"), "ROLE_USER", "Gary", "Gary@email.com", LocalDate.of(1974, 9, 1), "(555) 788-6549")); 
    User ASmith = userRepo.save(new User("Smith.A", "Adam", this.encoder.encode("12345"), "ROLE_USER", "Smith", "AdamS@email.com", LocalDate.of(1952, 3, 10), "(555) 892-888"));
    User Marx =  userRepo.save(new User("KM", "Karl", this.encoder.encode("12345"), "ROLE_USER", "Marx", "KM@email.com", LocalDate.of(1945, 4, 12), "(555) 822-1118"));
    User Morgan =  userRepo.save(new User("JPMorgan", "JP", this.encoder.encode("12345"), "ROLE_USER", "Morgan", "JMorgan@email.com", LocalDate.of(1947, 2, 11), "(555) 890-1566"));
    User Musk =  userRepo.save(new User("ElonMusk", "Elon", this.encoder.encode("12345"), "ROLE_USER", "Musk", "EMu@email.com", LocalDate.of(1987, 8, 20), "(555) 923-1738"));
    User Hippocrates =  userRepo.save(new User("HippoCr", "HippoCrates", this.encoder.encode("12345"), "ROLE_USER", "Unknown", "Hippo@email.com", LocalDate.of(1987, 3, 28), "(555) 890-1213"));
    User Papanikolaou = userRepo.save(new User("PapanikolaouG", "George", this.encoder.encode("12345"), "ROLE_USER", "Papanikoalou", "PapaNikolaou@email.com", LocalDate.of(1982, 11, 13), "(555) 890-1989"));
    
    // CREATE CONTACTS (for Bohr and Planck)
    userService.addContact(Bohr, Planck);
    userService.addContact(Bohr, Heisenberg);
    userService.addContact(Planck, Heisenberg);
    userService.addContact(Planck, Dirac);
    userService.addContact(Bohr, Dirac);
    userService.addContact(Bohr, Oppenh);
    userService.addContact(Bohr, Feynman);
    

    // CREATE REQUESTS (for Bohr and Planck)
    UserConnection conn = new UserConnection();
    conn.setUser1(Hawking.getEmail());
    conn.setUser2(Planck.getEmail());
    UserConnRepo.save(conn);
    
    conn = new UserConnection();
    conn.setUser1(Bohr.getEmail());
    conn.setUser2(Turing.getEmail());
    UserConnRepo.save(conn);

    conn = new UserConnection();
    conn.setUser1(Planck.getEmail());
    conn.setUser2(Turing.getEmail());
    UserConnRepo.save(conn);

    conn = new UserConnection();
    conn.setUser1(Bohr.getEmail());
    conn.setUser2(Darwin.getEmail());
    UserConnRepo.save(conn);
    
    conn = new UserConnection();
    conn.setUser1(Tesla.getEmail());
    conn.setUser2(Bohr.getEmail());
    UserConnRepo.save(conn);
    
    conn = new UserConnection();
    conn.setUser1(DaVinci.getEmail());
    conn.setUser2(Bohr.getEmail());
    UserConnRepo.save(conn);

    conn = new UserConnection();
    conn.setUser1(DaVinci.getEmail());
    conn.setUser2(Planck.getEmail());
    UserConnRepo.save(conn);


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////  ARTICLES  /////////////////////////////////////////////////////////

    // CREATE ARTICLES
    //Technology
    Article T1 = articleRepo.save(new Article("Discover the future of AI in healthcare. Join us at this link https://aihealthcare.com/learn more!!", null));
    Article T2 = articleRepo.save(new Article("Explore the world of quantum computing. Visit us at https://quantumworld.com to stay ahead!!", null));
    Article T3 = articleRepo.save(new Article("The future of autonomous driving is here! Discover more at https://autonomousdrive.com!!", null));
    Article T4 = articleRepo.save(new Article("Discover the breakthrough in nanotechnology. Explore the possibilities at https://nanotechfuture.com!!", null));
    Article T5 = articleRepo.save(new Article("Step into the world of robotics and automation. Begin your journey at https://roboticsrevolution.com!!", null));
    Article T6 = articleRepo.save(new Article("Step into the world of game development. Begin your journey at https://gamedevworld.com!!", null));
    Article T7 = articleRepo.save(new Article("Explore cutting-edge advancements in cybersecurity. Stay protected at https://cybersecure.com!!", null));
    Article T8 = articleRepo.save(new Article("Learn the secrets of machine learning and artificial intelligence at https://mlaiacademy.com!!", null));
    Article T9 = articleRepo.save(new Article("Become a leader in data science. Start today at https://datascienceacademy.com!!", null));
    Article T10 = articleRepo.save(new Article("Explore 3D printing technology and its future applications at https://3dprintrevolution.com!!", null));
    Article T11 = articleRepo.save(new Article("Discover the power of cloud computing for businesses at https://cloudfuture.com!!", null));
    Article T12 = articleRepo.save(new Article("Build a career in UX/UI design. Learn from the best at https://uxdesignpro.com!!", null));
    Article T13 = articleRepo.save(new Article("Discover the power of virtual reality in entertainment and beyond. Learn more at https://vrworld.com!!", null));
    // ADD ARTICLES TO USERS
    userService.addArticle(Bohr, T1);
    userService.addArticle(Oppenh, T2);
    userService.addArticle(Planck, T3);
    userService.addArticle(Heisenberg, T4);
    userService.addArticle(Dirac, T5);
    userService.addArticle(Godel, T6);
    userService.addArticle(Nietzsche, T7);
    userService.addArticle(Farmer, T8);
    userService.addArticle(Bohr, T9);
    userService.addArticle(Oppenh, T10);
    userService.addArticle(Planck, T11);
    userService.addArticle(Heisenberg, T12);
    userService.addArticle(Dirac, T13);
    
    //Agriculture
    Article A1 = articleRepo.save(new Article("Find the Amazing Opportities of Thessaly. Follow us in this link https://www.uth.gr/en/jobs!!", null));
    Article A2 = articleRepo.save(new Article("Revolutionize agriculture with modern technology. Get involved at https://agritech.com!!", null));
    Article A3 = articleRepo.save(new Article("Breaking: New Sustainable Farming Techniques Show Promising Results for Increased Crop Yields. Read more at https://sustainablefarmingnews.com!!", null));
    Article A4 = articleRepo.save(new Article("Latest Research: The Impact of Climate Change on Global Agriculture Trends. Learn more at https://climateagriculture.com!!", null));
    Article A5 = articleRepo.save(new Article("Join us for the National Agricultural Expo 2024, showcasing the latest innovations in farming technology. More info at https://agriculturalexpo.com!!", null));
    Article A6 = articleRepo.save(new Article("Emergency Alert: Severe Drought Conditions Affecting Major Farming Regions. Find updates and support at https://droughtresponse.com!!", null));
    Article A7 = articleRepo.save(new Article("Promote Your Organic Products! Join our marketplace and reach eco-conscious consumers. More details at https://organicmarketplace.com!!", null));
    Article A8 = articleRepo.save(new Article("Get 20% off on all agricultural supplies this month! Visit us at https://agrisuppliespromo.com!!", null));
    Article A9 = articleRepo.save(new Article("Upcoming Symposium: Innovations in Precision Agriculture – How Technology is Changing the Face of Farming. Register at https://precisionagriculturesymposium.com!!", null));
    Article A10 = articleRepo.save(new Article("Attend the Global Symposium on Sustainable Agriculture Practices to address food security challenges. Details at https://sustainableagriculturesymposium.com!!", null));
    // ADD ARTICLES TO USERS
    userService.addArticle(Farmer, A1);
    userService.addArticle(Farmer, A2);
    userService.addArticle(Farmer, A3);
    userService.addArticle(Farmer, A4);
    userService.addArticle(Farmer, A5);
    userService.addArticle(Farmer, A6);
    userService.addArticle(Farmer, A7);
    userService.addArticle(Farmer, A8);
    userService.addArticle(Farmer, A9);
    userService.addArticle(Farmer, A10);

    //Architecture
    Article Arch1 = articleRepo.save(new Article("Unlock the secrets of sustainable architecture. Dive into green building at https://greenbuild.com!!", null));
    Article Arch2 = articleRepo.save(new Article("Explore the role of sustainable materials in modern architecture at https://sustainablearchmaterials.com!!", null));
    Article Arch3 = articleRepo.save(new Article("Discover cutting-edge advancements in 3D printing technology for architecture. Read more at https://3dprintingarchitecture.com!!", null));
    Article Arch4 = articleRepo.save(new Article("Learn how biomimicry is shaping the future of building designs at https://biomimicryarchitecture.com!!", null));
    Article Arch5 = articleRepo.save(new Article("Investigating the impact of AI in urban planning and smart cities. Explore the research at https://aiurbanplanning.com!!", null));
    Article Arch6 = articleRepo.save(new Article("The science behind energy-efficient buildings: How architecture reduces carbon footprint. Details at https://energyefficientarchitecture.com!!", null));
    Article Arch7 = articleRepo.save(new Article("Explore how parametric design is revolutionizing modern architecture at https://parametricarchitecture.com!!", null));
    // ADD ARTICLES TO USERS
    userService.addArticle(Jackie, Arch1);
    userService.addArticle(Jackie, Arch2);
    userService.addArticle(Jackie, Arch3);
    userService.addArticle(JLo, Arch4);
    userService.addArticle(JLo, Arch5);
    userService.addArticle(WD, Arch6);
    userService.addArticle(WS, Arch7);

    //Finance
    Article F1 = articleRepo.save(new Article("Get the inside scoop on financial technologies and cryptocurrencies at https://fintechrevolution.com!!", null));
    Article F2 = articleRepo.save(new Article("Attend our lecture on financial risk management in a volatile economy. Register now at https://riskmanagementlecture.com!!", null));
    Article F3 = articleRepo.save(new Article("Master corporate finance strategies at our expert-led lecture. Learn more at https://corporatefinancelecture.com!!", null));
    Article F4 = articleRepo.save(new Article("Join us for an exclusive lecture on investment strategies for millennials. Sign up at https://investmentmillennials.com!!", null));
    Article F5 = articleRepo.save(new Article("Breaking: Major Central Banks Collaborate on Digital Currency Initiatives. Read more at https://centralbanksdigital.com!!", null));
    Article F6 = articleRepo.save(new Article("Market Update: Stock Prices Surge Amid Positive Economic Data. Get the latest at https://marketupdate.com!!", null));
    Article F7 = articleRepo.save(new Article("Breaking: New Regulations Proposed for Cryptocurrency Trading Platforms. Learn more at https://cryptoregulationnews.com!!", null));
    Article F8 = articleRepo.save(new Article("Investors React: Tech Stocks Lead Market Gains in Recent Trading Sessions. More details at https://techstocknews.com!!", null));
    Article F9 = articleRepo.save(new Article("Breaking: Global Supply Chain Disruptions Impacting Commodity Prices. Stay updated at https://commoditypricewatch.com!!", null));   
    // ADD ARTICLES TO USERS
    userService.addArticle(Franklin, F1);
    userService.addArticle(Franklin, F2);
    userService.addArticle(Franklin, F3);
    userService.addArticle(Tesla, F4);
    userService.addArticle(Franklin, F5);
    userService.addArticle(Franklin, F6);
    userService.addArticle(Einstein, F7);
    userService.addArticle(Tesla, F8);
    userService.addArticle(Franklin, F9);

    //Literacy
    Article L1 = articleRepo.save(new Article("Enhance your creative writing skills with experts. Learn more at https://creativewriters.com!!", null));
    Article L2 = articleRepo.save(new Article("Discover the transformative power of literacy in 'The Literacy Myth' by Harvey J. Graff. Read more at https://literacymyth.com!!", null));
    Article L3 = articleRepo.save(new Article("Explore Paulo Freire's revolutionary ideas in 'Pedagogy of the Oppressed'. Find insights at https://pedagogyoftheoppressed.com!!", null));
    Article L4 = articleRepo.save(new Article("Unpack the social significance of literacy in 'Literacy in American Lives' by Deborah Brandt. Learn more at https://literacyinamericanlives.com!!", null));
    Article L5 = articleRepo.save(new Article("Delve into the origins of writing with 'Reading the Past: Writing and Image in the Ancient Near East' by C.B.F. Walker. Details at https://readingthepast.com!!", null));
    Article L6 = articleRepo.save(new Article("Inspire a love for reading with 'The Book Whisperer' by Donalyn Miller. Discover tips at https://thebookwhisperer.com!!", null));
    Article L7 = articleRepo.save(new Article("Join us for an exploration of Plato's 'Allegory of the Cave' and its implications for knowledge. More at https://allegoryofthecavelecture.com!!", null));
    Article L8 = articleRepo.save(new Article("Attend a lecture on Aristotle's ethics and the pursuit of the good life. Learn more at https://aristotlesethicslecture.com!!", null));
    Article L9 = articleRepo.save(new Article("Discover the birth of modern philosophy with Descartes in our engaging lecture. Details at https://descartesmodernphilosophy.com!!", null));
    Article L10 = articleRepo.save(new Article("Dive into Jean-Paul Sartre's existentialism and the essence of freedom at https://sartreexistentialismlecture.com!!", null));
    Article L11 = articleRepo.save(new Article("Explore Kant's ethical theories on duty and moral law in our latest lecture. More info at https://kantsmoralphilosophy.com!!", null));
    // ADD ARTICLES TO USERS
    userService.addArticle(Franklin, L1);
    userService.addArticle(Nietzsche, L2);
    userService.addArticle(WD, L3);
    userService.addArticle(Nietzsche, L4);
    userService.addArticle(Mendel, L5);
    userService.addArticle(MorganF, L6);
    userService.addArticle(Curie, L7);
    userService.addArticle(Nietzsche, L8);
    userService.addArticle(Nietzsche, L9);
    userService.addArticle(Galilei, L10);
    userService.addArticle(Nietzsche, L11);    

    //Engineering
    Article E1 = articleRepo.save(new Article("The wonders of renewable energy await. Discover solar, wind, and hydro at https://renewablepower.com!!", null));
    Article E2 =articleRepo.save(new Article("Explore biotechnology breakthroughs that are changing the world. Learn more at https://biotechleaders.com!!", null));
    Article E3 =articleRepo.save(new Article("Explore environmental science and the impact on climate change at https://climatescience.com!!", null));
    Article E4 =articleRepo.save(new Article("Unlock the potential of space exploration! Join us at https://spacexlore.com for the future of space!!", null));
    Article E5 =articleRepo.save(new Article("Step into the future of renewable construction materials at https://greenconstruction.com!!", null));
    Article E6 =articleRepo.save(new Article("Breaking: New Engineering Breakthrough Promises to Revolutionize Renewable Energy Storage. Read more at https://engineeringnews.com!!", null));
    Article E7 =articleRepo.save(new Article("Latest Developments: Advances in AI-Driven Robotics Changing the Manufacturing Landscape. Discover more at https://roboticsengineering.com!!", null));
    Article E8 =articleRepo.save(new Article("Join us for the International Conference on Sustainable Engineering Practices 2024. More info at https://sustainableengineeringconference.com!!", null));
    Article E9 =articleRepo.save(new Article("Don't miss the Annual Engineering Innovations Expo showcasing cutting-edge technologies. Details at https://engineeringexpo.com!!", null));
    Article E10 =articleRepo.save(new Article("Get 15% off on all Engineering Software Tools for Students! Visit https://engineeringsoftwarepromo.com!!", null));
    Article E11 =articleRepo.save(new Article("Enroll in Our New Online Engineering Certification Courses and Boost Your Career! Learn more at https://onlineengineeringcourses.com!!", null));
    Article E12 =articleRepo.save(new Article("Discover the Top Engineering Trends to Watch in 2024. Explore the article at https://engineeringtrends.com!!", null));
    Article E13 =articleRepo.save(new Article("New Study: The Impact of Emerging Technologies on Civil Engineering Projects. Read the findings at https://civilengineeringstudy.com!!", null));
    // ADD ARTICLES TO USERS
    userService.addArticle(Babbage, E1);
    userService.addArticle(Tesla, E2);
    userService.addArticle(Faraday, E3);
    userService.addArticle(Oppenh, E4);
    userService.addArticle(Dirac, E5);
    userService.addArticle(Heisenberg, E6);
    userService.addArticle(Feynman, E7);
    userService.addArticle(Hubble, E8);
    userService.addArticle(Tesla, E9);
    userService.addArticle(Crick, E10);
    userService.addArticle(Tesla, E11);
    userService.addArticle(Tesla, E12);
    userService.addArticle(Fermi, E13);

    //Marketing
    Article M1 = articleRepo.save(new Article("Master the art of digital marketing with us. Dive in at https://digitalmarketingpro.com!!", null));
    Article M2 = articleRepo.save(new Article("Discover how digital transformation is changing industries at https://digitalchange.com!!", null));
    Article M3 = articleRepo.save(new Article("Join the future of e-commerce and online business at https://ecommercesuccess.com!!", null));
    Article M4 = articleRepo.save(new Article("Learn how AI is transforming customer service. Discover more at https://aicustomerservice.com!!", null));
    Article M5 = articleRepo.save(new Article("Breaking: Major Cities Adopt New Strategies to Boost Tourism Post-Pandemic. Read more at https://tourismnews.com!!", null));
    Article M6 = articleRepo.save(new Article("Latest Trends: The Rise of Eco-Tourism and Its Impact on Global Travel. Discover insights at https://ecotourismtrends.com!!", null));
    Article M7 = articleRepo.save(new Article("Join us for the International Tourism Marketing Conference 2024, where industry leaders share innovative strategies. More info at https://tourismconference.com!!", null));
    Article M8 = articleRepo.save(new Article("Don't miss the Travel Expo 2024, showcasing the best travel destinations and packages. Details at https://travelexpo.com!!", null));
    Article M9 = articleRepo.save(new Article("Explore New Destinations! Book your dream vacation now and get 30% off on all packages. Visit https://vacationpromo.com!!", null));
    Article M10 = articleRepo.save(new Article("Limited Time Offer: Free Travel Insurance with Every International Flight Booking! Learn more at https://travelinsurancepromo.com!!", null));
    Article M11 = articleRepo.save(new Article("Discover Effective Marketing Strategies for the Travel Industry in Our Latest Report. Download at https://tourismmarketinginsights.com!!", null));
    Article M12 = articleRepo.save(new Article("New Study Reveals Social Media's Impact on Travel Decisions. Explore the findings at https://socialmediatourism.com!!", null));
    // ADD ARTICLES TO USERS
    userService.addArticle(JLo, M1);
    userService.addArticle(Messi, M2);
    userService.addArticle(WS, M3);
    userService.addArticle(WD, M4);
    userService.addArticle(Jackie, M5);
    userService.addArticle(Napoleon, M6);
    userService.addArticle(MorganF, M7);
    userService.addArticle(DaVinci, M8);
    userService.addArticle(Einstein, M9);
    userService.addArticle(Hawking, M10);
    userService.addArticle(Fleming, M11);
    userService.addArticle(GrahamBell, M12);    

    //Law
    Article Law1 = articleRepo.save(new Article("Artificial Intelligence and the future of law. See more at https://lawtechai.com!!", null));
    Article Law2 = articleRepo.save(new Article("Don’t miss our seminar on privacy laws in the age of data breaches. Learn more at https://privacylawseminar.com!!", null));
    Article Law3 = articleRepo.save(new Article("Learn about environmental law advancements at our upcoming seminar. More info at https://envlawseminar.com!!", null));
    Article Law4 = articleRepo.save(new Article("Attend our legal forensics seminar: The future of criminal investigations. Register at https://forensicslawseminar.com!!", null));
    Article Law5 = articleRepo.save(new Article("Upcoming seminar: Blockchain and smart contracts in legal practice. Secure your spot at https://blockchainlawseminar.com!!", null));
    Article Law6 = articleRepo.save(new Article("Breaking: Landmark Supreme Court Ruling on Digital Privacy Rights. Read more at https://lawnews.com!!", null));
    Article Law7 = articleRepo.save(new Article("Latest Developments: New Legislation on Cybersecurity Compliance Set to Impact Businesses. Discover the details at https://cyberlawnews.com!!", null));
    Article Law8 = articleRepo.save(new Article("Join us for the Annual Legal Summit 2024, where leading experts discuss the future of law. More info at https://legalsummit.com!!", null));
    Article Law9 = articleRepo.save(new Article("Don't miss the International Conference on Criminal Justice Reform, focusing on innovative legal practices. Details at https://criminaljusticereform.com!!", null));
    Article Law10 = articleRepo.save(new Article("Special Offer: Free Consultation for New Clients at Our Law Firm! Schedule your appointment at https://lawfirmconsultation.com!!", null));
    Article Law11 = articleRepo.save(new Article("Enroll in our Online Legal Courses and Get 20% Off Your First Course! Learn more at https://onlinelegalcourses.com!!", null));
    // ADD ARTICLES TO USERS
    userService.addArticle(Jackie, Law1);
    userService.addArticle(JLo, Law2);
    userService.addArticle(WD, Law3);
    userService.addArticle(WS, Law4);
    userService.addArticle(Napoleon, Law5);
    userService.addArticle(MorganF, Law6);
    userService.addArticle(Messi, Law7);
    userService.addArticle(DaVinci, Law8);
    userService.addArticle(Einstein, Law9);
    userService.addArticle(Hawking, Law10);
    userService.addArticle(Fleming, Law11);    

    //Medicine
    Article Med1 = articleRepo.save(new Article("The next wave in biomedical engineering is here. Get involved at https://biomedfuture.com!!", null));
    Article Med2 = articleRepo.save(new Article("Get the latest on breakthroughs in medical research at https://medresearchtoday.com!!", null));
    Article Med3 = articleRepo.save(new Article("Stem cell therapy: Unlocking regenerative medicine at https://stemcellbreakthroughs.com!!", null));
    Article Med4 = articleRepo.save(new Article("Explore the future of robotic surgery. Learn more at https://roboticsurgerytoday.com!!", null));
    Article Med5 = articleRepo.save(new Article("Enhance mental health treatments with digital therapeutics at https://mentalhealthtech.com!!", null));
    Article Med6 = articleRepo.save(new Article("CRISPR technology and the promise of gene editing in medicine. Discover more at https://crisprmedicine.com!!", null));
    Article Med7 = articleRepo.save(new Article("Discover the latest in cardiovascular treatments at https://heartcaretoday.com!!", null));
    Article Med8 = articleRepo.save(new Article("Advancements in fertility treatments: The science of hope at https://fertilityscience.com!!", null));
    Article Med9 = articleRepo.save(new Article("How 3D printing is revolutionizing prosthetics and implants at https://med3dprint.com!!", null));
    Article Med10 = articleRepo.save(new Article("Fight antibiotic resistance with innovative drug discoveries at https://newantibiotics.com!!", null));
    // ADD ARTICLES TO USERS
    userService.addArticle(Jackie, Med1);
    userService.addArticle(JLo, Med2);
    userService.addArticle(WD, Med3);
    userService.addArticle(WS, Med4);
    userService.addArticle(WSJr, Med5);
    userService.addArticle(MorganF, Med6);
    userService.addArticle(Messi, Med7);
    userService.addArticle(MuhammedALi, Med8);
    userService.addArticle(Washington, Med9);
    userService.addArticle(Napoleon, Med10);    

    //Education
    Article Ed1 = articleRepo.save(new Article("Unlock your potential in the world of e-learning technologies at https://elearninginnovations.com!!", null));
    Article Ed2 = articleRepo.save(new Article("Breaking: New National Curriculum Guidelines Released to Enhance Student Learning Outcomes. Read more at https://educationnews.com!!", null));
    Article Ed3 = articleRepo.save(new Article("Latest Study: The Impact of Technology on Modern Education Revealed. Discover findings at https://educationtechstudy.com!!", null));
    Article Ed4 = articleRepo.save(new Article("Join us for the Annual Education Conference 2024, focusing on innovative teaching methods. More info at https://educationconference.com!!", null));
    Article Ed5 = articleRepo.save(new Article("Don't miss the International Summit on Education Reform, bringing together experts to discuss future trends. Details at https://educationreformsummit.com!!", null));
    Article Ed6 = articleRepo.save(new Article("Emergency Alert: School Closures Due to Severe Weather Conditions in Several Regions. Find updates at https://schoolclosures.com!!", null));
    Article Ed7 = articleRepo.save(new Article("Enroll Now! Get 50% off on your first online course at our educational platform. Visit https://onlinelearningpromo.com!!", null));
    Article Ed8 = articleRepo.save(new Article("Discover our new tutoring services and get a free trial lesson. Learn more at https://tutoringservices.com!!", null));
    Article Ed9 = articleRepo.save(new Article("Upgrade your study materials! 20% off all educational resources this month only. Shop at https://educationalresourcespromo.com!!", null));
    Article Ed10 = articleRepo.save(new Article("Upcoming Lecture: 'The Future of Education: Embracing Digital Learning Environments' - Join us for insights. Register at https://digitallearninglecture.com!!", null));
    Article Ed11 = articleRepo.save(new Article("Attend a lecture on 'The Importance of Emotional Intelligence in Education' to learn about its impact on student success. More info at https://emotionalintelligenceineducation.com!!", null));
    // ADD ARTICLES TO USERS
    userService.addArticle(Jackie, Ed1);
    userService.addArticle(JLo, Ed2);
    userService.addArticle(WD, Ed3);
    userService.addArticle(WS, Ed4);
    userService.addArticle(WSJr, Ed5);
    userService.addArticle(MorganF, Ed6);
    userService.addArticle(Messi, Ed7);
    userService.addArticle(MuhammedALi, Ed8);
    userService.addArticle(Washington, Ed9);
    userService.addArticle(Napoleon, Ed10);
    userService.addArticle(GrahamBell, Ed11);

    //Soft Skills and Others:
    //Communication Skills Articles
    Article S1 = articleRepo.save(new Article("Master Effective Communication Skills for Workplace Success. Learn more at https://communicationsuccess.com!!", null));
    Article S2 = articleRepo.save(new Article("The Importance of Non-Verbal Communication: How Body Language Impacts Your Message. Read more at https://nonverbalcommunication.com!!", null));
    Article S3 = articleRepo.save(new Article("Public Speaking Tips to Boost Your Confidence and Deliver Powerful Presentations. Find tips at https://publicspeakingmastery.com!!", null));
    Article S4 = articleRepo.save(new Article("How Active Listening Can Transform Your Relationships and Career. Discover techniques at https://activelistening.com!!", null));
    Article S5 = articleRepo.save(new Article("Breaking Down Barriers: Effective Cross-Cultural Communication in Global Teams. Learn more at https://crossculturalcommunication.com!!", null));
    Article S6 = articleRepo.save(new Article("Why Emotional Intelligence is the Key to Leadership and Career Success. Read more at https://emotionalintelligenceleadership.com!!", null));
    Article S7 = articleRepo.save(new Article("The Top 5 Soft Skills Every Professional Should Develop in 2024. Find out at https://topsoftskills.com!!", null));
    Article S8 = articleRepo.save(new Article("Critical Thinking and Problem-Solving: How to Excel in Complex Situations. Learn techniques at https://criticalthinking101.com!!", null));
    // ADD ARTICLES TO USERS
    userService.addArticle(Jackie, S1);
    userService.addArticle(JLo, S2);
    userService.addArticle(WD, S3);
    userService.addArticle(WS, S4);
    userService.addArticle(WSJr, S5);
    userService.addArticle(MorganF, S6);
    userService.addArticle(Messi, S7);
    userService.addArticle(MuhammedALi, S8);

    // ADD COMMENTS (for Bohr and Planck)
    // Create comments and save them to the repository
    Comment comm1 = new Comment();
    Comment comm2 = new Comment();
    Comment comm3 = new Comment();
    Comment comm4 = new Comment();
    comm1.setContent("Nice Post");
    comm2.setContent("Great Post");
    comm3.setContent("Interesting...");
    comm4.setContent("DM me, I have a question");
    commentRepo.save(comm1);
    commentRepo.save(comm2);
    commentRepo.save(comm3);
    commentRepo.save(comm4);
    //Assosiate comments with articles/users
    userService.addComment(T3, Bohr, comm1);
    userService.addComment(T11, Bohr, comm2);
    userService.addComment(T1, Planck, comm3);
    userService.addComment(T9, Planck, comm4);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////  LIKES  ////////////////////////////////////////////////////////////

    // ADD LIKES ON ARTICLES
    Bohr.likeArticle(T3); Bohr.likeArticle(T11); Planck.likeArticle(T1); Planck.likeArticle(T9);
    Mendel.likeArticle(E5); Lincoln.likeArticle(L10); Turing.likeArticle(Med8); Musk.likeArticle(Med2); Oppenh.likeArticle(Ed4);
    Pasteur.likeArticle(E11); Woodrow.likeArticle(F6); WS.likeArticle(Ed3); Nietzsche.likeArticle(A10); Babbage.likeArticle(A1);
    Kepler.likeArticle(Ed3); JLo.likeArticle(L1); Babbage.likeArticle(F9); MuhammedALi.likeArticle(A10); WSJr.likeArticle(Law1);
    Fermi.likeArticle(Ed3); Napoleon.likeArticle(Ed8); Turing.likeArticle(F7); Crick.likeArticle(Law2); Musk.likeArticle(Ed8); 
    Feynman.likeArticle(E3); Woodrow.likeArticle(Med8); Shapiro.likeArticle(Law7); Messi.likeArticle(Law3); MorganF.likeArticle(E10); 
    Hippocrates.likeArticle(E6); WSJr.likeArticle(E13); Einstein.likeArticle(Arch7); Mendel.likeArticle(Ed4); Einstein.likeArticle(F8); 
    Godel.likeArticle(Arch6); Fermi.likeArticle(Law9); Turing.likeArticle(F8); Nietzsche.likeArticle(Arch5); GrahamBell.likeArticle(Law2); 
    MuhammedALi.likeArticle(Arch5); Tesla.likeArticle(E1); Lovelace.likeArticle(Arch4); Farmer.likeArticle(F2); Shapiro.likeArticle(A2); 
    Jackie.likeArticle(Ed4); Darwin.likeArticle(E8); Kepler.likeArticle(Law5); Musk.likeArticle(L4); Hawking.likeArticle(F4);
    ASmith.likeArticle(F9); Darwin.likeArticle(Arch3); Lincoln.likeArticle(F5); Watson.likeArticle(F2); DaVinci.likeArticle(Arch1); 
    Washington.likeArticle(Law6); Messi.likeArticle(L6); Hawking.likeArticle(Med5); JLo.likeArticle(F4); Fermi.likeArticle(E5); 
    Marx.likeArticle(F7); Turing.likeArticle(L10); Fleming.likeArticle(A10); Napoleon.likeArticle(E8); Fermi.likeArticle(Law3);
    Morgan.likeArticle(E10); Fleming.likeArticle(E1); Papanikolaou.likeArticle(A1); WD.likeArticle(A1); Godel.likeArticle(F3); 
    Oppenh.likeArticle(F2); Marx.likeArticle(Arch7); Crick.likeArticle(Law4); Watson.likeArticle(Ed3); DaVinci.likeArticle(L10); 
    WGary.likeArticle(E9); Washington.likeArticle(F1); Dirac.likeArticle(E8); Lincoln.likeArticle(Law9); Jackie.likeArticle(Law1); 
    Farmer.likeArticle(E11); Heisenberg.likeArticle(Ed3); Shapiro.likeArticle(L9); Turing.likeArticle(E6); Fleming.likeArticle(Arch5);
    WS.likeArticle(L6); Fleming.likeArticle(E6); Nietzsche.likeArticle(Ed8); GrahamBell.likeArticle(Law4); Marx.likeArticle(F1);
    Napoleon.likeArticle(Med10); Lovelace.likeArticle(L3); Godel.likeArticle(E12); Heisenberg.likeArticle(F6); Woodrow.likeArticle(Arch5);
    Rutherford.likeArticle(E11); MuhammedALi.likeArticle(L1); Napoleon.likeArticle(Med3); Messi.likeArticle(A3); Curie.likeArticle(Law6); 
    Morgan.likeArticle(Law1); WGary.likeArticle(F4); Faraday.likeArticle(E2); Watson.likeArticle(Law8); Oppenh.likeArticle(E11); 
    WS.likeArticle(Ed2); Napoleon.likeArticle(Law11); Mendel.likeArticle(Med6); Shapiro.likeArticle(Med9); Franklin.likeArticle(Ed2);
    Kepler.likeArticle(A5); Newton.likeArticle(Med6); MorganF.likeArticle(Law9); JLo.likeArticle(E13); Fermi.likeArticle(Ed2); 
    GrahamBell.likeArticle(Ed1); Franklin.likeArticle(Arch2); MuhammedALi.likeArticle(Ed7); Messi.likeArticle(L8); Lovelace.likeArticle(Ed8);
    Papanikolaou.likeArticle(Law7); Nietzsche.likeArticle(Arch3); WS.likeArticle(A3); Galilei.likeArticle(Ed5); Franklin.likeArticle(A3);
    WSJr.likeArticle(L2); Feynman.likeArticle(L9); Crick.likeArticle(L2); Godel.likeArticle(Arch1); GrahamBell.likeArticle(F4); 
    Watson.likeArticle(L6); Galilei.likeArticle(A1); Hippocrates.likeArticle(Med10); DaVinci.likeArticle(Ed8); Hubble.likeArticle(E9); 
    Crick.likeArticle(L11); Darwin.likeArticle(Law7); Einstein.likeArticle(L4); Tesla.likeArticle(Law5); Oppenh.likeArticle(L7); 
    Bohr.likeArticle(L5); Messi.likeArticle(F7); Shapiro.likeArticle(Ed3); Oppenh.likeArticle(F1); Nietzsche.likeArticle(L9);
    Tesla.likeArticle(E5); Papanikolaou.likeArticle(Law5); Woodrow.likeArticle(Law7); WD.likeArticle(Law5); Napoleon.likeArticle(Ed3);
    Oppenh.likeArticle(L2); WD.likeArticle(A9); JLo.likeArticle(E4); Darwin.likeArticle(Ed7); Feynman.likeArticle(Med9); 
    Heisenberg.likeArticle(Arch5); Turing.likeArticle(Arch3); Hippocrates.likeArticle(Ed2); Oppenh.likeArticle(Arch5); 
    Kepler.likeArticle(F7); Washington.likeArticle(Ed7); Hippocrates.likeArticle(E2); Hubble.likeArticle(Med5); 
    Marx.likeArticle(L4); GrahamBell.likeArticle(Med9); Pasteur.likeArticle(Law3); Woodrow.likeArticle(F4); Babbage.likeArticle(F3);
    Feynman.likeArticle(A3); JLo.likeArticle(F8); Crick.likeArticle(L5); Franklin.likeArticle(Arch7); Hippocrates.likeArticle(F5); 
    Fermi.likeArticle(Law6); Shapiro.likeArticle(F8); Fleming.likeArticle(Arch4); ASmith.likeArticle(Law2); Rutherford.likeArticle(A8); 
    Mendel.likeArticle(E3); Franklin.likeArticle(Arch1); DaVinci.likeArticle(A10); Hubble.likeArticle(A1); Newton.likeArticle(E3); 
    Musk.likeArticle(Law3); WD.likeArticle(Arch6); Lovelace.likeArticle(E7); Feynman.likeArticle(Ed3); WGary.likeArticle(L4); 
    WSJr.likeArticle(E11); Lovelace.likeArticle(E6); GrahamBell.likeArticle(L5); Washington.likeArticle(E10); Darwin.likeArticle(L6); 
    Godel.likeArticle(Med2); Planck.likeArticle(F2); Jackie.likeArticle(E3); WGary.likeArticle(Ed7); Franklin.likeArticle(Med5); 
    Watson.likeArticle(Med8); Hawking.likeArticle(A6); Jackie.likeArticle(L3); WS.likeArticle(Med7); Bohr.likeArticle(Med10); 
    Crick.likeArticle(L4); Papanikolaou.likeArticle(E2); Woodrow.likeArticle(A4); WGary.likeArticle(E5); Crick.likeArticle(Ed1); 
    Hawking.likeArticle(A5); Galilei.likeArticle(A7); Marx.likeArticle(Med10); Faraday.likeArticle(Arch2); Farmer.likeArticle(A2); 
    Fleming.likeArticle(Ed2); Mendel.likeArticle(L5); Musk.likeArticle(Arch6); Musk.likeArticle(A4); Hippocrates.likeArticle(Law5); 
    Papanikolaou.likeArticle(F3); Nietzsche.likeArticle(E4); Pasteur.likeArticle(Ed2); Bohr.likeArticle(Law10); WD.likeArticle(F5); 
    Planck.likeArticle(Law9); Papanikolaou.likeArticle(A3); Euler.likeArticle(L1); Faraday.likeArticle(F3); Galilei.likeArticle(Law3); 
    Morgan.likeArticle(L3); Turing.likeArticle(F2); Pasteur.likeArticle(E2); Rutherford.likeArticle(A2); Crick.likeArticle(F4); 
    Copernicus.likeArticle(Law1); Copernicus.likeArticle(Ed2); Darwin.likeArticle(F5); ASmith.likeArticle(E11); Musk.likeArticle(Ed2); 
    MuhammedALi.likeArticle(Law5); Pasteur.likeArticle(F3); Shapiro.likeArticle(F3); MuhammedALi.likeArticle(E5); Tesla.likeArticle(E4); 
    Crick.likeArticle(E1); Mendel.likeArticle(L4); Curie.likeArticle(Ed5); WGary.likeArticle(Arch6); Morgan.likeArticle(Med2); WD.likeArticle(F6); 
    Rutherford.likeArticle(Law3); Watson.likeArticle(Med5); ASmith.likeArticle(Law8); Musk.likeArticle(F2); WS.likeArticle(Med2); 
    Curie.likeArticle(Med3); WD.likeArticle(E11); Galilei.likeArticle(Arch6); Faraday.likeArticle(E11); Darwin.likeArticle(E12); 
    Babbage.likeArticle(Med4); GrahamBell.likeArticle(E10); Farmer.likeArticle(A1); Fleming.likeArticle(Law9); Feynman.likeArticle(Arch6); 
    Darwin.likeArticle(Law9); GrahamBell.likeArticle(Ed4); Pasteur.likeArticle(E12); Fermi.likeArticle(L5); Dirac.likeArticle(F3); 
    Shapiro.likeArticle(E12); WSJr.likeArticle(Law11); ASmith.likeArticle(Arch1); Shapiro.likeArticle(Law9); Woodrow.likeArticle(Law9); 
    Shapiro.likeArticle(L8); Hubble.likeArticle(A7); Jackie.likeArticle(Law6); Curie.likeArticle(A6); Godel.likeArticle(Ed5); 
    Godel.likeArticle(L9); WGary.likeArticle(Arch2); Messi.likeArticle(E8); GrahamBell.likeArticle(Med3); Galilei.likeArticle(Ed2); 
    Galilei.likeArticle(A5); JLo.likeArticle(Med8); Fleming.likeArticle(E9); MuhammedALi.likeArticle(L10); Papanikolaou.likeArticle(F8); 
    Planck.likeArticle(Med4); MorganF.likeArticle(Med5); Morgan.likeArticle(L4); MuhammedALi.likeArticle(A4); Hubble.likeArticle(Law3); 
    Babbage.likeArticle(E3); Oppenh.likeArticle(Med3); ASmith.likeArticle(Med4); Babbage.likeArticle(Arch2); Lincoln.likeArticle(E13); 
    Newton.likeArticle(Med10); Woodrow.likeArticle(A9); Washington.likeArticle(Law5); Galilei.likeArticle(F3); Messi.likeArticle(E1); 
    MuhammedALi.likeArticle(Law2); Galilei.likeArticle(A3); Copernicus.likeArticle(E9); Papanikolaou.likeArticle(F1); Turing.likeArticle(Ed4); 
    Bohr.likeArticle(A7); Rutherford.likeArticle(Ed2); Napoleon.likeArticle(Law8); WS.likeArticle(E8); Marx.likeArticle(Ed7); 
    Papanikolaou.likeArticle(A10); Woodrow.likeArticle(F8); Jackie.likeArticle(Ed5); Mendel.likeArticle(Law10); GrahamBell.likeArticle(E2); 
    GrahamBell.likeArticle(Arch2); Washington.likeArticle(Law3); Papanikolaou.likeArticle(L2); Darwin.likeArticle(L7); Watson.likeArticle(L5); 
    WS.likeArticle(F4); Euler.likeArticle(Law1); Oppenh.likeArticle(E13); Dirac.likeArticle(Law8); Oppenh.likeArticle(A8); Jackie.likeArticle(F9); 
    Shapiro.likeArticle(L7); Farmer.likeArticle(L1); Farmer.likeArticle(Ed3); Darwin.likeArticle(Med4); Watson.likeArticle(Ed8); 
    Copernicus.likeArticle(Arch1); Marx.likeArticle(Law3); Jackie.likeArticle(Law10); Washington.likeArticle(Arch4); Papanikolaou.likeArticle(E7); 
    WS.likeArticle(E10); Feynman.likeArticle(Ed8); JLo.likeArticle(A10); Jackie.likeArticle(Law11); Dirac.likeArticle(F8); Einstein.likeArticle(A6); 
    DaVinci.likeArticle(Med3); Shapiro.likeArticle(A10); Bohr.likeArticle(Arch4); Hippocrates.likeArticle(Ed8); Turing.likeArticle(A6); 
    Messi.likeArticle(Med9); Musk.likeArticle(F1); Einstein.likeArticle(A5); WGary.likeArticle(Law8); MuhammedALi.likeArticle(Med2); 
    Washington.likeArticle(L6); Lovelace.likeArticle(Ed2); Shapiro.likeArticle(Med7); Papanikolaou.likeArticle(L3); Heisenberg.likeArticle(Law7); 
    Rutherford.likeArticle(Ed6); Curie.likeArticle(Law5); Pasteur.likeArticle(Law2); Galilei.likeArticle(Law8); Fleming.likeArticle(E3); Newton.likeArticle(F5); 
    Crick.likeArticle(A7); Nietzsche.likeArticle(E11); Planck.likeArticle(Ed8); MuhammedALi.likeArticle(E11); Crick.likeArticle(E5);
    Euler.likeArticle(Ed6); Lovelace.likeArticle(F5); ASmith.likeArticle(Ed8); Rutherford.likeArticle(Med8); Mendel.likeArticle(L1); 
    Dirac.likeArticle(Med7); Turing.likeArticle(A3); Nietzsche.likeArticle(E9); Woodrow.likeArticle(Arch2); Mendel.likeArticle(Ed6); 
    Galilei.likeArticle(Ed4); WGary.likeArticle(F1); Heisenberg.likeArticle(Law5); Feynman.likeArticle(Arch1); Watson.likeArticle(F4);
    Morgan.likeArticle(A8); Godel.likeArticle(A7); Messi.likeArticle(L1); Rutherford.likeArticle(A9); Hawking.likeArticle(Med2);
    Heisenberg.likeArticle(E5); Bohr.likeArticle(Arch2); Musk.likeArticle(L3); Hubble.likeArticle(Ed6); Einstein.likeArticle(Law9);
    Newton.likeArticle(E4); Lovelace.likeArticle(Law9); Feynman.likeArticle(A10); Feynman.likeArticle(Med5); Dirac.likeArticle(L3);
    Hawking.likeArticle(Ed8); Darwin.likeArticle(Law4); Crick.likeArticle(Arch6);


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////  JOBS  /////////////////////////////////////////////////////////

    // CREATE JOBS
    Job J1 = jobRepo.save(new Job("Agricultural Scientist", true, "Crop management, Soil analysis", 1200, true));
    Job J2 = jobRepo.save(new Job("Farm Manager", false, "Farm operations, Animal husbandry", 900, true));
    Job J3 = jobRepo.save(new Job("Agricultural Equipment Operator", true, "Tractor operation, Maintenance", 750, false));
    Job J4 = jobRepo.save(new Job("Agriculture Technician", true, "Soil sampling, Data collection", 800, true));
    Job J5 = jobRepo.save(new Job("Horticulturist", true, "Plant biology, Greenhouse management", 1100, true));
    Job J6 = jobRepo.save(new Job("Agronomy Specialist", true, "Crop rotation, Pest control", 950, true));
    Job J7 = jobRepo.save(new Job("Sustainable Agriculture Consultant", false, "Sustainable farming practices, Organic certification", 1300, true));
    Job J8 = jobRepo.save(new Job("Agricultural Engineer", true, "Irrigation systems, Machinery design", 1400, true));
    Job J9 = jobRepo.save(new Job("Farm Supervisor", false, "Farm logistics, Team management", 850, false));
    Job J10 = jobRepo.save(new Job("Soil Scientist", true, "Soil chemistry, Land evaluation", 1150, true));
    Job J11 = jobRepo.save(new Job("Animal Nutritionist", true, "Livestock feeding, Dietary planning", 1000, true));
    Job J12 = jobRepo.save(new Job("Agricultural Inspector", true, "Regulation compliance, Health standards", 900, false));
    Job J13 = jobRepo.save(new Job("Greenhouse Manager", true, "Greenhouse technology, Crop optimization", 850, true));
    Job J14 = jobRepo.save(new Job("Aquaculture Specialist", false, "Fish farming, Water quality management", 950, true));
    Job J15 = jobRepo.save(new Job("Agroforestry Consultant", true, "Forest management, Agroforestry techniques", 1200, true));
    Job J16 = jobRepo.save(new Job("Rural Development Officer", false, "Community engagement, Rural economics", 1100, true));
    Job J17 = jobRepo.save(new Job("Agriculture Marketing Manager", true, "Supply chain, Marketing strategies", 1300, true));
    Job J18 = jobRepo.save(new Job("Irrigation Specialist", true, "Water management, Irrigation system design", 1000, true));
    Job J19 = jobRepo.save(new Job("Poultry Farm Manager", true, "Poultry farming, Animal welfare", 900, true));
    Job J20 = jobRepo.save(new Job("Organic Farm Manager", true, "Organic farming, Sustainability", 950, false));
    Job J21 = jobRepo.save(new Job("Full Stack Developer", true, "Java, Spring, Angular", 1200, true));
    Job J22 = jobRepo.save(new Job("Data Analyst", true, "Python, MS Office", 1100, true));
    Job J23 = jobRepo.save(new Job("DevOps Engineer", false, "CI/CD, Docker, Jenkins", 1500, true));
    Job J24 = jobRepo.save(new Job("Cybersecurity Specialist", true, "Firewall, Network security, Python", 1600, true));
    Job J25 = jobRepo.save(new Job("Machine Learning Engineer", true, "TensorFlow, Python", 1700, true));
    Job J26 = jobRepo.save(new Job("Software Architect", true, "Java, Microservices, Cloud", 2000, true));
    Job J27 = jobRepo.save(new Job("Backend Developer", false, "Node.js, Express, MongoDB", 1400, true));
    Job J28 = jobRepo.save(new Job("Cloud Solutions Architect", true, "AWS, Azure, Google Cloud", 1900, true));
    Job J29 = jobRepo.save(new Job("Blockchain Developer", true, "Solidity, Smart contracts", 1600, true));
    Job J30 = jobRepo.save(new Job("Network Administrator", true, "Routing, Switching, Cisco", 1200, false));
    Job J31 = jobRepo.save(new Job("Mobile App Developer", true, "iOS, Swift, Android", 1300, true));
    Job J32 = jobRepo.save(new Job("Game Developer", true, "Unity, Unreal Engine, C#", 1500, true));
    Job J33 = jobRepo.save(new Job("QA Tester", false, "Manual Testing, Automation", 1000, true));
    Job J34 = jobRepo.save(new Job("IT Support Specialist", true, "Technical support, Troubleshooting", 900, false));
    Job J35 = jobRepo.save(new Job("UI/UX Designer", true, "Figma, Adobe XD", 1400, true));
    Job J36 = jobRepo.save(new Job("Robotics Engineer", true, "C++, Automation, Robotics", 2000, true));
    Job J37 = jobRepo.save(new Job("Database Administrator", true, "SQL, MySQL, Oracle", 1300, false));
    Job J38 = jobRepo.save(new Job("AI Researcher", true, "Artificial Intelligence, Neural networks", 2100, true));
    Job J39 = jobRepo.save(new Job("IoT Specialist", true, "IoT systems, Embedded systems", 1600, true));
    Job J40 = jobRepo.save(new Job("Frontend Developer", true, "React, JavaScript, CSS", 1200, true));
    Job J41 = jobRepo.save(new Job("General Practitioner", true, "Patient diagnosis, Medical treatments", 1800, true));
    Job J42 = jobRepo.save(new Job("Nurse", true, "Patient care, Medication administration", 1000, true));
    Job J43 = jobRepo.save(new Job("Surgeon", true, "Surgical procedures, Patient care", 2500, true));
    Job J44 = jobRepo.save(new Job("Pharmacist", true, "Prescription management, Drug safety", 1200, true));
    Job J45 = jobRepo.save(new Job("Dentist", false, "Dental care, Oral hygiene", 2000, true));
    Job J46 = jobRepo.save(new Job("Pediatrician", true, "Child care, Pediatric treatments", 1600, true));
    Job J47 = jobRepo.save(new Job("Medical Researcher", true, "Clinical trials, Medical innovations", 1900, true));
    Job J48 = jobRepo.save(new Job("Anesthesiologist", true, "Anesthesia management, Surgery support", 2200, true));
    Job J49 = jobRepo.save(new Job("Physician Assistant", true, "Patient diagnosis, Treatment planning", 1300, true));
    Job J50 = jobRepo.save(new Job("Radiologist", true, "Medical imaging, Diagnostic radiology", 2100, true));
    Job J51 = jobRepo.save(new Job("Optometrist", true, "Eye care, Vision correction", 1500, true));
    Job J52 = jobRepo.save(new Job("Psychiatrist", true, "Mental health, Therapy", 1700, true));
    Job J53 = jobRepo.save(new Job("Medical Lab Technician", false, "Lab testing, Blood analysis", 1000, false));
    Job J54 = jobRepo.save(new Job("Orthopedic Surgeon", true, "Bone surgeries, Trauma care", 2600, true));
    Job J55 = jobRepo.save(new Job("Cardiologist", true, "Heart health, Diagnostics", 2300, true));
    Job J56 = jobRepo.save(new Job("Obstetrician", true, "Pregnancy care, Childbirth", 1900, true));
    Job J57 = jobRepo.save(new Job("Dermatologist", true, "Skin care, Skin diseases", 1700, true));
    Job J58 = jobRepo.save(new Job("Medical Coding Specialist", false, "Coding, Billing", 1200, false));
    Job J59 = jobRepo.save(new Job("Emergency Room Nurse", true, "Patient triage, Emergency care", 1400, true));
    Job J60 = jobRepo.save(new Job("Medical Equipment Technician", true, "Equipment maintenance, Diagnostics", 1000, false));
    Job J61 = jobRepo.save(new Job("Literary Editor", true, "Proofreading, Copyediting", 1200, true));
    Job J62 = jobRepo.save(new Job("Content Writer", true, "Blog writing, SEO content", 800, true));
    Job J63 = jobRepo.save(new Job("Book Publisher", false, "Manuscript evaluation, Publishing", 1500, true));
    Job J64 = jobRepo.save(new Job("Creative Writer", true, "Fiction, Non-fiction, Storytelling", 1000, true));
    Job J65 = jobRepo.save(new Job("Literature Teacher", true, "Teaching, Lesson planning", 1300, true));
    Job J66 = jobRepo.save(new Job("Literary Critic", true, "Reviewing, Criticism", 1100, true));
    Job J67 = jobRepo.save(new Job("Poet", false, "Poetry writing, Literary events", 700, true));
    Job J68 = jobRepo.save(new Job("Translator", true, "Language skills, Translating books", 1000, true));
    Job J69 = jobRepo.save(new Job("Publishing Assistant", false, "Manuscript preparation, Copyediting", 900, true));
    Job J70 = jobRepo.save(new Job("Journalist", true, "News writing, Reportage", 1200, true));
    Job J71 = jobRepo.save(new Job("Screenwriter", true, "Scriptwriting, Storytelling", 1400, true));
    Job J72 = jobRepo.save(new Job("Book Reviewer", true, "Critical analysis, Literary reviews", 800, true));
    Job J73 = jobRepo.save(new Job("Children's Book Author", true, "Creative writing, Storytelling", 900, true));
    Job J74 = jobRepo.save(new Job("Playwright", true, "Drama writing, Theater", 1100, true));
    Job J75 = jobRepo.save(new Job("Essayist", true, "Non-fiction, Essays", 1000, true));
    Job J76 = jobRepo.save(new Job("Freelance Writer", true, "Content creation, SEO", 800, true));
    Job J77 = jobRepo.save(new Job("Copywriter", true, "Advertising, Branding", 900, true));
    Job J78 = jobRepo.save(new Job("Literary Agent", false, "Author representation, Contracts", 1500, true));
    Job J79 = jobRepo.save(new Job("Book Designer", true, "Graphic design, Book layouts", 1100, true));
    Job J80 = jobRepo.save(new Job("Publishing Executive", true, "Publishing strategy, Author management", 1600, true));
    Job J81 = jobRepo.save(new Job("High School Teacher", true, "Classroom management, Subject expertise", 1300, true));
    Job J82 = jobRepo.save(new Job("College Professor", true, "Research, Teaching, Academic writing", 1500, true));
    Job J83 = jobRepo.save(new Job("Elementary School Teacher", true, "Early childhood education, Lesson planning", 1200, true));
    Job J84 = jobRepo.save(new Job("School Principal", true, "Leadership, Administration", 1600, true));
    Job J85 = jobRepo.save(new Job("Special Education Teacher", false, "Special needs education, Patience", 1400, true));
    Job J86 = jobRepo.save(new Job("Librarian", true, "Cataloging, Information management", 1100, true));
    Job J87 = jobRepo.save(new Job("Education Coordinator", true, "Curriculum planning, Event organization", 1300, true));
    Job J88 = jobRepo.save(new Job("School Counselor", true, "Student guidance, Mental health", 1200, true));
    Job J89 = jobRepo.save(new Job("Preschool Teacher", true, "Early childhood education, Creativity", 1000, true));
    Job J90 = jobRepo.save(new Job("Admissions Counselor", true, "Student recruitment, Academic advising", 1300, true));
    // ADD JOBS TO USERS
    userService.addJob(Musk, J1);
    userService.addJob(WD, J2);
    userService.addJob(Morgan, J3);
    userService.addJob(Planck, J4);
    userService.addJob(Hubble, J5);
    userService.addJob(Tesla, J6);
    userService.addJob(Einstein, J7);
    userService.addJob(Napoleon, J8);
    userService.addJob(Shapiro, J9);
    userService.addJob(Washington, J10);
    userService.addJob(Fleming, J11);
    userService.addJob(Mendel, J12);
    userService.addJob(Curie, J13);
    userService.addJob(Bohr, J14);
    userService.addJob(Jackie, J15);
    userService.addJob(Marx, J16);
    userService.addJob(Feynman, J17);
    userService.addJob(GrahamBell, J18);
    userService.addJob(Dirac, J19);
    userService.addJob(Watson, J20);
    userService.addJob(DaVinci, J21);
    userService.addJob(Faraday, J22);
    userService.addJob(Newton, J23);
    userService.addJob(Kepler, J24);
    userService.addJob(MuhammedALi, J25);
    userService.addJob(Lovelace, J26);
    userService.addJob(Galilei, J27);
    userService.addJob(Oppenh, J28);
    userService.addJob(Darwin, J29);
    userService.addJob(JLo, J30);
    userService.addJob(Hawking, J31);
    userService.addJob(Farmer, J32);
    userService.addJob(ASmith, J33);
    userService.addJob(WS, J34);
    userService.addJob(Godel, J35);
    userService.addJob(WSJr, J36);
    userService.addJob(Rutherford, J37);
    userService.addJob(Messi, J38);
    userService.addJob(Euler, J39);
    userService.addJob(Feynman, J40);
    userService.addJob(Franklin, J41);
    userService.addJob(Fermi, J42);
    userService.addJob(WGary, J43);
    userService.addJob(Nietzsche, J44);
    userService.addJob(Copernicus, J45);
    userService.addJob(Planck, J46);
    userService.addJob(Hippocrates, J47);
    userService.addJob(Heisenberg, J48);
    userService.addJob(MorganF, J49);
    userService.addJob(Darwin, J50);
    userService.addJob(Babbage, J51);
    userService.addJob(Faraday, J52);
    userService.addJob(Woodrow, J53);
    userService.addJob(WSJr, J54);
    userService.addJob(Crick, J55);
    userService.addJob(JLo, J56);
    userService.addJob(Napoleon, J57);
    userService.addJob(Jackie, J58);
    userService.addJob(Fleming, J59);
    userService.addJob(Kepler, J60);
    userService.addJob(Einstein, J61);
    userService.addJob(GrahamBell, J62);
    userService.addJob(Lincoln, J63);
    userService.addJob(Turing, J64);
    userService.addJob(Hubble, J65);
    userService.addJob(Euler, J66);
    userService.addJob(Dirac, J67);
    userService.addJob(Musk, J68);
    userService.addJob(Feynman, J69);
    userService.addJob(Oppenh, J70);
    userService.addJob(WD, J71);
    userService.addJob(Watson, J72);
    userService.addJob(Heisenberg, J73);
    userService.addJob(Turing, J74);
    userService.addJob(Jackie, J75);
    userService.addJob(MorganF, J76);
    userService.addJob(Farmer, J77);
    userService.addJob(MuhammedALi, J78);
    userService.addJob(Planck, J79);
    userService.addJob(Bohr, J80);
    userService.addJob(DaVinci, J81);
    userService.addJob(Galilei, J82);
    userService.addJob(Shapiro, J83);
    userService.addJob(Franklin, J84);
    userService.addJob(Marx, J85);
    userService.addJob(WS, J86);
    userService.addJob(Hawking, J87);
    userService.addJob(Fermi, J88);
    userService.addJob(Feynman, J89);
    userService.addJob(Curie, J90);
    
    

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////  JOB APPLICATIONS  //////////////////////////////////////////////////////
    
    // CREATE JOB APPLICATIONS
    JobApplication jobApp1 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp2 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp3 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp4 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp5 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp6 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp7 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp8 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp9 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp10 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp11 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp12 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp13 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp14 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp15 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp16 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp17 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp18 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp19 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp20 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp21 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp22 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp23 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp24 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp25 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp26 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp27 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp28 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp29 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp30 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp31 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp32 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp33 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp34 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp35 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp36 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp37 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp38 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp39 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp40 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp41 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp42 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp43 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp44 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp45 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp46 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp47 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp48 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp49 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp50 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp51 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp52 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp53 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp54 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp55 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp56 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp57 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp58 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp59 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp60 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp61 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp62 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp63 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp64 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp65 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp66 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp67 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp68 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp69 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp70 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp71 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp72 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp73 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp74 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp75 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp76 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp77 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp78 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp79 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp80 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp81 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp82 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp83 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp84 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp85 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp86 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp87 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp88 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp89 = jobApplicationRepo.save(new JobApplication());
    JobApplication jobApp90 = jobApplicationRepo.save(new JobApplication());

    // ADD JOB APPLICATIONS ON JOBS/USERS
    userService.addJobApplication(J1, Jackie, jobApp1);
    userService.addJobApplication(J2, JLo, jobApp2);
    userService.addJobApplication(J3, WD, jobApp3);
    userService.addJobApplication(J4, WS, jobApp4);
    userService.addJobApplication(J5, WSJr, jobApp5);
    userService.addJobApplication(J6, MorganF, jobApp6);
    userService.addJobApplication(J7, Messi, jobApp7);
    userService.addJobApplication(J8, MuhammedALi, jobApp8);
    userService.addJobApplication(J9, Washington, jobApp9);
    userService.addJobApplication(J10, Napoleon, jobApp10);
    userService.addJobApplication(J11, GrahamBell, jobApp11);
    userService.addJobApplication(J12, Einstein, jobApp12);
    userService.addJobApplication(J13, Newton, jobApp13);
    userService.addJobApplication(J14, Curie, jobApp14);
    userService.addJobApplication(J15, Tesla, jobApp15);
    userService.addJobApplication(J16, Galilei, jobApp16);
    userService.addJobApplication(J17, DaVinci, jobApp17);
    userService.addJobApplication(J18, Darwin, jobApp18);
    userService.addJobApplication(J19, Hawking, jobApp19);
    userService.addJobApplication(J20, Turing, jobApp20);
    userService.addJobApplication(J21, Lovelace, jobApp21);
    userService.addJobApplication(J22, Franklin, jobApp22);
    userService.addJobApplication(J23, Pasteur, jobApp23);
    userService.addJobApplication(J24, Mendel, jobApp24);
    userService.addJobApplication(J25, Fleming, jobApp25);
    userService.addJobApplication(J26, Copernicus, jobApp26);
    userService.addJobApplication(J27, Kepler, jobApp27);
    userService.addJobApplication(J28, Babbage, jobApp28);
    userService.addJobApplication(J29, Bohr, jobApp29);
    userService.addJobApplication(J30, Planck, jobApp30);
    userService.addJobApplication(J31, Fermi, jobApp31);
    userService.addJobApplication(J32, Oppenh, jobApp32);
    userService.addJobApplication(J33, Heisenberg, jobApp33);
    userService.addJobApplication(J34, Euler, jobApp34);
    userService.addJobApplication(J35, Rutherford, jobApp35);
    userService.addJobApplication(J36, Feynman, jobApp36);
    userService.addJobApplication(J37, Faraday, jobApp37);
    userService.addJobApplication(J38, Dirac, jobApp38);
    userService.addJobApplication(J39, Hubble, jobApp39);
    userService.addJobApplication(J40, Watson, jobApp40);
    userService.addJobApplication(J41, Crick, jobApp41);
    userService.addJobApplication(J42, Godel, jobApp42);
    userService.addJobApplication(J43, Nietzsche, jobApp43);
    userService.addJobApplication(J44, Farmer, jobApp44);
    userService.addJobApplication(J45, Lincoln, jobApp45);
    userService.addJobApplication(J46, Shapiro, jobApp46);
    userService.addJobApplication(J47, Woodrow, jobApp47);
    userService.addJobApplication(J48, WGary, jobApp48);
    userService.addJobApplication(J49, ASmith, jobApp49);
    userService.addJobApplication(J50, Marx, jobApp50);
    userService.addJobApplication(J51, Morgan, jobApp51);
    userService.addJobApplication(J52, Musk, jobApp52);
    userService.addJobApplication(J53, Hippocrates, jobApp53);
    userService.addJobApplication(J54, Papanikolaou, jobApp54);
    userService.addJobApplication(J55, Jackie, jobApp55);
    userService.addJobApplication(J56, JLo, jobApp56);
    userService.addJobApplication(J57, WD, jobApp57);
    userService.addJobApplication(J58, WS, jobApp58);
    userService.addJobApplication(J59, WSJr, jobApp59);
    userService.addJobApplication(J60, MorganF, jobApp60);
    userService.addJobApplication(J61, Messi, jobApp61);
    userService.addJobApplication(J62, MuhammedALi, jobApp62);
    userService.addJobApplication(J63, Washington, jobApp63);
    userService.addJobApplication(J64, Napoleon, jobApp64);
    userService.addJobApplication(J65, GrahamBell, jobApp65);
    userService.addJobApplication(J66, Einstein, jobApp66);
    userService.addJobApplication(J67, Newton, jobApp67);
    userService.addJobApplication(J68, Curie, jobApp68);
    userService.addJobApplication(J69, Tesla, jobApp69);
    userService.addJobApplication(J70, Galilei, jobApp70);
    userService.addJobApplication(J71, DaVinci, jobApp71);
    userService.addJobApplication(J72, Darwin, jobApp72);
    userService.addJobApplication(J73, Hawking, jobApp73);
    userService.addJobApplication(J74, Turing, jobApp74);
    userService.addJobApplication(J75, Lovelace, jobApp75);
    userService.addJobApplication(J76, Franklin, jobApp76);
    userService.addJobApplication(J77, Pasteur, jobApp77);
    userService.addJobApplication(J78, Mendel, jobApp78);
    userService.addJobApplication(J79, Fleming, jobApp79);
    userService.addJobApplication(J80, Copernicus, jobApp80);
    userService.addJobApplication(J81, Kepler, jobApp81);
    userService.addJobApplication(J82, Babbage, jobApp82);
    userService.addJobApplication(J83, Bohr, jobApp83);
    userService.addJobApplication(J84, Planck, jobApp84);
    userService.addJobApplication(J85, Fermi, jobApp85);
    userService.addJobApplication(J86, Oppenh, jobApp86);
    userService.addJobApplication(J87, Heisenberg, jobApp87);
    userService.addJobApplication(J88, Euler, jobApp88);
    userService.addJobApplication(J89, Rutherford, jobApp89);
    userService.addJobApplication(J90, Feynman, jobApp90);
    



    // SAVE USERS (1 TIME TO AVOID DUBLICATES ON LIKES, JOB APPLICATIONS ETC)
    userRepo.save(Jackie);
    userRepo.save(JLo);
    userRepo.save(WD);
    userRepo.save(WS);
    userRepo.save(WSJr);
    userRepo.save(MorganF);
    userRepo.save(Messi);
    userRepo.save(MuhammedALi);
    userRepo.save(Washington);
    userRepo.save(Napoleon);
    userRepo.save(GrahamBell);
    userRepo.save(Einstein);
    userRepo.save(Newton);
    userRepo.save(Curie);
    userRepo.save(Tesla);
    userRepo.save(Galilei);
    userRepo.save(DaVinci);
    userRepo.save(Darwin);
    userRepo.save(Hawking);
    userRepo.save(Turing);
    userRepo.save(Lovelace);
    userRepo.save(Franklin);
    userRepo.save(Pasteur);
    userRepo.save(Mendel);
    userRepo.save(Fleming);
    userRepo.save(Copernicus);
    userRepo.save(Kepler);
    userRepo.save(Babbage);
    userRepo.save(Bohr);
    userRepo.save(Planck);
    userRepo.save(Fermi);
    userRepo.save(Oppenh);
    userRepo.save(Heisenberg);
    userRepo.save(Euler);
    userRepo.save(Rutherford);
    userRepo.save(Feynman);
    userRepo.save(Faraday);
    userRepo.save(Dirac);
    userRepo.save(Hubble);
    userRepo.save(Watson);
    userRepo.save(Crick);
    userRepo.save(Godel);
    userRepo.save(Nietzsche);
    userRepo.save(Farmer);
    userRepo.save(Lincoln);
    userRepo.save(Shapiro);
    userRepo.save(Woodrow);
    userRepo.save(WGary);
    userRepo.save(ASmith);
    userRepo.save(Marx);
    userRepo.save(Morgan);
    userRepo.save(Musk);
    userRepo.save(Hippocrates);
    userRepo.save(Papanikolaou);
  }


}