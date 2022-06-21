package observables;

import model.UserInfo;

public interface UserInfoObserver {
    // EFFECTS: runs this whenever user observable notifies observers.
    public void update(UserInfo userInfo);
}
