package bd.edu.seu.advocaid.interfaces;

import bd.edu.seu.advocaid.model.User;

public interface UserServiceInterface {
    public interface UserDashboardInterface {
        void saveOrUpdateOAuthUser(String email, String name, String picture);
        User loadManualUser(String email);
        void setOnlineStatus(User user);
    }
}
