package pl.edu.pb.shoppingapp;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NotificationRepository {
    private final NotificationDao notificationDao;
    private final LiveData<List<Notification>> notifications;

    NotificationRepository(Application application) {
        NotificationDatabase database = NotificationDatabase.getDatabase(application);
        notificationDao = database.notificationDao();
        notifications = notificationDao.findAll();
    }

    LiveData<List<Notification>> findAllNotifications() {
        return notifications;
    }

    void insert (Notification notification) {
        NotificationDatabase.databaseWriteExecutor.execute(() -> notificationDao.insert(notification));
    }

    void update(Notification notification) {
        NotificationDatabase.databaseWriteExecutor.execute(() -> notificationDao.update(notification));
    }

    void delete(Notification notification){
        NotificationDatabase.databaseWriteExecutor.execute(()->notificationDao.delete(notification));
    }
}