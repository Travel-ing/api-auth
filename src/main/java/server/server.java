package server;

import service.authService;

import javax.xml.ws.Endpoint;

public class server {
    public static void main(String[] args) {
        String url = "http://localhost:8084/";
        System.out.println("Service Auth");
        Endpoint.publish(url, new authService());
        System.out.println("Déployé sur " + url);
    }
}