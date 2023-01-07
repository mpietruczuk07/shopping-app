package pl.edu.pb.shoppingapp;

import pl.edu.pb.shoppingapp.model.GooglePlaceModel;

public interface NearLocationInterface {
    void onSaveClick(GooglePlaceModel googlePlaceModel);
    void onDirectionClick(GooglePlaceModel googlePlaceModel);
}
