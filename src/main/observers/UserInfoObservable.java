package observers;

import model.UserInfo;
import observables.UserInfoObserver;

import java.util.List;

public abstract class UserInfoObservable {
    private List<UserInfoObserver> observers;

    // EFFECTS: notifies observers to update
    public void notifyObservers(UserInfo userInfo) {
        for (UserInfoObserver observer : observers) {
            observer.update(userInfo);
        }
    }

    // EFFECTS: add observer
    public void addObserver(UserInfoObserver observer) {
        observers.add(observer);
    }

    // EFFECTS: remove observer
    public void removeObserver(UserInfoObserver observer) {
        if (observers.contains(observer)) {
            observers.remove(observer);
        }
    }

}
