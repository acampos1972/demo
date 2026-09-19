package com.example;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.FileWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.bson.Document;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase; 

/**
 * Aplicacion  de ejemplo que consume el endpoint search/shows de TVMaze y guarda un comentario en MongoDB
 * Autor: Alfredo Campos Hernandez
 * Fecha: 2024-06-20
 *
 */
public class App 
{
    //URL base del endpoint de TVMaze
    protected final String urlAPI = "https://api.tvmaze.com/";

    //MAnda una peticion GET al endpoint especificado y devuelve la respuesta como String
    public String GET(String endpoint) throws IOException {
        HttpURLConnection conexion = abrirConexion(endpoint, "GET");
        return leerRespuesta(conexion);
    }

    //Manda una peticion POST al endpoint especificado con el JSON proporcionado y devuelve la respuesta como String
    public String POST(String endpoint, String json) throws IOException {
        HttpURLConnection conexion = abrirConexion(endpoint, "POST");

        try (OutputStream salida = conexion.getOutputStream()) {
            salida.write(json.getBytes(StandardCharsets.UTF_8));
        }

        return leerRespuesta(conexion);
    }

    //Abere una conexion HTTP al endpoint especificado con el metodo proporcionado (GET o POST)
    private HttpURLConnection abrirConexion(String endpoint, String metodo) throws IOException {
        URL url = new URL(urlAPI + endpoint);
        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
        conexion.setRequestMethod(metodo);
        conexion.setRequestProperty("Accept", "application/json");

        if ("POST".equals(metodo)) {
            conexion.setDoOutput(true);
            conexion.setRequestProperty("Content-Type", "application/json");
        }

        return conexion;
    }

    //Lee la respuesta de la conexion HTTP y devuelve el contenido como String
    private String leerRespuesta(HttpURLConnection conexion) throws IOException {
        try (BufferedReader lector = new BufferedReader(
                new InputStreamReader(conexion.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder respuesta = new StringBuilder();
            String linea;

            while ((linea = lector.readLine()) != null) {
                respuesta.append(linea);
            }

            return respuesta.toString();
        } finally {
            conexion.disconnect();
        }
    }
    //Metodo para guardar la respuesta en un archivo
    public void saveReponse(String respuesta, String nombreArchivo) throws IOException {
        try (FileWriter fw = new FileWriter(nombreArchivo)) {
            fw.write(respuesta);
        }
    }

    //MEtodo principal de la aplicacion
    public static void main( String[] args ) throws IOException
    {
        App app = new App();

        //---------------------------------------------------
        //Realiza la consuslta al endpoint search/shows de TVMaze con la query "girls"
        String strGET = app.GET("search/shows?q=girls");
        //System.out.println(strGET);
        //app.saveReponse(strGET, "repuesta.json");

        //---------------------------------------------------
        //Convierte la respuesta JSON en un objeto Java usando Gson
        Gson gson = new Gson();
        List<TVSearch> listTV = gson.fromJson( strGET, new TypeToken<List<TVSearch>>() {}.getType() );
        Integer id = listTV.get(0).show.id;
        System.out.println("id = " + id);

        //---------------------------------------------------
        //Realiza la consuslta al endpoint shows/id de TVMaze con el id obtenido
        String str139 = app.GET("shows/"+id);
        app.saveReponse(str139, "repuestaDetalle.json");
        TVSearch.Show show139 = gson.fromJson(str139, TVSearch.Show.class);
        System.out.println("Nombre = " + show139.name);         

        //---------------------------------------------------
        //Inicia la conexión a la base de datos MongoDB
        String connectionString = "mongodb+srv://acampos1972_db_user:XentosdwGyQYK3wN@cluster0.wlfrrks.mongodb.net/?appName=Cluster0";
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();

        // Abre la conexión a MongoDB y realiza un ping para confirmar la conexión exitosa
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                // Inicializa la base de datos y la colección, manda un ping y luego inserta un documento de ejemplo
                MongoDatabase database = mongoClient.getDatabase("ratings");//admin
                database.runCommand(new Document("ping", 1));
                System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
               //Obtiene la colección "reviews
                MongoCollection<Document> reviews = database.getCollection("reviews");
                //Crea un documento de ejemplo y lo inserta en la colección "reviews"
                Document review = new Document()
                        .append("show_id", id.toString())
                        .append("comment", show139.name + " es un gran show!")
                        .append("rating", 5);
                reviews.insertOne(review);
            } catch (MongoException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println( "Todo funciona correctamente!" );
    }
}
