
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Insira qual usuário você deseja pesquisar: ");
        var user = scanner.nextLine();

        String url = "https://api.github.com/users/" + user;

         try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();
            System.out.println(json);

            Gson gson = new Gson();

            Profile gitProfile = gson.fromJson(json, Profile.class);

            System.out.println(gitProfile);

           if (gitProfile.message().equals("Not Found")){
                throw new ErroConsultaGitHubException ("Usuário não encontrado");
           }

         } catch (ErroConsultaGitHubException e){
             System.out.println(e.getMessage());
         } finally {
             System.out.println("Programa finalizado!");
         }
    }
}