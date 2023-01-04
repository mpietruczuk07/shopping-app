package pl.edu.pb.shoppingapp;

import java.util.ArrayList;
import java.util.Arrays;

import pl.edu.pb.shoppingapp.Model.PlaceModel;

public interface Constant {
    ArrayList<PlaceModel> places = new ArrayList<>(
            Arrays.asList(new PlaceModel(1, R.drawable.ic_baseline_shopping_bag_24, "Supermarket", "supermarket"),
                    new PlaceModel(2, R.drawable.ic_baseline_shopping_bag_24, "Book store", "book_store"),
                    new PlaceModel(3, R.drawable.ic_baseline_shopping_bag_24, "Clothing store", "clothing_store"),
                    new PlaceModel(4, R.drawable.ic_baseline_shopping_bag_24, "Electronics store", "electronics_store"),
                    new PlaceModel(5, R.drawable.ic_baseline_shopping_bag_24, "Drug store", "drugs_store"),
                    new PlaceModel(6, R.drawable.ic_baseline_shopping_bag_24, "Convenience store", "convenience_store"),
                    new PlaceModel(7, R.drawable.ic_baseline_shopping_bag_24, "Hardware store", "hardware_store")
    ));
}
