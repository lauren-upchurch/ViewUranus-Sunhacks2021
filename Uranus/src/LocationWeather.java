import java.io.*;
import org.json.*;
import java.net.*;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Missing error handling other than printing stack trace. Should probably include error handling for different web
 * responses
 */
public class LocationWeather {
    Double lat;
    Double lng;
    String latitude;
    String longitude;
    String location;

    ArrayList<ForecastData> forecasts;

    LocationWeather() {
        lat = null;
        lng = null;
        latitude = null;
        longitude = null;
        location = null;
        forecasts = null;
    }

    /**
     * This should probably be named something else....
     * @param inputLocation
     */
    LocationWeather(String inputLocation){
        this.location = inputLocation;

        try {
            String json = fetchURL("https://open.mapquestapi.com/geocoding/v1/address?key=9YVYmgCgouBGEFHNQJ1ZjQGXunMNgAyA&location=" + location);
            JSONObject jLocationResults = new JSONObject(json);
            JSONArray jLocArr = new JSONArray(jLocationResults.getJSONArray("results"));

            JSONObject results = jLocArr.getJSONObject(0);
            JSONArray arr = results.getJSONArray("locations");
            JSONObject locations = arr.getJSONObject(0);
            JSONObject latlng = locations.getJSONObject("displayLatLng");
            this.lat = latlng.getDouble("lat");
            this.lng = latlng.getDouble("lng");

            locationToWeather(lat.toString(), lng.toString());

            //System.out.println(lat + " " + lng);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public ArrayList<ForecastData> locationToWeather(String lat, String longt) {
        this.latitude = lat;
        this.longitude = longt;
        forecasts = new ArrayList<>();

        try {
            String json = fetchURL("https://api.weather.gov/points/" + lat + "," + longt);

            // Response from weather service
            JSONObject weaData = new JSONObject(json);
            // Gets properties
            JSONObject prop = weaData.getJSONObject("properties");
            // Gets link to weather forecast for the area
            String forecastURL = prop.getString("forecast");

            //System.out.println(json);

            String json2 = fetchURL(forecastURL);
            //System.out.print(json2);

            JSONObject weaFore = new JSONObject(json2);
            JSONObject prop2 = weaFore.getJSONObject("properties");
            JSONArray periods = new JSONArray(prop2.getJSONArray("periods"));

            for (int i = 0; i < periods.length(); i++) {
                JSONObject day = periods.getJSONObject(i);
                String dayName = day.getString("name");

                if (dayName.contains("Night")) {
                    String dayForecast = day.getString("shortForecast");
                    //System.out.println(day.getString("name") + ": " + dayForecast + "\n");
                    LocalDateTime dateTime = LocalDateTime.parse(day.getString("startTime").substring(0,19));
                    ForecastData data = new ForecastData(dateTime, dayName, dayForecast);
                    forecasts.add(data);
                }
            }

            for(ForecastData forecastData : forecasts) {
                System.out.println(forecastData.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return forecasts;
    }


    public ArrayList<ForecastData> getForecasts() {
        return forecasts;
    }


    // Pretty much only here so I can run this in my IDE
    public static void main(String args[]){

        // sample data using Austin
        String lat = "30.2672";
        String longi = "-97.7431";
        LocationWeather wea = new LocationWeather("Austin,TX");

    }


    /**
     * Straight out of Dr. Mehlhase's sample code for 321
     * @param aUrl
     * @return
     */
    public static String fetchURL(final String aUrl) {
        final StringBuilder sb = new StringBuilder();
        URLConnection conn = null;
        InputStreamReader in = null;
        try {
            final URL url = new URL(aUrl);
            conn = url.openConnection();
            if (conn != null)
                conn.setReadTimeout(10 * 1000); // timeout in 10 seconds
            if (conn != null && conn.getInputStream() != null) {
                in = new InputStreamReader(conn.getInputStream(), Charset.defaultCharset());
                final BufferedReader br = new BufferedReader(in);
                if (br != null) {
                    int ch;
                    // read the next character until end of reader
                    while ((ch = br.read()) != -1) {
                        sb.append((char) ch);
                    }
                    br.close();
                }
            }
            in.close();
        } catch (final Exception ex) {
            System.out.println("Exception in url request:" + ex.getMessage());
        }
        return sb.toString();
    }

}
