package pl.edu.pb.shoppingapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NotificationViewModel extends AndroidViewModel {
    private final NotificationRepository notificationRepository;
    private final LiveData<List<Notification>>notifications;

    public NotificationViewModel(@NonNull Application application) {
        super(application);
        notificationRepository = new NotificationRepository(application);
        notifications = notificationRepository.findAllNotifications();
    }

    public LiveData<List<Notification>> findAll() {
        return notifications;
    }

    public void insert(Notification notification) {
        notificationRepository.insert(notification);
    }

    public void delete(Notification notification) {
        notificationRepository.delete(notification);
    }

    public void update(Notification notification) {
        notificationRepository.update(notification);
    }
}