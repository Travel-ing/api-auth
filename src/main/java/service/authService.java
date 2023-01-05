package service;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.Date;
import javax.jws.WebParam;
import io.jsonwebtoken.Jwts;
import com.mongodb.client.MongoClient;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import metier.User;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import org.bson.BsonValue;
import org.mindrot.jbcrypt.BCrypt;
import com.google.gson.Gson;

import java.util.regex.Pattern;

import static com.mongodb.client.model.Filters.*;

@WebService( name = "auth" )
public class authService {
    @WebMethod(operationName = "authentication")
    public String Authentication(@WebParam(name = "Email") String email, @WebParam(name="Password") String password) {
        String uri = "mongodb+srv://admin:rTm2ylmfIqj2Clq8@cluster0.wtkwefc.mongodb.net/?retryWrites=true&w=majority";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("traveling");
            MongoCollection<Document> collection = database.getCollection("user");

            if(findUserByEmail(email) != null) {
                Document currentUser = findUserByEmail(email);
                if(!checkPassword(password, currentUser.get("password").toString())) {
                    return "Mot de passe incorrect";
                } else {
                    return currentUser.toJson();
                }
            } else {
                String p = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
                if(Pattern.matches(p, email)) {
                    String newHash = hashPassword(password);
                    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
                    User user = new User(
                            email,
                            newHash,
                            "user",
                            "",
                            new Date(),
                            new Date()
                    );
                    Gson gson = new Gson();
                    String accessToken = Jwts.builder().setSubject(gson.toJson(user).toString()).signWith(key).compact();
                    user.setAccessToken(accessToken);
                    BsonValue newUser = collection.insertOne(user.userToDocument()).getInsertedId();
                    try {
                        Document userFind = collection.find(eq("_id", newUser)).first();
                        return userFind.toJson();
                    } catch (Exception e) {
                        return "Impossible de vous créez un compte";
                    }
                } else {
                    return "L'email n'est pas du bon format";
                }
            }
        } catch (Exception e) {
            return "Une erreur est survenue";
        }
    }

    public static String hashPassword(String password_plaintext) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(password_plaintext, salt);
    }

    public static boolean checkPassword(String password_plaintext, String stored_hash) {
        if(null == stored_hash || !stored_hash.startsWith("$2a$"))
            throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");
        return BCrypt.checkpw(password_plaintext, stored_hash);
    }

    public static Document findUserByEmail (String email) {
        String uri = "mongodb+srv://admin:rTm2ylmfIqj2Clq8@cluster0.wtkwefc.mongodb.net/?retryWrites=true&w=majority";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("traveling");
            MongoCollection<Document> collection = database.getCollection("user");
            Document userFind = collection.find(eq("email", email)).first();
            return userFind;
        } catch (Exception e) {
            return null;
        }
    }
}
