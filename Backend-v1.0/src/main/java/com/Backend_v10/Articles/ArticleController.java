package com.Backend_v10.Articles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import javax.management.Query;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Backend_v10.Comments.Comment;
import com.Backend_v10.Comments.CommentRepository;
import com.Backend_v10.User.User;
import com.Backend_v10.User.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;



@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/article")
public class ArticleController {
    private final ArticleRepository repository;
    private final UserRepository  UserRepo;
    private final CommentRepository CommRepo;

    @Value("${file.upload-dir}")
    private String uploadDir;

    ArticleController(ArticleRepository repository, UserRepository URepo, CommentRepository commRepo){
        this.repository = repository;
        this.UserRepo = URepo;
        this.CommRepo = commRepo;
    }



    @Transactional
    @DeleteMapping("/delete/{id}")
    public void deleteArticle(@PathVariable Long id){
        Optional<Article> found_article = this.repository.findById(id);
        
        for(Comment comm: found_article.get().getArticleComments()){
            this.CommRepo.delete(comm);
        }
        //First we delete all article's comments
        found_article.get().getArticleComments().clear();

        //Then we delete all likes 
        this.repository.DeleteLikes(id);

        
        // if(found_article.isEmpty()){
        //     System.out.println("Artile Not Found");
        // }
        // else{
         this.repository.deleteById(id);
        // System.out.println("Article Found in DB");    }
    }




    @GetMapping("{id}/comments")
    public List<Comment> GetArticleComments(@PathVariable String id) {
        Optional<Article> a = this.repository.findById(Long.parseLong(id));
        //unwrap Optional with .get
        List<Comment>  Comments = a.get().getArticleComments();
        return Comments;
    }

    @GetMapping("{id}/likes")
    public List<User> GetArticleLikes(@PathVariable String id) {
        Optional<Article> a = this.repository.findById(Long.parseLong(id));
        
        List<User> likedByUsers = a.get().getLikedByUsers();
        return likedByUsers;
    }

    @GetMapping("{id}")
    public ResponseEntity<Article> GetArticle(@PathVariable String id) {
        Optional<Article> a = this.repository.findById(Long.parseLong(id));
        if (a.isPresent()) {
            return ResponseEntity.ok(a.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //---------Article Photo--------------//
        @PostMapping("/{article_id}/uploadPhoto")
    public Boolean UploadPhoto(@RequestParam("image") MultipartFile File, @PathVariable Long article_id){
       try{
        if(File.isEmpty() == true) {
            System.out.println("EMPTY FILE");
            return false;
        }


        Path path = Paths.get(uploadDir);
        // Ensure the directory exists or create it
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        Optional<Article> a = this.repository.findById(article_id);
        // Get the original file name (consider using a unique name to avoid conflicts)
        String originalFileName = a.get().getArticleID() + "ArticlePhoto" + ".jpg";   // ---> IDCover
        // Resolve the file path (directory + file name)
        Path filePath = path.resolve(originalFileName);               
        // Save the file to the local file system
        Files.write(filePath, File.getBytes());
        System.out.println("File uploaded successfully");
        
        //Save path in User Entity 
        a.get().setPhotoUrl(originalFileName);
        this.repository.save(a.get());

       }
       catch(IOException e){
        System.out.println("IOException uploading file");
        return false;
       }

        return true;
    }





    @GetMapping("/{article_id}/downloadPhoto")
    public ResponseEntity<Resource> DownloadProfilePhoto(@PathVariable Long article_id) {
        Optional<Article> a = this.repository.findById(article_id);
        
        try {
            // Build the path to the image
            String originalFileName = a.get().getArticleID() + "ArticlePhoto" + ".jpg";

            Path path = Paths.get(uploadDir).resolve(originalFileName);
            Resource resource = new UrlResource(path.toUri());
    
            if (resource.exists() && resource.isReadable()) {
                // Return the image with appropriate headers
                return ResponseEntity.ok()
                        // .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename="" + resource.getFilename() + """)
                        .body(resource);
            } else {
                throw new RuntimeException("File not found or not readable");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}


