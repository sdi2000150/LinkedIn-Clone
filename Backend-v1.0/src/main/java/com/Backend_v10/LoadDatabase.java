package com.Backend_v10;
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
        User admin1 = new User("teomor", "Theodoros", this.encoder.encode("12345"), "ROLE_ADMIN", "Moraitis", "teomor@email.com");
        User admin2 = new User("nickmosch", "Nikitas", this.encoder.encode("12345"), "ROLE_ADMIN", "Moschos", "nickmosch@email.com");
        User user1 = new User("bobross", "Bob", this.encoder.encode("12345"), "ROLE_USER", "Ross", "bobross@email.com");
        User user2 = new User("jetlee", "Jet", this.encoder.encode("12345"), "ROLE_USER", "Lee", "jetlee@email.com");
        User user3 = new User("TC", "Thomas", this.encoder.encode("12345"), "ROLE_USER", "Charles", "thomasch@email.com");
        User user4 = new User("TC", "Taylor", this.encoder.encode("12345"), "ROLE_USER", "Carlson", "taylorcar@email.com");
        User user5 = new User("marial", "Maria", this.encoder.encode("12345"), "ROLE_USER", "Lazarou", "marial@email.com");
        User user6 = new User("ketip", "Keti", this.encoder.encode("12345"), "ROLE_USER", "Perry", "ketip@email.com");
        // Save users to the repository
        userRepo.save(admin1);
        userRepo.save(admin2);
        userRepo.save(user1);
        userRepo.save(user2);
        userRepo.save(user3);
        userRepo.save(user4);
        userRepo.save(user5);
        userRepo.save(user6);
        
        // [SOLVED] PROBLEM occurs, with user who owns the article/job (JSON infinite creation) 
        // -> SOLUTION: @JsonManagedReference/@JsonBackReference in all involved entities 
                    //  OR @JsonIgnoreProperties in all involved entities

        // Create articles and jobs
        Article article1 = new Article("Just got my First Job!!", null);
        // Add a delay of 1 second (1000 milliseconds)
        Thread.sleep(1000);
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
        // userService.addContact(user1, user2); //for now jetlee is not a contact of bobross
        userService.addContact(user1, user3); // thomasch is a contact of bobross
        userService.addContact(user1, user4); // taylorcar is a contact of bobross

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
        // user3.likeArticle(article2);
        // user4.likeArticle(article1);
        user5.likeArticle(article1);

        // Save the updated users
        userRepo.save(user1);
        userRepo.save(user2);
        // userRepo.save(user3);
        // userRepo.save(user4);
        userRepo.save(user5);
        // Maybe dont need the below: (yes, not needed, maybe autosaved by the manytomany(mappedBy = "likedArticles")->(of user))
        // articleRepo.save(article1);
        // articleRepo.save(article2);

        // Other tests:

        //userRepo.save(user2);
        //UserConnRepo.delete(conn);
        System.out.println("check1");
        System.out.println(admin1.getLikedArticles().size());
        System.out.println("check2");

        List<String> Res = UserConnRepo.findUsersRequestingMe("jetlee@email.com");
        System.out.println(Res);

        //ADDING DATA (MOSTLY FOR THE RECOMMENDATION SYSTEM)
        CreateData(userService, UserConnRepo, userRepo, articleRepo, commentRepo, jobRepo, jobApplicationRepo, encoder);
        //Testing Recommendation System
        RecommendationSystem R = new RecommendationSystem(5, 10);
        R.UpdateArticleRecommendationMatrix(userRepo.findAll(), articleRepo.findAll());


        //Testing Results 

        List<Long> res = R.RecommendArticles(user6);
        System.out.println("RESULTS FOR USER:  " + res.toString());

      };
  }

    public void CreateData(UserService userService, UserConnectionRepository UserConnRepo, UserRepository userRepo, ArticleRepository articleRepo, CommentRepository commentRepo, JobRepository jobRepo, JobApplicationRepository jobApplicationRepo, PasswordEncoder encoder){
      userRepo.save(new User("MJackson", "Michael", this.encoder.encode("12345"), "ROLE_USER", "Jackson", "mjson@email.com"));
      userRepo.save(new User("JoDepp", "Johnny", this.encoder.encode("12345"), "ROLE_USER", "Depp", "deppj@email.com"));
      userRepo.save(new User("RW", "Rowan", this.encoder.encode("12345"), "ROLE_USER", "AKtinson", "RowanA@email.com"));
      userRepo.save(new User("Jackie", "Jackie", this.encoder.encode("12345"), "ROLE_USER", "Chan", "Jchan@email.com"));
      userRepo.save(new User("JLo", "Jennifer", this.encoder.encode("12345"), "ROLE_USER", "Lopez", "JLo78@email.com"));
      userRepo.save(new User("WD", "Walt", this.encoder.encode("12345"), "ROLE_USER", "Disney", "Disney@email.com"));
      userRepo.save(new User("WS", "Will", this.encoder.encode("12345"), "ROLE_USER", "Smith", "WillS@email.com"));
      userRepo.save(new User("WS", "Will", this.encoder.encode("12345"), "ROLE_USER", "Smith Jr", "WillSmithJR@email.com"));
      userRepo.save(new User("MorganF", "Morgan", this.encoder.encode("12345"), "ROLE_USER", "Freeman", "Freeman@email.com"));
      userRepo.save(new User("Messi", "Lionel", this.encoder.encode("12345"), "ROLE_USER", "Messi", "LMessi10@email.com"));
      userRepo.save(new User("MuhammedALi", "Casius", this.encoder.encode("12345"), "ROLE_USER", "Clay","Clay@email.com"));
      userRepo.save(new User("Washington", "George", this.encoder.encode("12345"), "ROLE_USER", "Washington","GeorgeW@email.com"));
      userRepo.save(new User("Napoleon The Emperor", "Napoleon", this.encoder.encode("12345"), "ROLE_USER", "Bonaparte","NapoleonEmperor@email.com"));
      userRepo.save(new User("GrahamBell", "Alexander", this.encoder.encode("12345"), "ROLE_USER", "Graham Bell","AlexBell@email.com"));
      userRepo.save(new User("GrahamBell", "Alexander", this.encoder.encode("12345"), "ROLE_USER", "Graham Bell","AlexBell@email.com"));
      userRepo.save(new User("Einstein", "Albert", this.encoder.encode("12345"), "ROLE_USER", "Albert Einstein", "einstein@email.com"));
      userRepo.save(new User("Newton", "Isaac", this.encoder.encode("12345"), "ROLE_USER", "Isaac Newton", "newton@email.com"));
      userRepo.save(new User("Curie", "Marie", this.encoder.encode("12345"), "ROLE_USER", "Marie Curie", "curie@email.com"));
      userRepo.save(new User("Tesla", "Nikola", this.encoder.encode("12345"), "ROLE_USER", "Nikola Tesla", "tesla@email.com"));
      userRepo.save(new User("Galilei", "Galileo", this.encoder.encode("12345"), "ROLE_USER", "Galileo Galilei", "galilei@email.com"));
      userRepo.save(new User("DaVinci", "Leonardo", this.encoder.encode("12345"), "ROLE_USER", "Leonardo Da Vinci", "davinci@email.com"));
      userRepo.save(new User("Darwin", "Charles", this.encoder.encode("12345"), "ROLE_USER", "Charles Darwin", "darwin@email.com"));
      userRepo.save(new User("Hawking", "Stephen", this.encoder.encode("12345"), "ROLE_USER", "Stephen Hawking", "hawking@email.com"));
      userRepo.save(new User("Turing", "Alan", this.encoder.encode("12345"), "ROLE_USER", "Alan Turing", "turing@email.com"));
      userRepo.save(new User("Lovelace", "Ada", this.encoder.encode("12345"), "ROLE_USER", "Ada Lovelace", "lovelace@email.com"));
      userRepo.save(new User("Franklin", "Rosalind", this.encoder.encode("12345"), "ROLE_USER", "Rosalind Franklin", "franklin@email.com"));
      userRepo.save(new User("Pasteur", "Louis", this.encoder.encode("12345"), "ROLE_USER", "Louis Pasteur", "pasteur@email.com"));
      userRepo.save(new User("Mendel", "Gregor", this.encoder.encode("12345"), "ROLE_USER", "Gregor Mendel", "mendel@email.com"));
      userRepo.save(new User("Fleming", "Alexander", this.encoder.encode("12345"), "ROLE_USER", "Alexander Fleming", "fleming@email.com"));
      userRepo.save(new User("Copernicus", "Nicolaus", this.encoder.encode("12345"), "ROLE_USER", "Nicolaus Copernicus", "copernicus@email.com"));
      userRepo.save(new User("Kepler", "Johannes", this.encoder.encode("12345"), "ROLE_USER", "Johannes Kepler", "kepler@email.com"));
      userRepo.save(new User("Babbage", "Charles", this.encoder.encode("12345"), "ROLE_USER", "Charles Babbage", "babbage@email.com"));
      userRepo.save(new User("Bohr", "Niels", this.encoder.encode("12345"), "ROLE_USER", "Niels Bohr", "bohr@email.com"));
      userRepo.save(new User("Planck", "Max", this.encoder.encode("12345"), "ROLE_USER", "Max Planck", "planck@email.com"));
      userRepo.save(new User("Fermi", "Enrico", this.encoder.encode("12345"), "ROLE_USER", "Enrico Fermi", "fermi@email.com"));
      userRepo.save(new User("Oppenheimer", "Robert", this.encoder.encode("12345"), "ROLE_USER", "Robert Oppenheimer", "oppenheimer@email.com"));
      userRepo.save(new User("Heisenberg", "Werner", this.encoder.encode("12345"), "ROLE_USER", "Werner Heisenberg", "heisenberg@email.com"));
      userRepo.save(new User("Euler", "Leonhard", this.encoder.encode("12345"), "ROLE_USER", "Leonhard Euler", "euler@email.com"));
      userRepo.save(new User("Rutherford", "Ernest", this.encoder.encode("12345"), "ROLE_USER", "Ernest Rutherford", "rutherford@email.com"));
      userRepo.save(new User("Feynman", "Richard", this.encoder.encode("12345"), "ROLE_USER", "Richard Feynman", "feynman@email.com"));
      userRepo.save(new User("Godel", "Kurt", this.encoder.encode("12345"), "ROLE_USER", "Kurt Godel", "godel@email.com"));
      userRepo.save(new User("Faraday", "Michael", this.encoder.encode("12345"), "ROLE_USER", "Michael Faraday", "faraday@email.com"));
      userRepo.save(new User("Dirac", "Paul", this.encoder.encode("12345"), "ROLE_USER", "Paul Dirac", "dirac@email.com"));
      userRepo.save(new User("Hubble", "Edwin", this.encoder.encode("12345"), "ROLE_USER", "Edwin Hubble", "hubble@email.com"));
      userRepo.save(new User("Watson", "James", this.encoder.encode("12345"), "ROLE_USER", "James Watson", "watson@email.com"));
      userRepo.save(new User("Crick", "Francis", this.encoder.encode("12345"), "ROLE_USER", "Francis Crick", "crick@email.com"));
      
      //Create Articles


      //Agriculture
      articleRepo.save(new Article("Find the Amazing Opportities of Thessaly. Follow us in this link https://www.uth.gr/en/jobs!!", null));
      articleRepo.save(new Article("Revolutionize agriculture with modern technology. Get involved at https://agritech.com!!", null));
      articleRepo.save(new Article("Breaking: New Sustainable Farming Techniques Show Promising Results for Increased Crop Yields. Read more at https://sustainablefarmingnews.com!!", null));
      articleRepo.save(new Article("Latest Research: The Impact of Climate Change on Global Agriculture Trends. Learn more at https://climateagriculture.com!!", null));
      articleRepo.save(new Article("Join us for the National Agricultural Expo 2024, showcasing the latest innovations in farming technology. More info at https://agriculturalexpo.com!!", null));
      articleRepo.save(new Article("Emergency Alert: Severe Drought Conditions Affecting Major Farming Regions. Find updates and support at https://droughtresponse.com!!", null));
      articleRepo.save(new Article("Promote Your Organic Products! Join our marketplace and reach eco-conscious consumers. More details at https://organicmarketplace.com!!", null));
      articleRepo.save(new Article("Get 20% off on all agricultural supplies this month! Visit us at https://agrisuppliespromo.com!!", null));
      articleRepo.save(new Article("Upcoming Symposium: Innovations in Precision Agriculture – How Technology is Changing the Face of Farming. Register at https://precisionagriculturesymposium.com!!", null));
      articleRepo.save(new Article("Attend the Global Symposium on Sustainable Agriculture Practices to address food security challenges. Details at https://sustainableagriculturesymposium.com!!", null));

      //Technology
      articleRepo.save(new Article("Discover the future of AI in healthcare. Join us at this link https://aihealthcare.com/learn more!!", null));
      articleRepo.save(new Article("Explore the world of quantum computing. Visit us at https://quantumworld.com to stay ahead!!", null));
      articleRepo.save(new Article("The future of autonomous driving is here! Discover more at https://autonomousdrive.com!!", null));
      articleRepo.save(new Article("Discover the breakthrough in nanotechnology. Explore the possibilities at https://nanotechfuture.com!!", null));
      articleRepo.save(new Article("Step into the world of robotics and automation. Begin your journey at https://roboticsrevolution.com!!", null));
      articleRepo.save(new Article("Step into the world of game development. Begin your journey at https://gamedevworld.com!!", null));
      articleRepo.save(new Article("Explore cutting-edge advancements in cybersecurity. Stay protected at https://cybersecure.com!!", null));
      articleRepo.save(new Article("Learn the secrets of machine learning and artificial intelligence at https://mlaiacademy.com!!", null));
      articleRepo.save(new Article("Become a leader in data science. Start today at https://datascienceacademy.com!!", null));
      articleRepo.save(new Article("Explore 3D printing technology and its future applications at https://3dprintrevolution.com!!", null));
      articleRepo.save(new Article("Discover the power of cloud computing for businesses at https://cloudfuture.com!!", null));
      articleRepo.save(new Article("Build a career in UX/UI design. Learn from the best at https://uxdesignpro.com!!", null));
      articleRepo.save(new Article("Discover the power of virtual reality in entertainment and beyond. Learn more at https://vrworld.com!!", null));
      
      //Architecture
      articleRepo.save(new Article("Unlock the secrets of sustainable architecture. Dive into green building at https://greenbuild.com!!", null));
      articleRepo.save(new Article("Explore the role of sustainable materials in modern architecture at https://sustainablearchmaterials.com!!", null));
      articleRepo.save(new Article("Discover cutting-edge advancements in 3D printing technology for architecture. Read more at https://3dprintingarchitecture.com!!", null));
      articleRepo.save(new Article("Learn how biomimicry is shaping the future of building designs at https://biomimicryarchitecture.com!!", null));
      articleRepo.save(new Article("Investigating the impact of AI in urban planning and smart cities. Explore the research at https://aiurbanplanning.com!!", null));
      articleRepo.save(new Article("The science behind energy-efficient buildings: How architecture reduces carbon footprint. Details at https://energyefficientarchitecture.com!!", null));
      articleRepo.save(new Article("Explore how parametric design is revolutionizing modern architecture at https://parametricarchitecture.com!!", null));

      //Finance
      articleRepo.save(new Article("Get the inside scoop on financial technologies and cryptocurrencies at https://fintechrevolution.com!!", null));
      articleRepo.save(new Article("Attend our lecture on financial risk management in a volatile economy. Register now at https://riskmanagementlecture.com!!", null));
      articleRepo.save(new Article("Master corporate finance strategies at our expert-led lecture. Learn more at https://corporatefinancelecture.com!!", null));
      articleRepo.save(new Article("Join us for an exclusive lecture on investment strategies for millennials. Sign up at https://investmentmillennials.com!!", null));
      articleRepo.save(new Article("Breaking: Major Central Banks Collaborate on Digital Currency Initiatives. Read more at https://centralbanksdigital.com!!", null));
      articleRepo.save(new Article("Market Update: Stock Prices Surge Amid Positive Economic Data. Get the latest at https://marketupdate.com!!", null));
      articleRepo.save(new Article("Breaking: New Regulations Proposed for Cryptocurrency Trading Platforms. Learn more at https://cryptoregulationnews.com!!", null));
      articleRepo.save(new Article("Investors React: Tech Stocks Lead Market Gains in Recent Trading Sessions. More details at https://techstocknews.com!!", null));
      articleRepo.save(new Article("Breaking: Global Supply Chain Disruptions Impacting Commodity Prices. Stay updated at https://commoditypricewatch.com!!", null));

      //Literacy
      articleRepo.save(new Article("Enhance your creative writing skills with experts. Learn more at https://creativewriters.com!!", null));
      articleRepo.save(new Article("Discover the transformative power of literacy in 'The Literacy Myth' by Harvey J. Graff. Read more at https://literacymyth.com!!", null));
      articleRepo.save(new Article("Explore Paulo Freire's revolutionary ideas in 'Pedagogy of the Oppressed'. Find insights at https://pedagogyoftheoppressed.com!!", null));
      articleRepo.save(new Article("Unpack the social significance of literacy in 'Literacy in American Lives' by Deborah Brandt. Learn more at https://literacyinamericanlives.com!!", null));
      articleRepo.save(new Article("Delve into the origins of writing with 'Reading the Past: Writing and Image in the Ancient Near East' by C.B.F. Walker. Details at https://readingthepast.com!!", null));
      articleRepo.save(new Article("Inspire a love for reading with 'The Book Whisperer' by Donalyn Miller. Discover tips at https://thebookwhisperer.com!!", null));
      articleRepo.save(new Article("Join us for an exploration of Plato's 'Allegory of the Cave' and its implications for knowledge. More at https://allegoryofthecavelecture.com!!", null));
      articleRepo.save(new Article("Attend a lecture on Aristotle's ethics and the pursuit of the good life. Learn more at https://aristotlesethicslecture.com!!", null));
      articleRepo.save(new Article("Discover the birth of modern philosophy with Descartes in our engaging lecture. Details at https://descartesmodernphilosophy.com!!", null));
      articleRepo.save(new Article("Dive into Jean-Paul Sartre's existentialism and the essence of freedom at https://sartreexistentialismlecture.com!!", null));
      articleRepo.save(new Article("Explore Kant's ethical theories on duty and moral law in our latest lecture. More info at https://kantsmoralphilosophy.com!!", null));
      
      //Engineering
      articleRepo.save(new Article("The wonders of renewable energy await. Discover solar, wind, and hydro at https://renewablepower.com!!", null));
      articleRepo.save(new Article("Explore biotechnology breakthroughs that are changing the world. Learn more at https://biotechleaders.com!!", null));
      articleRepo.save(new Article("Explore environmental science and the impact on climate change at https://climatescience.com!!", null));
      articleRepo.save(new Article("Unlock the potential of space exploration! Join us at https://spacexlore.com for the future of space!!", null));
      articleRepo.save(new Article("Step into the future of renewable construction materials at https://greenconstruction.com!!", null));
      articleRepo.save(new Article("Breaking: New Engineering Breakthrough Promises to Revolutionize Renewable Energy Storage. Read more at https://engineeringnews.com!!", null));
      articleRepo.save(new Article("Latest Developments: Advances in AI-Driven Robotics Changing the Manufacturing Landscape. Discover more at https://roboticsengineering.com!!", null));
      articleRepo.save(new Article("Join us for the International Conference on Sustainable Engineering Practices 2024. More info at https://sustainableengineeringconference.com!!", null));
      articleRepo.save(new Article("Don't miss the Annual Engineering Innovations Expo showcasing cutting-edge technologies. Details at https://engineeringexpo.com!!", null));
      articleRepo.save(new Article("Get 15% off on all Engineering Software Tools for Students! Visit https://engineeringsoftwarepromo.com!!", null));
      articleRepo.save(new Article("Enroll in Our New Online Engineering Certification Courses and Boost Your Career! Learn more at https://onlineengineeringcourses.com!!", null));
      articleRepo.save(new Article("Discover the Top Engineering Trends to Watch in 2024. Explore the article at https://engineeringtrends.com!!", null));
      articleRepo.save(new Article("New Study: The Impact of Emerging Technologies on Civil Engineering Projects. Read the findings at https://civilengineeringstudy.com!!", null));

      //Marketing
      articleRepo.save(new Article("Master the art of digital marketing with us. Dive in at https://digitalmarketingpro.com!!", null));
      articleRepo.save(new Article("Discover how digital transformation is changing industries at https://digitalchange.com!!", null));
      articleRepo.save(new Article("Join the future of e-commerce and online business at https://ecommercesuccess.com!!", null));
      articleRepo.save(new Article("Learn how AI is transforming customer service. Discover more at https://aicustomerservice.com!!", null));
      articleRepo.save(new Article("Breaking: Major Cities Adopt New Strategies to Boost Tourism Post-Pandemic. Read more at https://tourismnews.com!!", null));
      articleRepo.save(new Article("Latest Trends: The Rise of Eco-Tourism and Its Impact on Global Travel. Discover insights at https://ecotourismtrends.com!!", null));
      articleRepo.save(new Article("Join us for the International Tourism Marketing Conference 2024, where industry leaders share innovative strategies. More info at https://tourismconference.com!!", null));
      articleRepo.save(new Article("Don't miss the Travel Expo 2024, showcasing the best travel destinations and packages. Details at https://travelexpo.com!!", null));
      articleRepo.save(new Article("Explore New Destinations! Book your dream vacation now and get 30% off on all packages. Visit https://vacationpromo.com!!", null));
      articleRepo.save(new Article("Limited Time Offer: Free Travel Insurance with Every International Flight Booking! Learn more at https://travelinsurancepromo.com!!", null));
      articleRepo.save(new Article("Discover Effective Marketing Strategies for the Travel Industry in Our Latest Report. Download at https://tourismmarketinginsights.com!!", null));
      articleRepo.save(new Article("New Study Reveals Social Media's Impact on Travel Decisions. Explore the findings at https://socialmediatourism.com!!", null));

     
      //Law
      articleRepo.save(new Article("Artificial Intelligence and the future of law. See more at https://lawtechai.com!!", null));
      articleRepo.save(new Article("Don’t miss our seminar on privacy laws in the age of data breaches. Learn more at https://privacylawseminar.com!!", null));
      articleRepo.save(new Article("Learn about environmental law advancements at our upcoming seminar. More info at https://envlawseminar.com!!", null));
      articleRepo.save(new Article("Attend our legal forensics seminar: The future of criminal investigations. Register at https://forensicslawseminar.com!!", null));
      articleRepo.save(new Article("Upcoming seminar: Blockchain and smart contracts in legal practice. Secure your spot at https://blockchainlawseminar.com!!", null));
      articleRepo.save(new Article("Breaking: Landmark Supreme Court Ruling on Digital Privacy Rights. Read more at https://lawnews.com!!", null));
      articleRepo.save(new Article("Latest Developments: New Legislation on Cybersecurity Compliance Set to Impact Businesses. Discover the details at https://cyberlawnews.com!!", null));
      articleRepo.save(new Article("Join us for the Annual Legal Summit 2024, where leading experts discuss the future of law. More info at https://legalsummit.com!!", null));
      articleRepo.save(new Article("Don't miss the International Conference on Criminal Justice Reform, focusing on innovative legal practices. Details at https://criminaljusticereform.com!!", null));
      articleRepo.save(new Article("Special Offer: Free Consultation for New Clients at Our Law Firm! Schedule your appointment at https://lawfirmconsultation.com!!", null));
      articleRepo.save(new Article("Enroll in our Online Legal Courses and Get 20% Off Your First Course! Learn more at https://onlinelegalcourses.com!!", null));

      //Medicine
      articleRepo.save(new Article("The next wave in biomedical engineering is here. Get involved at https://biomedfuture.com!!", null));
      articleRepo.save(new Article("Get the latest on breakthroughs in medical research at https://medresearchtoday.com!!", null));
      articleRepo.save(new Article("Stem cell therapy: Unlocking regenerative medicine at https://stemcellbreakthroughs.com!!", null));
      articleRepo.save(new Article("Explore the future of robotic surgery. Learn more at https://roboticsurgerytoday.com!!", null));
      articleRepo.save(new Article("Enhance mental health treatments with digital therapeutics at https://mentalhealthtech.com!!", null));
      articleRepo.save(new Article("CRISPR technology and the promise of gene editing in medicine. Discover more at https://crisprmedicine.com!!", null));
      articleRepo.save(new Article("Discover the latest in cardiovascular treatments at https://heartcaretoday.com!!", null));
      articleRepo.save(new Article("Advancements in fertility treatments: The science of hope at https://fertilityscience.com!!", null));
      articleRepo.save(new Article("How 3D printing is revolutionizing prosthetics and implants at https://med3dprint.com!!", null));
      articleRepo.save(new Article("Fight antibiotic resistance with innovative drug discoveries at https://newantibiotics.com!!", null));

      //Education
      articleRepo.save(new Article("Unlock your potential in the world of e-learning technologies at https://elearninginnovations.com!!", null));
      articleRepo.save(new Article("Breaking: New National Curriculum Guidelines Released to Enhance Student Learning Outcomes. Read more at https://educationnews.com!!", null));
      articleRepo.save(new Article("Latest Study: The Impact of Technology on Modern Education Revealed. Discover findings at https://educationtechstudy.com!!", null));
      articleRepo.save(new Article("Join us for the Annual Education Conference 2024, focusing on innovative teaching methods. More info at https://educationconference.com!!", null));
      articleRepo.save(new Article("Don't miss the International Summit on Education Reform, bringing together experts to discuss future trends. Details at https://educationreformsummit.com!!", null));
      articleRepo.save(new Article("Emergency Alert: School Closures Due to Severe Weather Conditions in Several Regions. Find updates at https://schoolclosures.com!!", null));
      articleRepo.save(new Article("Enroll Now! Get 50% off on your first online course at our educational platform. Visit https://onlinelearningpromo.com!!", null));
      articleRepo.save(new Article("Discover our new tutoring services and get a free trial lesson. Learn more at https://tutoringservices.com!!", null));
      articleRepo.save(new Article("Upgrade your study materials! 20% off all educational resources this month only. Shop at https://educationalresourcespromo.com!!", null));
      articleRepo.save(new Article("Upcoming Lecture: 'The Future of Education: Embracing Digital Learning Environments' - Join us for insights. Register at https://digitallearninglecture.com!!", null));
      articleRepo.save(new Article("Attend a lecture on 'The Importance of Emotional Intelligence in Education' to learn about its impact on student success. More info at https://emotionalintelligenceineducation.com!!", null));

    }


}