package pl.edu.pb.shoppingapp.WebServices;

import pl.edu.pb.shoppingapp.Model.GoogleResponseModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface MapsService {
    @GET
    Call<GoogleResponseModel> getPlaces(@Url String url);
}
