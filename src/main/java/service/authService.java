package service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebService( name = "auth" )
public class authService {
    @WebMethod(operationName = "getTest")
    public String getTest() {
        return "Salut";
    }
}
